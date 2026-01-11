package ru.ryatronth.sd.ticket.service.attachment;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.ticket.adapter.FileClientAdapter;
import ru.ryatronth.sd.ticket.domain.attachment.TicketAttachmentEntity;
import ru.ryatronth.sd.ticket.domain.attachment.TicketAttachmentEntityRepository;
import ru.ryatronth.sd.ticket.domain.attachment.snapshot.FileSnapshot;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentCreateRequest;
import ru.ryatronth.sd.ticket.mapper.attachment.TicketFileSnapshotMapper;

@Service
@RequiredArgsConstructor
public class TicketAttachmentService {

  private final TicketEntityRepository ticketRepository;
  private final TicketCommentEntityRepository commentRepository;
  private final TicketAttachmentEntityRepository attachmentRepository;

  private final FileClientAdapter fileClientAdapter;
  private final TicketFileSnapshotMapper fileDtoMapper;

  @Transactional
  public TicketAttachmentEntity addToTicket(UUID ticketId, TicketAttachmentCreateRequest request) {
    var ticket =
        ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Заявка не найдена: " + ticketId));
    FileSnapshot file = fileDtoMapper.toDomainSnapshot(fileClientAdapter.resolveFileSnapshot(request.fileId()));

    var entity = new TicketAttachmentEntity();
    entity.setId(UUID.randomUUID());
    entity.setTicket(ticket);

    entity.setFileId(request.fileId());
    entity.setFileSnapshot(file);

    return attachmentRepository.save(entity);
  }

  @Transactional
  public TicketAttachmentEntity addToComment(UUID ticketId, UUID commentId, TicketAttachmentCreateRequest request) {
    var ticket =
        ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException("Заявка не найдена: " + ticketId));
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new NotFoundException("Комментарий не найден: " + commentId));
    FileSnapshot file = fileDtoMapper.toDomainSnapshot(fileClientAdapter.resolveFileSnapshot(request.fileId()));

    var entity = new TicketAttachmentEntity();
    entity.setId(UUID.randomUUID());
    entity.setTicket(ticket);
    entity.setComment(comment);

    entity.setFileId(request.fileId());
    entity.setFileSnapshot(file);

    return attachmentRepository.save(entity);
  }

}
