package com.unla.soapsys.helper;

import java.util.ArrayList;
import java.util.List;

import com.example.catalogos.CatalogoDTO;
import com.example.catalogos.CatalogoDTO.ProductoIds;
import com.example.catalogos.CrearCatalogoRequest;
import com.example.catalogos.CrearCatalogoResponse;
import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ObjectFactory;
import com.example.catalogos.ObtenerProductoPorCatalogoRequest;
import com.example.catalogos.ObtenerProductoPorCatalogoResponse;
import com.example.catalogos.ProductoDTO;
import com.unla.soapsys.response.Catalogo;
import com.unla.soapsys.response.CatalogoDTOFronted;
import com.unla.soapsys.response.Producto;

public class CatalogoHelper {


	public static GetAllCatalogosRequest getCatalogosRequest(String username) {
		GetAllCatalogosRequest getAllCatalogosRequest = new GetAllCatalogosRequest();
		getAllCatalogosRequest.setUsername(username);
		return getAllCatalogosRequest;
	}

	public static List<CatalogoDTOFronted> getCatalogos(ListCatalogoResponse listCatalogoResponse) {
		List<CatalogoDTOFronted> catalogoDTOFronted = new ArrayList<>();

		for (CatalogoDTO catalogo : listCatalogoResponse.getCatalogos()) {
			CatalogoDTOFronted catalogoFronted = new CatalogoDTOFronted();
			catalogoFronted.setNombre(catalogo.getNombre());
			catalogoFronted.setIdTienda(catalogo.getTiendaId().getValue());
			catalogoFronted.setId(catalogo.getId().getValue());
			catalogoFronted.setProductosIds(catalogo.getProductoIds().getProductoId());

			catalogoDTOFronted.add(catalogoFronted);
		}

		return catalogoDTOFronted;
	}
	
	public static ObtenerProductoPorCatalogoRequest crearObtenerProductoPorCatalogoRequest(Long catalogoId) {
		ObjectFactory factory = new ObjectFactory();
		ObtenerProductoPorCatalogoRequest obtenerProductoPorCatalogoRequest = new ObtenerProductoPorCatalogoRequest();
		
		obtenerProductoPorCatalogoRequest.setCatalogId(factory.createObtenerProductoPorCatalogoRequestCatalogId(catalogoId));
		return obtenerProductoPorCatalogoRequest;
	}
	
	public static List<Producto> obtenerProductoCatalogoResponseToProductos(ObtenerProductoPorCatalogoResponse response){
		List<Producto> list = new ArrayList<>();
		
		for(ProductoDTO producto : response.getProductos()) {
			Producto newProducto = new Producto();
			newProducto.setCantidad(producto.getCantidad().getValue().intValue());
			newProducto.setCodigo(producto.getCodigo());
			newProducto.setColor(producto.getColor());
			newProducto.setFoto(producto.getFoto());
			newProducto.setHabilitado(producto.isHabilitado());
			newProducto.setId(producto.getId().getValue());
			newProducto.setNombre(producto.getNombre());
			newProducto.setTalle(producto.getTalle());
			
			list.add(newProducto);
		}
		return list;
	}
	
	public static CrearCatalogoRequest crearCatalogoRequest(com.unla.soapsys.response.CatalogoDTO catalogo, String username) {
		ObjectFactory factory = new ObjectFactory();
		CrearCatalogoRequest request = new CrearCatalogoRequest();
		CatalogoDTO catalogoDTO = new CatalogoDTO();
		catalogoDTO.setNombre(catalogo.getNombre());
		catalogoDTO.setTiendaId(factory.createCatalogoDTOTiendaId(catalogo.getTiendaId()));
		catalogoDTO.setUserName(username);
		
		ProductoIds productoIds = new ProductoIds();
		for(Long productoId : catalogo.getProductoIds()) {
			productoIds.getProductoId().add(productoId);
		}
		catalogoDTO.setProductoIds(productoIds);
		
		
		request.setCatalogo(catalogoDTO);
		return request;
	}
	
	public static Catalogo crearCatalogo(CrearCatalogoResponse catalogoResponse) {
		Catalogo catalogo = new Catalogo();
		catalogo.setId(catalogoResponse.getCatalogo().getId().getValue());
		catalogo.setNombre(catalogoResponse.getCatalogo().getNombre());
		return catalogo;
	}

}
