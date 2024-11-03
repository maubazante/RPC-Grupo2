package com.unla.stockearte.helpers;

import java.math.BigInteger;
import java.util.List;

import com.example.catalogos.CatalogoDTO;
import com.example.catalogos.CatalogoDTO.ProductoIds;
import com.example.catalogos.CrearCatalogoRequest;
import com.example.catalogos.CrearCatalogoResponse;
import com.example.catalogos.EliminarCatalogoResponse;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.ModificarCatalogoResponse;
import com.example.catalogos.ObtenerProductoPorCatalogoResponse;
import com.example.catalogos.ProductoDTO;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.model.CatalogoProducto;
import com.unla.stockearte.model.Producto;

public class CatalogoHelper {

	public static ListCatalogoResponse getCatalogosResponse(List<Catalogo> catalogos) {
		com.example.catalogos.ObjectFactory factory = new com.example.catalogos.ObjectFactory();
		ListCatalogoResponse listCatalogoResponse = new ListCatalogoResponse();

		for (Catalogo catalogo : catalogos) {
			CatalogoDTO catalogoDTO = new CatalogoDTO();
			ProductoIds productoIds = new ProductoIds();
			catalogoDTO.setProductoIds(productoIds);
			catalogoDTO.setId(factory.createCatalogoDTOId(catalogo.getId()));
			catalogoDTO.setNombre(catalogo.getNombre());
			catalogoDTO.setTiendaId(factory.createCatalogoDTOTiendaId(catalogo.getTienda().getId()));

			for (CatalogoProducto catalogoProducto : catalogo.getCatalogoProductos()) {
				catalogoDTO.getProductoIds().getProductoId().add(catalogoProducto.getProducto().getId());
			}

			listCatalogoResponse.getCatalogos().add(catalogoDTO);
		}

		return listCatalogoResponse;
	}

	public static ObtenerProductoPorCatalogoResponse productosToObtenerProductoPorCatalogoResponse(
			List<Producto> productos) {
		com.example.catalogos.ObjectFactory factory = new com.example.catalogos.ObjectFactory();
		ObtenerProductoPorCatalogoResponse obtenerProductoPorCatalogoResponse = new ObtenerProductoPorCatalogoResponse();
		for (Producto producto : productos) {
			ProductoDTO productoDTO = new ProductoDTO();
			productoDTO.setCantidad(
					factory.createProductoDTOCantidad(BigInteger.valueOf(producto.getCantidad().longValue())));
			productoDTO.setCodigo(producto.getCodigo());
			productoDTO.setColor(producto.getColor());
			productoDTO.setFoto(producto.getFoto());
			productoDTO.setHabilitado(producto.isHabilitado());
			productoDTO.setId(factory.createCatalogoDTOId(producto.getId()));
			productoDTO.setNombre(producto.getNombre());
			productoDTO.setTalle(producto.getTalle());

			obtenerProductoPorCatalogoResponse.getProductos().add(productoDTO);
		}
		return obtenerProductoPorCatalogoResponse;
	}

	public static com.unla.stockearte.dto.CatalogoDTO catalogoCreateRequestToCatalogo(CrearCatalogoRequest request) {
		com.unla.stockearte.dto.CatalogoDTO catalogo = new com.unla.stockearte.dto.CatalogoDTO();
		catalogo.setNombre(request.getCatalogo().getNombre());
		catalogo.setTiendaId(request.getCatalogo().getTiendaId().getValue());
		catalogo.setProductoIds(request.getCatalogo().getProductoIds().getProductoId());
		return catalogo;
	}

	public static CrearCatalogoResponse crearCatalogoResponse(Catalogo catalogo) {
		com.example.catalogos.ObjectFactory factory = new com.example.catalogos.ObjectFactory();
		CrearCatalogoResponse catagaloResponse = new CrearCatalogoResponse();
		CatalogoDTO catalogoDTO = new CatalogoDTO();
		catalogoDTO.setNombre(catalogo.getNombre());
		catalogoDTO.setTiendaId(factory.createCatalogoDTOTiendaId(catalogo.getTienda().getId()));
		catalogoDTO.setId(factory.createCatalogoDTOId(catalogo.getId()));

		catagaloResponse.setCatalogo(catalogoDTO);
		return catagaloResponse;
	}

	public static com.unla.stockearte.dto.CatalogoDTO createCatalogoDTO(CatalogoDTO catalogoDTO) {
		com.unla.stockearte.dto.CatalogoDTO catalogo = new com.unla.stockearte.dto.CatalogoDTO();
		catalogo.setNombre(catalogoDTO.getNombre());
		catalogo.setTiendaId(catalogoDTO.getTiendaId().getValue());

		for (Long productoid : catalogoDTO.getProductoIds().getProductoId()) {
			catalogo.getProductoIds().add(productoid);
		}

		return catalogo;

	}

	public static ModificarCatalogoResponse crearModificarCatalogoResponse(Catalogo catalogo) {
		com.example.catalogos.ObjectFactory factory = new com.example.catalogos.ObjectFactory();
		ModificarCatalogoResponse modificarCatalogoResponse = new ModificarCatalogoResponse();
		CatalogoDTO catalogoDTO = new CatalogoDTO();
		catalogoDTO.setNombre(catalogo.getNombre());
		catalogoDTO.setId(factory.createCatalogoDTOId(catalogo.getId()));

		modificarCatalogoResponse.setCatalogo(catalogoDTO);

		return modificarCatalogoResponse;
	}
	
	public static EliminarCatalogoResponse crearEliminarCatalogoResponse(Long oid) {
		EliminarCatalogoResponse modificarCatalogoResponse = new EliminarCatalogoResponse();
		modificarCatalogoResponse.setMessage("Catálogo con id " + oid.toString() + " eliminado exitosamente");
		
		return modificarCatalogoResponse;
	}

}
