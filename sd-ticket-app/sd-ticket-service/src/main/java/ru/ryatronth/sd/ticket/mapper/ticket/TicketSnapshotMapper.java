package ru.ryatronth.sd.ticket.mapper.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.ticket.domain.event.snapshot.TicketSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.dto.event.snapshot.TicketSnapshotDto;
import ru.ryatronth.sd.ticket.mapper.category.TicketCategorySnapshotMapper;

@Mapper(
    componentModel = "spring",
    uses = {
        TicketCategorySnapshotMapper.class,
        TicketDepartmentSnapshotMapper.class,
        TicketUserSnapshotMapper.class,
        TicketWorkplaceSnapshotMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class TicketSnapshotMapper {

  @Autowired
  protected TicketCategorySnapshotMapper ticketCategorySnapshotMapper;

  @Autowired
  protected TicketDepartmentSnapshotMapper ticketDepartmentSnapshotMapper;

  @Autowired
  protected TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Autowired
  protected TicketWorkplaceSnapshotMapper ticketWorkplaceSnapshotMapper;

  @Mapping(target = "categoryId", source = "category.id")
  @Mapping(target = "requesterUserId", source = "requesterUserId")
  @Mapping(target = "requesterDepartmentId", source = "requesterDepartmentId")
  @Mapping(target = "currentDepartmentId", source = "currentDepartmentId")
  @Mapping(target = "assigneeUserId", source = "assigneeUserId")
  public abstract TicketSnapshot toDomainSnapshot(TicketEntity ticket);

  @Mapping(target = "id", source = "id")
  @Mapping(
      target = "category",
      expression = "java(ticketCategorySnapshotMapper.toSnapshotDto(snapshot.getCategoryId(), snapshot.getCategorySnapshot()))"
  )
  @Mapping(
      target = "requester",
      expression = "java(ticketUserSnapshotMapper.toSnapshotDto(snapshot.getRequesterUserId(), snapshot.getRequesterSnapshot()))"
  )
  @Mapping(
      target = "requesterDepartment",
      expression = "java(ticketDepartmentSnapshotMapper.toSnapshotDto(snapshot.getRequesterDepartmentId(), snapshot.getRequesterDepartmentSnapshot()))"
  )
  @Mapping(
      target = "currentDepartment",
      expression = "java(ticketDepartmentSnapshotMapper.toSnapshotDto(snapshot.getCurrentDepartmentId(), snapshot.getCurrentDepartmentSnapshot()))"
  )
  @Mapping(
      target = "requesterWorkplace",
      expression = "java(ticketWorkplaceSnapshotMapper.toSnapshotDto(snapshot.getRequesterWorkplaceSnapshot()))"
  )
  @Mapping(
      target = "assignee",
      expression = "java(snapshot.getAssigneeUserId() == null ? null : ticketUserSnapshotMapper.toSnapshotDto(snapshot.getAssigneeUserId(), snapshot.getAssigneeSnapshot()))"
  )
  public abstract TicketSnapshotDto toDto(TicketSnapshot snapshot);
}
