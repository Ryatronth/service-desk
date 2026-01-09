package ru.ryatronth.sd.iamsync.mapper;

import org.mapstruct.Mapper;
import ru.ryatronth.sd.iamsync.domain.IamUserEntity;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;

@Mapper(componentModel = "spring")
public interface IamUserMapper {

    IamUserDto toDto(IamUserEntity entity);

}
