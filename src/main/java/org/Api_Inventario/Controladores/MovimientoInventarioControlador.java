package org.Api_Inventario.Controladores;

import jakarta.validation.Valid;
import org.Api_Inventario.Servicios.Interfaces.IMovimientoInventarioServicios;
import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioGuardar;
import org.Api_Inventario.dtos.MovimientoInventario.MovimientoInventarioSalida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos-inventario")
public class MovimientoInventarioControlador {

    private final IMovimientoInventarioServicios movimientoServicios;

    public MovimientoInventarioControlador(
            IMovimientoInventarioServicios movimientoServicios) {

        this.movimientoServicios = movimientoServicios;
    }

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioSalida>> obtenerTodos() {

        return ResponseEntity.ok(
                movimientoServicios.obtenerTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioSalida> obtenerPorId(
            @PathVariable Integer id) {

        return movimientoServicios.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/inventario/{idInventario}")
    public ResponseEntity<List<MovimientoInventarioSalida>>
    obtenerPorInventario(
            @PathVariable Integer idInventario) {

        return ResponseEntity.ok(
                movimientoServicios.obtenerPorInventario(idInventario)
        );
    }

    @PostMapping
    public ResponseEntity<MovimientoInventarioSalida> guardar(
            @Valid @RequestBody MovimientoInventarioGuardar dto) {

        MovimientoInventarioSalida respuesta =
                movimientoServicios.guardar(dto);

        return new ResponseEntity<>(
                respuesta,
                HttpStatus.CREATED
        );
    }
}