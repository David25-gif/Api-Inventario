package org.Api_Inventario.Seguridad.Modelos;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "usuarios",
        uniqueConstraints = @UniqueConstraint(columnNames = "login")
)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String apellido;

    private String telefono;

    @Column(nullable = false)
    private String login;

    private String clave;

    @ManyToOne
    @JoinColumn(name = "RolId")
    private Rol rol;

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