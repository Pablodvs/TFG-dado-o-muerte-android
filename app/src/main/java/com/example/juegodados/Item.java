package com.example.juegodados;

import android.widget.Button;

public class Item {
    String Nombre;


    public Item(String texto){
        this.Nombre = texto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }
}
