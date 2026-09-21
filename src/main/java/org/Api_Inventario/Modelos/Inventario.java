package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Inventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdInventario")
    private Integer idInventario;

    // Referencia simple mientras la entidad Producto no exista todavía.
    @Column(name = "IdProducto", nullable = false, unique = true)
    private Integer idProducto;

    @Column(name = "CantidadActual", nullable = false)
    private Integer cantidadActual;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
}