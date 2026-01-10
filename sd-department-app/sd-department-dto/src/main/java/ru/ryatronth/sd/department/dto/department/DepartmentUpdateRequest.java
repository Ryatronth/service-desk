package ru.ryatronth.sd.department.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "DepartmentUpdateRequest", description = "Запрос на обновление подразделения")
public class DepartmentUpdateRequest {

  @Schema(description = "ID родительского подразделения (если есть)", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
  private UUID parentId;

  @Schema(description = "Код подразделения (1:1)", example = "DEP-001", nullable = true)
  private UUID codeId;

  @Schema(description = "ID типа подразделения", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
  private UUID typeId;

  @Schema(description = "Название подразделения", example = "Отдел разработки", nullable = true)
  private String name;

  @Schema(description = "Область/регион", example = "Москва", nullable = true)
  private String area;

  @Schema(description = "Адрес подразделения", example = "ул. Пушкина, д. 10", nullable = true)
  private String address;

}
