package org.Api_Inventario.Servicios.Interfaces;

import org.Api_Inventario.dtos.Categoria.CategoriaGuardar;
import org.Api_Inventario.dtos.Categoria.CategoriaModificar;
import org.Api_Inventario.dtos.Categoria.CategoriaSalida;

import java.util.List;
import java.util.Optional;

public interface ICategoriaServicios {

    List<CategoriaSalida> obtenerTodas();

    Optional<CategoriaSalida> obtenerPorId(Integer id);

    CategoriaSalida guardar(CategoriaGuardar dto);

    CategoriaSalida modificar(CategoriaModificar dto);

    void eliminar(Integer id);
}