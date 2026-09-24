package org.Api_Inventario.dtos.MovimientoInventario;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MovimientoInventarioGuardar(

        @NotNull(message = "El tipo de movimiento es obligatorio.")
        @Positive(message = "El tipo de movimiento debe ser mayor a 0.")
        Integer idTipoMovimiento,

        @NotNull(message = "La cantidad es obligatoria.")
        @Positive(message = "La cantidad debe ser mayor a 0.")
        Integer cantidad,

        @NotNull(message = "El costo unitario es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = true, message = "El costo unitario no puede ser negativo.")
        BigDecimal costoUnitario,

        String notas,

        @NotNull(message = "El inventario es obligatorio.")
        @Positive(message = "El inventario debe ser mayor a 0.")
        Integer idInventario,

        /*
         * Referencia opcional al detalle de documento creado en C#.
         * Para movimientos manuales puede ser null.
         */
        Integer idDetalleDocumento

) {
}