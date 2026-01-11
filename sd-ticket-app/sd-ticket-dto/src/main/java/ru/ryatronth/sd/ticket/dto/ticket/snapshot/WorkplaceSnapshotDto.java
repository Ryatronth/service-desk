package ru.ryatronth.sd.ticket.dto.ticket.snapshot;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "WorkplaceSnapshotDto", description = "Снимок рабочего места на момент действия")
public record WorkplaceSnapshotDto(

    @Schema(description = "Код рабочего места")
    String code

) {
}
