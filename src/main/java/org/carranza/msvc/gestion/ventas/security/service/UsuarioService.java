package org.carranza.msvc.gestion.ventas.security.service;

import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;

public interface UsuarioService {
	
	 Boolean updateCodigo2F(String codigo2F,String usuario);
	 
	 UsuarioEntity findByUsuario(String codigo2F);
}
