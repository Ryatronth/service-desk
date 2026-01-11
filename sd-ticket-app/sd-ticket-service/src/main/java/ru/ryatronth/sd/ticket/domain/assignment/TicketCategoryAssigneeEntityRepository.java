package ru.ryatronth.sd.ticket.domain.assignment;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketCategoryAssigneeEntityRepository
    extends JpaRepository<TicketCategoryAssigneeEntity, UUID> {

  @EntityGraph(attributePaths = "category")
  List<TicketCategoryAssigneeEntity> findAllByCategoryIdAndDepartmentId(UUID categoryId,
                                                                        UUID departmentId);

  @EntityGraph(attributePaths = "category")
  List<TicketCategoryAssigneeEntity> findAllByUserIdAndDepartmentId(UUID userId, UUID departmentId);

  void deleteAllByCategoryIdAndDepartmentId(UUID categoryId, UUID departmentId);

  void deleteAllByUserIdAndDepartmentId(UUID userId, UUID departmentId);

  @Query("""
      select a
      from TicketCategoryAssigneeEntity a
      where a.category.id = :categoryId and a.departmentId = :departmentId
      """)
  List<TicketCategoryAssigneeEntity> findAllFor(@Param("categoryId") UUID categoryId,
                                                @Param("departmentId") UUID departmentId);

}