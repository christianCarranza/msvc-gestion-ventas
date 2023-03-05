package org.carranza.msvc.gestion.ventas.security.service.impl;

import org.carranza.msvc.gestion.ventas.security.service.UsuarioService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.repository.UsuarioRepository;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	@Transactional
	@Override
	public Boolean updateCodigo2F(String codigo2f, String usuario) {
		try {
			usuarioRepository.updateCodigo2F(codigo2f, usuario);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public UsuarioEntity findByUsuario(String codigo2f) {
		return usuarioRepository.findByUsuario(codigo2f).get();
	}

}
