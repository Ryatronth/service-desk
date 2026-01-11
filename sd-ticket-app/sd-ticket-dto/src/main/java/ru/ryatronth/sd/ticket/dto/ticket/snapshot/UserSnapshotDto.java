package ru.ryatronth.sd.ticket.dto.ticket.snapshot;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "UserSnapshotDto", description = "Снимок пользователя на момент действия")
public record UserSnapshotDto(

    @Schema(description = "UUID пользователя")
    UUID id,

    @Schema(description = "Username", example = "roman.novikov")
    String username,

    @Schema(description = "Отображаемое имя", example = "Роман Новиков")
    String displayName,

    @Schema(description = "Email", example = "roman.novikov@company.ru")
    String email
) {
}
