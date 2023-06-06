package com.example.juegodados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PantallaFinal extends AppCompatActivity implements View.OnClickListener{
    private TextView tvNombrePerdedor;
    private Button btnJugarOtra, btnVolverMenu;
    private String nombre="";
    ArrayList<String> jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_final);
        tvNombrePerdedor = findViewById(R.id.tvNombrePerdedor);
        btnJugarOtra = findViewById(R.id.btnJugarOtra);
        btnVolverMenu = findViewById(R.id.btnVolverMenu);
        btnJugarOtra.setOnClickListener(this);
        btnVolverMenu.setOnClickListener(this);
        jugadores = getIntent().getStringArrayListExtra("jugadores");

        nombre = getIntent().getStringExtra("perdedor") ;
        System.out.println("Estoy en el nuevo intent" +nombre);

        tvNombrePerdedor.setText("El perdedor es: " + nombre);


    }
    @Override
    public void onBackPressed() {
        // Evitar que el botón de retroceso realice la acción predeterminada
        // No llames a super.onBackPressed()
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnJugarOtra:
                Intent intent = new Intent(this, Partida.class);
                intent.putExtra("jugadores", jugadores);
                startActivity(intent);
                break;
            case R.id.btnVolverMenu:
                Intent menu = new Intent(this, PantallaEleccion.class);
                startActivity(menu);
                break;
        }
    }
}