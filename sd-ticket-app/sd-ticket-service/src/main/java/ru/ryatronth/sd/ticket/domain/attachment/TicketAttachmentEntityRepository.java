package ru.ryatronth.sd.ticket.domain.attachment;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketAttachmentEntityRepository
    extends JpaRepository<TicketAttachmentEntity, UUID> {

}