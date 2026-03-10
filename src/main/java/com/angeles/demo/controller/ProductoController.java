package com.angeles.demo.controller;

import com.angeles.demo.dto.ProductoRequest;
import com.angeles.demo.entity.Producto;
import com.angeles.demo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;


    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setImagenUrl(request.getImagenUrl());

        Producto nuevoProducto = productoService.guardarProducto(
                producto,
                request.getFarmaciaId(),
                request.getCategoriaId()
        );

        return ResponseEntity.ok(nuevoProducto);
    }

    // Ver productos de una farmacia específica (Para gestión del dueño)
    @GetMapping("/farmacia/{farmaciaId}")
    public ResponseEntity<List<Producto>> listarPorFarmacia(@PathVariable Long farmaciaId) {
        return ResponseEntity.ok(productoService.obtenerPorFarmacia(farmaciaId));
    }

    // --- ÁREA PÚBLICA (Catálogo para Clientes) ---
    // Nota: Configurado en SecurityConfig como .permitAll()

    @GetMapping("/publico")
    public ResponseEntity<List<Producto>> catalogoGlobal() {
        return ResponseEntity.ok(productoService.obtenerTodoElCatalogo());
    }

    @GetMapping("/publico/buscar")
    public ResponseEntity<List<Producto>> buscarProducto(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> editarProducto(@PathVariable Long id, @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.editarProducto(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}