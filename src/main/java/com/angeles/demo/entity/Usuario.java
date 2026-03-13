package com.angeles.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import tools.jackson.databind.annotation.JsonAppend;

import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String email;

    @JsonIgnore //asi?
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(columnDefinition = "LONGTEXT")
    private String imagenUrl;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL)

    @JsonIgnore
    private List<Farmacia> farmacias;
}