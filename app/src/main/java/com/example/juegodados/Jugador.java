package com.example.juegodados;

import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private int puntuacion;
    private int vidas;
    private int tirarda;
    private ArrayList<Integer> dadosGuardados;

    public Jugador(String nombre) {
        this.nombre= nombre;
        this.puntuacion = 0;
        this.vidas=3;
        this.dadosGuardados=new ArrayList<>();
        this.tirarda =0;
    }

    //Getters and setters
    public void setTirarda(int tirarda) {
        this.tirarda = tirarda;
    }

    public int getTirarda() {
        return tirarda;
    }

    public void setPuntuacion() {
        if (this.puntuacion != 60) {
            sustituirComodines();
            int score = 0;
            int val = 0;
            int[] count = new int[7];
            for (int i : this.dadosGuardados) {
                count[i]++;
                val = i;
            }
            score = (count[val] * 10) + val;
            this.puntuacion=score;
        }System.out.println(puntuacion);
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setDadosGuardados(ArrayList<Integer> dadosGuardados) {
        this.dadosGuardados = dadosGuardados;
    }

    public ArrayList<Integer> getDadosGuardados() {
        return this.dadosGuardados;
    }

    public void setVidas(int vidas) {this.vidas = vidas;}

    public int getVidas() {return vidas;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //Metodos

    public void setEscalera() {
        this.puntuacion = 60;
    }

    public void limpiarDados() {
        this.dadosGuardados.clear();
    }

    public void sustituirComodines() {
        int valor=1;
        for (int v: dadosGuardados){
            if(v!=1){
                valor = v;
                break;
            }
        }
        for (int i = 0; i<dadosGuardados.size(); i++){
            if(dadosGuardados.get(i)==1){
                dadosGuardados.set(i, valor);
            }
        }
    }

    public void añadirDado(int dado) {
        this.dadosGuardados.add(dado);
    }

    public void perdedor(){
        this.puntuacion = 1000;
    }

    public static Jugador puntuacionMin(Jugador[] jugadores) {
        int minScore = Integer.MAX_VALUE;
        Jugador perdedor=null;
        String nomPerd= "";
        for (Jugador jugador : jugadores) {
            if (jugador.puntuacion <= minScore) {
                minScore = jugador.puntuacion;
                nomPerd = jugador.nombre;
                perdedor = jugador;
            }
        }
        perdedor.vidas--;
        if(perdedor.getVidas()<1) {
         //   Juego.finPartida =true;
        }
        String mensaje = "El perdedor es: " + nomPerd;
        System.out.println(mensaje);
        return perdedor;
    }

    public void resetRonda(){
        this.tirarda = 0;
        this.puntuacion= 0;
        this.dadosGuardados.clear();
    }
}

