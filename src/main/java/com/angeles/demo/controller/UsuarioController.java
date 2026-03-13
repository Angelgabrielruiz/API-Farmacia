package com.angeles.demo.controller;

import com.angeles.demo.dto.UpdateUsuarioRequest;
import com.angeles.demo.entity.Usuario;
import com.angeles.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/perfil")
    public ResponseEntity<Usuario> obtenerPerfil(Principal principal) {
        Usuario usuario = usuarioService.obtenerPerfil(principal.getName());
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/perfil")
    public ResponseEntity<Usuario> actualizarPerfil(Principal principal,
                                                     @RequestBody UpdateUsuarioRequest request) {
        Usuario actualizado = usuarioService.actualizarDatos(
                principal.getName(),
                request.getNombre(),
                request.getEmail(),
                request.getPassword(),
                request.getImagenUrl()
        );
        return ResponseEntity.ok(actualizado);
    }
}
