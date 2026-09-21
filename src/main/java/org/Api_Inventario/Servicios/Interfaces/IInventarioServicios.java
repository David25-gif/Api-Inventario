package org.Api_Inventario.Servicios.Interfaces;

import org.Api_Inventario.dtos.Inventario.InventarioGuardar;
import org.Api_Inventario.dtos.Inventario.InventarioSalida;
import org.Api_Inventario.dtos.Inventario.InventarioModificar;

import java.util.List;
import java.util.Optional;

public interface IInventarioServicios {
    List<InventarioSalida> obtenerTodos();
    Optional<InventarioSalida> obtenerPorProducto(Integer idProducto);
    InventarioSalida guardar(InventarioGuardar dto);
    InventarioSalida modificar(InventarioModificar dto);

    /**
     * Ajusta el stock de un producto. cantidad positiva = entrada,
     * cantidad negativa = salida. Si el producto no tiene fila de
     * inventario todavía, la crea en cero antes de ajustar.
     */
    InventarioSalida ajustarStock(Integer idProducto, Integer cantidad);

}