package org.carranza.msvc.gestion.ventas.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.repository.UsuarioRepository;
import static java.util.Objects.isNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;

	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {
			
			UsuarioEntity usuarioEntity= usuarioRepository.loadUserByUsuario(username).orElseThrow(null);
			log.info("usuarioEntity {}",usuarioEntity);
			if (isNull(usuarioEntity)) {
				throw new UsernameNotFoundException("Usuario no encontrado!");
			}
			
			return User
					.builder()
					.username(usuarioEntity.getUsuario())
					.password(usuarioEntity.getClave())
					.authorities(getAuthorities(usuarioEntity.getRoles()))
					.build();
			
		} catch (Exception e) {
			throw new UsernameNotFoundException("Error al cargar usuario!");
		}

	}
	
	private List<GrantedAuthority> getAuthorities(Set<RolEntity> authorities){
		List<GrantedAuthority> authorityList = new ArrayList<>();
		for (RolEntity authorityEntity : authorities) {
			authorityList.add(new SimpleGrantedAuthority(authorityEntity.getNombre()));
		}
		log.info("authorityList {}",authorityList);
		return authorityList;
	}	
}
