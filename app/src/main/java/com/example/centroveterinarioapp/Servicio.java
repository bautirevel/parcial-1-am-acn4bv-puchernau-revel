package com.example.centroveterinarioapp;

import java.io.Serializable;

public class Servicio implements Serializable {

    private String titulo;
    private String descripcion;
    private String detalle;
    private String precio;
    private String imagenUrl;

    public Servicio(String titulo, String descripcion, String detalle, String precio, String imagenUrl) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.detalle = detalle;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getPrecio() {
        return precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}
