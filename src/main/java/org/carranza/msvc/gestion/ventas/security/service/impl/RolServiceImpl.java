package org.carranza.msvc.gestion.ventas.security.service.impl;

import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.utils.PageUtil;
import org.carranza.msvc.gestion.ventas.security.dto.RolDTO;
import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.carranza.msvc.gestion.ventas.security.mapper.RolMapper;
import org.carranza.msvc.gestion.ventas.security.repository.RolRepository;
import org.carranza.msvc.gestion.ventas.security.service.RolService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;

    private final RolMapper rolMapper = org.carranza.msvc.gestion.ventas.security.mapper.RolMapper.INSTANCE;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }


    @Override
    public Page<RolDTO> findAllPage(Pageable paginador) {
        Page<RolEntity> lstRolEntity = rolRepository.findAllPage(paginador);
        List<RolDTO>  result = lstRolEntity.stream().map(rolMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<RolDTO>) PageUtil.paginate(result, paginador, lstRolEntity.getTotalElements());
    }

    @Override
    public List<RolDTO> findAll() {
        var lstRolEntity = rolRepository.findAll();


        return lstRolEntity.stream().map(rolMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public RolDTO findById(UUID id) {

        RolEntity rol = this.rolRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return rolMapper.entityToGetDto(rol);
    }

    @Override
    public RolDTO save(RolDTO RolDTO) {
        var rolEntity = rolMapper.postDtoToEntity(RolDTO);

        var save = this.rolRepository.save(rolEntity);

        return rolMapper.entityToGetDto(save);
    }

    @Override
    public RolDTO update(UUID id, RolDTO rolDTO) {
        RolEntity entity = this.rolRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
        RolEntity updated = rolMapper.postDtoToEntity(rolDTO);
        if(updated.getNombre() != null){
            entity.setNombre(updated.getNombre());
        }
        if(updated.getDescripcion() != null){
            entity.setDescripcion(updated.getDescripcion());
        }

        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        rolRepository.save(entity);
        return rolMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, RolDTO rolDTO) {
        RolEntity entity = this.rolRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(rolDTO.getUsuarioCreacion());
        entity.setEstado(rolDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.rolRepository.save(entity);
        return true;
    }

}
