package com.example.mobilsoft.app_student.viewHolders;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.interfaces.interfaceMateria;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Materia;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.List;

public class viewHolder_materia  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_nombre_materia,tv_profesor_materia,tv_descripcion_materia;
    public Button btn_eliminar_materia, btn_editar_materia;
    List<Materia> ListaObjetos;

    public viewHolder_materia(View itemView, List<Materia>datos) {
        super(itemView);

        ListaObjetos = datos;

        tv_nombre_materia = (TextView) itemView.findViewById(R.id.cm_nombre);
        tv_profesor_materia = (TextView) itemView.findViewById(R.id.cm_profesor);
        tv_descripcion_materia= (TextView) itemView.findViewById(R.id.cm_descripcion);

        btn_editar_materia = (Button) itemView.findViewById(R.id.cm_boton_editar);
        btn_eliminar_materia = (Button) itemView.findViewById(R.id.cm_boton_eliminar);

        btn_editar_materia.setOnClickListener(this);
        btn_eliminar_materia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int index =getAdapterPosition();
        Materia materia = ListaObjetos.get(index);
        switch (v.getId()){
            case R.id.cm_boton_editar:
                ((interfaceMateria) itemView.getContext()).onItemClickEditMateria(materia);
                break;
            case R.id.cm_boton_eliminar:
                eliminarMateria(v,materia);
                break;
        }
    }

    public void eliminarMateria(View v,Materia tarea){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(itemView.getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Boolean del = db.delete(Utilidades.TABLA_MATERIAS,Utilidades.CAMPO_ID_MATERIA + " = "+tarea.getId(), null) > 0;
        if(del) {
            ((interfaceMateria) itemView.getContext()).onItemClickRemoveMateria(tarea);
        }
    }
}
