package org.Api_Inventario.dtos.Inventario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InventarioSalida(

        Integer idInventario,
        Integer idProducto,
        BigDecimal precioCompra,
        BigDecimal precioVenta,
        Integer stockActual,
        Integer stockMinimo,
        Integer idEstado,
        LocalDateTime fechaCreacion

) implements Serializable {
}