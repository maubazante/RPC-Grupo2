package com.unla.stockearte.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Lob
    private byte[] foto; // La imagen se almacena como un blob

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 5)
    private String talle;

    @ManyToMany(mappedBy = "productos")
    private List<Tienda> tiendas;

    // Getters y setters
}