package org.Api_Inventario.Controladores;

import jakarta.validation.Valid;
import org.Api_Inventario.Servicios.Interfaces.ICategoriaServicios;
import org.Api_Inventario.dtos.Categoria.CategoriaGuardar;
import org.Api_Inventario.dtos.Categoria.CategoriaModificar;
import org.Api_Inventario.dtos.Categoria.CategoriaSalida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    private final ICategoriaServicios categoriaServicios;

    public CategoriaControlador(ICategoriaServicios categoriaServicios) {
        this.categoriaServicios = categoriaServicios;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaSalida>> obtenerTodas() {
        return ResponseEntity.ok(categoriaServicios.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaSalida> obtenerPorId(@PathVariable Integer id) {
        return categoriaServicios.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaSalida> guardar(@Valid @RequestBody CategoriaGuardar dto) {
        CategoriaSalida respuesta = categoriaServicios.guardar(dto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoriaSalida> modificar(@Valid @RequestBody CategoriaModificar dto) {
        return ResponseEntity.ok(categoriaServicios.modificar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaServicios.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}