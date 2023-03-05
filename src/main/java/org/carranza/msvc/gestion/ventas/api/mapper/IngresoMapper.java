package org.carranza.msvc.gestion.ventas.api.mapper;

import org.carranza.msvc.gestion.ventas.api.dto.IngresoDTO;
import org.carranza.msvc.gestion.ventas.api.entity.IngresoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngresoMapper {

    IngresoMapper INSTANCE = Mappers.getMapper(IngresoMapper.class);

    IngresoEntity postDtoToEntity(IngresoDTO dto);

    IngresoDTO entityToGetDto(IngresoEntity entity);

}