package com.unla.stockearte.endpointSoap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.catalogos.SendFileResponse;
import com.unla.stockearte.helpers.CatalogoHelper;
import com.unla.stockearte.model.Catalogo;
import com.unla.stockearte.service.CatalogoService;
import com.unla.stockearte.service.UsuarioService;

@Endpoint
public class UsuarioEndpoint {

	private static final String NAMESPACE_URI = "http://example.com/catalogos";

	@Autowired
    private UsuarioService usuarioCargaMasivaService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCatalogosRequest")
	@ResponsePayload
	public SendFileResponse getAllCatalogos(@RequestPayload SendFileResponse request) throws Exception {
		
		MultipartFile multipartFile = new MockMultipartFile(
			    "file",                   // Nombre del archivo
			    "nombreArchivo.pdf",       // Nombre que deseas dar al archivo
			    "application/pdf",         // Tipo de contenido MIME
			    fileData                   // El arreglo de bytes con los datos del archivo
			);
		List<String> errores = usuarioCargaMasivaService.procesarArchivoCSV(archivo);

		ListCatalogoResponse response = CatalogoHelper.getCatalogosResponse(catalogos);
		return response;
	}
	
}
