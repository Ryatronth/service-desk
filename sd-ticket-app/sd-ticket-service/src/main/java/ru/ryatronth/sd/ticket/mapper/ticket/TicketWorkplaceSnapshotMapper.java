package ru.ryatronth.sd.ticket.mapper.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.WorkplaceSnapshot;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.WorkplaceSnapshotDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketWorkplaceSnapshotMapper {

  WorkplaceSnapshotDto toSnapshotDto(WorkplaceSnapshot snapshot);
}
