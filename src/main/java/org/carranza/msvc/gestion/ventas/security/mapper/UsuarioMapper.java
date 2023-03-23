package org.carranza.msvc.gestion.ventas.security.mapper;

import org.carranza.msvc.gestion.ventas.security.dto.UsuarioDTO;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioEntity postDtoToEntity(UsuarioDTO dto);

    UsuarioDTO entityToGetDto(UsuarioEntity entity);

}
