package ru.ryatronth.sd.ticket.dto.ticket.snapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "DepartmentSnapshotDto", description = "Снимок филиала/подразделения на момент создания заявки")
public record DepartmentSnapshotDto(

    @Schema(description = "UUID подразделения")
    UUID id,

    @Schema(description = "Код", example = "DEP-001")
    String code,

    @Schema(description = "Название", example = "Филиал Москва")
    String name,

    @Schema(description = "Область/регион", example = "Москва")
    String area,

    @Schema(description = "Адрес", example = "ул. Пушкина, д. 1")
    String address
) {
}
