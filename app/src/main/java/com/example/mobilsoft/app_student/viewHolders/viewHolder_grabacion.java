package com.example.mobilsoft.app_student.viewHolders;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.interfaces.interfaceGrabacion;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Grabacion;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.List;

public class viewHolder_grabacion  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_descripcion_grabacion;
    public Button btn_eliminar_grabacion, btn_reproducir_grabacion;
    List<Grabacion> ListaObjetos;

    public viewHolder_grabacion(View itemView, List<Grabacion>datos) {
        super(itemView);

        ListaObjetos = datos;

        tv_descripcion_grabacion = (TextView) itemView.findViewById(R.id.rc_descripcion);

        btn_reproducir_grabacion = (Button) itemView.findViewById(R.id.rc_boton_reproducir);
        btn_eliminar_grabacion = (Button) itemView.findViewById(R.id.rc_boton_eliminar);

        btn_reproducir_grabacion.setOnClickListener(this);
        btn_eliminar_grabacion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int index =getAdapterPosition();
        Grabacion grabacion= ListaObjetos.get(index);
        switch (v.getId()){
            case R.id.rc_boton_reproducir:
                ((interfaceGrabacion) itemView.getContext()).onItemClickPlayGrabacion(grabacion);
                break;
            case R.id.rc_boton_eliminar:
                eliminarGrabacion(v,grabacion);
                break;
        }
    }

    public void eliminarGrabacion(View v,Grabacion grabacion){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(itemView.getContext(), Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Boolean del = db.delete(Utilidades.TABLA_GRABACIONES,Utilidades.CAMPO_ID_GRABACION + " = "+grabacion.getId(), null) > 0;
        if(del) {
            ((interfaceGrabacion) itemView.getContext()).onItemClickRemoveGrabacion(grabacion);
        }
    }
}
