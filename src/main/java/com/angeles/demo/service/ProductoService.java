package com.angeles.demo.service;

import com.angeles.demo.dto.ProductoRequest;
import com.angeles.demo.entity.Categoria;
import com.angeles.demo.entity.Farmacia;
import com.angeles.demo.entity.Producto;
import com.angeles.demo.repository.CategoriaRepository;
import com.angeles.demo.repository.FarmaciaRepository;
import com.angeles.demo.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final CategoriaRepository categoriaRepository;

    // Para que los dueños agreguen productos
    public Producto guardarProducto(Producto producto, Long farmaciaId, Long categoriaId) {
        Farmacia farmacia = farmaciaRepository.findById(farmaciaId)
                .orElseThrow(() -> new RuntimeException("Farmacia no encontrada"));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        producto.setFarmacia(farmacia);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    // Catálogo global para clientes
    public List<Producto> obtenerTodoElCatalogo() {
        return productoRepository.findAll();
    }

    // Catálogo específico (para que el dueño vea su inventario)
    public List<Producto> obtenerPorFarmacia(Long farmaciaId) {
        return productoRepository.findByFarmaciaId(farmaciaId);
    }

    // Filtro por búsqueda
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // ACTUALIZAR (PUT)
    public Producto editarProducto(Long id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setImagenUrl(request.getImagenUrl());
        // No cambiamos farmacia ni categoría por seguridad simple

        return productoRepository.save(producto);
    }

    // BORRAR (DELETE)
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}