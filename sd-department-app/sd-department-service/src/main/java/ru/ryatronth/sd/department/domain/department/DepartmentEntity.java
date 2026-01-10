package ru.ryatronth.sd.department.domain.department;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
import ru.ryatronth.sd.department.domain.code.DepartmentCodeEntity;
import ru.ryatronth.sd.department.domain.type.DepartmentTypeEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "department",
    indexes = {
        @Index(name = "idx_department_parent_id", columnList = "parent_id"),
        @Index(name = "idx_department_type_id", columnList = "type_id"),
        @Index(name = "idx_department_code", columnList = "code")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_department_code", columnNames = "code")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parent", "children", "type", "code"})
public class DepartmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "parent_id",
      foreignKey = @ForeignKey(name = "fk_department_parent")
  )
  private DepartmentEntity parent;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  @Builder.Default
  private Set<DepartmentEntity> children = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "code_id",
      nullable = false,
      unique = true,
      foreignKey = @ForeignKey(name = "fk_department_code_id")
  )
  private DepartmentCodeEntity code;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "type_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_department_type")
  )
  private DepartmentTypeEntity type;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "area", nullable = false, length = 255)
  private String area;

  @Column(name = "address", nullable = false, length = 512)
  private String address;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

}
