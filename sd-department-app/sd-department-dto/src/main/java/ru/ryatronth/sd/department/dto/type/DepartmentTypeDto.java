package ru.ryatronth.sd.department.dto.type;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
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
@Schema(name = "DepartmentTypeDto", description = "Тип подразделения")
public class DepartmentTypeDto {

  @Schema(description = "ID типа", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Название типа", example = "Офис")
  private String name;

  @Schema(description = "Дата создания", example = "2026-01-08T13:41:31Z")
  private Instant createdAt;

  @Schema(description = "Дата изменения", example = "2026-01-08T13:41:31Z")
  private Instant updatedAt;

}
