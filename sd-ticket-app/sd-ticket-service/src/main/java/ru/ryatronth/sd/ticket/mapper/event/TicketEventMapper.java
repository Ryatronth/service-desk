package ru.ryatronth.sd.ticket.mapper.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntity;
import ru.ryatronth.sd.ticket.dto.event.TicketEventDto;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketSnapshotMapper;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketUserSnapshotMapper;

@Mapper(
    componentModel = "spring",
    uses = {
        TicketSnapshotMapper.class,
        TicketUserSnapshotMapper.class,
        JacksonPayloadMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public abstract class TicketEventMapper {

  @Autowired
  protected TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Mapping(target = "ticketSnapshot", source = "ticketSnapshot")
  @Mapping(
      target = "actorSnapshot",
      expression = "java(ticketUserSnapshotMapper.toSnapshotDto(entity.getActorUserId(), entity.getActorSnapshot()))"
  )
  @Mapping(target = "payload", source = "payload", qualifiedByName = "toJson")
  public abstract TicketEventDto toDto(TicketEventEntity entity);
}
