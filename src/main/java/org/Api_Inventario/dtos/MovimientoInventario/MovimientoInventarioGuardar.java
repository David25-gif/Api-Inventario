package org.Api_Inventario.dtos.MovimientoInventario;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record MovimientoInventarioGuardar(

        @NotNull(message = "El tipo de movimiento es obligatorio.")
        @Positive(message = "El tipo de movimiento debe ser mayor a 0.")
        Integer idTipoMovimiento,

        @NotNull(message = "La cantidad es obligatoria.")
        @Positive(message = "La cantidad debe ser mayor a 0.")
        Integer cantidad,

        @NotNull(message = "El costo unitario es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = true,
                message = "El costo unitario no puede ser negativo.")
        BigDecimal costoUnitario,

        @Size(max = 255,
                message = "Las notas no pueden exceder los 255 caracteres.")
        String notas,

        @NotNull(message = "El usuario es obligatorio.")
        @Positive(message = "El usuario debe ser mayor a 0.")
        Integer creadoPorUsuario,

        @NotNull(message = "El inventario es obligatorio.")
        @Positive(message = "El inventario debe ser mayor a 0.")
        Integer idInventario,

        @Positive(message = "El detalle del documento debe ser mayor a 0.")
        Integer idDetalleDocumento

) {}