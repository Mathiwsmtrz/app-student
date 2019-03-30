package com.example.mobilsoft.app_student.viewHolders;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.interfaces.interfaceItinerario;
import com.example.mobilsoft.app_student.itinerario;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Itinerario;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.List;

public class viewHolder_itinerario extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_nombre_tarea,tv_fecha_tarea,tv_hora_tarea,tv_estado_tarea;
    public Button  btn_eliminar_tarea, btn_editar_tarea;
    List<Itinerario> ListaObjetos;

    public viewHolder_itinerario(View itemView,List<Itinerario>datos) {
        super(itemView);

        ListaObjetos = datos;

        tv_nombre_tarea = (TextView) itemView.findViewById(R.id.ci_nombre_tarea);
        tv_fecha_tarea = (TextView) itemView.findViewById(R.id.ci_fecha_tarea);
        tv_hora_tarea = (TextView) itemView.findViewById(R.id.ci_hora_tarea);
        tv_estado_tarea = (TextView) itemView.findViewById(R.id.ci_estado_tarea);

        btn_editar_tarea = (Button) itemView.findViewById(R.id.ci_boton_editar);
        btn_eliminar_tarea = (Button) itemView.findViewById(R.id.ci_boton_eliminar);

        btn_editar_tarea.setOnClickListener(this);
        btn_eliminar_tarea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int index =getAdapterPosition();
        Itinerario tarea = ListaObjetos.get(index);
        switch (v.getId()){
            case R.id.ci_boton_editar:
                ((interfaceItinerario) itemView.getContext()).onItemClickEditItinerario(tarea);
                break;
            case R.id.ci_boton_eliminar:
                eliminarTarea(v,tarea);
                break;
        }
    }

    public void eliminarTarea(View v,Itinerario tarea){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(itemView.getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Boolean del = db.delete(Utilidades.TABLA_ITINERARIO,Utilidades.CAMPO_ID_ITINERARIO + " = "+tarea.getId(), null) > 0;
        if(del) {
            ((interfaceItinerario) itemView.getContext()).onItemClickRemoveItinerario(tarea);
        }
    }
}
