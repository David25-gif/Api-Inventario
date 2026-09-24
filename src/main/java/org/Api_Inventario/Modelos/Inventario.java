package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryId")
    private Integer idInventario;

    @Column(name = "PurchasePrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra;

    @Column(name = "SalePrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVenta;

    // Cantidad utilizada para identificar niveles bajos de inventario.
    @Column(name = "MinimumStock", nullable = false)
    private Integer stockMinimo;

    // Las existencias se actualizan mediante MovimientoInventario.
    @Column(name = "CurrentStock", nullable = false)
    private Integer stockActual;

    // Cada producto mantiene un único registro de inventario.
    @Column(name = "ProductId", nullable = false, unique = true)
    private Integer idProducto;

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