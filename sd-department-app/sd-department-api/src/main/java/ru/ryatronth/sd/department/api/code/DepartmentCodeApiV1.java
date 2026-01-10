package ru.ryatronth.sd.department.api.code;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.ryatronth.sd.department.api.code.filter.DepartmentCodeFilters;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;

@Tag(
    name = "Department Codes",
    description = "CRUD API для кодов подразделений (справочник). Код редактируемый, связь с Department по codeId (UUID)."
)
public interface DepartmentCodeApiV1 {

  String BASE_PATH = "/api/v1/department-codes";

//    @Operation(summary = "Создать код подразделения", description = "Создаёт запись кода. Поле code уникально.")
//    @ApiResponse(
//        responseCode = "201",
//        description = "Код создан",
//        content = @Content(schema = @Schema(implementation = DepartmentCodeDto.class))
//    )
//    @PostMapping
//    ResponseEntity<DepartmentCodeDto> create(@RequestBody DepartmentCodeCreateRequest request);

  @Operation(summary = "Получить код по id")
  @ApiResponse(
      responseCode = "200",
      description = "Код найден",
      content = @Content(schema = @Schema(implementation = DepartmentCodeDto.class))
  )
  @ApiResponse(responseCode = "404", description = "Код не найден")
  @GetMapping("/{id}")
  ResponseEntity<DepartmentCodeDto> getById(@PathVariable("id") UUID id);

  @Operation(summary = "Список кодов", description = "Постраничный список кодов с фильтрами.")
  @ApiResponse(
      responseCode = "200",
      description = "Страница кодов",
      content = @Content(schema = @Schema(implementation = Page.class))
  )
  @GetMapping
  ResponseEntity<Page<DepartmentCodeDto>> getAll(
      @ParameterObject DepartmentCodeFilters filters,
      @ParameterObject Pageable pageable);

//    @Operation(summary = "Обновить код", description = "Изменяет строковое поле code. FK не ломается.")
//    @ApiResponse(
//        responseCode = "200",
//        description = "Код обновлён",
//        content = @Content(schema = @Schema(implementation = DepartmentCodeDto.class))
//    )
//    @ApiResponse(responseCode = "404", description = "Код не найден")
//    @PutMapping("/{id}")
//    ResponseEntity<DepartmentCodeDto> update(
//        @PathVariable("id") UUID id,
//        @RequestBody DepartmentCodeUpdateRequest request);

//    @Operation(
//        summary = "Удалить код",
//        description = "Удаляет код. Если код привязан к подразделению — БД запретит удаление (FK RESTRICT)."
//    )
//    @ApiResponse(responseCode = "204", description = "Код удалён")
//    @ApiResponse(responseCode = "404", description = "Код не найден")
//    @DeleteMapping("/{id}")
//    ResponseEntity<Void> delete(@PathVariable("id") UUID id);

}
