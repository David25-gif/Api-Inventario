package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Producto;
import org.Api_Inventario.Repositorios.ICategoriaRepositorios;
import org.Api_Inventario.Repositorios.IProductoRepositorios;
import org.Api_Inventario.Servicios.Interfaces.IProductoServicios;
import org.Api_Inventario.dtos.Producto.ProductoGuardar;
import org.Api_Inventario.dtos.Producto.ProductoModificar;
import org.Api_Inventario.dtos.Producto.ProductoSalida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServicios implements IProductoServicios {

    private final IProductoRepositorios productoRepositorio;
    private final ICategoriaRepositorios categoriaRepositorio;

    public ProductoServicios(IProductoRepositorios productoRepositorio,
                             ICategoriaRepositorios categoriaRepositorio) {
        this.productoRepositorio = productoRepositorio;
        this.categoriaRepositorio = categoriaRepositorio;
    }

    @Override
    public List<ProductoSalida> obtenerTodos() {
        return productoRepositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductoSalida> obtenerPorId(Integer id) {
        return productoRepositorio.findById(id).map(this::convertirADto);
    }

    @Override
    public List<ProductoSalida> obtenerPorCategoria(Integer idCategoria) {
        // Antes de buscar los productos comprobamos que la categoría exista.
        if (!categoriaRepositorio.existsById(idCategoria)) {
            throw new RuntimeException("La categoría no existe.");
        }

        return productoRepositorio.findByIdCategoria(idCategoria).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoSalida guardar(ProductoGuardar dto,
                                  Integer idUsuario) {
        validarCategoriaExiste(dto.idCategoria());

        if (productoRepositorio.existsByCodigoBarras(dto.codigoBarras())) {
            throw new IllegalArgumentException("Ya existe un producto con el mismo código de barras.");
        }

        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setCodigoBarras(dto.codigoBarras());
        producto.setImagenUrl(dto.imagenUrl());
        //Estado "Activo" por defecto.
        producto.setIdEstado(1);
        producto.setIdCategoria(dto.idCategoria());

        //Se asigna el usuario logueado automaticamente.
        producto.setCreadoPorUsuario(idUsuario);
        // fechaCreacion se asigna en @PrePersist

        Producto guardado = productoRepositorio.save(producto);
        return convertirADto(guardado);
    }

    @Override
    @Transactional
    public ProductoSalida modificar(ProductoModificar dto) {
        Producto producto = productoRepositorio.findById(dto.idProducto())
                .orElseThrow(() -> new RuntimeException("El producto no existe."));

        validarCategoriaExiste(dto.idCategoria());

        if (productoRepositorio.existsByCodigoBarrasAndIdProductoNot(
                dto.codigoBarras(), dto.idProducto())) {
            throw new IllegalArgumentException("Ya existe otro producto con el mismo código de barras.");
        }

        // fechaCreacion y creadoPorUsuario no se modifican nunca.
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setCodigoBarras(dto.codigoBarras());
        producto.setImagenUrl(dto.imagenUrl());
        producto.setIdEstado(dto.idEstado());
        producto.setIdCategoria(dto.idCategoria());

        Producto modificado = productoRepositorio.save(producto);
        return convertirADto(modificado);
    }

    private void validarCategoriaExiste(Integer idCategoria) {
        if (!categoriaRepositorio.existsById(idCategoria)) {
            throw new IllegalArgumentException("La categoría indicada no existe.");
        }
    }

    private ProductoSalida convertirADto(Producto entidad) {
        return new ProductoSalida(
                entidad.getIdProducto(),
                entidad.getNombre(),
                entidad.getDescripcion(),
                entidad.getCodigoBarras(),
                entidad.getImagenUrl(),
                entidad.getFechaCreacion(),
                entidad.getIdEstado(),
                entidad.getIdCategoria()
        );
    }
}