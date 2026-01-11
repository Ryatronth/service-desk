package ru.ryatronth.sd.ticket.domain.attachment.snapshot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSnapshot {

  private String originalName;

  private String contentType;

  private Long sizeBytes;

}
