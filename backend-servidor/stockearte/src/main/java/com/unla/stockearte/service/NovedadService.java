package com.unla.stockearte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unla.stockearte.model.Novedad;
import com.unla.stockearte.repository.NovedadRepository;

@Service
public class NovedadService {

    @Autowired
    private NovedadRepository novedadRepository;

    public Novedad saveNovedad(Novedad novedad) {
        return novedadRepository.save(novedad);
    }
    
    public List<Novedad> getAllNovedades() {
        return novedadRepository.findAll();
    }
}