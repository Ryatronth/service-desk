package ru.ryatronth.sd.department.api.department;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ryatronth.sd.department.api.department.filter.DepartmentFilters;
import ru.ryatronth.sd.department.api.department.filter.DepartmentParentFilters;
import ru.ryatronth.sd.department.dto.department.DepartmentCreateRequest;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;
import ru.ryatronth.sd.department.dto.department.DepartmentUpdateRequest;

@Tag(name = "Departments", description = "CRUD API для подразделений (иерархия + код 1:1 + тип)")
public interface DepartmentApiV1 {

  String BASE_PATH = "/api/v1/departments";

  @Operation(summary = "Создать подразделение", description = "Создаёт подразделение. codeId обязателен (код 1:1). parentId/typeId опциональны.")
  @ApiResponse(responseCode = "201", description = "Подразделение создано", content = @Content(schema = @Schema(implementation = DepartmentDto.class)))
  @PostMapping
  ResponseEntity<DepartmentDto> create(@RequestBody DepartmentCreateRequest request);

  @Operation(summary = "Получить подразделение по id", description = "Возвращает подразделение с раскрытыми связями (parent/code/type).")
  @ApiResponse(responseCode = "200", description = "Подразделение найдено", content = @Content(schema = @Schema(implementation = DepartmentDto.class)))
  @ApiResponse(responseCode = "404", description = "Подразделение не найдено")
  @GetMapping("/{id}")
  ResponseEntity<DepartmentDto> getById(@PathVariable UUID id);

  @Operation(summary = "Получить список подразделений (полная модель)", description = "Постраничный список подразделений с фильтрами. Возвращает раскрытые parent/code/type.")
  @ApiResponse(responseCode = "200", description = "Страница подразделений", content = @Content(schema = @Schema(implementation = Page.class)))
  @GetMapping
  ResponseEntity<Page<DepartmentDto>> getAll(@ParameterObject DepartmentFilters filters,
                                             @ParameterObject Pageable pageable);

  @Operation(summary = "Получить спиок подразделений, подходящих в качестве родительского", description = "Постраничный список подразделений в сокращённом виде (для списков/таблиц).")
  @ApiResponse(responseCode = "200", description = "Страница подразделений", content = @Content(schema = @Schema(implementation = Page.class)))
  @GetMapping("/short/parents")
  ResponseEntity<Page<DepartmentShortDto>> getAllForParent(@ParameterObject DepartmentParentFilters filters,
                                                           @ParameterObject Pageable pageable);

  @Operation(summary = "Получить список подразделений (сокращённая модель)", description = "Постраничный список подразделений в сокращённом виде (для списков/таблиц).")
  @ApiResponse(responseCode = "200", description = "Страница подразделений", content = @Content(schema = @Schema(implementation = Page.class)))
  @GetMapping("/short")
  ResponseEntity<Page<DepartmentShortDto>> getAllShort(@ParameterObject DepartmentFilters filters,
                                                       @ParameterObject Pageable pageable);

  @Operation(summary = "Обновить подразделение", description = "Обновляет подразделение. Можно менять codeId (перепривязка к новому коду), parentId, typeId и поля.")
  @ApiResponse(responseCode = "200", description = "Подразделение обновлено", content = @Content(schema = @Schema(implementation = DepartmentDto.class)))
  @ApiResponse(responseCode = "404", description = "Подразделение не найдено")
  @PutMapping("/{id}")
  ResponseEntity<DepartmentDto> update(@PathVariable UUID id,
                                       @RequestBody DepartmentUpdateRequest request);

  @Operation(summary = "Удалить подразделение", description =
      "Удаляет только подразделение (код/тип не удаляются). " +
          "Удаление может быть запрещено, если есть дочерние подразделения (FK parent_id RESTRICT).")
  @ApiResponse(responseCode = "204", description = "Подразделение удалено")
  @ApiResponse(responseCode = "404", description = "Подразделение не найдено")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);

}
