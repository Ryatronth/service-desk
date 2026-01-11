package ru.ryatronth.sd.ticket.mapper.ticket;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.UserSnapshotDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketUserSnapshotMapper {

  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(
      target = "displayName",
      expression = "java(iamUserDto.buildFullName())"
  )
  UserSnapshot toDomainSnapshot(IamUserDto iamUserDto);

  @Mapping(target = "id", source = "id")
  UserSnapshotDto toSnapshotDto(UUID id, UserSnapshot snapshot);

}
