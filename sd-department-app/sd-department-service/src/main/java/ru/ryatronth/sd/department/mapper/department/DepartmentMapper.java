package ru.ryatronth.sd.department.mapper.department;

import org.mapstruct.Mapper;
import ru.ryatronth.sd.department.domain.department.DepartmentEntity;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

  DepartmentDto toDto(DepartmentEntity entity);

  DepartmentShortDto toShortDto(DepartmentEntity entity);

}
