package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentCreateRequest;

@Schema(name = "TicketCreateRequest", description = "Создание заявки")
public record TicketCreateRequest(

    @NotBlank
    @Schema(description = "Заголовок", requiredMode = Schema.RequiredMode.REQUIRED, example = "Не работает доступ к VPN")
    String title,

    @NotBlank
    @Schema(description = "Описание", requiredMode = Schema.RequiredMode.REQUIRED, example = "При попытке подключиться ошибка 691...")
    String description,

    @NotNull
    @Schema(description = "UUID категории", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID categoryId,

    @Schema(description = "Файлы, приложенные к обращению")
    List<TicketAttachmentCreateRequest> attachments
) {
}
