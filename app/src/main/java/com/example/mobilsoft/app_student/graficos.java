package com.example.mobilsoft.app_student;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.modelos.Utilidades;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class graficos extends AppCompatActivity {

    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    String[] LengendsLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    String idUsuario;
    List<Nota> ListaNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos);

        SharedPreferences preferences = getSharedPreferences("info", Context.MODE_PRIVATE);
        idUsuario = preferences.getString("iduser","");

        chart = (BarChart) findViewById(R.id.barChart);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        addDataChart();

        Bardataset = new BarDataSet(BARENTRY, "Notas");

        BARDATA = new BarData(Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);


        Legend legend = chart.getLegend();
        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        // l.setTypeface(...);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        l.setExtra(ColorTemplate.COLORFUL_COLORS,LengendsLabels);


    }

    public void addDataChart(){
        /*
            BARENTRY.add(new BarEntry(2f, 0));
            BARENTRY.add(new BarEntry(4f, 1));
            BARENTRY.add(new BarEntry(6f, 2));
            BARENTRY.add(new BarEntry(8f, 3));
            BARENTRY.add(new BarEntry(7f, 4));
            BARENTRY.add(new BarEntry(3f, 5));

            BarEntryLabels.add("January");
            BarEntryLabels.add("February");
            BarEntryLabels.add("March");
            BarEntryLabels.add("April");
            BarEntryLabels.add("May");
            BarEntryLabels.add("June");
        */
        ListaNota = new ArrayList<Nota>();
        Nota nota= null;
        LengendsLabels=new String[]{
                null,null,null,null,null};

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(), Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor c = db.rawQuery("select n."+Utilidades.CAMPO_ID_NOTA+",n."+Utilidades.CAMPO_ID_MATERIA+",m."+Utilidades.CAMPO_NOMBRE_MATERIA+",m."+Utilidades.CAMPO_PROFESOR_MATERIA+",n."+Utilidades.CAMPO_NUMERO_NOTAS+",n."+Utilidades.CAMPO_NOTA_NOTAS+",n."+Utilidades.CAMPO_ID_USUARIO_FK+" from "+ Utilidades.TABLA_NOTAS + " n INNER JOIN "+Utilidades.TABLA_MATERIAS+" m on m."+Utilidades.CAMPO_ID_MATERIA+" = n."+Utilidades.CAMPO_ID_MATERIA+" where n." + Utilidades.CAMPO_ID_USUARIO_FK + " ='" + idUsuario + "' ORDER BY "+Utilidades.CAMPO_NOTA_NOTAS+" DESC LIMIT 5", null);
        Integer pos = 0;

        if(c.getCount()>0) {
            while (c.moveToNext()) {
                nota = new Nota(null, null, null,null,null, null, null);
                nota.setId(c.getInt(0));
                nota.setId_materia(c.getInt(1));
                nota.setNombre_materia(c.getString(2));
                nota.setNombre_profesor(c.getString(3));
                nota.setNumero(c.getInt(4));
                nota.setNota(c.getFloat(5));
                nota.setId_usuario(c.getString(6));

                BARENTRY.add(new BarEntry((pos+1)*2f, nota.getNota()));
                BarEntryLabels.add("Nota " + nota.getNumero().toString() + " - " +nota.getNombre_materia());
                ListaNota.add(nota);
                LengendsLabels[pos] = "Nota " + nota.getNumero().toString() + " - " +nota.getNombre_materia();
                pos++;
            }
        }
    }


}
