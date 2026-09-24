package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "InventoryMovement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InventoryMovementId")
    private Integer idMovimientoInventario;

    @Column(name = "MovementTypeId", nullable = false)
    private Integer idTipoMovimiento;

    @Column(name = "Quantity", nullable = false)
    private Integer cantidad;

    @Column(name = "UnitCost", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    @Column(name = "Notes", length = 255)
    private String notas;

    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "CreatedByUser", nullable = false)
    private Integer creadoPorUsuario;

    @Column(name = "InventoryId", nullable = false)
    private Integer idInventario;

    /*
     * Este ID proviene del sistema de C#.
     * No se necesita tener DetalleDocumento como entidad en esta API.
     * Puede ser null para movimientos manuales.
     */
    @Column(name = "DocumentDetailId")
    private Integer idDetalleDocumento;

    // La fecha se genera desde la API y no se recibe desde el cliente.
    @PrePersist
    public void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}