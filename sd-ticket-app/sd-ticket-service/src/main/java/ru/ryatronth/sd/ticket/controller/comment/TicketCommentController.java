package ru.ryatronth.sd.ticket.controller.comment;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ryatronth.sd.ticket.api.comment.TicketCommentApiV1;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentCreateRequest;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentDto;
import ru.ryatronth.sd.ticket.mapper.comment.TicketCommentMapper;
import ru.ryatronth.sd.ticket.service.comment.TicketCommentService;

@RestController
@RequestMapping(TicketCommentApiV1.BASE_PATH)
@RequiredArgsConstructor
public class TicketCommentController implements TicketCommentApiV1 {

  private final TicketCommentService commentService;
  private final TicketCommentMapper commentMapper;

  @Override
  public ResponseEntity<List<TicketCommentDto>> getAll(UUID ticketId, Pageable pageable) {
    var list = commentService.getAll(ticketId, pageable)
        .stream()
        .map(commentMapper::toDto)
        .toList();

    return ResponseEntity.ok(list);
  }

  @Override
  public ResponseEntity<TicketCommentDto> add(UUID ticketId, TicketCommentCreateRequest request) {
    var created = commentService.add(ticketId, request);

    var location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(created.getId())
        .toUri();

    return ResponseEntity.created(location).body(commentMapper.toDto(created));
  }

}
