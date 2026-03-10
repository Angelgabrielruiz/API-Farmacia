package com.angeles.demo.controller;

import com.angeles.demo.dto.CategoriaRequest;
import com.angeles.demo.entity.Categoria;
import com.angeles.demo.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    // GET: Público (configurado así en SecurityConfig)
    // Usado para llenar el "Spinner" o menú desplegable en la app móvil
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    // POST: Para crear nuevas categorías
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        return ResponseEntity.ok(categoriaService.crearCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> editarCategoria(@PathVariable Long id, @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.editarCategoria(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}