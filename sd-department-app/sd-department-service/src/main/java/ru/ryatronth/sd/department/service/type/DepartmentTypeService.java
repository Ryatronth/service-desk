package ru.ryatronth.sd.department.service.type;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.department.api.common.SearchMode;
import ru.ryatronth.sd.department.api.type.filter.DepartmentTypeFilters;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeEntity;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeRepository;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeSpecifications;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeCreateRequest;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeDto;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeUpdateRequest;
import ru.ryatronth.sd.department.mapper.type.DepartmentTypeMapper;
import ru.ryatronth.sd.error.exception.NotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentTypeService {

    private final DepartmentTypeRepository repository;

    private final DepartmentTypeMapper mapper;

    @Transactional
    public DepartmentTypeDto create(DepartmentTypeCreateRequest request) {
        var e = new DepartmentTypeEntity();
        e.setName(request.getName().trim());
        return mapper.toDto(repository.save(e));
    }

    @Transactional(readOnly = true)
    public DepartmentTypeDto getById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Вид подразделения не найден: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<DepartmentTypeDto> getAll(DepartmentTypeFilters filters, Pageable pageable) {
        var spec = DepartmentTypeSpecifications.Filter.builder()
                                                      .q(filters == null ? null : filters.getQ())
                                                      .mode(filters == null || filters.getMode() == null ? SearchMode.AND : filters.getMode())
                                                      .build()
                                                      .toSpec();

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    @Transactional
    public DepartmentTypeDto update(UUID id, DepartmentTypeUpdateRequest request) {
        var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Вид подразделения не найден: " + id));

        if (request.getName() != null && !request.getName().isBlank()) {
            entity.setName(request.getName().trim());
        }

        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
