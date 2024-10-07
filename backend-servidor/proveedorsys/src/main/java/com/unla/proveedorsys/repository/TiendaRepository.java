package com.unla.proveedorsys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.unla.proveedorsys.model.Tienda;

import java.util.List;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {


    List<Tienda> findByHabilitada(Boolean habilitada);
    
    List<Tienda> findByCiudad(String ciudad);
    
    List<Tienda> findByProvincia(String provincia);
    
    List<Tienda> findByEsCasaCentral(Boolean esCasaCentral);
}
