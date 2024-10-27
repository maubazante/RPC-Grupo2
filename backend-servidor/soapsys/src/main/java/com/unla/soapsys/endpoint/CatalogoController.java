package com.unla.soapsys.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.catalogos.GetAllCatalogosRequest;
import com.example.catalogos.ListCatalogoResponse;
import com.example.filtroordenes.AddFiltroOrdenResponse;
import com.example.filtroordenes.FiltroOrdenesDTO;
import com.example.filtroordenes.FiltroOrdenesDeleteResponse;
import com.example.filtroordenes.GetFiltroOrdenResponse;
import com.example.filtroordenes.ListFiltroOrdenesResponse;
import com.example.filtroordenes.UpdateFiltroOrdenResponse;
import com.unla.soapsys.helper.CatalogoHelper;
import com.unla.soapsys.helper.Helper;
import com.unla.soapsys.request.FiltroOrdenesRequest;
import com.unla.soapsys.response.FiltroOrdenes;
import com.unla.soapsys.response.Catalogo;
import com.unla.soapsys.response.CatalogoDTOFronted;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "http://localhost:4200")
public class CatalogoController {
	
	@Autowired
	@Qualifier("webServiceTemplateCatalogo")
    private WebServiceTemplate webServiceTemplate;


	@GetMapping
	public ResponseEntity<List<CatalogoDTOFronted>> getAllCatalogos(@RequestParam String username) {
		ListCatalogoResponse listCatalogoResponse = (ListCatalogoResponse) webServiceTemplate.marshalSendAndReceive(CatalogoHelper.getCatalogosRequest(username));
		List<CatalogoDTOFronted> resp = CatalogoHelper.getCatalogos(listCatalogoResponse);
		return new ResponseEntity<List<CatalogoDTOFronted>>(resp, HttpStatus.OK);
	}
}
