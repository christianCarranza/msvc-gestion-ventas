package org.carranza.msvc.gestion.ventas.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.carranza.msvc.gestion.ventas.security.entity.UsuarioEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "ventas")
public class VentaEntity extends org.carranza.msvc.gestion.ventas.api.entity.AuditoriaEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "idventa", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;

    @Column(name = "tipo_comprobante", nullable = false)
    private String tipoComprobante;

    @Column(name = "serie_comprobante")
    private String serieComprobante;

    @Column(name = "num_comprobante")
    private String numComprobante;

    @Column(name = "fecha_hora", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "impuesto")
    private Double impuesto;

    @Column(name = "total")
    private Double total;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcliente", nullable = false)
    private org.carranza.msvc.gestion.ventas.api.entity.PersonaEntity cliente;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private UsuarioEntity usuario;

    private static final long serialVersionUID = -2170897015344177815L;

}
