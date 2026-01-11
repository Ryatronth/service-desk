package ru.ryatronth.sd.ticket.adapter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.file.api.FileClientV1;
import ru.ryatronth.sd.file.dto.FileDto;

@Component
@RequiredArgsConstructor
public class FileClientAdapter {

  private final FileClientV1 fileClient;

  public FileDto resolveFileSnapshot(UUID fileId) {
    if (fileId == null) {
      throw new IllegalArgumentException("fileId is null");
    }

    ResponseEntity<FileDto> resp = fileClient.getFile(fileId);
    FileDto file = resp == null ? null : resp.getBody();
    if (file == null) {
      throw new NotFoundException("Файл не найден в file-service: " + fileId);
    }

    return file;
  }

}
