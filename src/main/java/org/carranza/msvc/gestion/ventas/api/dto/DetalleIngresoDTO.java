package org.carranza.msvc.gestion.ventas.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class DetalleIngresoDTO extends AuditoriaDTO {
    private UUID id;
    private IngresoDTO ingreso;
    private ArticuloDTO articulo;
    private double cantidad;
    private double precio;

}
