package org.Api_Inventario.Seguridad.Modelos;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "Users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "UserName"),
                @UniqueConstraint(columnNames = "Email"),
                @UniqueConstraint(columnNames = "PersonId")
        }
)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private Integer idUsuario;

    @Column(name = "UserName", nullable = false, length = 50)
    private String login;

    @Column(name = "Email", nullable = false, length = 255)
    private String email;

    @Column(name = "PasswordHash", nullable = false, length = 255)
    private String clave;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "TempPasswordHash", length = 255)
    private String claveTemporal;

    @Column(name = "TempPasswordExpiry")
    private LocalDateTime expiracionClaveTemporal;

    /*
     * Se mantiene la relación con Rol porque Spring Security
     * necesita conocer el rol del usuario autenticado.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RolId", nullable = false)
    private Rol rol;

    /*
     * Se captura el IdPersona en transaccion.
     */
    @Column(name = "PersonId", nullable = false)
    private Integer idPersona;

    @Column(name = "StatusId", nullable = false)
    private Integer idEstado;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        // Spring Security trabaja con el prefijo ROLE_ al utilizar hasRole().
        return List.of(
                new SimpleGrantedAuthority(
                        "ROLE_" + rol.getNombre().toUpperCase()
                )
        );
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}