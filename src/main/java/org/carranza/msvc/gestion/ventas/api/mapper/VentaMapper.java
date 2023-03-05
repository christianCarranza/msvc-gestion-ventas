package org.carranza.msvc.gestion.ventas.api.mapper;

import org.carranza.msvc.gestion.ventas.api.dto.VentaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VentaMapper {

    VentaMapper INSTANCE = Mappers.getMapper(VentaMapper.class);

    VentaEntity postDtoToEntity(VentaDTO dto);

    VentaDTO entityToGetDto(VentaEntity entity);

}
