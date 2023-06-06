package com.example.juegodados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

public class MyAdapterItem extends RecyclerView.Adapter<MyAdapterItem.MyViewHolder> {
    private ArrayList<Item> nombres;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public Button eliminarButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre);
            eliminarButton = itemView.findViewById(R.id.eliminar);
        }
    }

    public MyAdapterItem(ArrayList<Item> nombres) {
        this.nombres = nombres;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item nombreActual = nombres.get(position);
        holder.nombreTextView.setText(nombreActual.getNombre());
        holder.eliminarButton.setTag(nombreActual);
        holder.eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<Item> iter = nombres.iterator();
                String nombre = nombreActual.getNombre();
                while (iter.hasNext()) {
                    Item item = iter.next();
                    if (item.getNombre().equals(nombre)) {
                        iter.remove();
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nombres.size();
    }
}

