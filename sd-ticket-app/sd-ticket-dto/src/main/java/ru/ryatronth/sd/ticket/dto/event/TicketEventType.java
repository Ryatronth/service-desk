package ru.ryatronth.sd.ticket.dto.event;

public enum TicketEventType {
  CREATED,
  STATUS_CHANGED,
  ASSIGNEE_CHANGED,
  PRIORITY_CHANGED,
  SLA_CHANGED,
  COMMENT_ADDED,
  RESOLVED
}
