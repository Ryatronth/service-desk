package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TicketAssignmentType", description = "Тип назначения пользователя")
public enum TicketAssignmentType {
  @Schema(description = "Исполнитель конкретной категории")
  EXECUTOR,
  @Schema(description = "Менеджер департамента (распространяется на все категории, хранится с categoryId=null)")
  MANAGER
}
