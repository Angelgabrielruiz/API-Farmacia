package com.angeles.demo.repository;

import com.angeles.demo.entity.Farmacia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {
    List<Farmacia> findByPropietarioId(Long usuarioId);
}