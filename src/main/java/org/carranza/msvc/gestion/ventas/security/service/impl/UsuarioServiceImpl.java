package org.carranza.msvc.gestion.ventas.security.service.impl;

import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.utils.PageUtil;
import org.carranza.msvc.gestion.ventas.security.dto.RolDTO;
import org.carranza.msvc.gestion.ventas.security.dto.UsuarioDTO;
import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.carranza.msvc.gestion.ventas.security.mapper.UsuarioMapper;
import org.carranza.msvc.gestion.ventas.security.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.carranza.msvc.gestion.ventas.security.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

	@Transactional
	@Override
	public Boolean updateCodigo2F(String codigo2f, String usuario) {
		try {

			UsuarioEntity usuarioEntity = this.usuarioRepository.findByUsuario(usuario).get();
			usuarioEntity.setCodigo2F(codigo2f);
			usuarioEntity.setFechaModificacion(LocalDateTime.now());
			usuarioRepository.saveAndFlush(usuarioEntity);

//			usuarioRepository.updateCodigo2F(codigo2f, usuario);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public UsuarioEntity findByCodigo(String codigo2f) {
		try {
			return usuarioRepository.findByCodigo(codigo2f).get();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UsuarioEntity findByUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario).get();
	}

	@Override
	public Page<UsuarioDTO> findAllPage(Pageable paginador) {
		Page<UsuarioEntity> lstUsuarioEntity = usuarioRepository.findAllPage(paginador);
		List<UsuarioDTO>  result = lstUsuarioEntity.stream().map(usuarioMapper::entityToGetDto).collect(Collectors.toList());

		return (Page<UsuarioDTO>) PageUtil.paginate(result, paginador, lstUsuarioEntity.getTotalElements());
	}

	@Override
	public List<UsuarioDTO> findAll() {
		var lstRolEntity = usuarioRepository.findAll();


		return lstRolEntity.stream().map(usuarioMapper::entityToGetDto).collect(Collectors.toList());
	}

	@Override
	public UsuarioDTO findById(UUID id) {

		UsuarioEntity rol = this.usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

		return usuarioMapper.entityToGetDto(rol);
	}

	@Override
	public UsuarioDTO save(UsuarioDTO usuarioDTO) {
		var rolEntity = usuarioMapper.postDtoToEntity(usuarioDTO);

		var save = this.usuarioRepository.save(rolEntity);

		return usuarioMapper.entityToGetDto(save);
	}

	@Override
	public UsuarioDTO update(UUID id, UsuarioDTO usuarioDTO) {
		UsuarioEntity entity = this.usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
		UsuarioEntity updated = usuarioMapper.postDtoToEntity(usuarioDTO);

		if (updated.getUsuario() != null) {
			entity.setUsuario(updated.getUsuario());
		}
		if(updated.getCodigo2F()!=null) {
			entity.setCodigo2F(updated.getCodigo2F());
		}
		if(updated.getNombre() != null){
			entity.setNombre(updated.getNombre());
		}
		if(updated.getTipoDocumento() != null){
			entity.setTipoDocumento(updated.getTipoDocumento());
		}
		if(updated.getNumDocumento() != null){
			entity.setNumDocumento(updated.getNumDocumento());
		}
		if(updated.getDireccion() != null){
			entity.setDireccion(updated.getDireccion());
		}
		if(updated.getTelefono() != null){
			entity.setTelefono(updated.getTelefono());
		}
		if(updated.getEmail() != null){
			entity.setEmail(updated.getEmail());
		}
		if(updated.getClave() != null){
			entity.setClave(updated.getClave());
		}

		entity.setFechaModificacion(LocalDateTime.now());
		entity.setUsuarioModificacion(updated.getUsuarioCreacion());
		usuarioRepository.save(entity);
		return usuarioMapper.entityToGetDto(entity);
	}

	@Override
	public Boolean delete(UUID id, UsuarioDTO usuarioDTO) {
		UsuarioEntity entity = this.usuarioRepository.findById(id).orElseThrow(RuntimeException::new);
		entity.setFechaModificacion(LocalDateTime.now());
		entity.setUsuarioModificacion(usuarioDTO.getUsuarioCreacion());
		entity.setEstado(usuarioDTO.getEstado()); // 0 = Inactivo, 1 = Activo
		this.usuarioRepository.save(entity);
		return true;
	}

}
