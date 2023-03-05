package org.carranza.msvc.gestion.ventas.api.service.impl;

import org.carranza.msvc.gestion.ventas.api.dto.PersonaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.PersonaEntity;
import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.mapper.PersonaMapper;
import org.carranza.msvc.gestion.ventas.api.repository.PersonaRepository;
import org.carranza.msvc.gestion.ventas.api.service.PersonaService;
import org.carranza.msvc.gestion.ventas.api.utils.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonaServiceImpl implements PersonaService {
    private final PersonaRepository PersonaRepository;

    private final PersonaMapper PersonaMapper = org.carranza.msvc.gestion.ventas.api.mapper.PersonaMapper.INSTANCE;

    public PersonaServiceImpl(PersonaRepository PersonaRepository) {
        this.PersonaRepository = PersonaRepository;
    }


    @Override
    public Page<PersonaDTO> findAllPage(Pageable paginador) {
        Page<PersonaEntity> lstPersonaEntity = PersonaRepository.findAllPage(paginador);
        List<PersonaDTO>  result = lstPersonaEntity.stream().map(PersonaMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<PersonaDTO>) PageUtil.paginate(result, paginador, lstPersonaEntity.getTotalElements());
    }

    @Override
    public List<PersonaDTO> findAll() {
        var lstPersonaEntity = PersonaRepository.findAll();


        return lstPersonaEntity.stream().map(PersonaMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public PersonaDTO findById(UUID id) {

        PersonaEntity Persona = this.PersonaRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return PersonaMapper.entityToGetDto(Persona);
    }

    @Override
    public PersonaDTO save(PersonaDTO PersonaDTO) {
        var PersonaEntity = PersonaMapper.postDtoToEntity(PersonaDTO);

        var save = this.PersonaRepository.save(PersonaEntity);

        return PersonaMapper.entityToGetDto(save);
    }

    @Override
    public PersonaDTO update(UUID id, PersonaDTO PersonaDTO) {
        PersonaEntity entity = this.PersonaRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
        PersonaEntity updated = PersonaMapper.postDtoToEntity(PersonaDTO);
        if(updated.getNombre() != null){
            entity.setNombre(updated.getNombre());
        }
        if(updated.getTipoPersona() != null){
            entity.setTipoPersona(updated.getTipoPersona());
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

        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        PersonaRepository.save(entity);
        return PersonaMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, PersonaDTO PersonaDTO) {
        PersonaEntity entity = this.PersonaRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(PersonaDTO.getUsuarioCreacion());
        entity.setEstado(PersonaDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.PersonaRepository.save(entity);
        return true;
    }

    @Override
    public Page<PersonaDTO> findAllPageCliente(Pageable paginador) {
        Page<PersonaEntity> lstPersonaEntity = PersonaRepository.findAllPageCliente(paginador);
        List<PersonaDTO>  result = lstPersonaEntity.stream().map(PersonaMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<PersonaDTO>) PageUtil.paginate(result, paginador, lstPersonaEntity.getTotalElements());
    }

    @Override
    public Page<PersonaDTO> findAllPageProveedor(Pageable paginador) {
        Page<PersonaEntity> lstPersonaEntity = PersonaRepository.findAllPageProveedor(paginador);
        List<PersonaDTO>  result = lstPersonaEntity.stream().map(PersonaMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<PersonaDTO>) PageUtil.paginate(result, paginador, lstPersonaEntity.getTotalElements());
    }
}
