package ru.ryatronth.sd.ticket.mapper.attachment;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.ryatronth.sd.file.dto.FileDto;
import ru.ryatronth.sd.ticket.domain.attachment.snapshot.FileSnapshot;
import ru.ryatronth.sd.ticket.dto.attachment.shapshot.FileSnapshotDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketFileSnapshotMapper {

  @Mapping(target = "originalName", source = "originalName")
  @Mapping(target = "contentType", source = "contentType")
  @Mapping(target = "sizeBytes", source = "sizeBytes")
  FileSnapshot toDomainSnapshot(FileDto file);

  @Mapping(target = "id", source = "id")
  FileSnapshotDto toSnapshotDto(UUID id, FileSnapshot snapshot);

}
