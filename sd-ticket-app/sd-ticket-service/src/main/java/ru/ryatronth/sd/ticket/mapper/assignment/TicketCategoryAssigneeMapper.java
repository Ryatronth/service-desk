package ru.ryatronth.sd.ticket.mapper.assignment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntity;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;
import ru.ryatronth.sd.ticket.mapper.category.TicketCategoryMapper;

@Mapper(componentModel = "spring", uses = {TicketCategoryMapper.class})
public interface TicketCategoryAssigneeMapper {

  @Mapping(target = "category", source = "category")
  TicketCategoryAssigneeDto toDto(TicketCategoryAssigneeEntity entity);
}
