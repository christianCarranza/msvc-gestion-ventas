package org.carranza.msvc.gestion.ventas.security.service;

import org.carranza.msvc.gestion.ventas.security.dto.RolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RolService {

    Page<RolDTO> findAllPage(Pageable paginador);

    List<RolDTO> findAll();

    RolDTO findById(UUID id);

    RolDTO save(RolDTO rolDTO);

    RolDTO update(UUID id, RolDTO rolDTO);

    Boolean delete (UUID id, RolDTO rolDTO);

}
