package com.unla.stockearte.repository;

import java.util.Set;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.stockearte.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Set<Usuario> findByUsernameContaining(String username);

    Set<Usuario> findByTiendaId(Long tiendaId);

    Set<Usuario> findByUsernameContainingAndTiendaId(String username, Long tiendaId);

    Optional<Usuario> findByUsername(String username);
}
