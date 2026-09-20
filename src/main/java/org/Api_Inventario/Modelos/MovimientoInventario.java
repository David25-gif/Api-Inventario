package org.Api_Inventario.Modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MovimientoInventario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMovimientoInventario")
    private Integer idMovimientoInventario;

    @Column(name = "IdTipoMovimiento", nullable = false)
    private Integer idTipoMovimiento;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "CostoUnitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoUnitario;

    @Column(name = "Notas", length = 255)
    private String notas;

    @Column(name = "FechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "CreadoPorUsuario", nullable = false)
    private Integer creadoPorUsuario;

    @Column(name = "IdInventario", nullable = false)
    private Integer inventario;

    //La fecha se genera desde la API y no se recibe desde el cliente.
    @PrePersist
    public void asignarFechaCreacion() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
