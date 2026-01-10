package ru.ryatronth.sd.department.api.type;

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
import ru.ryatronth.sd.department.api.type.filter.DepartmentTypeFilters;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeCreateRequest;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeDto;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeUpdateRequest;

@Tag(name = "Department Types", description = "CRUD API для типов подразделений (справочник).")
public interface DepartmentTypeApiV1 {

  String BASE_PATH = "/api/v1/department-types";

  @Operation(summary = "Создать тип подразделения", description = "Создаёт тип. name уникален.")
  @ApiResponse(responseCode = "201", description = "Тип создан",
      content = @Content(schema = @Schema(implementation = DepartmentTypeDto.class)))
  @PostMapping
  ResponseEntity<DepartmentTypeDto> create(@RequestBody DepartmentTypeCreateRequest request);

  @Operation(summary = "Получить тип по id")
  @ApiResponse(responseCode = "200", description = "Тип найден",
      content = @Content(schema = @Schema(implementation = DepartmentTypeDto.class)))
  @ApiResponse(responseCode = "404", description = "Тип не найден")
  @GetMapping("/{id}")
  ResponseEntity<DepartmentTypeDto> getById(@PathVariable("id") UUID id);

  @Operation(summary = "Список типов", description = "Постраничный список типов с фильтрами.")
  @ApiResponse(responseCode = "200", description = "Страница типов",
      content = @Content(schema = @Schema(implementation = Page.class)))
  @GetMapping
  ResponseEntity<Page<DepartmentTypeDto>> getAll(@ParameterObject DepartmentTypeFilters filters,
                                                 @ParameterObject Pageable pageable);

  @Operation(summary = "Обновить тип", description = "Переименовывает тип (name).")
  @ApiResponse(responseCode = "200", description = "Тип обновлён",
      content = @Content(schema = @Schema(implementation = DepartmentTypeDto.class)))
  @ApiResponse(responseCode = "404", description = "Тип не найден")
  @PutMapping("/{id}")
  ResponseEntity<DepartmentTypeDto> update(@PathVariable("id") UUID id,
                                           @RequestBody DepartmentTypeUpdateRequest request);

  @Operation(summary = "Удалить тип",
      description = "Удаляет тип. Если тип привязан к подразделениям — БД запретит удаление (FK RESTRICT).")
  @ApiResponse(responseCode = "204", description = "Тип удалён")
  @ApiResponse(responseCode = "404", description = "Тип не найден")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable("id") UUID id);

}
