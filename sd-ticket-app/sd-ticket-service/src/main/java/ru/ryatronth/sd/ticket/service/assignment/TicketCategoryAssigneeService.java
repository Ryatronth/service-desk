package ru.ryatronth.sd.ticket.service.assignment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.ConflictException;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.ticket.api.assignment.filter.TicketCategoryAssigneeFilters;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntityRepository;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeSpecifications;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntityRepository;
import ru.ryatronth.sd.ticket.dto.assignment.TicketAssignmentType;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeCreateRequest;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeUpdateRequest;

@Service
@RequiredArgsConstructor
public class TicketCategoryAssigneeService {

  private final TicketCategoryAssigneeEntityRepository repository;
  private final TicketCategoryEntityRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<TicketCategoryAssigneeEntity> search(TicketCategoryAssigneeFilters filters, Pageable pageable) {
    var spec = TicketCategoryAssigneeSpecifications.Filter.builder()
        .departmentId(filters.departmentId())
        .categoryId(filters.categoryId())
        .userId(filters.userId())
        .assignmentType(filters.assignmentType())
        .mode(filters.mode() == null ? SearchMode.AND : filters.mode())
        .build()
        .toSpec();

    return repository.findAll(spec, pageable);
  }

  @Transactional(readOnly = true)
  public TicketCategoryAssigneeEntity getByIdOrThrow(UUID id) {
    return repository.findWithCategoryById(id).orElseThrow(() -> new NotFoundException("Назначение не найдено: " + id));
  }

  @Transactional
  public TicketCategoryAssigneeEntity create(TicketCategoryAssigneeCreateRequest request) {
    var entity = new TicketCategoryAssigneeEntity();
    entity.setDepartmentId(request.departmentId());
    entity.setUserId(request.userId());
    entity.setAssignmentType(request.assignmentType());

    if (request.assignmentType() == TicketAssignmentType.MANAGER) {
      entity.setCategory(null);
    } else {
      var category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new NotFoundException("Категория обращения не найдена: " + request.categoryId()));
      entity.setCategory(category);
    }

    try {
      return repository.save(entity);
    } catch (DataIntegrityViolationException e) {
      throw new ConflictException("");
    }
  }

  @Transactional
  public TicketCategoryAssigneeEntity update(UUID id, TicketCategoryAssigneeUpdateRequest request) {
    var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Назначение не найдено: " + id));

    entity.setDepartmentId(request.departmentId());
    entity.setUserId(request.userId());
    entity.setAssignmentType(request.assignmentType());

    if (request.assignmentType() == TicketAssignmentType.MANAGER) {
      entity.setCategory(null);
    } else {
      var category = categoryRepository.findById(request.categoryId())
          .orElseThrow(() -> new NotFoundException("Категория обращения не найдена: " + request.categoryId()));
      entity.setCategory(category);
    }

    try {
      var saved = repository.save(entity);
      return repository.findWithCategoryById(saved.getId()).orElse(saved);
    } catch (DataIntegrityViolationException e) {
      throw new ConflictException("");
    }
  }

  @Transactional
  public void delete(UUID id) {
    repository.deleteById(id);
  }

}
