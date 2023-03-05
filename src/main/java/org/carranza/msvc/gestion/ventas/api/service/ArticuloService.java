

package org.carranza.msvc.gestion.ventas.api.service;

import org.carranza.msvc.gestion.ventas.api.dto.ArticuloDTO;

import java.util.List;

public interface ArticuloService extends GenericService<ArticuloDTO>{
    List<ArticuloDTO> findByLikeNombre(String nombre);
    ArticuloDTO findByCodigo(String codigo);
}

