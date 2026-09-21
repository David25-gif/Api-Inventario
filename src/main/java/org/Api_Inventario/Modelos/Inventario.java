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

    // Cada producto mantiene un único registro de inventario.
    @Column(name = "IdProducto", nullable = false, unique = true)
    private Integer idProducto;

    // Las existencias se actualizan mediante MovimientoInventario.
    @Column(name = "StockActual", nullable = false)
    private Integer stockActual;

    // Cantidad utilizada para identificar niveles bajos de inventario.
    @Column(name = "StockMinimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "FechaActualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;
}