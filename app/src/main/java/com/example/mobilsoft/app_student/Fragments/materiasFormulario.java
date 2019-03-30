package com.example.mobilsoft.app_student.Fragments;


import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Materia;
import com.example.mobilsoft.app_student.modelos.Utilidades;


/**
 * A simple {@link Fragment} subclass.
 */
public class materiasFormulario extends DialogFragment implements View.OnClickListener{

    EditText nombreEditText, profesorEditText, descripcionEditText;
    String nombreVal, profesorVal, descripcionVal, idUsuario;
    Button btn_guardar_materia;
    Boolean update;

    public materiasFormulario() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materias_formulario, container, false);
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        nombreEditText = (EditText) getView().findViewById(R.id.txt_nombre_materia);
        profesorEditText = (EditText) getView().findViewById(R.id.txt_profesor_materia);
        descripcionEditText = (EditText) getView().findViewById(R.id.txt_descripcion_materia);
        btn_guardar_materia = (Button) getView().findViewById(R.id.btn_guardar_materia);

        btn_guardar_materia.setOnClickListener(this);

        Integer idItem = getArguments().getInt("idMateria",0);
        if(idItem!=0){
            update = true;
            nombreEditText.setText(getArguments().getString("nombre",""));
            profesorEditText.setText(getArguments().getString("profesor",""));
            descripcionEditText.setText(getArguments().getString("descripcion",""));
        }else{
            update = false;
        }
    }

    public static materiasFormulario newInstance(String title, Materia data) {
        materiasFormulario frag = new materiasFormulario();
        Bundle args = new Bundle();
        args.putString("title", title);
        if(data.getId() != null) {
            args.putInt("idMateria", data.getId());
            args.putString("nombre", data.getNombres());
            args.putString("profesor", data.getProfesor());
            args.putString("descripcion", data.getDescripcion());
            args.putString("idUsuario", data.getId_usuario());
        }
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_guardar_materia:
                guardarMateria();
                break;
        }
    }

    public void guardarMateria(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db =conn.getWritableDatabase();

        nombreVal = nombreEditText.getText().toString();
        profesorVal = profesorEditText.getText().toString();
        descripcionVal = descripcionEditText.getText().toString();

        if(!nombreVal.isEmpty() && !profesorVal.isEmpty() && !descripcionVal.isEmpty()){
            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_NOMBRE_MATERIA, nombreVal);
            values.put(Utilidades.CAMPO_PROFESOR_MATERIA, profesorVal);
            values.put(Utilidades.CAMPO_DESCRIPCION_MATERIA, descripcionVal);
            values.put(Utilidades.CAMPO_ID_USUARIO_FK, idUsuario);
            if(!update) {
                Long idRes = db.insert(Utilidades.TABLA_MATERIAS, Utilidades.CAMPO_ID_MATERIA, values);
                Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                db.update(Utilidades.TABLA_MATERIAS, values, Utilidades.CAMPO_ID_MATERIA+"="+ getArguments().getInt("idMateria",0),null);
                Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            }

            nombreEditText.setText("");
            profesorEditText.setText("");
            descripcionEditText.setText("");
            dismiss();
        }else {
            Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
