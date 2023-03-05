package org.carranza.msvc.gestion.ventas.api.service.impl;

import org.carranza.msvc.gestion.ventas.api.dto.ArticuloDTO;
import org.carranza.msvc.gestion.ventas.api.entity.ArticuloEntity;
import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.mapper.ArticuloMapper;
import org.carranza.msvc.gestion.ventas.api.repository.ArticuloRepository;
import org.carranza.msvc.gestion.ventas.api.service.ArticuloService;
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
public class ArticuloServiceImpl implements ArticuloService {
    private final ArticuloRepository ArticuloRepository;

    private final ArticuloMapper ArticuloMapper = org.carranza.msvc.gestion.ventas.api.mapper.ArticuloMapper.INSTANCE;

    public ArticuloServiceImpl(ArticuloRepository ArticuloRepository) {
        this.ArticuloRepository = ArticuloRepository;
    }


    @Override
    public Page<ArticuloDTO> findAllPage(Pageable paginador) {
        Page<ArticuloEntity> lstArticuloEntity = ArticuloRepository.findAllPage(paginador);
        List<ArticuloDTO>  result = lstArticuloEntity.stream().map(ArticuloMapper::entityToGetDto).collect(Collectors.toList());

        return (Page<ArticuloDTO>) PageUtil.paginate(result, paginador, lstArticuloEntity.getTotalElements());
    }

    @Override
    public List<ArticuloDTO> findAll() {
        var lstArticuloEntity = ArticuloRepository.findAll();


        return lstArticuloEntity.stream().map(ArticuloMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public ArticuloDTO findById(UUID id) {

        ArticuloEntity Articulo = this.ArticuloRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return ArticuloMapper.entityToGetDto(Articulo);
    }

    @Override
    public ArticuloDTO save(ArticuloDTO ArticuloDTO) {
        var ArticuloEntity = ArticuloMapper.postDtoToEntity(ArticuloDTO);

        var save = this.ArticuloRepository.save(ArticuloEntity);

        return ArticuloMapper.entityToGetDto(save);
    }

    @Override
    public ArticuloDTO update(UUID id, ArticuloDTO ArticuloDTO) {
        ArticuloEntity entity = this.ArticuloRepository.findById(id).orElseThrow(() -> new NotFoundException("Articulo no encontrado para este id :: " + id));
        ArticuloEntity updated = ArticuloMapper.postDtoToEntity(ArticuloDTO);
        if(updated.getNombre() != null){
            entity.setNombre(updated.getNombre());
        }
        if(updated.getDescripcion() != null){
            entity.setDescripcion(updated.getDescripcion());
        }
        if(updated.getCodigo() != null){
            entity.setCodigo(updated.getCodigo());
        }
        if(updated.getPrecioVenta() != null){
            entity.setPrecioVenta(updated.getPrecioVenta());
        }
        if(updated.getStock() != null){
            entity.setStock(updated.getStock());
        }
        if(updated.getCategoria() != null){
            entity.setCategoria(updated.getCategoria());
        }

        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        ArticuloRepository.save(entity);
        return ArticuloMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, ArticuloDTO ArticuloDTO) {
        ArticuloEntity entity = this.ArticuloRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(ArticuloDTO.getUsuarioCreacion());
        entity.setEstado(ArticuloDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.ArticuloRepository.save(entity);
        return true;
    }

    @Override
    public List<ArticuloDTO> findByLikeNombre(String nombre) {
        Optional<ArticuloEntity> lstArticuloEntity = this.ArticuloRepository.findByLikeNombre(nombre);
        return lstArticuloEntity.stream().map(ArticuloMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public ArticuloDTO findByCodigo(String codigo) {
        ArticuloEntity Articulo = this.ArticuloRepository.findByCodigo(codigo).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return ArticuloMapper.entityToGetDto(Articulo);
    }
}
