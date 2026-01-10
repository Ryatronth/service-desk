package ru.ryatronth.sd.department.dto.type;

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
@Schema(name = "DepartmentTypeCreateRequest", description = "Запрос на создание типа подразделения")
public class DepartmentTypeCreateRequest {

  @Schema(description = "Название типа (уникальное)", example = "Офис", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

}
