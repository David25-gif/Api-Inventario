package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Inventario;
import org.Api_Inventario.Repositorios.IInventarioRepositorios;
import org.Api_Inventario.Servicios.Interfaces.IInventarioServicios;
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

    public InventarioServicios(IInventarioRepositorios inventarioRepositorio) {
        this.inventarioRepositorio = inventarioRepositorio;
    }

    @Override
    public List<InventarioSalida> obtenerTodos() {
        return inventarioRepositorio.findAll().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<InventarioSalida> obtenerPorProducto(Integer idProducto) {
        return inventarioRepositorio.findByIdProducto(idProducto).map(this::convertirADto);
    }

    @Override
    @Transactional
    public InventarioSalida ajustarStock(Integer idProducto, Integer cantidad) {
        Inventario inventario = inventarioRepositorio.findByIdProducto(idProducto)
                .orElseGet(() -> {
                    Inventario nuevo = new Inventario();
                    nuevo.setIdProducto(idProducto);
                    nuevo.setCantidadActual(0);
                    return nuevo;
                });

        int nuevaCantidad = inventario.getCantidadActual() + cantidad;
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("Stock insuficiente para el producto " + idProducto);
        }

        inventario.setCantidadActual(nuevaCantidad);
        inventario.setFechaActualizacion(LocalDateTime.now());

        Inventario guardado = inventarioRepositorio.save(inventario);
        return convertirADto(guardado);
    }

    private InventarioSalida convertirADto(Inventario entidad) {
        return new InventarioSalida(
                entidad.getIdInventario(),
                entidad.getIdProducto(),
                entidad.getCantidadActual(),
                entidad.getFechaActualizacion()
        );
    }
}