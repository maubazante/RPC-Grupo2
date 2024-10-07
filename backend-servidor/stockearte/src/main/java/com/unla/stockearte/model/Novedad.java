package com.unla.stockearte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "novedades")
public class Novedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_producto", nullable = false)
    private String codigoProducto;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

	@Column(name = "talle", nullable = false)
    private String talle;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "url_foto", length = 300)
    private String urlFoto;

    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getTalle() { return talle; }
    public void setTalle(String talle) { this.talle = talle; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public Boolean getHabilitado() { return habilitado; }
    public void setHabilitado(Boolean habilitado) { this.habilitado = habilitado; }
    
    public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
