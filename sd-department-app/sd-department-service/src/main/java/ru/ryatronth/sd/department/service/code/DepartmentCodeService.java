package ru.ryatronth.sd.department.service.code;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.department.api.code.filter.DepartmentCodeFilters;
import ru.ryatronth.sd.department.api.common.SearchMode;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeEntity;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeRepository;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeSpecifications;
import ru.ryatronth.sd.error.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class DepartmentCodeService {

  private final DepartmentCodeRepository repository;

  @Transactional(readOnly = true)
  public DepartmentCodeEntity getById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Код подразделения не найден: " + id));
  }

  @Transactional(readOnly = true)
  public Page<DepartmentCodeEntity> getAll(DepartmentCodeFilters filters, Pageable pageable) {
    var spec = DepartmentCodeSpecifications.Filter.builder()
        .q(filters == null ? null : filters.getQ())
        .code(filters == null ? null : filters.getCode())
        .mode(filters == null || filters.getMode() == null ? SearchMode.AND : filters.getMode())
        .build()
        .toSpec();

    return repository.findAll(spec, pageable);
  }

}
