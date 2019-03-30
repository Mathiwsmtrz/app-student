package com.example.mobilsoft.app_student.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilsoft.app_student.R;
import com.example.mobilsoft.app_student.modelos.Nota;
import com.example.mobilsoft.app_student.viewHolders.viewHolder_nota;

import java.util.List;

public class adapter_nota  extends RecyclerView.Adapter<viewHolder_nota>{

    List<Nota> ListaObjetos;

    public adapter_nota(List<Nota> listaObjetos) {
        ListaObjetos = listaObjetos;
    }

    @NonNull
    @Override
    public viewHolder_nota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota,parent,false);
        return new viewHolder_nota(vista,ListaObjetos);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_nota holder, int position) {
        holder.tv_materia_nota.setText(ListaObjetos.get(position).getNombre_materia());
        holder.tv_profesor_nota.setText(ListaObjetos.get(position).getNombre_profesor());
        holder.tv_numero_nota.setText("Nota #" + ListaObjetos.get(position).getNumero().toString());
        holder.tv_valor_nota.setText(ListaObjetos.get(position).getNota().toString());
    }


    @Override
    public int getItemCount() {
        return ListaObjetos.size();
    }
}
