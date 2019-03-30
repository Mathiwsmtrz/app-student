package com.example.mobilsoft.app_student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Utilidades;

public class login extends AppCompatActivity {
    EditText usuarioEditText, contrasenaEditText;
    String usuarioVal, contrasenaVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioEditText = (EditText) findViewById(R.id.txt_login_usuario);
        contrasenaEditText = (EditText) findViewById(R.id.txt_login_contrasena);
    }

    public void registrarme(View view) {
        Intent registrarmeActy = new Intent(getApplicationContext(), registrarme.class);
        startActivity(registrarmeActy);
    }


    public void ingresar(View view) {
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();

        usuarioVal = usuarioEditText.getText().toString();
        contrasenaVal = contrasenaEditText.getText().toString();

        if (!usuarioVal.isEmpty() && !contrasenaVal.isEmpty()) {
            Cursor c = db.rawQuery("select * from " + Utilidades.TABLA_USUARIOS + " where " + Utilidades.CAMPO_USUARIO_USUARIO + " = '" + usuarioVal + "' and " + Utilidades.CAMPO_CONTRASENA_USUARIO + " = '" + contrasenaVal + "'", null);
            if (c.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "Usuario y/o contraseña incorrectas", Toast.LENGTH_SHORT).show();
            } else {
                c.moveToFirst();
                SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
                SharedPreferences.Editor backInfo = preferences.edit();
                backInfo.putString("iduser", c.getString(0));
                backInfo.putString("user", usuarioVal);
                backInfo.putString("pass", contrasenaVal);
                backInfo.commit();

                Intent Home = new Intent(getApplicationContext(), MainActivity.class);
                Home.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Home);
                finish();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
