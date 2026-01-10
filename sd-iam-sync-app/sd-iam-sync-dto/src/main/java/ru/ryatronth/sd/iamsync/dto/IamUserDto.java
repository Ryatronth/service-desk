package ru.ryatronth.sd.iamsync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;

@Schema(name = "IamUserDto", description = "Пользователь, синхронизированный из Keycloak (IAM)")
public record IamUserDto(
    @Schema(description = "Идентификатор пользователя в системе", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6") UUID id,

    @Schema(description = "Логин/username пользователя", example = "ivanov.i") String username,

    @Schema(description = "Email пользователя", example = "ivanov.i@company.ru") String email,

    @Schema(description = "Телефон пользователя", example = "+79990001122") String phone,

    @Schema(description = "Имя", example = "Иван") String firstName,

    @Schema(description = "Фамилия", example = "Иванов") String lastName,

    @Schema(description = "Отчество", example = "Иванович") String patronymic,

    @Schema(description = "Признак активности пользователя", example = "true") boolean enabled,

    @Schema(description = "Идентификатор подразделения (в домене Directory)", example = "dep-001") String departmentCode,

    @Schema(description = "Идентификатор рабочего места", example = "wp-ny-01") String workplaceCode,

    @Schema(description = "Набор ролей пользователя", example = "[\"ROLE_USER\",\"ROLE_TICKET_EXECUTOR\"]") Set<String> roles) {
}
