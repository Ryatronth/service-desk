package ru.ryatronth.sd.file.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.ryatronth.sd.file.api.FileApiV1;
import ru.ryatronth.sd.file.dto.FileDto;
import ru.ryatronth.sd.file.service.S3Service;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController implements FileApiV1 {

    private final S3Service s3Service;

    @Override
    public ResponseEntity<FileDto> initUpload(String originalName, String contentType, Long sizeBytes) {
        FileDto dto = s3Service.initUpload(originalName, contentType, sizeBytes);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Override
    public ResponseEntity<FileDto> confirmUpload(UUID fileId) {
        FileDto dto = s3Service.confirmUpload(fileId);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<FileDto> getFile(UUID fileId) {
        FileDto dto = s3Service.getFile(fileId);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteFile(UUID fileId) {
        s3Service.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

}
