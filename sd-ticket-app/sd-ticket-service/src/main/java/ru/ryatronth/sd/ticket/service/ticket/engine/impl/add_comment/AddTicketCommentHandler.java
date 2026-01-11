package ru.ryatronth.sd.ticket.service.ticket.engine.impl.add_comment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentCreateRequest;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.service.attachment.TicketAttachmentService;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class AddTicketCommentHandler implements OnTicketHandler<AddTicketCommentCommand, AddTicketCommentResult> {

  private static final String COMMENT = "Добавлен комментарий к обращению";

  private final TicketCommentEntityRepository ticketCommentEntityRepository;

  private final TicketAttachmentService ticketAttachmentService;

  @Override
  public Class<AddTicketCommentCommand> supports() {
    return AddTicketCommentCommand.class;
  }

  @Override
  public HandlerResult<AddTicketCommentResult> handle(OnTicketContext ctx, AddTicketCommentCommand command) {
    var cu = ctx.actor();
    var ticket = ctx.ticketOrThrow();

    var entity = new TicketCommentEntity();
    entity.setTicket(ticket);

    entity.setAuthorUserId(UUID.fromString(cu.userId()));
    entity.setAuthorSnapshot(
        UserSnapshot.builder()
            .username(cu.username())
            .displayName(cu.buildFullName())
            .email(cu.email())
            .build()
    );
    entity.setBody(command.request().body().trim());

    entity = ticketCommentEntityRepository.save(entity);

    for (TicketAttachmentCreateRequest attachment : command.request().attachments()) {
      ticketAttachmentService.addToComment(ticket.getId(), entity.getId(), attachment);
    }

    var payload = new AddTicketCommentEventPayload(entity.getId(), entity.getBody());
    var event = new TicketHistoryEvent(TicketEventType.COMMENT_ADDED, payload, COMMENT);

    return HandlerResult.of(new AddTicketCommentResult(entity), event);
  }
}
