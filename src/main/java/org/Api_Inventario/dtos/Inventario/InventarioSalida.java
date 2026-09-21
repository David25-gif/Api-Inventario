package org.Api_Inventario.dtos.Inventario;

import java.io.Serializable;
import java.time.LocalDateTime;

public record InventarioSalida(
        Integer idInventario,
        Integer idProducto,
        Integer cantidadActual,
        LocalDateTime fechaActualizacion
) implements Serializable {
}