package com.example.juegodados;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantallaEleccion extends AppCompatActivity {
    ArrayList<Item> listDatos;
    EditText etJugador;
    RecyclerView recycler;
    Button añadir, iniciarP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion);

        iniciarP = findViewById(R.id.btnIniciarJuego);
        listDatos = new ArrayList<>();
        añadir = findViewById(R.id.btnAñadir);
        etJugador = findViewById(R.id.etJugadores);
        recycler = findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new MyAdapterItem(listDatos));
        iniciarP.setVisibility(View.INVISIBLE);

        actualizarBotonIniciar();

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etJugador.getText().length()>0){
                    listDatos.add(new Item(etJugador.getText().toString()));
                    recycler.getAdapter().notifyItemInserted(listDatos.size() - 1);
                    etJugador.setText("");
                    actualizarBotonIniciar();
                }
            }
        });

        etJugador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No se requiere implementación
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No se requiere implementación
            }

            @Override
            public void afterTextChanged(Editable s) {
                actualizarBotonIniciar();
            }
        });

        iniciarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarJuego();
            }
        });
    }

    public void actualizarBotonIniciar() {
        if (listDatos.size() > 1) {
            iniciarP.setVisibility(View.VISIBLE);
        } else {
            iniciarP.setVisibility(View.INVISIBLE);
        }
    }

    public void iniciarJuego() {
        Intent intent = new Intent(this, Partida.class);
        intent.putExtra("jugadores", jugadores());
        startActivity(intent);
    }

    public ArrayList<String> jugadores() {
        ArrayList<String> jugadores = new ArrayList<>();
        for (Item i : listDatos) {
            jugadores.add(i.getNombre());
        }
        return jugadores;
    }
}
