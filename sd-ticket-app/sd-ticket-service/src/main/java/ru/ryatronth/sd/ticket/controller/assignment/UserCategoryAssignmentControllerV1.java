package ru.ryatronth.sd.ticket.controller.assignment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.ticket.api.assignment.UserCategoryAssignmentApiV1;
import ru.ryatronth.sd.ticket.dto.assignment.UserCategoriesDto;
import ru.ryatronth.sd.ticket.dto.assignment.UserCategoriesUpdateRequest;
import ru.ryatronth.sd.ticket.mapper.assignment.UserCategoriesMapper;
import ru.ryatronth.sd.ticket.service.assignment.UserCategoryAssignmentService;

@RequiredArgsConstructor
@RestController
@RequestMapping(UserCategoryAssignmentApiV1.BASE_PATH)
public class UserCategoryAssignmentControllerV1 implements UserCategoryAssignmentApiV1 {

  private final UserCategoryAssignmentService service;
  private final UserCategoriesMapper mapper;

  @Override
  public ResponseEntity<UserCategoriesDto> getUserCategories(UUID userId, UUID departmentId) {
    return ResponseEntity.ok(
        mapper.toDto(userId, departmentId, service.getUserAssignments(userId, departmentId)));
  }

  @Override
  public ResponseEntity<UserCategoriesDto> updateUserCategories(UUID userId,
                                                                UserCategoriesUpdateRequest request) {
    var saved =
        service.replaceUserCategories(userId, request.departmentId(), request.categoryIds());
    return ResponseEntity.ok(mapper.toDto(userId, request.departmentId(), saved));
  }

  @Override
  public ResponseEntity<Void> deleteUserCategories(UUID userId, UUID departmentId) {
    service.deleteUserCategories(userId, departmentId);
    return ResponseEntity.noContent().build();
  }
}
