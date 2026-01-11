package ru.ryatronth.sd.ticket.mapper.attachment;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.ticket.domain.attachment.TicketAttachmentEntity;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentDto;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class TicketAttachmentMapper {

  @Autowired
  protected TicketFileSnapshotMapper ticketFileSnapshotMapper;

  @Mapping(target = "ticketId", source = "ticket.id")
  @Mapping(target = "commentId", source = "comment.id")
  @Mapping(
      target = "fileSnapshot",
      expression = "java(ticketFileSnapshotMapper.toSnapshotDto(entity.getFileId(), entity.getFileSnapshot()))"
  )
  public abstract TicketAttachmentDto toDto(TicketAttachmentEntity entity);

  public List<TicketAttachmentDto> toDtoList(List<TicketAttachmentEntity> entities) {
    if (entities == null) {
      return null;
    }
    return entities.stream().map(this::toDto).toList();
  }
}
