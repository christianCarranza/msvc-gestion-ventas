package org.carranza.msvc.gestion.ventas.api.service.impl;

import org.carranza.msvc.gestion.ventas.api.dto.IngresoDTO;
import org.carranza.msvc.gestion.ventas.api.entity.IngresoEntity;
import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.mapper.IngresoMapper;
import org.carranza.msvc.gestion.ventas.api.repository.IngresoRepository;
import org.carranza.msvc.gestion.ventas.api.service.IngresoService;
import org.carranza.msvc.gestion.ventas.api.utils.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IngresoServiceImpl implements IngresoService {
    private final IngresoRepository IngresoRepository;

    private final IngresoMapper IngresoMapper = org.carranza.msvc.gestion.ventas.api.mapper.IngresoMapper.INSTANCE;

    public IngresoServiceImpl(IngresoRepository IngresoRepository) {
        this.IngresoRepository = IngresoRepository;
    }


    @Override
    public Page<IngresoDTO> findAllPage(Pageable paginador) {
        Page<IngresoEntity> lstIngresoEntity = IngresoRepository.findAllPage(paginador);
        List<IngresoDTO>  result = lstIngresoEntity.stream().map(IngresoMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<IngresoDTO>) PageUtil.paginate(result, paginador, lstIngresoEntity.getTotalElements());
    }

    @Override
    public List<IngresoDTO> findAll() {
        var lstIngresoEntity = IngresoRepository.findAll();


        return lstIngresoEntity.stream().map(IngresoMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public IngresoDTO findById(UUID id) {

        IngresoEntity Ingreso = this.IngresoRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return IngresoMapper.entityToGetDto(Ingreso);
    }

    @Override
    public IngresoDTO save(IngresoDTO IngresoDTO) {
        var IngresoEntity = IngresoMapper.postDtoToEntity(IngresoDTO);

        var save = this.IngresoRepository.save(IngresoEntity);

        return IngresoMapper.entityToGetDto(save);
    }

    @Override
    public IngresoDTO update(UUID id, IngresoDTO IngresoDTO) {
        IngresoEntity entity = this.IngresoRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
        IngresoEntity updated = IngresoMapper.postDtoToEntity(IngresoDTO);
        if(updated.getTipoComprobante() != null){
            entity.setTipoComprobante(updated.getTipoComprobante());
        }
        if(updated.getSerieComprobante() != null){
            entity.setSerieComprobante(updated.getSerieComprobante());
        }
        if(updated.getNumComprobante() != null){
            entity.setNumComprobante(updated.getNumComprobante());
        }
        if(updated.getFechaHora() != null){
            entity.setFechaHora(updated.getFechaHora());
        }
        if(updated.getImpuesto() != null){
            entity.setImpuesto(updated.getImpuesto());
        }
        if(updated.getTotal() != null){
            entity.setTotal(updated.getTotal());
        }
        if(updated.getProveedor() != null){
            entity.setProveedor(updated.getProveedor());
        }
        if(updated.getUsuario() != null){
            entity.setUsuario(updated.getUsuario());
        }


        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        IngresoRepository.save(entity);
        return IngresoMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, IngresoDTO IngresoDTO) {
        IngresoEntity entity = this.IngresoRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(IngresoDTO.getUsuarioCreacion());
        entity.setEstado(IngresoDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.IngresoRepository.save(entity);
        return true;
    }

}
