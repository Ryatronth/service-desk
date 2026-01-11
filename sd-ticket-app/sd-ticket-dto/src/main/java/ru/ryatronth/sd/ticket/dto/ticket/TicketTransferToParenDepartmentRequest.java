package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TicketTransferToParenDepartmentRequest", description = "Запрос на перевод обращения в родительский филиал с назначением исполнителя")
public record TicketTransferToParenDepartmentRequest(

    @Schema(description = "Комментарий/причина перевода")
    String comment
) {
}
