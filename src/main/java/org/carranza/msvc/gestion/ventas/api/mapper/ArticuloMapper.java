package org.carranza.msvc.gestion.ventas.api.mapper;

import org.carranza.msvc.gestion.ventas.api.dto.ArticuloDTO;
import org.carranza.msvc.gestion.ventas.api.entity.ArticuloEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArticuloMapper {

    ArticuloMapper INSTANCE = Mappers.getMapper(ArticuloMapper.class);

    ArticuloEntity postDtoToEntity(ArticuloDTO dto);

    ArticuloDTO entityToGetDto(ArticuloEntity entity);

}