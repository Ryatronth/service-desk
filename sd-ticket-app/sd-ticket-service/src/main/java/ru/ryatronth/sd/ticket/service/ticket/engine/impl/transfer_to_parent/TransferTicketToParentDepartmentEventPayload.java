package ru.ryatronth.sd.ticket.service.ticket.engine.impl.transfer_to_parent;

import java.util.UUID;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.DepartmentSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;

public record TransferTicketToParentDepartmentEventPayload(

    DepartmentSnapshot fromDepartment,

    DepartmentSnapshot toDepartment,

    UUID oldAssigneeUserId,

    UUID newAssigneeUserId,

    UserSnapshot oldAssigneeSnapshot,

    UserSnapshot newAssigneeSnapshot
) {
}
