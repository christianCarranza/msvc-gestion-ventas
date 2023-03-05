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
public class DetalleVentaDTO extends ArticuloDTO{
    private UUID id;
    private Integer cantidad;
    private double precio;
    private double descuento;
    private VentaDTO venta;
    private ArticuloDTO articulo;

}
