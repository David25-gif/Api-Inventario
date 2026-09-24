package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Inventario;
import org.Api_Inventario.Repositorios.IInventarioRepositorios;
import org.Api_Inventario.Servicios.Interfaces.IInventarioServicios;
import org.Api_Inventario.dtos.Inventario.InventarioGuardar;
import org.Api_Inventario.dtos.Inventario.InventarioModificar;
import org.Api_Inventario.dtos.Inventario.InventarioSalida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventarioServicios implements IInventarioServicios {

    private final IInventarioRepositorios inventarioRepositorio;

    public InventarioServicios(
            IInventarioRepositorios inventarioRepositorio) {

        this.inventarioRepositorio = inventarioRepositorio;
    }

    @Override
    public List<InventarioSalida> obtenerTodos() {

        return inventarioRepositorio.findAll()
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InventarioSalida> obtenerPorProducto(
            Integer idProducto) {

        return inventarioRepositorio
                .findByIdProducto(idProducto)
                .map(this::convertirADto);
    }

    @Override
    @Transactional
    public InventarioSalida guardar(InventarioGuardar dto) {

        // Cada producto debe tener un único registro de inventario.
        if (inventarioRepositorio
                .findByIdProducto(dto.idProducto())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Ya existe un registro de inventario para el producto "
                            + dto.idProducto()
            );
        }

        Inventario inventario = new Inventario();

        inventario.setIdProducto(dto.idProducto());
        inventario.setPrecioCompra(dto.precioCompra());
        inventario.setPrecioVenta(dto.precioVenta());
        inventario.setStockActual(dto.stockActual());
        inventario.setStockMinimo(dto.stockMinimo());
        inventario.setIdEstado(1);
        inventario.setFechaCreacion(LocalDateTime.now());

        Inventario guardado =
                inventarioRepositorio.save(inventario);

        return convertirADto(guardado);
    }

    @Override
    @Transactional
    public InventarioSalida modificar(InventarioModificar dto) {

        Inventario inventario = inventarioRepositorio
                .findById(dto.idInventario())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un inventario con id "
                                        + dto.idInventario()
                        )
                );

        /*
         * El stock actual no se modifica desde este método.
         * Las entradas y salidas deben realizarse mediante
         * MovimientoInventario para conservar el historial.
         */
        inventario.setPrecioCompra(dto.precioCompra());
        inventario.setPrecioVenta(dto.precioVenta());
        inventario.setStockMinimo(dto.stockMinimo());
        inventario.setIdEstado(dto.idEstado());

        Inventario guardado =
                inventarioRepositorio.save(inventario);

        return convertirADto(guardado);
    }

    private InventarioSalida convertirADto(Inventario entidad) {

        return new InventarioSalida(
                entidad.getIdInventario(),
                entidad.getIdProducto(),
                entidad.getPrecioCompra(),
                entidad.getPrecioVenta(),
                entidad.getStockActual(),
                entidad.getStockMinimo(),
                entidad.getIdEstado(),
                entidad.getFechaCreacion()
        );
    }
}