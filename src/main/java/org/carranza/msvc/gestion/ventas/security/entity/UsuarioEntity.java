package org.carranza.msvc.gestion.ventas.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.carranza.msvc.gestion.ventas.api.entity.AuditoriaEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioEntity extends AuditoriaEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "idusuario", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    private UUID id;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "CODIGO_2F")
    private String codigo2F;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "num_documento")
    private String numDocumento;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "clave", nullable = false)
    private String clave;

    // Authorities
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "TBL_USUARIO_ROL", joinColumns = { @JoinColumn(name = "idusuario") }, inverseJoinColumns = {
            @JoinColumn(name = "idrol") })
    private Set<RolEntity> roles = new HashSet<>();
    private static final long serialVersionUID = -2170897015344177815L;

}