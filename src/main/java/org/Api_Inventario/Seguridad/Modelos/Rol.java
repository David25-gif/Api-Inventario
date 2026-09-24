package org.Api_Inventario.Seguridad.Modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RolId")
    private Integer idRol;

    @Column(name = "Name", nullable = false, length = 30)
    private String nombre;

    @Column(name = "Description", length = 200)
    private String descripcion;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "StatusId", nullable = false)
    private Integer idEstado;
}