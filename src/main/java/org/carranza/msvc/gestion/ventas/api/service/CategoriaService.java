package org.carranza.msvc.gestion.ventas.api.service;

import org.carranza.msvc.gestion.ventas.api.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService extends GenericService<CategoriaDTO>{
    List<CategoriaDTO> findByLikeNombre(String nombre);
}

