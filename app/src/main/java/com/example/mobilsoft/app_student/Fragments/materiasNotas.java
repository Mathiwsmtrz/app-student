package com.example.mobilsoft.app_student.Fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.mainClass;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Materia;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class materiasNotas extends DialogFragment  implements View.OnClickListener{

    Spinner spn_numeros, spn_materias;
    Button btn_agregar_nota;
    Integer numero_nota_val,materia_val;
    TextView txt_nota_minima;
    String idUsuario;
    Float valor_nota;
    Boolean update;
    EditText tv_valor_nota;
    ArrayList<String> listado_numeros_nota= new ArrayList<String>(Arrays.asList("Nota 1","Nota 2","Nota 3"));
    ArrayList<String> listado_materias;
    ArrayList<Materia> listaMaterias;

    public materiasNotas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materias_notas, container, false);
        return view;
    }

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();

        SharedPreferences preferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        tv_valor_nota = (EditText) getView().findViewById(R.id.txt_valor_nota);

        spn_numeros = (Spinner) getView().findViewById(R.id.spn_numero_nota);
        spn_materias = (Spinner) getView().findViewById(R.id.spn_materia_nota);
        txt_nota_minima = (TextView) getView().findViewById(R.id.txt_nota_minima);
        txt_nota_minima.setText("");

        spn_numeros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                numero_nota_val = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numero_nota_val = 0;
            }
        });
        spn_materias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>0) {
                    materia_val = listaMaterias.get(position - 1).getId();
                    String nota_minima = new mainClass().nota_minima(materia_val,getContext(),idUsuario);
                    txt_nota_minima.setText(nota_minima);
                }else
                    materia_val = 0;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                materia_val = 0;
            }
        });


        btn_agregar_nota = (Button) getView().findViewById(R.id.btn_agregar_nota);
        btn_agregar_nota.setOnClickListener(this);

        renderSpinnerNotas(getView());
        consultarMaterias();


        Integer idItem = getArguments().getInt("idNota",0);
        if(idItem!=0){
            update = true;
            tv_valor_nota.setText(getArguments().getString("nota",""));
            spn_numeros.setSelection(getArguments().getInt("numero",0) -1);
            Integer indexMateria = indexMateriaForId(getArguments().getInt("idMateria",0));
            if(indexMateria!=-1){
                spn_materias.setSelection(indexMateria);
            }
        }else{
            update = false;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_agregar_nota:
                gaurdarNota();
                break;
        }
    }

    public int indexMateriaForId(Integer id){
        boolean index;
        for (int i = 0; i < listaMaterias.size(); i++)
        {
            if (listaMaterias.get(i).getId() == id)
            {
                return i+1;
            }
        }

        return -1;
    }

    public void renderSpinnerNotas(View view){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,listado_numeros_nota);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_numeros.setAdapter(adapter);
    }
    public void renderSpinnerMaterias(View view){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,listado_materias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_materias.setAdapter(adapter);
    }

    public void consultarMaterias(){
        listaMaterias = new ArrayList<Materia>();
        listado_materias = new ArrayList<String>();
        listado_materias.add("Seleccione una materia");
        Materia materia= null;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+ Utilidades.TABLA_MATERIAS + " where " + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "'", null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                materia = new Materia(null, null, null, null, null);
                materia.setId(c.getInt(0));
                materia.setNombres(c.getString(1));
                materia.setProfesor(c.getString(2));
                materia.setDescripcion(c.getString(3));
                materia.setId_usuario(c.getString(4));

                listaMaterias.add(materia);
                listado_materias.add(materia.getId().toString()+" - "+materia.getNombres()+" ("+materia.getProfesor()+")");
            }
        }
        renderSpinnerMaterias(getView());
    }

    public static materiasNotas newInstance(String title,Nota data) {
        materiasNotas frag = new materiasNotas();
        Bundle args = new Bundle();
        args.putString("title", title);
        if(data.getId() != null) {
            args.putString("idUsuario", data.getId_usuario());
            args.putInt("idNota", data.getId());
            args.putInt("idMateria", data.getId_materia());
            args.putInt("numero", data.getNumero());
            args.putFloat("nota", data.getNota());
        }
        frag.setArguments(args);
        return frag;
    }


    public void gaurdarNota(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db =conn.getWritableDatabase();

        if(!tv_valor_nota.getText().toString().isEmpty())
            valor_nota = Float.valueOf(tv_valor_nota.getText().toString());
        else valor_nota = Float.valueOf(0);

        if( valor_nota >=0 && materia_val!=0 && numero_nota_val!=0 && !tv_valor_nota.getText().toString().isEmpty()){

            Cursor v = db.rawQuery("select * from "+Utilidades.TABLA_NOTAS+" where "+Utilidades.CAMPO_ID_MATERIA+" = "+materia_val+" and "+Utilidades.CAMPO_NUMERO_NOTAS+" = "+numero_nota_val,null);
            if(v.getCount()>0){
                Toast.makeText(getContext(),"Ya existe esta nota en esta materia.",Toast.LENGTH_SHORT).show();
            }else {
                ContentValues values = new ContentValues();
                values.put(Utilidades.CAMPO_ID_MATERIA, materia_val);
                values.put(Utilidades.CAMPO_NUMERO_NOTAS, numero_nota_val);
                values.put(Utilidades.CAMPO_NOTA_NOTAS, valor_nota);
                values.put(Utilidades.CAMPO_ID_USUARIO_FK, idUsuario);
                if (!update) {
                    Long idRes = db.insert(Utilidades.TABLA_NOTAS, Utilidades.CAMPO_ID_NOTA, values);
                    Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    db.update(Utilidades.TABLA_NOTAS, values, Utilidades.CAMPO_ID_NOTA + "=" + getArguments().getInt("idNota", 0), null);
                    Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                }

                tv_valor_nota.setText("");
                spn_materias.setSelection(0);
                spn_numeros.setSelection(0);
                dismiss();
            }
        }else {
            Toast.makeText(getContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

}
