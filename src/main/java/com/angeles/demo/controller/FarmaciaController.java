package com.angeles.demo.controller;

import com.angeles.demo.dto.FarmaciaRequest;
import com.angeles.demo.entity.Farmacia;
import com.angeles.demo.entity.Usuario;
import com.angeles.demo.service.FarmaciaService;
import com.angeles.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/farmacias")
@RequiredArgsConstructor
public class FarmaciaController {

    private final FarmaciaService farmaciaService;
    private final UsuarioService usuarioService;

    // Endpoint para que un DUEÑO registre su farmacia
    @PostMapping
    public ResponseEntity<Farmacia> crearFarmacia(@RequestBody FarmaciaRequest request, Principal principal) {
        // 1. Obtenemos el usuario del token
        String email = principal.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Mapeamos DTO a Entidad
        Farmacia nuevaFarmacia = new Farmacia();
        nuevaFarmacia.setNombre(request.getNombre());
        nuevaFarmacia.setDireccion(request.getDireccion());
        nuevaFarmacia.setTelefono(request.getTelefono());

        // 3. Guardamos usando el servicio
        return ResponseEntity.ok(farmaciaService.registrarFarmacia(nuevaFarmacia, usuario.getId()));
    }

    // Endpoint para que un DUEÑO vea SUS farmacias
    @GetMapping("/mis-farmacias")
    public ResponseEntity<List<Farmacia>> listarMisFarmacias(Principal principal) {
        String email = principal.getName();
        Usuario usuario = usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(farmaciaService.listarPorDueno(usuario.getId()));
    }

    // Endpoint público (o autenticado) para ver todas las farmacias
    @GetMapping
    public ResponseEntity<List<Farmacia>> listarTodas() {
        return ResponseEntity.ok(farmaciaService.listarTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Farmacia> editarFarmacia(@PathVariable Long id, @RequestBody FarmaciaRequest request) {
        return ResponseEntity.ok(farmaciaService.editarFarmacia(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFarmacia(@PathVariable Long id) {
        farmaciaService.eliminarFarmacia(id);
        return ResponseEntity.noContent().build();
    }
}