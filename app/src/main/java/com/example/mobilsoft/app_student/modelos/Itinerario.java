package com.example.mobilsoft.app_student.modelos;

public class Itinerario {

    private Integer id;
    private String fecha;
    private String hora;
    private String anotacion;
    private String id_usuario;

    public Itinerario(Integer id, String fecha, String hora, String anotacion, String id_usuario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.anotacion = anotacion;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
