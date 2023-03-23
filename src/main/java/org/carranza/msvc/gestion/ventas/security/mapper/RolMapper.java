package org.carranza.msvc.gestion.ventas.security.mapper;

import org.carranza.msvc.gestion.ventas.security.dto.RolDTO;
import org.carranza.msvc.gestion.ventas.security.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolMapper {

    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);

    RolEntity postDtoToEntity(RolDTO dto);

    RolDTO entityToGetDto(RolEntity entity);

}
