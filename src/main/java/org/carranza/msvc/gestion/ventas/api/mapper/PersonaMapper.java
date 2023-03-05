package org.carranza.msvc.gestion.ventas.api.mapper;

import org.carranza.msvc.gestion.ventas.api.dto.PersonaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.PersonaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonaMapper {

    PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);

    PersonaEntity postDtoToEntity(PersonaDTO dto);

    PersonaDTO entityToGetDto(PersonaEntity entity);

}
