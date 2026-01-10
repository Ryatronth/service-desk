package ru.ryatronth.sd.iamsync.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.iamsync.api.filter.IamUserFilters;
import ru.ryatronth.sd.iamsync.api.filter.SearchMode;
import ru.ryatronth.sd.iamsync.domain.IamUserEntity;
import ru.ryatronth.sd.iamsync.domain.IamUserRepository;
import ru.ryatronth.sd.iamsync.domain.IamUserSpecifications;

@Service
@RequiredArgsConstructor
public class IamUserService {

  private final IamUserRepository repository;

  @Transactional(readOnly = true)
  public IamUserEntity getById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
  }

  @Transactional(readOnly = true)
  public IamUserEntity getByUsername(String username) {
    return repository.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
  }

  @Transactional(readOnly = true)
  public Page<IamUserEntity> search(IamUserFilters filters, Pageable pageable) {
    var spec = IamUserSpecifications.Filter.builder()
        .q(filters.getQ())
        .enabled(filters.getEnabled())
        .username(filters.getUsername())
        .email(filters.getEmail())
        .phone(filters.getPhone())
        .departmentCode(filters.getDepartmentCode())
        .workplaceCode(filters.getWorkplaceCode())
        .mode(filters.getMode() == null ? SearchMode.AND : filters.getMode())
        .build()
        .toSpec();

    return repository.findAll(spec, pageable);
  }

}
