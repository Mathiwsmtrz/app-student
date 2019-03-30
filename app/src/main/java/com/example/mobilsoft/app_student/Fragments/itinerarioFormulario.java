package com.example.mobilsoft.app_student.Fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Itinerario;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class itinerarioFormulario  extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    //Variables para obtener la fecha
    private static final String CERO = "0";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    Boolean update;

    EditText fechaEditText, horaEditText, anotacionEditText;
    String fechaVal, horaVal, anotacionVal,idUsuario;
    Button btn_obtener_fecha,btn_obtener_hora,btn_guardar_tarea;

    public itinerarioFormulario() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        int myInteger = getArguments().getInt("anIntToSend");
        View view = inflater.inflate(R.layout.fragment_itinerario_formulario, null);

        return view;
    }

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        fechaEditText = (EditText) getView().findViewById(R.id.txt_fecha_tarea);
        horaEditText = (EditText) getView().findViewById(R.id.txt_hora_tarea);
        anotacionEditText = (EditText) getView().findViewById(R.id.txt_anotacion_tarea);
        btn_obtener_fecha = (Button) getView().findViewById(R.id.btn_obtener_fecha);
        btn_obtener_hora = (Button) getView().findViewById(R.id.btn_obtener_hora);
        btn_guardar_tarea = (Button) getView().findViewById(R.id.btn_guardar_tarea);

        fechaEditText.setKeyListener(null);
        horaEditText.setKeyListener(null);
        btn_obtener_fecha.setOnClickListener(this);
        btn_obtener_hora.setOnClickListener(this);
        btn_guardar_tarea.setOnClickListener(this);


        Integer idItem = getArguments().getInt("idTarea",0);
        if(idItem!=0){
            update = true;
            fechaEditText.setText(getArguments().getString("fecha",""));
            horaEditText.setText(getArguments().getString("hora",""));
            anotacionEditText.setText(getArguments().getString("anotacion",""));
        }else{
            update = false;
        }
    }


    public static itinerarioFormulario newInstance(String title, Itinerario data) {
        itinerarioFormulario frag = new itinerarioFormulario();
        Bundle args = new Bundle();
        args.putString("title", title);
        if(data.getId() != null) {
            args.putInt("idTarea", data.getId());
            args.putString("fecha", data.getFecha());
            args.putString("hora", data.getHora());
            args.putString("anotacion", data.getAnotacion());
            args.putString("idUsuario", data.getId_usuario());
        }
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_obtener_fecha:
                obtenerFecha();
                break;
            case R.id.btn_obtener_hora:
                obtenerHora();
                break;
            case R.id.btn_guardar_tarea:
                guardarTarea();
                break;
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                fechaEditText.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
        },anio, mes, dia);
        recogerFecha.show();

    }
    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                horaEditText.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
        }, hora, minuto, false);
        recogerHora.show();
    }

    public void guardarTarea(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db =conn.getWritableDatabase();

        fechaVal = fechaEditText.getText().toString();
        horaVal = horaEditText.getText().toString();
        anotacionVal = anotacionEditText.getText().toString();

        if(!fechaVal.isEmpty() && !horaVal.isEmpty() && !anotacionVal.isEmpty()){
            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_FECHA_ITINERARIO, fechaVal);
            values.put(Utilidades.CAMPO_HORA_ITINERARIO, horaVal);
            values.put(Utilidades.CAMPO_ANOTACION_ITINERARIO, anotacionVal);
            values.put(Utilidades.CAMPO_ID_USUARIO_FK, idUsuario);
            if(!update) {
                Long idRes = db.insert(Utilidades.TABLA_ITINERARIO, Utilidades.CAMPO_ID_ITINERARIO, values);
                Toast.makeText(getContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                db.update(Utilidades.TABLA_ITINERARIO, values, Utilidades.CAMPO_ID_ITINERARIO +"="+ getArguments().getInt("idTarea",0),null);
                Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            }

            fechaEditText.setText("");
            horaEditText.setText("");
            anotacionEditText.setText("");
            dismiss();
        }else {
            Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}
