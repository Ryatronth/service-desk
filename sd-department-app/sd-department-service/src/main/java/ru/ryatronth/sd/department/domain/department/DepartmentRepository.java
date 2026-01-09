package ru.ryatronth.sd.department.domain.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID>, JpaSpecificationExecutor<DepartmentEntity> {}
