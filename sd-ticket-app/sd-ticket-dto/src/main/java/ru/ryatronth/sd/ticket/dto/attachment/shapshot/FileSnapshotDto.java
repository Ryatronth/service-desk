package ru.ryatronth.sd.ticket.dto.attachment.shapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "FileSnapshotDto", description = "Снимок метаданных файла")
public record FileSnapshotDto(

    @Schema(description = "UUID файла")
    UUID id,

    @Schema(description = "Оригинальное имя файла", example = "photo.jpg")
    String originalName,

    @Schema(description = "Тип контента файла", example = "application/jpeg")
    String contentType,

    @Schema(description = "Размер файла в байтах", example = "12345")
    Long sizeBytes

) {
}
