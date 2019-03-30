package com.example.mobilsoft.app_student.modelos;

public class Materia {

    private Integer id;
    private String nombres;
    private String profesor;
    private String descripcion;
    private String id_usuario;

    public Materia(Integer id, String nombres, String profesor, String descripcion, String id_usuario) {
        this.id = id;
        this.nombres = nombres;
        this.profesor = profesor;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
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
