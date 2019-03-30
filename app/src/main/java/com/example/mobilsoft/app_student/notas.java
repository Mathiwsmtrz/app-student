package com.example.mobilsoft.app_student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mobilsoft.app_student.Adapters.adapter_nota;
import com.example.mobilsoft.app_student.Fragments.materiasNotas;
import com.example.mobilsoft.app_student.interfaces.interfaceNota;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class notas extends AppCompatActivity   implements DialogInterface.OnDismissListener,interfaceNota {

    String idUsuario;
    List<Nota> ListaNota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        consultarNotas();
    }

    public void renderList(){
        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor_notas);
        contenedor.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        contenedor.setAdapter(new adapter_nota(ListaNota));
        contenedor.setLayoutManager(layout);

    }

    public void consultarNotas(){
        ListaNota = new ArrayList<Nota>();
        Nota nota= null;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select n."+Utilidades.CAMPO_ID_NOTA+",n."+Utilidades.CAMPO_ID_MATERIA+",m."+Utilidades.CAMPO_NOMBRE_MATERIA+",m."+Utilidades.CAMPO_PROFESOR_MATERIA+",n."+Utilidades.CAMPO_NUMERO_NOTAS+",n."+Utilidades.CAMPO_NOTA_NOTAS+",n."+Utilidades.CAMPO_ID_USUARIO_FK+" from "+ Utilidades.TABLA_NOTAS + " n INNER JOIN "+Utilidades.TABLA_MATERIAS+" m on m."+Utilidades.CAMPO_ID_MATERIA+" = n."+Utilidades.CAMPO_ID_MATERIA+" where n." + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "' ORDER BY m."+Utilidades.CAMPO_NOMBRE_MATERIA+" ASC, n."+Utilidades.CAMPO_NUMERO_NOTAS+" ASC" , null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                nota = new Nota(null, null, null,null,null, null, null);
                nota.setId_usuario(idUsuario);
                nota.setId(c.getInt(0));
                nota.setId_materia(c.getInt(1));
                nota.setNombre_materia(c.getString(2));
                nota.setNombre_profesor(c.getString(3));
                nota.setNumero(c.getInt(4));
                nota.setNota(c.getFloat(5));

                ListaNota.add(nota);
            }
        }
        renderList();
    }

    public void nuevaNota(View view){
        Nota nota = new Nota(null, null, null,null,null, null, null);
        openForm(nota);
    }

    public void openForm(Nota nota){
        FragmentManager fm = getSupportFragmentManager();
        materiasNotas alertDialog = materiasNotas.newInstance("Some title",nota);
        alertDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        consultarNotas();
    }

    @Override
    public void onItemClickRemoveNota(Nota data) {
        Toast.makeText(getApplicationContext(), "Esta nota fue eliminada.", Toast.LENGTH_SHORT).show();
        consultarNotas();
    }

    @Override
    public void onItemClickEditNota(Nota data) {
        openForm(data);
    }
}
