package ru.ryatronth.sd.ticket.domain.assignment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCategoryAssigneeEntityRepository
    extends JpaRepository<TicketCategoryAssigneeEntity, UUID> {

  @EntityGraph(attributePaths = "category")
  List<TicketCategoryAssigneeEntity> findAllByCategoryIdAndDepartmentId(UUID categoryId,
                                                                        UUID departmentId);

  @EntityGraph(attributePaths = "category")
  List<TicketCategoryAssigneeEntity> findAllByUserIdAndDepartmentId(UUID userId, UUID departmentId);

  void deleteAllByCategoryIdAndDepartmentId(UUID categoryId, UUID departmentId);

  void deleteAllByUserIdAndDepartmentId(UUID userId, UUID departmentId);

}