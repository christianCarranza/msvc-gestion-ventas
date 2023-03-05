package org.carranza.msvc.gestion.ventas.api.service;

import org.carranza.msvc.gestion.ventas.api.dto.PersonaDTO;
import org.carranza.msvc.gestion.ventas.api.entity.PersonaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonaService extends GenericService<PersonaDTO>{
    Page<PersonaDTO> findAllPageCliente(Pageable paginador);
    Page<PersonaDTO> findAllPageProveedor(Pageable paginador);
}

