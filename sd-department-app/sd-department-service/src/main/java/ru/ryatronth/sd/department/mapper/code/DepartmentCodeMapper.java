package ru.ryatronth.sd.department.mapper.code;

import org.mapstruct.Mapper;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeEntity;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;

@Mapper(componentModel = "spring")
public interface DepartmentCodeMapper {

    DepartmentCodeDto toDto(DepartmentCodeEntity entity);

}
