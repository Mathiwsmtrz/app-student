package com.example.mobilsoft.app_student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;

public class MainActivity extends AppCompatActivity {

    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"bd_turnos",null,1);
        boolean login = verificarLogin();
        if(!login){
            Intent Login = new Intent(getApplicationContext(), login.class);
            Login.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(Login);
            finish();
        }else{

        }
    }

    public boolean verificarLogin(){
        boolean login;

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        usuario = preferences.getString("user","");
        if(!usuario.isEmpty()) {
            login = true;
        }else{
            login = false;
        }

        return login;
    }

    public void cerrar_sesion(View view){
        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        SharedPreferences.Editor backInfo = preferences.edit();
        backInfo.putString("user","");
        backInfo.putString("pass","");
        backInfo.commit();

        Intent loginActy = new Intent(getApplicationContext(), login.class);
        startActivity(loginActy);
        finish();
    }

    public void abrirActivity(View view){
        Intent ListSong = new Intent(getApplicationContext(), itinerario.class);
        switch (view.getId()){
            case R.id.btn_home_tareas :
                ListSong = new Intent(getApplicationContext(), itinerario.class);
                break;
            case R.id.btn_home_notas :
                ListSong = new Intent(getApplicationContext(), notas.class);
                break;
            case R.id.btn_home_materias :
                ListSong = new Intent(getApplicationContext(), materias.class);
                break;
            case R.id.btn_home_records :
                ListSong = new Intent(getApplicationContext(), records.class);
                break;
            case R.id.btn_home_graficos :
                ListSong = new Intent(getApplicationContext(), graficos.class);
                break;
        }

        startActivity(ListSong);
    }
}
