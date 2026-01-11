package ru.ryatronth.sd.ticket.service.comment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntityRepository;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentCreateRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.TicketEngine;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.add_comment.AddTicketCommentCommand;

@Service
@RequiredArgsConstructor
public class TicketCommentService {

  private final TicketEngine engine;

  private final TicketCommentEntityRepository commentRepository;

  @Transactional(readOnly = true)
  public Page<TicketCommentEntity> getAll(UUID ticketId, Pageable pageable) {
    return commentRepository.findAllByTicketIdOrderByCreatedAtAsc(ticketId, pageable);
  }

  @Transactional
  public TicketCommentEntity add(UUID ticketId, TicketCommentCreateRequest request) {
    return engine.executeOnTicket(new AddTicketCommentCommand(ticketId, request)).comment();
  }

}
