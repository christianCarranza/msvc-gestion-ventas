package org.carranza.msvc.gestion.ventas.api.repository;

import org.carranza.msvc.gestion.ventas.api.entity.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository  extends JpaRepository<CategoriaEntity, UUID> {

    @Query("select c from CategoriaEntity c where UPPER(c.nombre) like UPPER(:nombre) ORDER BY c.nombre")
    Optional<CategoriaEntity> findByLikeNombre(@Param("nombre") String nombre);

    @Query("select c from CategoriaEntity c")
    Page<CategoriaEntity> findAllPage(Pageable pageable);
}
