package com.example.mobilsoft.app_student;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilsoft.app_student.Adapters.adapter_grabacion;
import com.example.mobilsoft.app_student.Fragments.materiasFormulario;
import com.example.mobilsoft.app_student.interfaces.interfaceGrabacion;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Grabacion;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class records extends AppCompatActivity implements View.OnClickListener,interfaceGrabacion {

    TextView txt_estado_ec;
    String idUsuario;
    EditText tv_descripcion;
    Button btn_iniciar,btn_detener,btn_guardar,btn_reproducir,btn_pausar;
    MediaRecorder recorder;
    MediaPlayer player;
    List<Grabacion> ListaGrabacion;
    File archivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        btn_iniciar = (Button) findViewById(R.id.rec_iniciar);
        btn_detener = (Button) findViewById(R.id.rec_detener);
        btn_guardar = (Button) findViewById(R.id.rec_guardar);
        btn_reproducir = (Button) findViewById(R.id.rec_reproducir);
        btn_pausar = (Button) findViewById(R.id.rec_pausar);
        tv_descripcion = (EditText) findViewById(R.id.txt_name_record);

        btn_iniciar.setOnClickListener(this);
        btn_detener.setOnClickListener(this);
        btn_guardar.setOnClickListener(this);
        btn_reproducir.setOnClickListener(this);
        btn_pausar.setOnClickListener(this);

        mFileName = getExternalCacheDir().getAbsolutePath();

        btn_iniciar.setEnabled(true);
        btn_detener.setEnabled(false);
        btn_reproducir.setEnabled(false);
        btn_pausar.setEnabled(false);
        btn_guardar.setEnabled(false);

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        consultarGrabaciones();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rec_iniciar :{
                grabar();
                break;
            }
            case R.id.rec_detener :{
                detener();
                break;
            }
            case R.id.rec_guardar :{
                guardar();
                break;
            }
            case R.id.rec_reproducir :{
                reproducir();
                break;
            }
            case R.id.rec_pausar :{
                pausar();
                break;
            }
        }
    }

    private static final String LOG_TAG = "AudioRecordTest";
    private MediaPlayer   mPlayer = null;
    private static String mFileName,uFileName = null;
    private MediaRecorder mRecorder = null;

    public void grabar() {
        long time= System.currentTimeMillis();
        uFileName = mFileName+"/"+time+".3gp";
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(uFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        btn_iniciar.setEnabled(false);
        btn_detener.setEnabled(true);
    }

    public void detener() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        btn_iniciar.setEnabled(true);
        btn_detener.setEnabled(false);
        btn_reproducir.setEnabled(true);
        btn_guardar.setEnabled(true);
    }

    public void reproducir() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(uFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        btn_iniciar.setEnabled(false);
        btn_detener.setEnabled(false);
        btn_reproducir.setEnabled(false);
        btn_pausar.setEnabled(true);
    }

    public void pausar() {
        mPlayer.release();
        mPlayer = null;
        btn_iniciar.setEnabled(true);
        btn_detener.setEnabled(false);
        btn_reproducir.setEnabled(true);
        btn_pausar.setEnabled(false);
    }


    public void guardar(){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db =conn.getWritableDatabase();

        if(!tv_descripcion.getText().toString().isEmpty() && !uFileName.toString().isEmpty()){
            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_RUTA_GRABACION, uFileName.toString());
            values.put(Utilidades.CAMPO_DESCRIPCION_GRABACION, tv_descripcion.getText().toString());
            values.put(Utilidades.CAMPO_ID_USUARIO_FK, idUsuario);
            Long idRes = db.insert(Utilidades.TABLA_GRABACIONES, Utilidades.CAMPO_ID_GRABACION, values);
            Toast.makeText(getApplicationContext(), "Guardado correctamente " + idRes.toString(), Toast.LENGTH_SHORT).show();
            tv_descripcion.setText("");

            btn_iniciar.setEnabled(true);
            btn_detener.setEnabled(false);
            btn_reproducir.setEnabled(false);
            btn_pausar.setEnabled(false);
            btn_guardar.setEnabled(false);

            uFileName = null;

            consultarGrabaciones();
        }else {
            Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    public void renderList(){
        RecyclerView contenedor = (RecyclerView) findViewById(R.id.contenedor_grabaciones);
        contenedor.setHasFixedSize(true);
        LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        contenedor.setAdapter(new adapter_grabacion(ListaGrabacion));
        contenedor.setLayoutManager(layout);

    }

    public void consultarGrabaciones(){
        ListaGrabacion = new ArrayList<Grabacion>();
        Grabacion grabacion= null;

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+ Utilidades.TABLA_GRABACIONES + " where " + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "'", null);

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                grabacion = new Grabacion(null, null, null, null);
                grabacion.setId_usuario(idUsuario);
                grabacion.setId(c.getInt(0));
                grabacion.setRuta(c.getString(1));
                grabacion.setDescripcion(c.getString(2));

                ListaGrabacion.add(grabacion);
            }
        }
        renderList();
    }

    @Override
    public void onItemClickRemoveGrabacion(Grabacion data) {
        Toast.makeText(getApplicationContext(), data.getDescripcion() + " fue eliminado del itinerario.", Toast.LENGTH_SHORT).show();
        consultarGrabaciones();
    }

    @Override
    public void onItemClickPlayGrabacion(Grabacion data) {
        if(mPlayer!=null){
            mPlayer.release();
            mPlayer = null;
        }
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(data.getRuta());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
}
