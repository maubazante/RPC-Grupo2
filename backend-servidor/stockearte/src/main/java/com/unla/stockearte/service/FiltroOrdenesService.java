package com.unla.stockearte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.filtroordenes.FiltroOrdenesRequest;
import com.unla.stockearte.model.FiltroOrdenes;
import com.unla.stockearte.model.Usuario;
import com.unla.stockearte.repository.FiltroOrdenesRepository;
import com.unla.stockearte.repository.UsuarioRepository;

@Service(FiltroOrdenesService.BEAN_NAME)
public class FiltroOrdenesService {

	public final static String BEAN_NAME = "FiltroOrdenesService";
	
	@Autowired
	FiltroOrdenesRepository filtroOrdenesRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public FiltroOrdenes crearFiltroOrdenes(FiltroOrdenesRequest filtro) throws Exception {
		try {
			FiltroOrdenes entity = new FiltroOrdenes();
			entity.setNombre(filtro.getNombre());
			entity.setFiltroProducto(filtro.getFiltroProducto().getValue());
			entity.setFiltroFecha(filtro.getFiltroFecha().getValue());
			entity.setFiltroEstado(filtro.getFiltroEstado().getValue());
			entity.setFiltroTienda(filtro.getFiltroTienda().getValue());
			
			Optional<Usuario> user = usuarioRepository.findById(filtro.getUserId().getValue());
			if(user.isPresent()) {
				entity.setFkUsuariosId(filtro.getUserId().getValue());
				return filtroOrdenesRepository.save(entity);
			}else {
				throw new Exception("Se necesita un usuario para asignarle un filtro");				
			}
		} catch (Exception e) {
			throw new Exception("Error en crearFiltroOrdenes", e);
		}
	}
	
	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public FiltroOrdenes modificarFiltroOrdenes(FiltroOrdenes filtro) throws Exception{
		try {
			FiltroOrdenes entity = new FiltroOrdenes();
			entity.setNombre(filtro.getNombre());
			entity.setFiltroProducto(filtro.getFiltroProducto());
			entity.setFiltroFecha(filtro.getFiltroFecha());
			entity.setFiltroEstado(filtro.getFiltroEstado());
			entity.setFiltroTienda(filtro.getFiltroTienda());
			entity.setId(filtro.getId());
			
			Optional<Usuario> user = usuarioRepository.findById(filtro.getFkUsuariosId());
			if(user.isPresent()) {
				entity.setFkUsuariosId(filtro.getFkUsuariosId());
				return filtroOrdenesRepository.save(entity);
			}else {
				throw new Exception("Se necesita un usuario para modificar un filtro");				
			}
		} catch (Exception e) {
			throw new Exception("Error en crearFiltroOrdenes", e);
		}
	}
	
	
	@Transactional(readOnly = false, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public Boolean eliminarFiltroOrdenes(Long id) throws Exception {
		try {
			Optional<FiltroOrdenes> filtro = filtroOrdenesRepository.findById(id);
			if(filtro.isPresent()) {
				filtroOrdenesRepository.delete(filtro.get());
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		} catch (Exception e) {
			throw new Exception("Error en crearFiltroOrdenes", e);
		}
	}
	
	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public List<FiltroOrdenes> getFiltroOrdenesPorUsuario(Long usuarioId) throws Exception {
		try {
			Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
			if(usuario.isPresent()) {
				List<FiltroOrdenes> filtros = filtroOrdenesRepository.findByFkUsuariosId(usuarioId);
				return filtros;
			}
			return null;
		} catch (Exception e) {
			throw new Exception("Error en crearFiltroOrdenes", e);
		}
	}
	
	@Transactional(readOnly = true, rollbackForClassName = { "java.lang.Throwable",
	"java.lang.Exception" }, propagation = Propagation.REQUIRED)
	public FiltroOrdenes getPorFiltroId(Long id) throws Exception {
		try {
			Optional<FiltroOrdenes> filtro = filtroOrdenesRepository.findById(id);
			return filtro.get();
		} catch (Exception e) {
			throw new Exception("Error en crearFiltroOrdenes", e);
		}
	}
	
}
