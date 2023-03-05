package org.carranza.msvc.gestion.ventas.api.repository;

import org.carranza.msvc.gestion.ventas.api.entity.VentaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, UUID> {

    @Query("select c from VentaEntity c")
    Page<VentaEntity> findAllPage(Pageable pageable);
}
