package org.Api_Inventario.Servicios.Interfaces;

import org.Api_Inventario.dtos.Producto.ProductoGuardar;
import org.Api_Inventario.dtos.Producto.ProductoModificar;
import org.Api_Inventario.dtos.Producto.ProductoSalida;

import java.util.List;
import java.util.Optional;

public interface IProductoServicios {
    List<ProductoSalida> obtenerTodos();
    Optional<ProductoSalida> obtenerPorId(Integer id);
    List<ProductoSalida> obtenerPorCategoria(Integer idCategoria);
    ProductoSalida guardar(ProductoGuardar dto);
    ProductoSalida modificar(ProductoModificar dto);
}