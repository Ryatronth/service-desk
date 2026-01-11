package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_assignee;

import java.util.UUID;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;

public record ChangeTicketAssigneeEventPayload(
    UUID oldAssigneeUserId,
    UserSnapshot oldAssigneeUser,
    UUID newAssigneeUserId,
    UserSnapshot newAssigneeUser
) {
}
