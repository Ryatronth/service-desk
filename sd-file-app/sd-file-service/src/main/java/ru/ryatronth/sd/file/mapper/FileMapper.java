package ru.ryatronth.sd.file.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ryatronth.sd.file.domain.FileEntity;
import ru.ryatronth.sd.file.dto.FileDto;

@Mapper(componentModel = "spring")
public interface FileMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "originalName", source = "originalName")
  @Mapping(target = "contentType", source = "contentType")
  @Mapping(target = "sizeBytes", source = "sizeBytes")
  @Mapping(target = "bucket", source = "bucket")
  @Mapping(target = "key", source = "key")
  @Mapping(target = "url", source = "url")
  @Mapping(target = "status", source = "status")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  FileDto toDto(FileEntity entity);

}
