package com.example.mobilsoft.app_student.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.Materia;
import com.example.mobilsoft.app_student.viewHolders.viewHolder_materia;

import java.util.List;

public class adapter_materia extends RecyclerView.Adapter<viewHolder_materia>  {

    List<Materia> ListaObjetos;

    public adapter_materia(List<Materia> listaObjetos) {
        ListaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public viewHolder_materia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia,parent,false);
        return new viewHolder_materia(vista,ListaObjetos);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_materia holder, int position) {
        holder.tv_nombre_materia.setText(ListaObjetos.get(position).getNombres());
        holder.tv_profesor_materia.setText(ListaObjetos.get(position).getProfesor());
        holder.tv_descripcion_materia.setText(ListaObjetos.get(position).getDescripcion());
    }


    @Override
    public int getItemCount() {
        return ListaObjetos.size();
    }
}
