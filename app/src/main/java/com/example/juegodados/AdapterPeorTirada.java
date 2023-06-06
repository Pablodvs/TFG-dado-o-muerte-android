package com.example.juegodados;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterPeorTirada extends RecyclerView.Adapter<AdapterPeorTirada.peorTiradaHolder> {

    private ArrayList<Integer> diceValues; // ArrayList que contiene los valores del dado
    private HashMap<Integer, Integer> diceImages; // HashMap que asocia los valores del dado con las imágenes correspondientes

    public static class peorTiradaHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public peorTiradaHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dadoPT);
        }
    }

    public AdapterPeorTirada(ArrayList<Integer> diceValues) {
        this.diceValues = diceValues;

        // Inicializar el HashMap de imágenes
        diceImages = new HashMap<>();
        diceImages.put(1, R.drawable.dado1);
        diceImages.put(2, R.drawable.dado2);
        diceImages.put(3, R.drawable.dado3);
        diceImages.put(4, R.drawable.dado4);
        diceImages.put(5, R.drawable.dado5);
        diceImages.put(6, R.drawable.dado6);
    }

    @Override
    public peorTiradaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.peor_tirada, parent, false);
        return new peorTiradaHolder(view);
    }

    @Override
    public void onBindViewHolder(peorTiradaHolder holder, int position) {
        int diceValue = diceValues.get(position);
        int imageResId = diceImages.get(diceValue);
        holder.imageView.setImageResource(imageResId);
    }

    @Override
    public int getItemCount() {
        return diceValues.size();
    }
}
