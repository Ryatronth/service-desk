package ru.ryatronth.sd.ticket.service.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntityRepository;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntityRepository;

@Service
@RequiredArgsConstructor
public class TicketCategoryAssigneeService {

  private final TicketCategoryAssigneeEntityRepository repository;
  private final TicketCategoryEntityRepository categoryRepository;

  @Transactional(readOnly = true)
  public List<TicketCategoryAssigneeEntity> getAssignees(UUID categoryId, UUID departmentId) {
    ensureCategoryExists(categoryId);
    return repository.findAllByCategoryIdAndDepartmentId(categoryId, departmentId);
  }

  @Transactional
  public List<TicketCategoryAssigneeEntity> replaceAssignees(UUID categoryId, UUID departmentId,
                                                             Set<UUID> userIds) {
    ensureCategoryExists(categoryId);

    repository.deleteAllByCategoryIdAndDepartmentId(categoryId, departmentId);

    if (userIds == null || userIds.isEmpty()) {
      return List.of();
    }

    var category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new NotFoundException("Категория обращения не найдена: " + categoryId));

    var saved = new ArrayList<TicketCategoryAssigneeEntity>(userIds.size());
    for (UUID userId : userIds) {
      saved.add(repository.save(
          TicketCategoryAssigneeEntity.builder().category(category).departmentId(departmentId)
              .userId(userId).build()));
    }

    return saved;
  }

  @Transactional
  public void deleteAssignees(UUID categoryId, UUID departmentId) {
    ensureCategoryExists(categoryId);
    repository.deleteAllByCategoryIdAndDepartmentId(categoryId, departmentId);
  }

  private void ensureCategoryExists(UUID categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new NotFoundException("Категория обращения не найдена: " + categoryId);
    }
  }
}
