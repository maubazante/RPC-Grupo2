package com.unla.soapsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class SoapClienteConfig {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.example.filtroordenes");
		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
		WebServiceTemplate template = new WebServiceTemplate();
		template.setMarshaller(marshaller);
		template.setUnmarshaller(marshaller);
		template.setDefaultUri("http://localhost:8080/ws");
		return template;
	}

	@Bean
	public Jaxb2Marshaller marshallerCatalogos() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.example.catalogos");
		return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplateCatalogo(Jaxb2Marshaller marshallerCatalogos) {
		WebServiceTemplate template = new WebServiceTemplate();
		template.setMarshaller(marshallerCatalogos);
		template.setUnmarshaller(marshallerCatalogos);
		template.setDefaultUri("http://localhost:8080/ws");
		return template;
	}
	@Bean
	public Jaxb2Marshaller marshallerUsuarios() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    marshaller.setContextPath("com.example.usuarios");
	    return marshaller;
	}

	@Bean
	public WebServiceTemplate webServiceTemplateCargaUsuario(Jaxb2Marshaller marshallerUsuarios) {
	    WebServiceTemplate template = new WebServiceTemplate();
	    template.setMarshaller(marshallerUsuarios);
	    template.setUnmarshaller(marshallerUsuarios);
	    template.setDefaultUri("http://localhost:8080/ws");
	    return template;
	}

}
