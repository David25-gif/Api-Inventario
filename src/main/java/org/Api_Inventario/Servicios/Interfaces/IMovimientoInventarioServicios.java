package org.Api_Inventario.Servicios.Interfaces;

import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioGuardar;
import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioSalida;

import java.util.List;
import java.util.Optional;

public interface IMovimientoInventarioServicios {

    List<MovimientoInventarioSalida> obtenerTodos();

    Optional<MovimientoInventarioSalida> obtenerPorId(Integer id);

    List<MovimientoInventarioSalida> obtenerPorInventario(Integer idInventario);

    MovimientoInventarioSalida guardar(MovimientoInventarioGuardar dto);
}