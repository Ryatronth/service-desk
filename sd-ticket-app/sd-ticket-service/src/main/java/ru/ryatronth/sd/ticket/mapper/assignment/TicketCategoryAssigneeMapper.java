package ru.ryatronth.sd.ticket.mapper.assignment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;

@Mapper(componentModel = "spring")
public interface TicketCategoryAssigneeMapper {

  @Mapping(target = "categoryId", source = "category.id")
  TicketCategoryAssigneeDto toDto(TicketCategoryAssigneeEntity entity);
}
