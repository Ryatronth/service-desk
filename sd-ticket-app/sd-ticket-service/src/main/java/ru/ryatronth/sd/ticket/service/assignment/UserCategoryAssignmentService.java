package ru.ryatronth.sd.ticket.service.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntityRepository;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntityRepository;

@Service
@RequiredArgsConstructor
public class UserCategoryAssignmentService {

  private final TicketCategoryAssigneeEntityRepository repository;
  private final TicketCategoryEntityRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<TicketCategoryAssigneeEntity> getUserAssignments(UUID userId, UUID departmentId) {
    return repository.findAllByUserIdAndDepartmentId(userId, departmentId);
  }

  @Transactional
  public List<TicketCategoryAssigneeEntity> replaceUserCategories(UUID userId, UUID departmentId,
                                                                  Set<UUID> categoryIds) {
    repository.deleteAllByUserIdAndDepartmentId(userId, departmentId);

    if (categoryIds == null || categoryIds.isEmpty()) {
      return List.of();
    }

    var saved = new ArrayList<TicketCategoryAssigneeEntity>(categoryIds.size());
    for (UUID categoryId : categoryIds) {
      var category = categoryRepository.findById(categoryId)
          .orElseThrow(() -> new ru.ryatronth.sd.error.exception.NotFoundException(
              "Категория обращения не найдена: " + categoryId));

      saved.add(repository.save(TicketCategoryAssigneeEntity.builder()
          .category(category)
          .departmentId(departmentId)
          .userId(userId)
          .build()));
    }

    return saved;
  }

  @Transactional
  public void deleteUserCategories(UUID userId, UUID departmentId) {
    repository.deleteAllByUserIdAndDepartmentId(userId, departmentId);
  }

}
