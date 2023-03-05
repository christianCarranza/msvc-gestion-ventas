package org.carranza.msvc.gestion.ventas.api.repository;

import org.carranza.msvc.gestion.ventas.api.entity.IngresoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngresoRepository extends JpaRepository<IngresoEntity, UUID> {

    @Query("select c from IngresoEntity c")
    Page<IngresoEntity> findAllPage(Pageable pageable);
}
