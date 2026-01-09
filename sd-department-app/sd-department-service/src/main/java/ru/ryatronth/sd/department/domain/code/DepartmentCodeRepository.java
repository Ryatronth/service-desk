package ru.ryatronth.sd.department.domain.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentCodeRepository extends JpaRepository<DepartmentCodeEntity, UUID>, JpaSpecificationExecutor<DepartmentCodeEntity> {

    Optional<DepartmentCodeEntity> findByCodeIgnoreCase(String code);

}
