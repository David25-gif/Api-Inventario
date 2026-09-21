package org.Api_Inventario.dtos.Inventario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventarioGuardar(

        @NotNull(message = "El idProducto es obligatorio.")
        @Positive(message = "El idProducto debe ser mayor a 0.")
        Integer idProducto,

        @NotNull(message = "El stockActual es obligatorio.")
        @PositiveOrZero(message = "El stockActual no puede ser negativo.")
        Integer stockActual,

        @NotNull(message = "El stockMinimo es obligatorio.")
        @PositiveOrZero(message = "El stockMinimo no puede ser negativo.")
        Integer stockMinimo

) {
}