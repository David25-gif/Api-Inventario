package org.Api_Inventario.dtos.Producto;

import java.io.Serializable;
import java.time.LocalDateTime;

// No se expone creadoPorUsuario: es información interna.
// El precio y el stock llegarán con el DTO de catálogo, cuando Inventario tenga SalePrice.
public record ProductoSalida(
        Integer idProducto,
        String nombre,
        String descripcion,
        String codigoBarras,
        String imagenUrl,
        LocalDateTime fechaCreacion,
        Integer idEstado,
        Integer idCategoria
) implements Serializable {
}