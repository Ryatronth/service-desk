package ru.ryatronth.sd.ticket.domain.ticket.snapshot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSnapshot {

  private String username;

  private String displayName;

  private String email;

}
