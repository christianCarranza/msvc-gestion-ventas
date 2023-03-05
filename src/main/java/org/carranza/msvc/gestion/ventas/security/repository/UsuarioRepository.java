package org.carranza.msvc.gestion.ventas.security.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{

	@Query("select u from UsuarioEntity u where u.usuario=:usuario and u.estado=1")
	Optional<UsuarioEntity> loadUserByUsuario(String usuario);

	@Query("select u from UsuarioEntity u where u.codigo2F=:codigo2F and u.estado=1")
	Optional<UsuarioEntity> findByUsuario(String codigo2F);

	@Modifying
	@Query(nativeQuery = true,value ="UPDATE UsuarioEntity SET CODIGO_2F=:codigo2F WHERE USUARIO=:usuario")
	void updateCodigo2F(@Param("codigo2F") String codigo2F,@Param("usuario") String usuario);

}
