package ru.ryatronth.sd.iamsync.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.iamsync.api.IamUsersApiV1;
import ru.ryatronth.sd.iamsync.api.filter.IamUserFilters;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;
import ru.ryatronth.sd.iamsync.mapper.IamUserMapper;
import ru.ryatronth.sd.iamsync.service.IamUserService;

@RestController
@RequestMapping(IamUsersApiV1.BASE_PATH)
@RequiredArgsConstructor
public class IamUsersControllerV1 implements IamUsersApiV1 {

  private final IamUserService service;

  private final IamUserMapper mapper;

  @Override
  public ResponseEntity<IamUserDto> getUserById(UUID id) {
    var entity = service.getById(id);
    return ResponseEntity.ok(mapper.toDto(entity));
  }

  @Override
  public ResponseEntity<IamUserDto> getUserByUsername(String username) {
    var entity = service.getByUsername(username);
    return ResponseEntity.ok(mapper.toDto(entity));
  }

  @Override
  public ResponseEntity<Page<IamUserDto>> getUsers(IamUserFilters filters, Pageable pageable) {
    var page = service.search(filters, pageable).map(mapper::toDto);

    return ResponseEntity.ok(page);
  }

}
