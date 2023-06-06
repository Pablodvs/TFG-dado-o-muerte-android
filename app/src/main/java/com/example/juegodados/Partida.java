package com.example.juegodados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Partida extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivDado1, ivDado2, ivDado3, ivDado4, ivDado5;
    private ImageView[] imagenes;
    private Button btnTirarDados;
    private Button btnAddDado;
    private RecyclerView recycler, recyclerView;
    private TextView tvJugador, tvTirada, tvVidas, tvPeorTirada;
    private ArrayList<String> jugadoresString;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Integer> dadosTirada, dadosTiradaOr, peorTirada;
    private ArrayList<Dado> dados;
    private HashMap<Integer, Integer> valorDados;
    private int[] imagenesInt = {R.drawable.dado1, R.drawable.dado2, R.drawable.dado3,
            R.drawable.dado4, R.drawable.dado5, R.drawable.dado6};
    private Random random = new Random();
    private boolean finPartida = false;
    private boolean finRonda = false;
    private int ronda = 0;
    private int tiradasMax = 3;
    private Jugador jugadorActual;
    private int turno;
    private Jugador perdedor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        btnTirarDados = findViewById(R.id.btnTirarDados);
        tvVidas = findViewById(R.id.tvVida2);
        tvPeorTirada = findViewById(R.id.tvNombre);
        tvJugador = findViewById(R.id.tvJugador);
        tvTirada = findViewById(R.id.tvTiradaA);
        ivDado1 = findViewById(R.id.image_view_dado1);
        ivDado2 = findViewById(R.id.image_view_dado2);
        ivDado3 = findViewById(R.id.image_view_dado3);
        ivDado4 = findViewById(R.id.image_view_dado4);
        ivDado5 = findViewById(R.id.image_view_dado5);
        btnAddDado = findViewById(R.id.btnPlantarse);

        imagenes = new ImageView[]{ivDado1, ivDado2, ivDado3, ivDado4, ivDado5};

        jugadoresString = getIntent().getStringArrayListExtra("jugadores");
        crearJugadores();

        dados = new ArrayList<>();
        dadosTiradaOr = new ArrayList<>();
        peorTirada = new ArrayList<>();
        valorDados = new HashMap<>();
        dadosTirada = new ArrayList<>();

        btnAddDado.setOnClickListener(this);
        ivDado1.setOnClickListener(null);
        ivDado2.setOnClickListener(null);
        ivDado3.setOnClickListener(null);
        ivDado4.setOnClickListener(null);
        ivDado5.setOnClickListener(null);
        btnTirarDados.setOnClickListener(this);

        recycler = findViewById(R.id.recy);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(new MyAdapterDado(dados));

        recyclerView = findViewById(R.id.rvPeorTirada);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new AdapterPeorTirada(peorTirada));
        recyclerView.setItemAnimator(null);

        ronda = 0;
        asignarImagen();
        iniciarRonda();
        turno = 0;
        perdedor = new Jugador("");

    }

    @Override
    public void onBackPressed() {
        // Evitar que el botón de retroceso realice la acción predeterminada
        // No llames a super.onBackPressed()
    }

    @Override
    public void onClick(View view) {
        int cara = 0;
        int dadoIndex = -1;

        switch (view.getId()) {
            case R.id.image_view_dado1:
                dadoIndex = 0;
                break;
            case R.id.image_view_dado2:
                dadoIndex = 1;
                break;
            case R.id.image_view_dado3:
                dadoIndex = 2;
                break;
            case R.id.image_view_dado4:
                dadoIndex = 3;
                break;
            case R.id.image_view_dado5:
                dadoIndex = 4;
                break;
        }

        if (dadoIndex >= 0) {
            cara = valorDados.get(dadosTirada.get(dadoIndex));
            guardarDado(cara);
            ImageView imageView = (ImageView) view;
            imageView.setOnClickListener(null);
            imageView.setImageResource(R.drawable.dado0);
            guardarDados(jugadorActual);
        } else {
            switch (view.getId()) {
                case R.id.btnTirarDados:
                    tirada();
                    break;
                case R.id.btnPlantarse:
                    guardarDado();
                    break;
            }
        }
    }

    public void guardarDado(){
        escalera(jugadorActual.getDadosGuardados());
        guardarDados(jugadorActual);
        jugadorActual.setPuntuacion();
        cambioJugador();
    }

    public void iniciarRonda(){
        resetearDatos();
        if(ronda==0){
            Jugador primer = primerJugador(jugadores);
            int index = jugadores.indexOf(primer);
            jugadores= ordenarJugadores(jugadores, index);
        }else {
            jugadores = ordenarJugadores(jugadores, jugadores.indexOf(perdedor));
        }
        jugadorActual=jugadores.get(0);
        tvJugador.setText(jugadorActual.getNombre());
        tvVidas.setText(String.valueOf(jugadorActual.getVidas()));
        ronda++;
    }

    public Jugador primerJugador(ArrayList<Jugador> jugadores){
        Random random = new Random();
        int indiceAleatorio = random.nextInt(jugadores.size());
        Jugador jugadorAleatorio = jugadores.get(indiceAleatorio);
        return jugadorAleatorio;
    }

    public void tirarDados(){
        int duration =300;
        dadosTirada.clear();
        dadosTiradaOr.clear();

        int dadosT = 5 - dados.size();
        for ( int h = 4 ; h>=dadosT;h--){
            imagenes[h].setImageResource(R.drawable.dado0);
            imagenes[h].setOnClickListener(null);
        }
        for (int i = 0 ; i<dadosT ; i++){
            dadosTirada.add(i,rollDice((imagenes[i]), duration));
            duration +=400;
        }
    }


    private void peorTirada() {
        int minScore=100;
        String nombre="";
        ArrayList<Integer> peorTirada = new ArrayList();
        for (Jugador jugador : jugadores) {
            int puntuacion = jugador.getPuntuacion();
            if (puntuacion <= minScore && puntuacion !=0) {
                minScore = jugador.getPuntuacion();
                peorTirada = jugador.getDadosGuardados();
                nombre = jugador.getNombre();
            }
        }

        tvPeorTirada.setText("Perdiendo esta ronda ("+ nombre+")");
        recyclerView.setAdapter(new AdapterPeorTirada(peorTirada));

    }


    public void asignarImagen(){
        int valor = 1;
        for(int i: imagenesInt){
            valorDados.put(valor,i);
            valor++;
        }
    }

    public void guardarDado(int cara) {
        if(dados.size()<5){
            dados.add(new Dado(cara));
        }
        recycler.setAdapter(new MyAdapterDado(dados));
    }

    public void guardarDados(Jugador j){
        ArrayList<Integer> dadosGuardar = new ArrayList<>();
        for(Dado d : dados){
            dadosGuardar.add(d.getValor());
        }
        j.setDadosGuardados(dadosGuardar);
    }

    public boolean escalera(ArrayList<Integer> arr) {
        boolean[] numerosPresentes = new boolean[7];

        for (int i = 0; i < arr.size(); i++) {
            int numero = arr.get(i);
            if (numero >= 2 && numero <= 6) {
                numerosPresentes[numero] = true;
            }
        }

        for (int j = 2; j <= 6; j++) {
            if (!numerosPresentes[j]) {
                return false;
            }
        }

        jugadorActual.setEscalera();
        return true;
    }


    public void crearJugadores(){
        jugadores=new ArrayList<>();
        for(String j: jugadoresString){
            jugadores.add(new Jugador(j));
        }
    }

    public void cambioJugador(){
        if(turno<jugadores.size()-1){
            if(turno == 0){
                tiradasMax= jugadorActual.getTirarda();
            }
            jugadorActual=jugadores.get(jugadores.indexOf(jugadorActual)+1);
            for(ImageView i:imagenes){
                i.setImageResource(imagenesInt[0]);
                i.setOnClickListener(null);
            }
            btnTirarDados.setClickable(true);
            turno++;
            peorTirada();
        }else{
            perdedorRonda();
            peorTirada.clear();
            recyclerView.setAdapter(new AdapterPeorTirada(peorTirada));
            btnTirarDados.setClickable(true);
            turno=0;
            iniciarRonda();
        }
        dados.clear();
        recycler.setAdapter(new MyAdapterDado(dados));
        tvJugador.setText(jugadorActual.getNombre());
        tvVidas.setText(String.valueOf(jugadorActual.getVidas()));
        tvTirada.setText(String.valueOf(jugadorActual.getTirarda()) + "/" +(tiradasMax));
    }
    public void perdedorRonda(){
        perdedor= new Jugador("");
        perdedor.perdedor();
        for (int i =0; i<jugadores.size(); i++){
            if(jugadores.get(i).getPuntuacion()<=perdedor.getPuntuacion()){
                perdedor=jugadores.get(i);
                jugadores.get(i).limpiarDados();
            }else{
                jugadores.get(i).limpiarDados();
            }
        }for(Jugador j:jugadores){
            j.limpiarDados();
        }
        perdedor.setVidas(perdedor.getVidas()-1);
        tvPeorTirada.setText("");
        tiradasMax=3;
        resetRonda();
        finPartida();
    }

    public ArrayList<Jugador> ordenarJugadores(ArrayList<Jugador> jugadores, int indice) {
        List<Jugador> jugadoresFinal = jugadores.subList(indice,jugadores.size());
        List<Jugador> jugadoresInicio= jugadores.subList(0,indice);
        ArrayList<Jugador>jugadoresOrdenados= new ArrayList<>();
        jugadoresOrdenados.addAll(jugadoresFinal);
        jugadoresOrdenados.addAll(jugadoresInicio);
        return jugadoresOrdenados;
    }

    public void resetearDatos(){
        for(ImageView i : imagenes ){
            i.setImageResource(imagenesInt[0]);
            i.setOnClickListener(null);
        }
    }

    private int rollDice(final ImageView imagen, int duration) {
        final int randomNumber = random.nextInt(6);

        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(imagen, "rotation", 0f, 360f);
        rotateAnimator.setDuration(duration);
        rotateAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator changeAnimator = ObjectAnimator.ofInt(imagen, "imageResource",
                imagenesInt[randomNumber], imagenesInt[(randomNumber + 1) % 6],
                imagenesInt[(randomNumber + 2) % 6], imagenesInt[(randomNumber + 3) % 6],
                imagenesInt[randomNumber]);
        changeAnimator.setDuration(duration);
        changeAnimator.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimator, changeAnimator);

        animatorSet.start();


        imagen.setOnClickListener(this);
        return randomNumber + 1;
    }

    public void resetRonda(){
        for(Jugador j: jugadores){
            j.resetRonda();
        }
    }

    public void tirada(){
        if (jugadorActual.getTirarda() <= tiradasMax - 1) {
            tirarDados();
            jugadorActual.setTirarda(jugadorActual.getTirarda() + 1);
            tvTirada.setText(String.valueOf(jugadorActual.getTirarda()) + "/" +(tiradasMax));
        } else {
            btnTirarDados.setClickable(false);
            finRonda = true;
        }
    }
    public void finPartida(){
        for(Jugador j : jugadores){
            if(j.getVidas() == 0){
                Intent intent = new Intent(this, PantallaFinal.class);
                intent.putExtra("perdedor", j.getNombre());
                intent.putExtra("jugadores", jugadoresString);
                startActivity(intent);
            }
        }
    }

}