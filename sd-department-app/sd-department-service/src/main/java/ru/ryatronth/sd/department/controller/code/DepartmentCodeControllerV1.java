package ru.ryatronth.sd.department.controller.code;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.department.api.code.DepartmentCodeApiV1;
import ru.ryatronth.sd.department.api.code.filter.DepartmentCodeFilters;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;
import ru.ryatronth.sd.department.mapper.code.DepartmentCodeMapper;
import ru.ryatronth.sd.department.service.code.DepartmentCodeService;

@RequiredArgsConstructor
@RestController
@RequestMapping(DepartmentCodeApiV1.BASE_PATH)
public class DepartmentCodeControllerV1 implements DepartmentCodeApiV1 {

  private final DepartmentCodeService service;

  private final DepartmentCodeMapper mapper;

  @Override
  public ResponseEntity<DepartmentCodeDto> getById(UUID id) {
    return ResponseEntity.ok(mapper.toDto(service.getById(id)));
  }

  @Override
  public ResponseEntity<Page<DepartmentCodeDto>> getAll(DepartmentCodeFilters filters,
                                                        Pageable pageable) {
    return ResponseEntity.ok(service.getAll(filters, pageable).map(mapper::toDto));
  }

}
