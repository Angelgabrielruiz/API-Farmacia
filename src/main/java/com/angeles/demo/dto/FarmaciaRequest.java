package com.angeles.demo.dto;

import lombok.Data;

@Data
public class FarmaciaRequest {
    private String nombre;
    private String direccion;
    private String telefono;
    private Double latitud;
    private Double longitud;
}