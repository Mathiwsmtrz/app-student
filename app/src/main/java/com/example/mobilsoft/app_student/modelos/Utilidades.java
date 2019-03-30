package com.example.mobilsoft.app_student.modelos;

public class Utilidades {

    public static final String CAMPO_ID_USUARIO_FK = "id_usuario";

    public static final String TABLA_ITINERARIO = "itinerario";
    public static final String CAMPO_ID_ITINERARIO = "id_itinerario";
    public static final String CAMPO_FECHA_ITINERARIO = "fecha";
    public static final String CAMPO_HORA_ITINERARIO = "hora";
    public static final String CAMPO_ANOTACION_ITINERARIO = "anotacion";
    public static final String CREAR_TABLA_ITINERARIO = "CREATE TABLE "+TABLA_ITINERARIO+
        "("+CAMPO_ID_ITINERARIO+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CAMPO_FECHA_ITINERARIO+" STRING, "+
            CAMPO_HORA_ITINERARIO+" STRING, "+
            CAMPO_ANOTACION_ITINERARIO+" STRING, "+
            CAMPO_ID_USUARIO_FK+" VARCHAR(15))";

    public static final String TABLA_MATERIAS = "materias";
    public static final String CAMPO_ID_MATERIA = "id_materia";
    public static final String CAMPO_NOMBRE_MATERIA= "nombre";
    public static final String CAMPO_PROFESOR_MATERIA = "profesor";
    public static final String CAMPO_DESCRIPCION_MATERIA = "descripcion";
    public static final String CREAR_TABLA_MATERIAS = "CREATE TABLE "+TABLA_MATERIAS+
            "("+CAMPO_ID_MATERIA+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CAMPO_NOMBRE_MATERIA+" VARCHAR(150), "+
            CAMPO_PROFESOR_MATERIA+" VARCHAR(150), "+
            CAMPO_DESCRIPCION_MATERIA+" VARCHAR(150), "+
            CAMPO_ID_USUARIO_FK+" VARCHAR(15))";

    public static final String TABLA_NOTAS = "materias_nota";
    public static final String CAMPO_ID_NOTA = "id_nota";
    public static final String CAMPO_NOTA_NOTAS = "nota";
    public static final String CAMPO_NUMERO_NOTAS = "numero";
    public static final String CREAR_TABLA_NOTAS = "CREATE TABLE "+TABLA_NOTAS+
            "("+CAMPO_ID_NOTA+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CAMPO_ID_MATERIA+" INTEGER, "+
            CAMPO_NOTA_NOTAS+" DECIMAL(10, 5), "+
            CAMPO_NUMERO_NOTAS+" INTEGER, "+
            CAMPO_ID_USUARIO_FK+" VARCHAR(15))";

    public static final String TABLA_GRABACIONES = "grabaciones";
    public static final String CAMPO_ID_GRABACION = "id_grabacion";
    public static final String CAMPO_RUTA_GRABACION = "ruta";
    public static final String CAMPO_DESCRIPCION_GRABACION = "descripcion";
    public static final String CREAR_TABLA_GRABACIONES = "CREATE TABLE "+TABLA_GRABACIONES+
            "("+CAMPO_ID_GRABACION+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            CAMPO_RUTA_GRABACION+" VARCHAR(1000), "+
            CAMPO_DESCRIPCION_GRABACION+" VARCHAR(250), "+
            CAMPO_ID_USUARIO_FK+" VARCHAR(15))";

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String CAMPO_ID_USUARIO = "identificacion";
    public static final String CAMPO_NOMBRES_USUARIO = "nombres";
    public static final String CAMPO_APELLIDOS_USUARIO = "apellidos";
    public static final String CAMPO_USUARIO_USUARIO = "usuario";
    public static final String CAMPO_CONTRASENA_USUARIO = "contrasena";
    public static final String CREAR_TABLA_USUARIOS = "CREATE TABLE "+TABLA_USUARIOS+
            "("+CAMPO_ID_USUARIO+" VARCHAR(15),"+
            CAMPO_NOMBRES_USUARIO+" VARCHAR(150), "+
            CAMPO_APELLIDOS_USUARIO+" VARCHAR(150), "+
            CAMPO_USUARIO_USUARIO+" VARCHAR(15), "+
            CAMPO_CONTRASENA_USUARIO+" VARCHAR(15) )";

    public static final Integer VERSION_BD = 3;
    public static final String NOMBRE_BD = "bd_app_student";
}
