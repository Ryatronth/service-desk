package ru.ryatronth.sd.iamsync.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IamUserRepository
    extends JpaRepository<IamUserEntity, UUID>, JpaSpecificationExecutor<IamUserEntity> {

  @Override
  @EntityGraph(attributePaths = "roles")
  Page<IamUserEntity> findAll(Specification<IamUserEntity> spec, Pageable pageable);

  @EntityGraph(attributePaths = "roles")
  Optional<IamUserEntity> findById(UUID id);

  @EntityGraph(attributePaths = "roles")
  Optional<IamUserEntity> findByUsernameIgnoreCase(String username);
}

