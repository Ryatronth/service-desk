package ru.ryatronth.sd.ticket.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentCreateRequest;

@Schema(name = "TicketCommentCreateRequest", description = "Добавление комментария")
public record TicketCommentCreateRequest(

    @Schema(description = "Текст комментария", requiredMode = Schema.RequiredMode.REQUIRED)
    String body,

    @Schema(description = "Файлы, приложенные к комментарию")
    List<TicketAttachmentCreateRequest> attachments

) {
}
