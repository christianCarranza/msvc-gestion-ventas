package org.carranza.msvc.gestion.ventas.api.mapper;

import org.carranza.msvc.gestion.ventas.api.dto.CategoriaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriaMapper {

    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    CategoriaEntity postDtoToEntity(CategoriaDTO dto);

    CategoriaDTO entityToGetDto(CategoriaEntity entity);

}
