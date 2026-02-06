package com.angeles.demo.dto;

import lombok.Data;

@Data
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String imagenUrl;

    // Solo enviamos los IDs, es más fácil para la app móvil
    private Long farmaciaId;
    private Long categoriaId;
}