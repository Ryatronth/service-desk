package ru.ryatronth.sd.department.mapper.type;

import org.mapstruct.Mapper;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeEntity;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeDto;

@Mapper(componentModel = "spring")
public interface DepartmentTypeMapper {

  DepartmentTypeDto toDto(DepartmentTypeEntity entity);

}
