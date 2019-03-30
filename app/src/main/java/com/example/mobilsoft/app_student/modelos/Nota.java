package com.example.mobilsoft.app_student.modelos;

public class Nota {

    private Integer id;
    private Integer id_materia;
    private Integer numero;
    private String nombre_materia;
    private String nombre_profesor;
    private Float nota;
    private String id_usuario;

    public Nota(Integer id, Integer id_materia, Integer numero, String nombre_materia, String nombre_profesor, Float nota, String id_usuario) {
        this.id = id;
        this.id_materia = id_materia;
        this.numero = numero;
        this.nombre_materia = nombre_materia;
        this.nombre_profesor = nombre_profesor;
        this.nota = nota;
        this.id_usuario = id_usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_materia() {
        return id_materia;
    }

    public void setId_materia(Integer id_materia) {
        this.id_materia = id_materia;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getNombre_profesor() {
        return nombre_profesor;
    }

    public void setNombre_profesor(String nombre_profesor) {
        this.nombre_profesor = nombre_profesor;
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
