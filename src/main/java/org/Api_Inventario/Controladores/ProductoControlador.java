package org.Api_Inventario.Controladores;

import jakarta.validation.Valid;
import org.Api_Inventario.Seguridad.Modelos.Usuario;
import org.Api_Inventario.Servicios.Interfaces.IProductoServicios;
import org.Api_Inventario.dtos.Producto.ProductoGuardar;
import org.Api_Inventario.dtos.Producto.ProductoModificar;
import org.Api_Inventario.dtos.Producto.ProductoSalida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    private final IProductoServicios productoServicios;

    public ProductoControlador(IProductoServicios productoServicios) {
        this.productoServicios = productoServicios;
    }

    @GetMapping
    public ResponseEntity<List<ProductoSalida>> obtenerTodos() {
        return ResponseEntity.ok(productoServicios.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoSalida> obtenerPorId(@PathVariable Integer id) {
        return productoServicios.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoSalida>> obtenerPorCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(productoServicios.obtenerPorCategoria(idCategoria));
    }

    @PostMapping
    public ResponseEntity<ProductoSalida> guardar(
            @Valid @RequestBody ProductoGuardar dto,
            @AuthenticationPrincipal Usuario usuario) {

        ProductoSalida respuesta =
                productoServicios.guardar(
                        dto,
                        usuario.getIdUsuario()
                );

        return new ResponseEntity<>(
                respuesta,
                HttpStatus.CREATED
        );
    }

    @PutMapping
    public ResponseEntity<ProductoSalida> modificar(@Valid @RequestBody ProductoModificar dto) {
        return ResponseEntity.ok(productoServicios.modificar(dto));
    }
}