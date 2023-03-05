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
public class CategoriaDTO extends AuditoriaDTO {

    private UUID id;
    private String nombre;
    private String descripcion;

}
