package ru.ryatronth.sd.department.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentShortDto", description = "Сокращённая модель подразделения для списков (с раскрытыми связями)")
public class DepartmentShortDto {

  @Schema(description = "ID подразделения", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(description = "Название подразделения", example = "Отдел разработки")
  private String name;

  @Schema(description = "Код подразделения (1:1)")
  private DepartmentCodeDto code;

  @Schema(description = "Область/регион", example = "Москва")
  private String area;

  @Schema(description = "Адрес подразделения", example = "ул. Пушкина, д. 10")
  private String address;

  @Schema(description = "Дата создания", example = "2026-01-08T13:41:31Z")
  private Instant createdAt;

  @Schema(description = "Дата изменения", example = "2026-01-08T13:41:31Z")
  private Instant updatedAt;

}
