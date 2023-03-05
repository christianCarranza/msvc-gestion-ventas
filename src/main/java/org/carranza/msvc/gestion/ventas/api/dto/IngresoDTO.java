package org.carranza.msvc.gestion.ventas.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.carranza.msvc.gestion.ventas.security.dto.UsuarioDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class IngresoDTO extends AuditoriaDTO {
    private UUID id;
    private String tipoComprobante;
    private String serieComprobante;
    private String numComprobante;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaHora;
    private double impuesto;
    private double total;

    private PersonaDTO proveedor;

    private UsuarioDTO usuario;

}

