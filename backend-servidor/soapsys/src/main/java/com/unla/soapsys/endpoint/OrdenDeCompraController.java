package com.unla.soapsys.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.filtroordenes.InformeOrdenCompraRequest;
import com.example.filtroordenes.InformeOrdenCompraResponse;
import com.unla.soapsys.helper.Helper;
import com.unla.soapsys.response.InformeOrdenCompraDTO;

@RestController
@RequestMapping("/api/ordenDeCompra")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenDeCompraController {

	@Autowired
    private WebServiceTemplate webServiceTemplate;
	
	@GetMapping(path = "/list")
	public ResponseEntity<List<InformeOrdenCompraDTO>> getListOrdenDeCompra() {
		InformeOrdenCompraResponse informeOrdenCompraResponse = (InformeOrdenCompraResponse) webServiceTemplate.marshalSendAndReceive(new InformeOrdenCompraRequest());
		List<InformeOrdenCompraDTO> response = Helper.crearInformesOrdenCompraDTO(informeOrdenCompraResponse);
		return new ResponseEntity<List<InformeOrdenCompraDTO>>(response, HttpStatus.OK);
	}
	
}
