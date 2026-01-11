package ru.ryatronth.sd.ticket.dto.ticket.snapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "CategorySnapshotDto", description = "Снимок категории")
public record CategorySnapshotDto(

    @Schema(description = "UUID категории")
    UUID id,

    @Schema(description = "Название категории", example = "Доступы / ServiceDesk")
    String name
) {
}
