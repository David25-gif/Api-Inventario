package org.Api_Inventario.dtos.Inventario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventarioModificar(

        @NotNull(message = "El idInventario es obligatorio.")
        @Positive(message = "El idInventario debe ser mayor a 0.")
        Integer idInventario,

        @NotNull(message = "El stockActual es obligatorio.")
        @PositiveOrZero(message = "El stockActual no puede ser negativo.")
        Integer stockActual
) {
}