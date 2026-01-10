package ru.ryatronth.sd.department.domain.code;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentCodeRepository extends JpaRepository<DepartmentCodeEntity, UUID>,
    JpaSpecificationExecutor<DepartmentCodeEntity> {

  Optional<DepartmentCodeEntity> findByCodeIgnoreCase(String code);

}
