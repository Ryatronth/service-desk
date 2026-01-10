package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@Schema(description = "Категории, назначенные пользователю как исполнителю в филиале")
public record UserCategoriesDto(

    @Schema(description = "ID пользователя") UUID userId,

    @Schema(description = "ID филиала (department.id)") UUID departmentId,

    @Schema(description = "Список категорий") List<UserCategoryItem> categories) {
  @Schema(description = "Элемент категории (минимальный)")
  public record UserCategoryItem(@Schema(description = "ID категории") UUID categoryId,
                                 @Schema(description = "Название категории") String name,
                                 @Schema(description = "Приоритет") TicketPriority priority) {
  }
}
