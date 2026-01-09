package ru.ryatronth.sd.department.controller.department;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.department.api.department.DepartmentApiV1;
import ru.ryatronth.sd.department.api.department.filter.DepartmentFilters;
import ru.ryatronth.sd.department.dto.department.DepartmentCreateRequest;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;
import ru.ryatronth.sd.department.dto.department.DepartmentUpdateRequest;
import ru.ryatronth.sd.department.service.department.DepartmentService;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(DepartmentApiV1.BASE_PATH)
public class DepartmentControllerV1 implements DepartmentApiV1 {

    private final DepartmentService service;

    @Override
    public ResponseEntity<DepartmentDto> create(DepartmentCreateRequest request) {
        return ResponseEntity.status(201).body(service.create(request));
    }

    @Override
    public ResponseEntity<DepartmentDto> getById(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<Page<DepartmentDto>> getAll(DepartmentFilters filters, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(filters, pageable));
    }

    @Override
    public ResponseEntity<Page<DepartmentShortDto>> getAllShort(DepartmentFilters filters, Pageable pageable) {
        return ResponseEntity.ok(service.getAllShort(filters, pageable));
    }

    @Override
    public ResponseEntity<DepartmentDto> update(UUID id, DepartmentUpdateRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
