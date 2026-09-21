package org.Api_Inventario.dtos.MovimientoInventario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventarioSalida implements Serializable {

    private Integer idMovimientoInventario;
    private Integer idTipoMovimiento;
    private Integer cantidad;
    private BigDecimal costoUnitario;
    private String notas;
    private LocalDateTime fechaCreacion;
    private Integer creadoPorUsuario;
    private Integer idInventario;
    private Integer idDetalleDocumento;

    // Estos valores permiten comprobar fácilmente el efecto del movimiento.
    private Integer stockAnterior;
    private Integer stockNuevo;
}