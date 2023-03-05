package org.carranza.msvc.gestion.ventas.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.carranza.msvc.gestion.ventas.api.dto.AuditoriaDTO;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class RolDTO extends AuditoriaDTO {
    private UUID id;
    private String nombre;

    private String descripcion;

}
