package com.example.mobilsoft.app_student;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Utilidades;

public class registrarme extends AppCompatActivity {

    EditText identificacionEditText,nombresEditText,apellidosEditText,usuarioEditText,contrasenaEditText;
    String identificacionVal,nombresVal,apellidosVal,usuarioVal,contrasenaVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarme);

        identificacionEditText = (EditText) findViewById(R.id.txt_registrarme_identificacion);
        nombresEditText = (EditText) findViewById(R.id.txt_registrarme_nombres);
        apellidosEditText = (EditText) findViewById(R.id.txt_registrarme_apellidos);
        usuarioEditText = (EditText) findViewById(R.id.txt_registrarme_usuario);
        contrasenaEditText = (EditText) findViewById(R.id.txt_registrarme_contrasena);
    }

    public void regresar(View view){
        Intent loginActy = new Intent(getApplicationContext(), login.class);
        startActivity(loginActy);
    }

    public void registrarme(View view){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db =conn.getWritableDatabase();

        identificacionVal = identificacionEditText.getText().toString();
        nombresVal = nombresEditText.getText().toString();
        apellidosVal = apellidosEditText.getText().toString();
        usuarioVal = usuarioEditText.getText().toString();
        contrasenaVal = contrasenaEditText.getText().toString();

        if(!identificacionVal.isEmpty() && !nombresVal.isEmpty() && !apellidosVal.isEmpty() && !usuarioVal.isEmpty() && !contrasenaVal.isEmpty()){
            Cursor c = db.rawQuery("select * from "+Utilidades.TABLA_USUARIOS+" where "+Utilidades.CAMPO_ID_USUARIO+" = '"+identificacionVal+"'",null);
            if(c.getCount()>0){
                Toast.makeText(getApplicationContext(),"Ya existe un usuario registrado con esta identificacion",Toast.LENGTH_SHORT).show();
            }else{
                Cursor u = db.rawQuery("select * from "+Utilidades.TABLA_USUARIOS+" where "+Utilidades.CAMPO_USUARIO_USUARIO+" = '"+usuarioVal+"'",null);
                if(u.getCount()>0){
                    Toast.makeText(getApplicationContext(),"Este nombre de usuario ya se encuentra registrado en la base de datos.",Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_ID_USUARIO, identificacionVal);
                    values.put(Utilidades.CAMPO_NOMBRES_USUARIO, nombresVal);
                    values.put(Utilidades.CAMPO_APELLIDOS_USUARIO, apellidosVal);
                    values.put(Utilidades.CAMPO_USUARIO_USUARIO, usuarioVal);
                    values.put(Utilidades.CAMPO_CONTRASENA_USUARIO, contrasenaVal);
                    Long idRes = db.insert(Utilidades.TABLA_USUARIOS, Utilidades.CAMPO_ID_USUARIO, values);
                    Toast.makeText(getApplicationContext(), "Id Registro: " + idRes, Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor backInfo = preferences.edit();
                    backInfo.putString("iduser", identificacionVal);
                    backInfo.putString("user", usuarioVal);
                    backInfo.putString("pass", contrasenaVal);
                    backInfo.commit();

                    Intent Home = new Intent(getApplicationContext(), MainActivity.class);
                    Home.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(Home);
                    finish();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
