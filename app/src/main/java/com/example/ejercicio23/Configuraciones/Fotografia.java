package com.example.ejercicio23.Configuraciones;

public class Fotografia {
    private Integer id;
    private String descripcion;
    private byte[] foto;

    public Fotografia() {
    }

    public Fotografia(Integer id, String descripcion, byte[] foto) {
        this.id = id;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
