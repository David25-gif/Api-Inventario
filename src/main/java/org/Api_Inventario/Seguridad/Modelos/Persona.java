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
@Table(name = "Person")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PersonId")
    private Integer idPersona;

    @Column(name = "FirstName", nullable = false, length = 100)
    private String nombre;

    @Column(name = "LastName", nullable = false, length = 100)
    private String apellido;

    @Column(name = "Adress", length = 250)
    private String direccion;

    @Column(name = "PhoneNumber", length = 20)
    private String telefono;

    @Column(name = "Dui", length = 10)
    private String dui;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "StatusId", nullable = false)
    private Integer idEstado;

    // La fecha se genera desde la API y no se recibe desde el cliente.
    @PrePersist
    public void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}