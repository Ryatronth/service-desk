package ru.ryatronth.sd.file.service;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.file.config.properties.S3Properties;
import ru.ryatronth.sd.file.domain.FileEntity;
import ru.ryatronth.sd.file.domain.FileRepository;
import ru.ryatronth.sd.file.dto.FileStatus;
import ru.ryatronth.sd.file.exception.FileIsNotReadyException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

  private final FileRepository fileRepository;

  private final S3Properties s3Properties;

  private final S3Client s3Client;

  private final S3Presigner s3Presigner;

  public FileEntity initUpload(String originalName, String contentType, Long sizeBytes) {
    UUID id = UUID.randomUUID();
    String key = s3Properties.getKeyPrefix() + "/" + id + "/" + originalName;

    String presignedPutUrl = generatePresignedPutUrl(key, contentType);

    FileEntity entity = FileEntity.builder()
        .id(id)
        .originalName(originalName)
        .contentType(contentType)
        .sizeBytes(sizeBytes)
        .bucket(s3Properties.getBucket())
        .key(key)
        .url(presignedPutUrl)
        .temporary(true)
        .status(FileStatus.PENDING_UPLOAD)
        .build();

    return fileRepository.save(entity);
  }

  @Transactional
  public FileEntity confirmUpload(UUID id) {
    FileEntity entity = findByIdOrThrow(id);

    boolean exists = objectExists(entity.getBucket(), entity.getKey());
    if (!exists) {
      entity.setStatus(FileStatus.FAILED);
      fileRepository.save(entity);
      throw new NotFoundException("Файл отсутствует в S3");
    }

    entity.setTemporary(false);
    entity.setStatus(FileStatus.READY);

    String presignedGetUrl = generatePresignedGetUrl(entity.getKey());
    entity.setUrl(presignedGetUrl);

    entity = fileRepository.save(entity);
    return entity;
  }

  @Transactional(readOnly = true)
  public FileEntity getFile(UUID id) {
    FileEntity entity = findByIdOrThrow(id);

    if (entity.getStatus() != FileStatus.READY) {
      throw new FileIsNotReadyException("Файл ещё не готов к загрузке");
    }

    if (isUrlExpired(entity)) {
      log.debug("URL устарел — создаём новый для файла {}", id);
      entity.setUrl(generatePresignedGetUrl(entity.getKey()));
      entity = fileRepository.save(entity);
    }

    return entity;
  }

  @Transactional
  public void deleteFile(UUID id) {
    FileEntity entity = findByIdOrThrow(id);

    try {
      s3Client.deleteObject(b -> b.bucket(entity.getBucket()).key(entity.getKey()));
    } catch (S3Exception e) {
      log.warn("Не удалось удалить файл из S3: {}", e.getMessage());
    }

    entity.setStatus(FileStatus.DELETED);
    fileRepository.delete(entity);
  }

  private FileEntity findByIdOrThrow(UUID id) {
    return fileRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Файл не найден: " + id));
  }

  private String generatePresignedPutUrl(String key, String contentType) {
    PutObjectPresignRequest request = PutObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(s3Properties.getUrlExpirationMinutes()))
        .putObjectRequest(p -> p.bucket(s3Properties.getBucket())
            .key(key)
            .contentType(contentType))
        .build();
    URL url = s3Presigner.presignPutObject(request).url();
    return url.toString();
  }

  private String generatePresignedGetUrl(String key) {
    GetObjectPresignRequest request = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(s3Properties.getUrlExpirationMinutes()))
        .getObjectRequest(p -> p.bucket(s3Properties.getBucket()).key(key))
        .build();
    URL url = s3Presigner.presignGetObject(request).url();
    return url.toString();
  }

  private boolean objectExists(String bucket, String key) {
    try {
      s3Client.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
      return true;
    } catch (NoSuchKeyException e) {
      return false;
    }
  }

  private boolean isUrlExpired(FileEntity entity) {
    return entity.getUpdatedAt() == null || entity.getUpdatedAt()
        .isBefore(Instant.now()
            .minus(Duration.ofMinutes(s3Properties.getUrlExpirationMinutes())));
  }

}
