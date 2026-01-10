package ru.ryatronth.sd.department.domain.type;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentTypeRepository extends JpaRepository<DepartmentTypeEntity, UUID>,
    JpaSpecificationExecutor<DepartmentTypeEntity> {
}
