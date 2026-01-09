package ru.ryatronth.sd.department.controller.type;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.department.api.type.DepartmentTypeApiV1;
import ru.ryatronth.sd.department.api.type.filter.DepartmentTypeFilters;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeCreateRequest;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeDto;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeUpdateRequest;
import ru.ryatronth.sd.department.service.type.DepartmentTypeService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(DepartmentTypeApiV1.BASE_PATH)
public class DepartmentTypeControllerV1 implements DepartmentTypeApiV1 {

    private final DepartmentTypeService service;

    @Override
    public ResponseEntity<DepartmentTypeDto> create(DepartmentTypeCreateRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @Override
    public ResponseEntity<DepartmentTypeDto> getById(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<Page<DepartmentTypeDto>> getAll(DepartmentTypeFilters filters, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(filters, pageable));
    }

    @Override
    public ResponseEntity<DepartmentTypeDto> update(UUID id, DepartmentTypeUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
