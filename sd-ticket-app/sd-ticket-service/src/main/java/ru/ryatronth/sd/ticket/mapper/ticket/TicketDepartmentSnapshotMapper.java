package ru.ryatronth.sd.ticket.mapper.ticket;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.DepartmentSnapshot;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.DepartmentSnapshotDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TicketDepartmentSnapshotMapper {

  @Mapping(target = "code", source = "code.code")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "area", source = "area")
  @Mapping(target = "address", source = "address")
  DepartmentSnapshot toDomainSnapshot(DepartmentDto departmentDto);

  @Mapping(target = "code", source = "code.code")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "area", source = "area")
  @Mapping(target = "address", source = "address")
  DepartmentSnapshot toDomainSnapshot(DepartmentShortDto departmentDto);

  @Mapping(target = "id", source = "id")
  DepartmentSnapshotDto toSnapshotDto(UUID id, DepartmentSnapshot snapshot);
}

