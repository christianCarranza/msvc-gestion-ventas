package org.carranza.msvc.gestion.ventas.api.service.impl;

import org.carranza.msvc.gestion.ventas.api.dto.CategoriaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.CategoriaEntity;
import org.carranza.msvc.gestion.ventas.api.exception.NotFoundException;
import org.carranza.msvc.gestion.ventas.api.mapper.CategoriaMapper;
import org.carranza.msvc.gestion.ventas.api.repository.CategoriaRepository;
import org.carranza.msvc.gestion.ventas.api.service.CategoriaService;
import org.carranza.msvc.gestion.ventas.api.utils.FechasUtil;
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
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;

    private final CategoriaMapper categoriaMapper = CategoriaMapper.INSTANCE;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }


    @Override
    public Page<CategoriaDTO> findAllPage(Pageable paginador) {
        Page<CategoriaEntity> lstCategoriaEntity = categoriaRepository.findAllPage(paginador);
        List<CategoriaDTO>  result = lstCategoriaEntity.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());

        result.forEach(categoriaDTO -> categoriaDTO.setFechaCreacion(null));//quitar

        return (Page<CategoriaDTO>) PageUtil.paginate(result, paginador, lstCategoriaEntity.getTotalElements());
    }

    @Override
    public List<CategoriaDTO> findAll() {
        var lstCategoriaEntity = categoriaRepository.findAll();


        return lstCategoriaEntity.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO findById(UUID id) {

        CategoriaEntity categoria = this.categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("No existe un registro para el ID suministrado."));

        return categoriaMapper.entityToGetDto(categoria);
    }

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        var categoriaEntity = categoriaMapper.postDtoToEntity(categoriaDTO);

        var save = this.categoriaRepository.save(categoriaEntity);

        return categoriaMapper.entityToGetDto(save);
    }

    @Override
    public CategoriaDTO update(UUID id, CategoriaDTO categoriaDTO) {
        CategoriaEntity entity = this.categoriaRepository.findById(id).orElseThrow(() -> new NotFoundException("Caterogia no encontrado para este id :: " + id));
        CategoriaEntity updated = categoriaMapper.postDtoToEntity(categoriaDTO);
        if(updated.getNombre() != null){
            entity.setNombre(updated.getNombre());
        }
        if(updated.getDescripcion() != null){
            entity.setDescripcion(updated.getDescripcion());
        }

        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(updated.getUsuarioCreacion());
        categoriaRepository.save(entity);
        return categoriaMapper.entityToGetDto(entity);
    }

    @Override
    public Boolean delete(UUID id, CategoriaDTO categoriaDTO) {
        CategoriaEntity entity = this.categoriaRepository.findById(id).orElseThrow(RuntimeException::new);
        entity.setFechaModificacion(LocalDateTime.now());
        entity.setUsuarioModificacion(categoriaDTO.getUsuarioCreacion());
        entity.setEstado(categoriaDTO.getEstado()); // 0 = Inactivo, 1 = Activo
        this.categoriaRepository.save(entity);
        return true;
    }

    @Override
    public List<CategoriaDTO> findByLikeNombre(String nombre) {
        Optional<CategoriaEntity> lstCategoriaEntity = this.categoriaRepository.findByLikeNombre(nombre);
        return lstCategoriaEntity.stream().map(categoriaMapper::entityToGetDto).collect(Collectors.toList());
    }
}
