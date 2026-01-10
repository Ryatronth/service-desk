package ru.ryatronth.sd.department.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.ryatronth.sd.department.domain.department.DepartmentEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department_type", uniqueConstraints = {
    @UniqueConstraint(name = "uk_department_type_name", columnNames = "name")})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "departments")
public class DepartmentTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @Column(name = "name", nullable = false, unique = true, length = 128)
  private String name;

  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  @Builder.Default
  private Set<DepartmentEntity> departments = new HashSet<>();

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

}
