package ru.ryatronth.sd.department.service.code;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.department.api.code.filter.DepartmentCodeFilters;
import ru.ryatronth.sd.department.api.common.SearchMode;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeRepository;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeSpecifications;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;
import ru.ryatronth.sd.department.mapper.code.DepartmentCodeMapper;
import ru.ryatronth.sd.error.exception.NotFoundException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentCodeService {

    private final DepartmentCodeRepository repository;

    private final DepartmentCodeMapper mapper;

    @Transactional(readOnly = true)
    public DepartmentCodeDto getById(UUID id) {
        var entity = repository.findById(id).orElseThrow(() -> new NotFoundException("Код подразделения не найден: " + id));
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<DepartmentCodeDto> getAll(DepartmentCodeFilters filters, Pageable pageable) {
        var spec = DepartmentCodeSpecifications.Filter.builder()
                                                      .q(filters == null ? null : filters.getQ())
                                                      .code(filters == null ? null : filters.getCode())
                                                      .mode(filters == null || filters.getMode() == null ? SearchMode.AND : filters.getMode())
                                                      .build()
                                                      .toSpec();

        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

}
