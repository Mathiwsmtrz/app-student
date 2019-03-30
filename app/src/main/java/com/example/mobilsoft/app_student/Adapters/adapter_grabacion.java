package com.example.mobilsoft.app_student.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.Grabacion;
import com.example.mobilsoft.app_student.viewHolders.viewHolder_grabacion;

import java.util.List;

public class adapter_grabacion extends RecyclerView.Adapter<viewHolder_grabacion>  {

    List<Grabacion> ListaObjetos;

    public adapter_grabacion(List<Grabacion> listaObjetos) {
        ListaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public viewHolder_grabacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grabacion,parent,false);
        return new viewHolder_grabacion(vista,ListaObjetos);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_grabacion holder, int position) {
        holder.tv_descripcion_grabacion.setText(ListaObjetos.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return ListaObjetos.size();
    }
}
