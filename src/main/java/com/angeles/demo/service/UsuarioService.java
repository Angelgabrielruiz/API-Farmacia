package com.angeles.demo.service;

import com.angeles.demo.entity.Usuario;
import com.angeles.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Esto se inyectará desde SecurityConfig

    public Usuario registrar(Usuario usuario) {
        // Encriptamos la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obtenerPerfil(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizarDatos(String email, String nombre, String nuevoEmail, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (nombre != null && !nombre.isBlank()) {
            usuario.setNombre(nombre);
        }
        if (nuevoEmail != null && !nuevoEmail.isBlank()) {
            // Verificar que el nuevo email no esté en uso por otro usuario
            Optional<Usuario> existente = usuarioRepository.findByEmail(nuevoEmail);
            if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
                throw new RuntimeException("El email ya está en uso");
            }
            usuario.setEmail(nuevoEmail);
        }
        if (password != null && !password.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }

        return usuarioRepository.save(usuario);
    }
}