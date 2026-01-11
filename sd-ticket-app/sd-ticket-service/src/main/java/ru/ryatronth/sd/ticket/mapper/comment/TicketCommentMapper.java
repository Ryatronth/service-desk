package ru.ryatronth.sd.ticket.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentDto;
import ru.ryatronth.sd.ticket.mapper.attachment.TicketAttachmentMapper;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketUserSnapshotMapper;

@Mapper(
    componentModel = "spring",
    uses = {
        TicketUserSnapshotMapper.class,
        TicketAttachmentMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class TicketCommentMapper {

  @Autowired
  protected TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Autowired
  protected TicketAttachmentMapper ticketAttachmentMapper;

  @Mapping(
      target = "authorSnapshot",
      expression = "java(ticketUserSnapshotMapper.toSnapshotDto(entity.getAuthorUserId(), entity.getAuthorSnapshot()))"
  )
  @Mapping(target = "attachments", source = "attachments")
  public abstract TicketCommentDto toDto(TicketCommentEntity entity);
}
