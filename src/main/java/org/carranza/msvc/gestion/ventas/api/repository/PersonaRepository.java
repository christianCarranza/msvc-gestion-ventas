package org.carranza.msvc.gestion.ventas.api.repository;

import org.carranza.msvc.gestion.ventas.api.entity.PersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, UUID> {

    @Query("select c from PersonaEntity c")
    Page<PersonaEntity> findAllPage(Pageable pageable);

    @Query("select c from PersonaEntity c where c.tipoPersona = 'Cliente'")
    Page<PersonaEntity> findAllPageCliente(Pageable pageable);

    @Query("select c from PersonaEntity c where c.tipoPersona = 'Proveedor'")
    Page<PersonaEntity> findAllPageProveedor(Pageable pageable);
}
