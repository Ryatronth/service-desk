package ru.ryatronth.sd.ticket.mapper.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.dto.ticket.TicketDto;
import ru.ryatronth.sd.ticket.dto.ticket.TicketShortDto;
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
public abstract class TicketMapper {

  @Autowired
  protected TicketCategorySnapshotMapper ticketCategorySnapshotMapper;

  @Autowired
  protected TicketDepartmentSnapshotMapper ticketDepartmentSnapshotMapper;

  @Autowired
  protected TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Autowired
  protected TicketWorkplaceSnapshotMapper ticketWorkplaceSnapshotMapper;

  @Mapping(
      target = "categorySnapshot",
      expression = "java(ticketCategorySnapshotMapper.toSnapshotDto(entity.getCategory().getId(), entity.getCategorySnapshot()))"
  )
  @Mapping(
      target = "requesterSnapshot",
      expression = "java(ticketUserSnapshotMapper.toSnapshotDto(entity.getRequesterUserId(), entity.getRequesterSnapshot()))"
  )
  @Mapping(
      target = "assigneeSnapshot",
      expression = "java(entity.getAssigneeUserId() == null ? null : ticketUserSnapshotMapper.toSnapshotDto(entity.getAssigneeUserId(), entity.getAssigneeSnapshot()))"
  )
  @Mapping(
      target = "requesterDepartmentSnapshot",
      expression = "java(ticketDepartmentSnapshotMapper.toSnapshotDto(entity.getRequesterDepartmentId(), entity.getRequesterDepartmentSnapshot()))"
  )
  @Mapping(
      target = "currentDepartmentSnapshot",
      expression = "java(ticketDepartmentSnapshotMapper.toSnapshotDto(entity.getCurrentDepartmentId(), entity.getCurrentDepartmentSnapshot()))"
  )
  @Mapping(
      target = "requesterWorkplaceSnapshot",
      expression = "java(ticketWorkplaceSnapshotMapper.toSnapshotDto(entity.getRequesterWorkplaceSnapshot()))"
  )
  public abstract TicketDto toDto(TicketEntity entity);

  @Mapping(
      target = "categorySnapshot",
      expression = "java(ticketCategorySnapshotMapper.toSnapshotDto(entity.getCategory().getId(), entity.getCategorySnapshot()))"
  )
  @Mapping(
      target = "requesterSnapshot",
      expression = "java(ticketUserSnapshotMapper.toSnapshotDto(entity.getRequesterUserId(), entity.getRequesterSnapshot()))"
  )
  @Mapping(
      target = "assigneeSnapshot",
      expression = "java(entity.getAssigneeUserId() == null ? null : ticketUserSnapshotMapper.toSnapshotDto(entity.getAssigneeUserId(), entity.getAssigneeSnapshot()))"
  )
  public abstract TicketShortDto toShortDto(TicketEntity entity);
}
