package org.Api_Inventario.dtos.MovimientoInventario;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    //Estos valores permiten comprobar facilmente el efecto de cada movimiento.
    private Integer stockAnterior;
    private Integer stockNuevo;
}