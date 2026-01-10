package ru.ryatronth.sd.ticket.domain.category;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketCategoryEntityRepository extends JpaRepository<TicketCategoryEntity, UUID>,
    JpaSpecificationExecutor<TicketCategoryEntity> {

  Optional<TicketCategoryEntity> findByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCase(String name);

}