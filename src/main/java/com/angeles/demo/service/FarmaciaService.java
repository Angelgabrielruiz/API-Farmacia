package com.angeles.demo.service;

import com.angeles.demo.dto.FarmaciaRequest;
import com.angeles.demo.entity.Farmacia;
import com.angeles.demo.entity.Usuario;
import com.angeles.demo.repository.FarmaciaRepository;
import com.angeles.demo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;
    private final UsuarioRepository usuarioRepository;

    public Farmacia registrarFarmacia(Farmacia farmacia, Long idDueno) {
        Usuario dueno = usuarioRepository.findById(idDueno)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        farmacia.setPropietario(dueno);
        return farmaciaRepository.save(farmacia);
    }

    public List<Farmacia> listarPorDueno(Long idDueno) {
        return farmaciaRepository.findByPropietarioId(idDueno);
    }

    public List<Farmacia> listarTodas() {
        return farmaciaRepository.findAll();
    }

    public Farmacia editarFarmacia(Long id, FarmaciaRequest request) {
        Farmacia farmacia = farmaciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmacia no encontrada"));

        farmacia.setNombre(request.getNombre());
        farmacia.setDireccion(request.getDireccion());
        farmacia.setTelefono(request.getTelefono());

        return farmaciaRepository.save(farmacia);
    }

    public void eliminarFarmacia(Long id) {
        if (!farmaciaRepository.existsById(id)) {
            throw new RuntimeException("Farmacia no encontrada");
        }
        farmaciaRepository.deleteById(id);
    }
}