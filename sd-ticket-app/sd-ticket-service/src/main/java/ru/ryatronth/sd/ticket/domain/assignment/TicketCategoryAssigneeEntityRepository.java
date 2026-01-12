package ru.ryatronth.sd.ticket.domain.assignment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.ryatronth.sd.ticket.dto.assignment.TicketAssignmentType;

public interface TicketCategoryAssigneeEntityRepository
    extends JpaRepository<TicketCategoryAssigneeEntity, UUID>, JpaSpecificationExecutor<TicketCategoryAssigneeEntity> {

  @EntityGraph(attributePaths = "category")
  Optional<TicketCategoryAssigneeEntity> findWithCategoryById(UUID id);

  List<TicketCategoryAssigneeEntity> findByIdAndDepartmentIdAndAssignmentType(UUID id, UUID departmentId, TicketAssignmentType assignmentType);

}