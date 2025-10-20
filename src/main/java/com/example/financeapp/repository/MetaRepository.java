package com.example.financeapp.repository;

import com.example.financeapp.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List; // Adicione esta importação

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {

    List<Meta> findAllByUsuarioId(Long usuarioId);
}