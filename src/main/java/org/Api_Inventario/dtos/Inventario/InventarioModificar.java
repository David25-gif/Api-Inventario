package org.Api_Inventario.dtos.Inventario;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record InventarioModificar(

        @NotNull(message = "El idInventario es obligatorio.")
        @Positive(message = "El idInventario debe ser mayor a 0.")
        Integer idInventario,

        @NotNull(message = "El precio de compra es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "El precio de compra no puede ser negativo.")
        BigDecimal precioCompra,

        @NotNull(message = "El precio de venta es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "El precio de venta no puede ser negativo.")
        BigDecimal precioVenta,

        @NotNull(message = "El stock mínimo es obligatorio.")
        @PositiveOrZero(message = "El stock mínimo no puede ser negativo.")
        Integer stockMinimo,

        @NotNull(message = "El estado es obligatorio.")
        @Positive(message = "El estado debe ser mayor a 0.")
        Integer idEstado

) {
}