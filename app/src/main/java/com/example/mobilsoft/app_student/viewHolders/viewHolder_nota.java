package com.example.mobilsoft.app_student.viewHolders;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.interfaces.interfaceNota;
import com.example.mobilsoft.app_student.modelos.ConexionSQLiteHelper;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.modelos.Utilidades;

import java.util.List;

public class viewHolder_nota   extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView tv_materia_nota,tv_profesor_nota,tv_valor_nota,tv_numero_nota;
    public Button btn_eliminar_nota, btn_editar_nota;
    List<Nota> ListaObjetos;

    public viewHolder_nota(View itemView, List<Nota>datos) {
        super(itemView);

        ListaObjetos = datos;

        tv_materia_nota = (TextView) itemView.findViewById(R.id.cn_materia);
        tv_profesor_nota = (TextView) itemView.findViewById(R.id.cn_profesor);
        tv_valor_nota= (TextView) itemView.findViewById(R.id.cn_nota);
        tv_numero_nota= (TextView) itemView.findViewById(R.id.cn_numero);

        btn_editar_nota = (Button) itemView.findViewById(R.id.cn_boton_editar);
        btn_eliminar_nota = (Button) itemView.findViewById(R.id.cn_boton_eliminar);

        btn_editar_nota.setOnClickListener(this);
        btn_eliminar_nota.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int index =getAdapterPosition();
        Nota nota = ListaObjetos.get(index);
        switch (v.getId()){
            case R.id.cn_boton_editar:
                ((interfaceNota) itemView.getContext()).onItemClickEditNota(nota);
                break;
            case R.id.cn_boton_eliminar:
                eliminarNota(v,nota);
                break;
        }
    }

    public void eliminarNota(View v,Nota tarea){
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(itemView.getContext(),Utilidades.NOMBRE_BD,null,Utilidades.VERSION_BD);
        SQLiteDatabase db = conn.getWritableDatabase();
        Boolean del = db.delete(Utilidades.TABLA_NOTAS,Utilidades.CAMPO_ID_NOTA + " = "+tarea.getId(), null) > 0;
        if(del) {
            ((interfaceNota) itemView.getContext()).onItemClickRemoveNota(tarea);
        }
    }
}
