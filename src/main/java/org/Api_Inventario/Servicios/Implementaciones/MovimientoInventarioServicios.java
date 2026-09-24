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
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MovimientoInventarioSalida> obtenerPorId(Integer id) {

        return movimientoRepositorio.findById(id)
                .map(this::convertirADto);
    }

    @Override
    public List<MovimientoInventarioSalida> obtenerPorInventario(
            Integer idInventario) {

        return movimientoRepositorio
                .findByIdInventario(idInventario)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoInventarioSalida guardar(
            MovimientoInventarioGuardar dto,
            Integer idUsuario) {

        Inventario inventario = inventarioRepositorio
                .findById(dto.idInventario())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "El inventario indicado no existe."
                        )
                );

        /*
         * Tipos utilizados actualmente:
         * 1 = Entrada
         * 2 = Salida
         * 3 = Ajuste
         * 4 = Devolución
         * 5 = Transferencia
         */
        switch (dto.idTipoMovimiento()) {

            // Entrada y devolución aumentan las existencias.
            case 1, 4 -> inventario.setStockActual(
                    inventario.getStockActual() + dto.cantidad()
            );

            // Salida y transferencia reducen las existencias.
            case 2, 5 -> {

                if (inventario.getStockActual() < dto.cantidad()) {
                    throw new IllegalArgumentException(
                            "No existe stock suficiente para realizar el movimiento."
                    );
                }

                inventario.setStockActual(
                        inventario.getStockActual() - dto.cantidad()
                );
            }

            /*
             * El ajuste necesita una regla propia.
             * Por ahora se interpreta la cantidad como el nuevo stock físico.
             */
            case 3 -> inventario.setStockActual(dto.cantidad());

            default -> throw new IllegalArgumentException(
                    "El tipo de movimiento indicado no es válido."
            );
        }

        inventarioRepositorio.save(inventario);

        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.setIdTipoMovimiento(dto.idTipoMovimiento());
        movimiento.setCantidad(dto.cantidad());
        movimiento.setCostoUnitario(dto.costoUnitario());
        movimiento.setNotas(dto.notas());

        // Se obtiene del usuario autenticado, no del JSON.
        movimiento.setCreadoPorUsuario(idUsuario);

        movimiento.setIdInventario(dto.idInventario());

        /*
         * Puede contener el ID creado por C# o ser null
         * cuando se trate de un movimiento manual.
         */
        movimiento.setIdDetalleDocumento(
                dto.idDetalleDocumento()
        );

        MovimientoInventario guardado =
                movimientoRepositorio.save(movimiento);

        return convertirADto(guardado);
    }

    private MovimientoInventarioSalida convertirADto(
            MovimientoInventario entidad) {

        return new MovimientoInventarioSalida(
                entidad.getIdMovimientoInventario(),
                entidad.getIdTipoMovimiento(),
                entidad.getCantidad(),
                entidad.getCostoUnitario(),
                entidad.getNotas(),
                entidad.getFechaCreacion(),
                entidad.getCreadoPorUsuario(),
                entidad.getIdInventario(),
                entidad.getIdDetalleDocumento()
        );
    }
}