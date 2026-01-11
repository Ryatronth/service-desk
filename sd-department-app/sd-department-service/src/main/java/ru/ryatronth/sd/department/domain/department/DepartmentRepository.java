package ru.ryatronth.sd.department.domain.department;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
    extends JpaRepository<DepartmentEntity, UUID>, JpaSpecificationExecutor<DepartmentEntity> {

  @Override
  @EntityGraph(attributePaths = {"parent", "type", "code"})
  Optional<DepartmentEntity> findById(UUID id);

  @Override
  @EntityGraph(attributePaths = {"parent", "type", "code"})
  Page<DepartmentEntity> findAll(Specification<DepartmentEntity> spec, Pageable pageable);

}
