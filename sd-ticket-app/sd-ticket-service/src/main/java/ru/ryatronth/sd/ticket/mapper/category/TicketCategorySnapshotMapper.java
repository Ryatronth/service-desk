package ru.ryatronth.sd.ticket.mapper.category;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.CategorySnapshot;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.CategorySnapshotDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketCategorySnapshotMapper {

  @Mapping(target = "id", source = "id")
  CategorySnapshotDto toSnapshotDto(UUID id, CategorySnapshot snapshot);
}
