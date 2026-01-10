package ru.ryatronth.sd.department.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "DepartmentCodeUpdateRequest", description = "Запрос на обновление кода подразделения")
public class DepartmentCodeUpdateRequest {

  @Schema(description = "Код подразделения (уникальный)", example = "DEP-001", requiredMode = Schema.RequiredMode.REQUIRED)
  private String code;

}
