package org.carranza.msvc.gestion.ventas.security.repository;

import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, UUID> {

    @Query("select c from RolEntity c")
    Page<RolEntity> findAllPage(Pageable pageable);

}
