package com.angeles.demo.dto;

import com.angeles.demo.entity.Rol;
import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String email;
    private String password;
    private Rol rol; // Se enviará como "ROLE_CLIENTE" o "ROLE_DUENO"
    private String imagenUrl;
}