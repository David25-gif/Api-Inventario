package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Categoria;
import org.Api_Inventario.Repositorios.ICategoriaRepositorios;
import org.Api_Inventario.Servicios.Interfaces.ICategoriaServicios;
import org.Api_Inventario.dtos.Categoria.CategoriaGuardar;
import org.Api_Inventario.dtos.Categoria.CategoriaModificar;
import org.Api_Inventario.dtos.Categoria.CategoriaSalida;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServicios implements ICategoriaServicios {

    private final ICategoriaRepositorios categoriaRepositorio;

    public CategoriaServicios(ICategoriaRepositorios categoriaRepositorio) {
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<CategoriaSalida> obtenerTodas() {
        return categoriaRepositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaSalida> obtenerPorId(Integer id) {
        return categoriaRepositorio.findById(id).map(this::convertirADto);
    }

    @Override
    public CategoriaSalida guardar(CategoriaGuardar dto) {
        if (categoriaRepositorio.existsByNombre(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con el mismo nombre.");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        Categoria guardada = categoriaRepositorio.save(categoria);
        return convertirADto(guardada);
    }

    @Override
    public CategoriaSalida modificar(CategoriaModificar dto) {
        Categoria categoria = categoriaRepositorio.findById(dto.idCategoria())
                .orElseThrow(() -> new RuntimeException("La categoría no existe."));

        if (categoriaRepositorio.existsByNombreAndIdCategoriaNot(dto.nombre(), dto.idCategoria())) {
            throw new IllegalArgumentException("Ya existe otra categoría con el mismo nombre.");
        }

        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        Categoria modificada = categoriaRepositorio.save(categoria);
        return convertirADto(modificada);
    }

    @Override
    public void eliminar(Integer id) {
        if (!categoriaRepositorio.existsById(id)) {
            throw new RuntimeException("La categoría no existe.");
        }
        categoriaRepositorio.deleteById(id);
    }

    private CategoriaSalida convertirADto(Categoria entidad) {
        return new CategoriaSalida(
                entidad.getIdCategoria(),
                entidad.getNombre(),
                entidad.getDescripcion()
        );
    }
}