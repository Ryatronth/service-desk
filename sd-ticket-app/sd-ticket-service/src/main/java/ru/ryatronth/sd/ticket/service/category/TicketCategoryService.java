package ru.ryatronth.sd.ticket.service.category;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.ticket.api.category.filters.TicketCategoryFilters;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntity;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntityRepository;
import ru.ryatronth.sd.ticket.domain.category.TicketCategorySpecifications;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryCreateRequest;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryUpdateRequest;

@Service
@RequiredArgsConstructor
public class TicketCategoryService {

  private final TicketCategoryEntityRepository repository;

  @Transactional(readOnly = true)
  public TicketCategoryEntity getById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Категория обращения не найдена: " + id));
  }

  @Transactional(readOnly = true)
  public Page<TicketCategoryEntity> getAll(TicketCategoryFilters filters, Pageable pageable) {
    var spec = TicketCategorySpecifications.Filter.builder().q(filters == null ? null : filters.q())
        .priority(filters == null ? null : filters.priority()).build().toSpec();

    return repository.findAll(spec, pageable);
  }

  @Transactional
  public TicketCategoryEntity create(TicketCategoryCreateRequest request) {
    var entity = new TicketCategoryEntity();

    entity.setName(request.name().trim());
    entity.setDescription(request.description());
    entity.setPriority(request.priority());
    entity.setExpectedDurationMinutes(request.expectedDurationMinutes());

    return repository.save(entity);
  }

  @Transactional
  public TicketCategoryEntity update(UUID id, TicketCategoryUpdateRequest request) {
    var entity = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Категория обращения не найдена: " + id));

    if (request.name() != null && !request.name().isBlank()) {
      entity.setName(request.name().trim());
    }
    if (request.description() != null) {
      entity.setDescription(request.description());
    }
    if (request.priority() != null) {
      entity.setPriority(request.priority());
    }
    if (request.expectedDurationMinutes() != null) {
      entity.setExpectedDurationMinutes(request.expectedDurationMinutes());
    }

    return repository.save(entity);
  }

  @Transactional
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}
