package com.unla.soapsys.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String codigo;
    private String talle;
    private String foto;
    private String color;
    private int cantidad;

    // Getters y setters...
}
