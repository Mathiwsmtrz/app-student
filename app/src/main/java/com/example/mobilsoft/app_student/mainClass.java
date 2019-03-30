package com.example.mobilsoft.app_student;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class mainClass {

    private static DecimalFormat df2 = new DecimalFormat(".##");
    public String nota_minima(Integer materia, Context context,String idUsuario) {
        String texto = "";

        ArrayList<Nota> ListaNota = new ArrayList<Nota>();
        Nota nota= null;

        double por_nota1 = 0.20;
        double por_nota2 = 0.30;
        double por_nota3 = 0.50;

        double nota1 = (0);
        double nota2 = (0);
        double nota3 = (0);

        double acomulado = (0);
        double faltante = (0);
        double minimo = (0);

        Boolean ext1 = false;
        Boolean ext2 = false;
        Boolean ext3 = false;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(context, Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select n."+Utilidades.CAMPO_ID_NOTA+",n."+Utilidades.CAMPO_ID_MATERIA+",m."+Utilidades.CAMPO_NOMBRE_MATERIA+",m."+Utilidades.CAMPO_PROFESOR_MATERIA+",n."+Utilidades.CAMPO_NUMERO_NOTAS+",n."+Utilidades.CAMPO_NOTA_NOTAS+",n."+Utilidades.CAMPO_ID_USUARIO_FK+" from "+ Utilidades.TABLA_NOTAS + " n INNER JOIN "+Utilidades.TABLA_MATERIAS+" m on m."+Utilidades.CAMPO_ID_MATERIA+" = n."+Utilidades.CAMPO_ID_MATERIA+" where n." + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "' and n."+Utilidades.CAMPO_ID_MATERIA+"="+materia, null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                nota = new Nota(null, null, null,null,null, null, null);
                nota.setId(c.getInt(0));
                nota.setId_materia(c.getInt(1));
                nota.setNombre_materia(c.getString(2));
                nota.setNombre_profesor(c.getString(3));
                nota.setNumero(c.getInt(4));
                nota.setNota(c.getFloat(5));
                nota.setId_usuario(c.getString(6));

                if(nota.getNumero()==1) {nota1 = nota.getNota(); ext1=true;};
                if(nota.getNumero()==2) {nota2 = nota.getNota(); ext2=true;};
                if(nota.getNumero()==3) {nota3 = nota.getNota(); ext3=true;};

                ListaNota.add(nota);
            }
        }

        acomulado = (nota1*por_nota1) + (nota2*por_nota2) + (nota3*por_nota3);

        if(acomulado < 3 && ext1 && ext2){
            faltante = (3-acomulado);
            minimo = (faltante / por_nota3);
            if(minimo<=5) texto= ((String) ("La nota minima a sacar es de " + df2.format(minimo) + " en la Nota 3"));
            else texto = "La materia esta perdida(El maximo a sacar en esta nota es inferior al necesario para llegar a 3)";
        }else if(acomulado < 3 && ext1 && ext3){
            faltante = (3-acomulado);
            minimo = (faltante / por_nota2);
            if(minimo<=5) texto= ((String) ("La nota minima a sacar es de " + df2.format(minimo) + " en la Nota 2"));
            else texto = "La materia esta perdida(El maximo a sacar en esta nota es inferior al necesario para llegar a 3)";
        }else if(acomulado < 3 && ext2 && ext3){
            faltante = (3-acomulado);
            minimo = (faltante / por_nota1);
            if(minimo<=5) texto= ((String) ("La nota minima a sacar es de " + df2.format(minimo) + " en la Nota 1"));
            else texto = "La materia esta perdida(El maximo a sacar en esta nota es inferior al necesario para llegar a 3)";
        }else{
            texto="";
        }

        return texto;
    }
}
