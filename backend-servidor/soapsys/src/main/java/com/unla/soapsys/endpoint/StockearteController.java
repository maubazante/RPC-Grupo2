package com.unla.soapsys.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.filtroordenes.AddFiltroOrdenResponse;
import com.example.filtroordenes.FiltroOrdenesDeleteResponse;
import com.example.filtroordenes.GetFiltroOrdenResponse;
import com.example.filtroordenes.ListFiltroOrdenesResponse;
import com.example.filtroordenes.UpdateFiltroOrdenResponse;
import com.unla.soapsys.helper.Helper;
import com.unla.soapsys.request.FiltroOrdenesRequest;
import com.unla.soapsys.response.FiltroOrdenes;

@RestController
@RequestMapping("/api/filtroOrdenCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class StockearteController {
	
	@Autowired
    private WebServiceTemplate webServiceTemplate;

	@PostMapping
	public ResponseEntity<FiltroOrdenes> addFiltroOrdenes(@RequestBody FiltroOrdenesRequest request) throws Exception {
		AddFiltroOrdenResponse addFiltroOrdenResponse = (AddFiltroOrdenResponse) webServiceTemplate.marshalSendAndReceive(Helper.crearAddFiltroOrdenesRequest(request));
		FiltroOrdenes response = Helper.createFiltroOrdenesAtravesDeAddFiltroOrdenesRequest(addFiltroOrdenResponse);
		return new ResponseEntity<FiltroOrdenes>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteFiltroOrdenes(@PathVariable Long id) throws Exception {
		FiltroOrdenesDeleteResponse resp = (FiltroOrdenesDeleteResponse) webServiceTemplate.marshalSendAndReceive(Helper.crearFiltroOrdenesDeleteRequest(id));
		return new ResponseEntity<Boolean>(resp.getResponse().getValue(), HttpStatus.OK);
	}

	@GetMapping(path = "/list/{usuarioId}")
	public ResponseEntity<List<FiltroOrdenes>> getListFiltroOrdenes(@PathVariable Long usuarioId) throws Exception {
		ListFiltroOrdenesResponse filtroResponse = (ListFiltroOrdenesResponse) webServiceTemplate.marshalSendAndReceive(Helper.crearListFiltroOrdenesRequest(usuarioId));
		List<FiltroOrdenes> resp = Helper.listFiltroOrdenesAtravesDeListFiltroOrdenesRequest(filtroResponse);
		return new ResponseEntity<List<FiltroOrdenes>>(resp, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FiltroOrdenes> getFiltroOrdenes(@PathVariable Long id) throws Exception {
		GetFiltroOrdenResponse filtroResponse = (GetFiltroOrdenResponse) webServiceTemplate.marshalSendAndReceive(Helper.crearGetFiltroOrdenRequest(id));
		FiltroOrdenes response = Helper.filtroOrdenesDTOToFiltroOrdenes(filtroResponse.getFiltroOrdenes().getValue());
		return new ResponseEntity<FiltroOrdenes>(response, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<FiltroOrdenes> updateFiltroOrdenes(@RequestBody FiltroOrdenesRequest request) throws Exception {
		UpdateFiltroOrdenResponse updateFiltroOrdenResponse = (UpdateFiltroOrdenResponse) webServiceTemplate.marshalSendAndReceive(Helper.crearUpdateFiltroOrdenRequest(request));
	    FiltroOrdenes filtroOrden = Helper.crearFiltroOrdenesAtravesDeUpdateFiltroOrdenResponse(updateFiltroOrdenResponse);
	    return new ResponseEntity<FiltroOrdenes>(filtroOrden, HttpStatus.OK);
	}
}
