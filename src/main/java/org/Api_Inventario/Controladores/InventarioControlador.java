package org.Api_Inventario.Controladores;

import org.Api_Inventario.Servicios.Interfaces.IInventarioServicios;
import org.Api_Inventario.dtos.Inventario.InventarioSalida;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioControlador {

    private final IInventarioServicios inventarioServicios;

    public InventarioControlador(IInventarioServicios inventarioServicios) {
        this.inventarioServicios = inventarioServicios;
    }

    @GetMapping
    public ResponseEntity<List<InventarioSalida>> obtenerTodos() {
        return ResponseEntity.ok(inventarioServicios.obtenerTodos());
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<InventarioSalida> obtenerPorProducto(@PathVariable Integer idProducto) {
        return inventarioServicios.obtenerPorProducto(idProducto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/producto/{idProducto}/ajustar")
    public ResponseEntity<InventarioSalida> ajustarStock(@PathVariable Integer idProducto,
                                                         @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioServicios.ajustarStock(idProducto, cantidad));
    }
}