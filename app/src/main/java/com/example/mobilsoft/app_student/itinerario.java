package com.example.mobilsoft.app_student;

import android.app.Application;
import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilsoft.app_student.Adapters.adapter_itinerario;
import com.example.mobilsoft.app_student.Fragments.itinerarioFormulario;
import com.example.mobilsoft.app_student.interfaces.interfaceItinerario;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Itinerario;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class itinerario extends AppCompatActivity  implements DialogInterface.OnDismissListener,interfaceItinerario , View.OnClickListener{

    String idUsuario;
    List<Itinerario> ListaItinerario;
    private static final String CERO = "0";
    private static final String BARRA = "/";
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    EditText fecha_inicioEditText,fecha_finEditText;
    Button btn_filtrardata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerario);

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        fecha_finEditText = (EditText) findViewById(R.id.fecha_fin);
        fecha_inicioEditText = (EditText) findViewById(R.id.fecha_inicio);
        btn_filtrardata = (Button) findViewById(R.id.buscar_datos_tareas);

        fecha_inicioEditText.setKeyListener(null);
        fecha_finEditText.setKeyListener(null);

        fecha_inicioEditText.setOnClickListener(this);
        fecha_finEditText.setOnClickListener(this);
        btn_filtrardata.setOnClickListener(this);

        consultarItinerario();
    }

    public void renderList(){
        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor_itinerarios);
        contenedor.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        contenedor.setAdapter(new adapter_itinerario(ListaItinerario));
        contenedor.setLayoutManager(layout);

    }

    public void consultarItinerario(){
        ListaItinerario = new ArrayList<Itinerario>();
        Itinerario itinerario= null;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();

        String inicio = fecha_inicioEditText.getText().toString();
        String fin = fecha_finEditText.getText().toString();

        if(inicio.isEmpty()){inicio=Utilidades.CAMPO_FECHA_ITINERARIO;}else inicio = "'"+inicio+"'";
        if(fin.isEmpty()){fin=Utilidades.CAMPO_FECHA_ITINERARIO;} else fin = "'"+fin+"'";

        Cursor c = db.rawQuery("select * from "+Utilidades.TABLA_ITINERARIO + " where " + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "' and "+Utilidades.CAMPO_FECHA_ITINERARIO+" >= "+inicio+" and "+Utilidades.CAMPO_FECHA_ITINERARIO+" <= " +fin, null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                itinerario = new Itinerario(null, null, null, null, null);
                itinerario.setId_usuario(idUsuario);
                itinerario.setId(c.getInt(0));
                itinerario.setFecha(c.getString(1));
                itinerario.setHora(c.getString(2));
                itinerario.setAnotacion(c.getString(3));

                ListaItinerario.add(itinerario);
            }
        }
        renderList();
    }

    public void obtenerFechaInicio(View v){
        DatePickerDialog recogerFecha = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fecha_inicioEditText.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },anio, mes, dia);
        recogerFecha.show();
    }

    public void obtenerFechaFin(View v){
        DatePickerDialog recogerFecha = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fecha_finEditText.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },anio, mes, dia);
        recogerFecha.show();
    }


    public void nuevaTarea(View view){
        Itinerario itinerario = new Itinerario(null, null, null, null, null);
        openForm(itinerario);
    }
    public void openForm(Itinerario data){
        FragmentManager fm = getSupportFragmentManager();
        itinerarioFormulario alertDialog = itinerarioFormulario.newInstance("Some title",data);
        alertDialog.show(fm, "fragment_alert");
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        consultarItinerario();
    }

    @Override
    public void onItemClickRemoveItinerario(Itinerario data) {
        Toast.makeText(getApplicationContext(), data.getAnotacion() + " fue eliminado del itinerario.", Toast.LENGTH_SHORT).show();
        consultarItinerario();
    }

    @Override
    public void onItemClickEditItinerario(Itinerario data) {
        openForm(data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fecha_inicio:
                obtenerFechaInicio(v);
                break;
            case R.id.fecha_fin:
                obtenerFechaFin(v);
                break;
            case R.id.buscar_datos_tareas:
                consultarItinerario();
                break;
        }
    }
}
