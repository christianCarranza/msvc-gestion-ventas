package org.carranza.msvc.gestion.ventas.security.service;

import org.carranza.msvc.gestion.ventas.security.dto.UsuarioDTO;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
	
	 Boolean updateCodigo2F(String codigo2F,String usuario);
	 
	 UsuarioEntity findByUsuario(String codigo2F);

	 //otros

	Page<UsuarioDTO> findAllPage(Pageable paginador);

	List<UsuarioDTO> findAll();

	UsuarioDTO findById(UUID id);

	UsuarioDTO save(UsuarioDTO usuarioDTO);

	UsuarioDTO update(UUID id, UsuarioDTO usuarioDTO);

	Boolean delete (UUID id, UsuarioDTO usuarioDTO);
}
