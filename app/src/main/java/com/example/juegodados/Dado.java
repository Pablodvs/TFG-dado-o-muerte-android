package com.example.juegodados;

public class Dado {
    private static int nextId = 1; // Agregamos un id estático para cada dado
    private final int id;
    private int cara;
    private int valor;
    private int[] imagenesInt = {R.drawable.dado1, R.drawable.dado2, R.drawable.dado3,
            R.drawable.dado4, R.drawable.dado5, R.drawable.dado6};

    public Dado(int cara){
        this.id = nextId++;
        this.cara= cara;
    }

    public Dado(){
        this.id = nextId++;
        this.cara= R.drawable.dado1;
    }

    public int getCara() {
        return cara;
    }

    public int imagenInt() {
        return cara;
    }

    public void setCara(int cara) {
        this.cara = cara;
    }

    public int getValor() {
        setValor();
        return valor;
    }

    public void setValor() {
        int caraActual = cara;
        switch (caraActual) {
            case R.drawable.dado1:
                this.valor = 1;
                break;
            case R.drawable.dado2:
                this.valor = 2;
                break;
            case R.drawable.dado3:
                this.valor = 3;
                break;
            case R.drawable.dado4:
                this.valor = 4;
                break;
            case R.drawable.dado5:
                this.valor = 5;
                break;
            case R.drawable.dado6:
                this.valor = 6;
                break;

        }
    }
    public int getId() {
        return id;
    }
}