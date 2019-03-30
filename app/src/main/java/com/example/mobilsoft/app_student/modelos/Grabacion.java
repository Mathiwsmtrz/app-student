package com.example.mobilsoft.app_student.modelos;

public class Grabacion {

    private Integer id;
    private String ruta;
    private String descripcion;
    private String id_usuario;

    public Grabacion(Integer id, String ruta, String descripcion, String id_usuario) {
        this.id = id;
        this.ruta = ruta;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
