package org.Api_Inventario.Servicios.Implementaciones;

import org.Api_Inventario.Modelos.Inventario;
import org.Api_Inventario.Modelos.MovimientoInventario;
import org.Api_Inventario.Repositorios.IInventarioRepositorios;
import org.Api_Inventario.Repositorios.IMovimientoInventarioRepositorios;
import org.Api_Inventario.Servicios.Interfaces.IMovimientoInventarioServicios;
import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioGuardar;
import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioSalida;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoInventarioServicios
        implements IMovimientoInventarioServicios {

    private final IMovimientoInventarioRepositorios movimientoRepositorio;
    private final IInventarioRepositorios inventarioRepositorio;

    public MovimientoInventarioServicios(
            IMovimientoInventarioRepositorios movimientoRepositorio,
            IInventarioRepositorios inventarioRepositorio) {

        this.movimientoRepositorio = movimientoRepositorio;
        this.inventarioRepositorio = inventarioRepositorio;
    }

    @Override
    public List<MovimientoInventarioSalida> obtenerTodos() {

        return movimientoRepositorio.findAll()
                .stream()
                .map(movimiento -> convertirADto(movimiento, null, null))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovimientoInventarioSalida> obtenerPorId(Integer id) {

        return movimientoRepositorio.findById(id)
                .map(movimiento -> convertirADto(movimiento, null, null));
    }

    @Override
    public List<MovimientoInventarioSalida> obtenerPorInventario(
            Integer idInventario) {

        // Antes de buscar el historial comprobamos que el inventario realmente exista.
        if (!inventarioRepositorio.existsById(idInventario)) {
            throw new RuntimeException("El inventario no existe.");
        }

        return movimientoRepositorio
                .findByIdInventarioOrderByFechaCreacionDesc(idInventario)
                .stream()
                .map(movimiento -> convertirADto(movimiento, null, null))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoInventarioSalida guardar(
            MovimientoInventarioGuardar dto) {

        Inventario inventario = inventarioRepositorio
                .findById(dto.idInventario())
                .orElseThrow(() ->
                        new RuntimeException("El inventario no existe."));

        int stockAnterior = inventario.getStockActual();
        int stockNuevo;

         // Cada tipo de movimiento tiene un efecto diferente sobre el stock.
         // La validación se realiza antes de guardar cualquier cambio.

        switch (dto.idTipoMovimiento()) {

            case 1 -> {
                // Entrada: se agregan nuevas unidades al inventario.
                stockNuevo = stockAnterior + dto.cantidad();
            }

            case 2 -> {
                // Una salida nunca puede dejar el inventario con stock negativo.
                validarStockDisponible(stockAnterior, dto.cantidad());
                stockNuevo = stockAnterior - dto.cantidad();
            }

            case 3 -> {
                // En un ajuste, la cantidad representa el nuevo stock físico.
                stockNuevo = dto.cantidad();
            }

            case 4 -> {
                // Una devolución devuelve unidades nuevamente al inventario.
                stockNuevo = stockAnterior + dto.cantidad();
            }

            case 5 -> {
                // La transferencia retira unidades del inventario de origen.
                validarStockDisponible(stockAnterior, dto.cantidad());
                stockNuevo = stockAnterior - dto.cantidad();
            }

            default -> throw new IllegalArgumentException(
                    "El tipo de movimiento no es válido.");
        }

        inventario.setStockActual(stockNuevo);

        MovimientoInventario movimiento = new MovimientoInventario();

        movimiento.setIdTipoMovimiento(dto.idTipoMovimiento());
        movimiento.setCantidad(dto.cantidad());
        movimiento.setCostoUnitario(dto.costoUnitario());
        movimiento.setNotas(dto.notas());
        movimiento.setCreadoPorUsuario(dto.creadoPorUsuario());
        movimiento.setIdInventario(dto.idInventario());
        movimiento.setIdDetalleDocumento(dto.idDetalleDocumento());

         //Inventario y movimiento forman una sola operación.
         //@Transactional evita que se guarde solo una de las dos partes
         // si ocurre un error durante el proceso.

        inventarioRepositorio.save(inventario);

        MovimientoInventario movimientoGuardado =
                movimientoRepositorio.save(movimiento);

        return convertirADto(
                movimientoGuardado,
                stockAnterior,
                stockNuevo
        );
    }

    private void validarStockDisponible(
            Integer stockActual,
            Integer cantidad) {

        if (cantidad > stockActual) {
            throw new IllegalArgumentException(
                    "Stock insuficiente para realizar el movimiento.");
        }
    }

    private MovimientoInventarioSalida convertirADto(
            MovimientoInventario entidad,
            Integer stockAnterior,
            Integer stockNuevo) {

        return new MovimientoInventarioSalida(
                entidad.getIdMovimientoInventario(),
                entidad.getIdTipoMovimiento(),
                entidad.getCantidad(),
                entidad.getCostoUnitario(),
                entidad.getNotas(),
                entidad.getFechaCreacion(),
                entidad.getCreadoPorUsuario(),
                entidad.getIdInventario(),
                entidad.getIdDetalleDocumento(),
                stockAnterior,
                stockNuevo
        );
    }
}