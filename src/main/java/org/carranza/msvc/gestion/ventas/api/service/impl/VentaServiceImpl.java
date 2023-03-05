package org.carranza.msvc.gestion.ventas.api.service.impl;

import org.carranza.msvc.gestion.ventas.api.dto.VentaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.VentaEntity;
import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.mapper.VentaMapper;
import org.carranza.msvc.gestion.ventas.api.repository.VentaRepository;
import org.carranza.msvc.gestion.ventas.api.service.VentaService;
import org.carranza.msvc.gestion.ventas.api.utils.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {
    private final VentaRepository VentaRepository;

    private final VentaMapper VentaMapper = org.carranza.msvc.gestion.ventas.api.mapper.VentaMapper.INSTANCE;

    public VentaServiceImpl(VentaRepository VentaRepository) {
        this.VentaRepository = VentaRepository;
    }


    @Override
    public Page<VentaDTO> findAllPage(Pageable paginador) {
        Page<VentaEntity> lstVentaEntity = VentaRepository.findAllPage(paginador);
        List<VentaDTO>  result = lstVentaEntity.stream().map(VentaMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<VentaDTO>) PageUtil.paginate(result, paginador, lstVentaEntity.getTotalElements());
    }

    @Override
    public List<VentaDTO> findAll() {
        var lstVentaEntity = VentaRepository.findAll();


        return lstVentaEntity.stream().map(VentaMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public VentaDTO findById(UUID id) {

        VentaEntity Venta = this.VentaRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return VentaMapper.entityToGetDto(Venta);
    }

    @Override
    public VentaDTO save(VentaDTO VentaDTO) {
        var VentaEntity = VentaMapper.postDtoToEntity(VentaDTO);

        var save = this.VentaRepository.save(VentaEntity);

        return VentaMapper.entityToGetDto(save);
    }

    @Override
    public VentaDTO update(UUID id, VentaDTO VentaDTO) {
        VentaEntity entity = this.VentaRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
        VentaEntity updated = VentaMapper.postDtoToEntity(VentaDTO);
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
        if(updated.getCliente() != null){
            entity.setCliente(updated.getCliente());
        }
        if(updated.getUsuario() != null){
            entity.setUsuario(updated.getUsuario());
        }


        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        VentaRepository.save(entity);
        return VentaMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, VentaDTO VentaDTO) {
        VentaEntity entity = this.VentaRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(VentaDTO.getUsuarioCreacion());
        entity.setEstado(VentaDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.VentaRepository.save(entity);
        return true;
    }

}
