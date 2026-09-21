package org.Api_Inventario.Servicios.Interfaces;

import org.Api_Inventario.dtos.Inventario.InventarioGuardar;
import org.Api_Inventario.dtos.Inventario.InventarioModificar;
import org.Api_Inventario.dtos.Inventario.InventarioSalida;

import java.util.List;
import java.util.Optional;

public interface IInventarioServicios {

    List<InventarioSalida> obtenerTodos();

    Optional<InventarioSalida> obtenerPorProducto(Integer idProducto);

    InventarioSalida guardar(InventarioGuardar dto);

    InventarioSalida modificar(InventarioModificar dto);
}