package com.example.juegodados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;

public class MyAdapterDado extends RecyclerView.Adapter<MyAdapterDado.MyViewHolderDado> {
    private ArrayList<Dado> dados;

    public static class MyViewHolderDado extends RecyclerView.ViewHolder {
        public ImageView imagenDado;
        public Button eliminarButton;
        public int id; // Agregamos un atributo id

        public MyViewHolderDado(View itemView) {
            super(itemView);
            imagenDado = itemView.findViewById(R.id.dadoG);
            eliminarButton = itemView.findViewById(R.id.eliminar);
        }

    }

    public MyAdapterDado(ArrayList<Dado> dados) {
        this.dados = dados;
    }


    @NonNull
    @Override
    public MyViewHolderDado onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dados_guardados, parent, false);
        return new MyViewHolderDado(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderDado holder, int position) {
        Dado dadoActual = dados.get(position);
        holder.id = dadoActual.getId(); // Seteamos el id en el ViewHolder
        holder.imagenDado.setImageResource(dadoActual.imagenInt());
        holder.eliminarButton.setTag(holder.id);
        holder.eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<Dado> iter = dados.iterator();
                int id = (int) view.getTag(); // Obtenemos el id del botón
                while (iter.hasNext()) {
                    Dado dado = iter.next();
                    if (dado.getId() == id) { // Eliminamos el dado por su id
                        iter.remove();
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }
}
