package ru.ryatronth.sd.department.service.department;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.department.api.common.SearchMode;
import ru.ryatronth.sd.department.api.department.filter.DepartmentFilters;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeRepository;
import ru.ryatronth.sd.department.domain.department.DepartmentEntity;
import ru.ryatronth.sd.department.domain.department.DepartmentRepository;
import ru.ryatronth.sd.department.domain.department.DepartmentSpecifications;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeRepository;
import ru.ryatronth.sd.department.dto.department.DepartmentCreateRequest;
import ru.ryatronth.sd.department.dto.department.DepartmentUpdateRequest;
import ru.ryatronth.sd.error.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  private final DepartmentRepository repository;

  private final DepartmentCodeRepository codeRepository;

  private final DepartmentTypeRepository typeRepository;

  @Transactional(readOnly = true)
  public DepartmentEntity getById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Подразделение не найдено: " + id));
  }

  @Transactional(readOnly = true)
  public Page<DepartmentEntity> getAll(DepartmentFilters filters, Pageable pageable) {
    var spec = DepartmentSpecifications.Filter.builder()
        .q(filters == null ? null : filters.getQ())
        .parentId(filters == null ? null : filters.getParentId())
        .codeId(filters == null ? null : filters.getCodeId())
        .typeId(filters == null ? null : filters.getTypeId())
        .mode(filters == null || filters.getMode() == null ? SearchMode.AND : filters.getMode())
        .build()
        .toSpec();

    return repository.findAll(spec, pageable);
  }

  @Transactional
  public DepartmentEntity create(DepartmentCreateRequest request) {
    var entity = new DepartmentEntity();

    entity.setName(request.getName().trim());
    entity.setArea(request.getArea().trim());
    entity.setAddress(request.getAddress().trim());

    var code = codeRepository.findById(request.getCodeId())
        .orElseThrow(
            () -> new NotFoundException("Код подразделения не найден: " + request.getCodeId()));
    entity.setCode(code);

    if (request.getParentId() != null) {
      var parent = repository.findById(request.getParentId())
          .orElseThrow(
              () -> new NotFoundException("Подразделение не найдено: " + request.getParentId()));
      entity.setParent(parent);
    }

    if (request.getTypeId() != null) {
      var type = typeRepository.findById(request.getTypeId())
          .orElseThrow(
              () -> new NotFoundException("Вид подразделения не найден: " + request.getTypeId()));
      entity.setType(type);
    }

    return repository.save(entity);
  }

  @Transactional
  public DepartmentEntity update(UUID id, DepartmentUpdateRequest request) {
    var entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Подразделение не найдено: " + id));

    if (request.getName() != null && !request.getName().isBlank()) {
      entity.setName(request.getName().trim());
    }
    if (request.getArea() != null && !request.getArea().isBlank()) {
      entity.setArea(request.getArea().trim());
    }
    if (request.getAddress() != null && !request.getAddress().isBlank()) {
      entity.setAddress(request.getAddress().trim());
    }

    if (request.getCodeId() != null) {
      var code = codeRepository.findById(request.getCodeId())
          .orElseThrow(
              () -> new NotFoundException("Код подразделения не найден: " + request.getCodeId()));
      entity.setCode(code);
    }

    if (request.getParentId() != null) {
      var parent = repository.findById(request.getParentId())
          .orElseThrow(
              () -> new NotFoundException("Подразделение не найдено: " + request.getParentId()));
      entity.setParent(parent);
    }

    if (request.getTypeId() != null) {
      var type = typeRepository.findById(request.getTypeId())
          .orElseThrow(
              () -> new NotFoundException("Вид подразделения не найден: " + request.getTypeId()));
      entity.setType(type);
    }

    return repository.save(entity);
  }

  @Transactional
  public void delete(UUID id) {
    repository.deleteById(id);
  }

}
