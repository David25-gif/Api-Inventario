package org.Api_Inventario.dtos.MovimientoInventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimientoInventarioSalida(

        Integer idMovimientoInventario,
        Integer idTipoMovimiento,
        Integer cantidad,
        BigDecimal costoUnitario,
        String notas,
        LocalDateTime fechaCreacion,
        Integer creadoPorUsuario,
        Integer idInventario,
        Integer idDetalleDocumento,
        Integer stockAnterior,
        Integer stockNuevo

) {}