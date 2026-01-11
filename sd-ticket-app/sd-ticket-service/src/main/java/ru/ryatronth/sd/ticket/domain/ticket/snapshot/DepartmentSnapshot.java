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
public class DepartmentSnapshot {

  private String code;

  private String name;

  private String area;

  private String address;
}
