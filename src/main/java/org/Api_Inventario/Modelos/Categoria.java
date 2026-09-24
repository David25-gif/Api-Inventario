package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Integer idCategoria;

    @Column(name = "Name", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "Description", length = 255)
    private String descripcion;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    // Estado actual de la categoría.
    // Permite desactivarla sin eliminar el registro de la BD.
    @Column(name = "StatusId", nullable = false)
    private Integer idEstado;

    // Usuario que creó originalmente la categoría.
    @Column(name = "CreatedByUser", nullable = false)
    private Integer creadoPorUsuario;

    // La fecha se genera desde la API y no se recibe desde el cliente.
    @PrePersist
    public void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}