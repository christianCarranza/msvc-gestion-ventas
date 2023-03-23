package org.carranza.msvc.gestion.ventas.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ArticuloDTO extends AuditoriaDTO {
    private UUID id;

    private String codigo;

    private String nombre;

    private Double precioVenta;

    private Integer stock;

    private String descripcion;

    private CategoriaDTO categoria;
}
