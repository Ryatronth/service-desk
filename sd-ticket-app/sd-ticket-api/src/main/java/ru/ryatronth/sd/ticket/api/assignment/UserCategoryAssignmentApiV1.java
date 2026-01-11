package ru.ryatronth.sd.ticket.api.assignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ryatronth.sd.ticket.dto.assignment.UserCategoriesDto;
import ru.ryatronth.sd.ticket.dto.assignment.UserCategoriesUpdateRequest;

@Tag(name = "User Category Assignments", description = "Управление категориями, доступными пользователю как исполнителю (по филиалу department.id)")
public interface UserCategoryAssignmentApiV1 {

  String BASE_PATH = "/api/v1/users";

  @Operation(summary = "Получить категории, назначенные пользователю как исполнителю в филиале")
  @GetMapping("/{userId}/ticket-categories")
  ResponseEntity<UserCategoriesDto> getUserCategories(
      @PathVariable @Parameter(description = "ID пользователя") UUID userId,
      @Parameter(description = "ID филиала (department.id)") @RequestParam("departmentId")
      UUID departmentId);

  @Operation(summary = "Задать категории пользователю как исполнителю в филиале", description = "Полностью заменяет список категорий, на которые пользователь назначен исполнителем в указанном филиале")
  @PutMapping("/{userId}/ticket-categories")
  ResponseEntity<UserCategoriesDto> updateUserCategories(@PathVariable UUID userId, @RequestBody
  UserCategoriesUpdateRequest request);

  @Operation(summary = "Удалить все назначения категорий пользователю в филиале")
  @DeleteMapping("/{userId}/ticket-categories")
  ResponseEntity<Void> deleteUserCategories(@PathVariable UUID userId,
                                            @RequestParam("departmentId") UUID departmentId);
}
