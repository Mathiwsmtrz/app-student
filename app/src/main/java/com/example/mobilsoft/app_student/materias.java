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

import com.example.mobilsoft.app_student.Adapters.adapter_materia;
import com.example.mobilsoft.app_student.Fragments.materiasFormulario;
import com.example.mobilsoft.app_student.interfaces.interfaceMateria;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Materia;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class materias extends AppCompatActivity  implements DialogInterface.OnDismissListener,interfaceMateria {

    String idUsuario;
    List<Materia> ListaMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materias);

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        consultarMaterias();
    }

    public void renderList(){
        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor_materias);
        contenedor.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        contenedor.setAdapter(new adapter_materia(ListaMateria));
        contenedor.setLayoutManager(layout);

    }

    public void consultarMaterias(){
        ListaMateria = new ArrayList<Materia>();
        Materia materia= null;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+ Utilidades.TABLA_MATERIAS + " where " + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "'", null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                materia = new Materia(null, null, null, null, null);
                materia.setId_usuario(idUsuario);
                materia.setId(c.getInt(0));
                materia.setNombres(c.getString(1));
                materia.setProfesor(c.getString(2));
                materia.setDescripcion(c.getString(3));

                ListaMateria.add(materia);
            }
        }
        renderList();
    }

    public void nuevaMateria(View view){
        Materia materia = new Materia(null, null, null, null, null);
        openForm(materia);
    }

    public void openForm(Materia data){
        FragmentManager fm = getSupportFragmentManager();
        materiasFormulario alertDialog = materiasFormulario.newInstance("Some title",data);
        alertDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        consultarMaterias();
    }

    @Override
    public void onItemClickRemoveMateria(Materia data) {
        Toast.makeText(getApplicationContext(), data.getNombres() + " fue eliminado del itinerario.", Toast.LENGTH_SHORT).show();
        consultarMaterias();
    }

    @Override
    public void onItemClickEditMateria(Materia data) {
        openForm(data);
    }
}
