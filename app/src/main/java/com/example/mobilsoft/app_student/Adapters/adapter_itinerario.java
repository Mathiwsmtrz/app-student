package com.example.mobilsoft.app_student.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.Itinerario;
import com.example.mobilsoft.app_student.viewHolders.viewHolder_itinerario;

import java.util.List;

public class adapter_itinerario extends RecyclerView.Adapter<viewHolder_itinerario> {

    List<Itinerario> ListaObjetos;

    public adapter_itinerario(List<Itinerario> listaObjetos) {
        ListaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public viewHolder_itinerario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_itinerario,parent,false);
        return new viewHolder_itinerario(vista,ListaObjetos);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_itinerario holder, int position) {
        holder.tv_nombre_tarea.setText(ListaObjetos.get(position).getAnotacion());
        holder.tv_fecha_tarea.setText(ListaObjetos.get(position).getFecha());
        holder.tv_hora_tarea.setText(ListaObjetos.get(position).getHora());
        holder.tv_estado_tarea.setText("Activa");
    }


    @Override
    public int getItemCount() {
        return ListaObjetos.size();
    }
}
