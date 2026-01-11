package ru.ryatronth.sd.ticket.adapter;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.department.api.department.DepartmentClientV1;
import ru.ryatronth.sd.department.api.department.filter.DepartmentFilters;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;
import ru.ryatronth.sd.error.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class DepartmentClientAdapter {

  private final DepartmentClientV1 departmentClient;

  public DepartmentDto getByIdOrThrow(UUID id) {
    var body = departmentClient.getById(id).getBody();
    if (body == null) {
      throw new NotFoundException("Подразделение не найдено: " + id);
    }
    return body;
  }

  public DepartmentShortDto findFirstByCode(String departmentCode) {
    if (departmentCode == null || departmentCode.isBlank()) {
      return null;
    }

    var filters = new DepartmentFilters();
    filters.setQ(departmentCode);

    var page = departmentClient.getAllShort(filters, Pageable.ofSize(1)).getBody();
    if (page == null || page.isEmpty()) {
      return null;
    }

    return page.getContent().getFirst();
  }

  public DepartmentDto getParentOrThrow(UUID departmentId) {
    DepartmentDto current = getByIdOrThrow(departmentId);

    if (current.getParent() == null || current.getParent().getId() == null) {
      throw new NotFoundException("Родительское подразделение не найдено для: " + departmentId);
    }

    return getByIdOrThrow(current.getParent().getId());
  }
}
