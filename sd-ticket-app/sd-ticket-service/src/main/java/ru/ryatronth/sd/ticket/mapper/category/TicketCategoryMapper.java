package ru.ryatronth.sd.ticket.mapper.category;

import org.mapstruct.Mapper;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntity;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryDto;

@Mapper(componentModel = "spring")
public interface TicketCategoryMapper {
  TicketCategoryDto toDto(TicketCategoryEntity entity);
}
