package com.unla.stockearte.endpointSoap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.filtroordenes.AddFiltroOrdenResponse;
import com.example.filtroordenes.AddFiltroOrdenesRequest;
import com.example.filtroordenes.FiltroOrdenesDeleteRequest;
import com.example.filtroordenes.FiltroOrdenesDeleteResponse;
import com.example.filtroordenes.GetFiltroOrdenRequest;
import com.example.filtroordenes.GetFiltroOrdenResponse;
import com.example.filtroordenes.InformeOrdenCompraRequest;
import com.example.filtroordenes.InformeOrdenCompraResponse;
import com.example.filtroordenes.ListFiltroOrdenesRequest;
import com.example.filtroordenes.ListFiltroOrdenesResponse;
import com.example.filtroordenes.UpdateFiltroOrdenRequest;
import com.example.filtroordenes.UpdateFiltroOrdenResponse;
import com.unla.stockearte.helpers.Helper;
import com.unla.stockearte.model.FiltroOrdenes;
import com.unla.stockearte.service.FiltroOrdenesService;
import com.unla.stockearte.service.OrdenDeCompraService;

@Endpoint
public class FiltroOrdenesEndpoint {

	private static final String NAMESPACE_URI = "http://example.com/filtroordenes";
	
	@Autowired
	@Qualifier(FiltroOrdenesService.BEAN_NAME)
	FiltroOrdenesService filtroService;
	
	@Autowired
	OrdenDeCompraService orderDeCompraService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddFiltroOrdenesRequest")
    @ResponsePayload
    public AddFiltroOrdenResponse addFiltroOrdenes(@RequestPayload AddFiltroOrdenesRequest request) throws Exception {
    	AddFiltroOrdenResponse response = Helper.crearAddFiltroOrdenResponse(filtroService.crearFiltroOrdenes(Helper.crearFiltroOrdenesATravesAddFiltroRequest(request)));
    	return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FiltroOrdenesDeleteRequest")
    @ResponsePayload
    public FiltroOrdenesDeleteResponse deleteFiltroOrdenes(@RequestPayload FiltroOrdenesDeleteRequest request) throws Exception {
    	FiltroOrdenesDeleteResponse response = Helper.crearFiltroOrdenesDeleteResponse(filtroService.eliminarFiltroOrdenes(request.getId().getValue()));
    	return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ListFiltroOrdenesRequest")
    @ResponsePayload
    public ListFiltroOrdenesResponse getListFiltroOrdenes(@RequestPayload ListFiltroOrdenesRequest request) throws Exception{
    	List<FiltroOrdenes> listFiltroOrdenes = filtroService.getFiltroOrdenesPorUsuario(request.getUsuarioId().getValue());
    	ListFiltroOrdenesResponse response = Helper.crearListFiltroOrdenesResponse(listFiltroOrdenes);
    	return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetFiltroOrdenRequest")
    @ResponsePayload
    public GetFiltroOrdenResponse getFiltroOrden(@RequestPayload GetFiltroOrdenRequest request) throws Exception{
    	FiltroOrdenes filtroOrden = filtroService.getPorFiltroId(request.getId().getValue());
    	GetFiltroOrdenResponse response = Helper.crearGetFiltroResponse(filtroOrden);
    	return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateFiltroOrdenRequest")
    @ResponsePayload
    public UpdateFiltroOrdenResponse updateFiltroOrdenResponse(@RequestPayload UpdateFiltroOrdenRequest request) throws Exception{
    	UpdateFiltroOrdenResponse response = Helper.crearupdateFiltroOrdenResponse(filtroService.modificarFiltroOrdenes(Helper.crearFiltroOrdenAtravesUpdateFiltroRequest(request)));
    	return response;
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "InformeOrdenCompraRequest")
    @ResponsePayload
    public InformeOrdenCompraResponse informeOrdenDeCompra(@RequestPayload InformeOrdenCompraRequest request) throws Exception{
    	InformeOrdenCompraResponse response = Helper.crearInformeOrdenCompraResponse(orderDeCompraService.getList());
    	return response;
    }
}
