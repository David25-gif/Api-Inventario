package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCategoria")
    private Integer idCategoria;

    @Column(name = "Nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "Descripcion", length = 255)
    private String descripcion;

    // Estado actual de la categoría.
    // Permite desactivarla sin eliminar el registro de la BD.
    @Column(name = "IdEstado", nullable = false)
    private Integer idEstado;

    // Usuario que creó originalmente la categoría.
    @Column(name = "CreadoPorUsuario", nullable = false)
    private Integer creadoPorUsuario;
}