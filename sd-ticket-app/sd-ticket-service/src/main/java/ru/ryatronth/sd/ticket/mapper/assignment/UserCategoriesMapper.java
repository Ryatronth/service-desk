package ru.ryatronth.sd.ticket.mapper.assignment;

import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.dto.assignment.UserCategoriesDto;

@Mapper(componentModel = "spring")
public interface UserCategoriesMapper {

  default UserCategoriesDto toDto(UUID userId, UUID departmentId,
                                  List<TicketCategoryAssigneeEntity> assignments) {
    var items = assignments.stream()
        .map(a -> new UserCategoriesDto.UserCategoryItem(a.getCategory().getId(),
            a.getCategory().getName(), a.getCategory().getPriority()))
        .toList();

    return new UserCategoriesDto(userId, departmentId, items);
  }
}
