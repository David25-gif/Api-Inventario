package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Categoria;
import org.Api_Inventario.Repositorios.ICategoriaRepositorios;
import org.Api_Inventario.Servicios.Interfaces.ICategoriaServicios;
import org.Api_Inventario.dtos.Categoria.CategoriaGuardar;
import org.Api_Inventario.dtos.Categoria.CategoriaModificar;
import org.Api_Inventario.dtos.Categoria.CategoriaSalida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServicios implements ICategoriaServicios {

    private final ICategoriaRepositorios categoriaRepositorio;

    public CategoriaServicios(
            ICategoriaRepositorios categoriaRepositorio) {

        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<CategoriaSalida> obtenerTodas() {

        return categoriaRepositorio.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaSalida> obtenerPorId(Integer id) {

        return categoriaRepositorio.findById(id)
                .map(this::convertirADto);
    }

    @Override
    @Transactional
    public CategoriaSalida guardar(CategoriaGuardar dto) {

        // El nombre de la categoría debe ser único.
        if (categoriaRepositorio.existsByNombre(dto.nombre())) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con el mismo nombre."
            );
        }

        Categoria categoria = new Categoria();

        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        categoria.setIdEstado(dto.idEstado());
        categoria.setCreadoPorUsuario(dto.creadoPorUsuario());

        Categoria guardada =
                categoriaRepositorio.save(categoria);

        return convertirADto(guardada);
    }

    @Override
    @Transactional
    public CategoriaSalida modificar(CategoriaModificar dto) {

        Categoria categoria = categoriaRepositorio
                .findById(dto.idCategoria())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "La categoría no existe."
                        )
                );

        // Evita que otra categoría utilice el mismo nombre.
        if (categoriaRepositorio
                .existsByNombreAndIdCategoriaNot(
                        dto.nombre(),
                        dto.idCategoria())) {

            throw new IllegalArgumentException(
                    "Ya existe otra categoría con el mismo nombre."
            );
        }

        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        // El estado sí puede cambiar para permitir activar
        // o desactivar la categoría.
        categoria.setIdEstado(dto.idEstado());

        /*
         * creadoPorUsuario no se modifica porque representa
         * al usuario que creó originalmente el registro.
         */

        Categoria modificada =
                categoriaRepositorio.save(categoria);

        return convertirADto(modificada);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {

        if (!categoriaRepositorio.existsById(id)) {
            throw new IllegalArgumentException(
                    "La categoría no existe."
            );
        }

        categoriaRepositorio.deleteById(id);
    }

    // Convierte la entidad de BD al DTO que será enviado al cliente.
    private CategoriaSalida convertirADto(Categoria entidad) {

        return new CategoriaSalida(
                entidad.getIdCategoria(),
                entidad.getNombre(),
                entidad.getDescripcion(),
                entidad.getIdEstado(),
                entidad.getCreadoPorUsuario()
        );
    }
}