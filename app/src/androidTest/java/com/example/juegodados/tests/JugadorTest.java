package com.example.juegodados.tests;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.runner.RunWith;
import com.example.juegodados.Jugador;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JugadorTest {
    private Jugador jugador;

    @Before
    public void setUp() {
        jugador = new Jugador("John");
    }

    @Test
    public void testSetTirada() {
        jugador.setTirarda(2);
        Assert.assertEquals(2, jugador.getTirarda());
    }

    @Test
    public void testSetPuntuacion() {
        ArrayList<Integer> dadosGuardados = new ArrayList<>();
        dadosGuardados.add(4);
        dadosGuardados.add(4);
        dadosGuardados.add(2);
        dadosGuardados.add(3);
        dadosGuardados.add(4);
        jugador.setDadosGuardados(dadosGuardados);

        jugador.setPuntuacion();

        Assert.assertEquals(34, jugador.getPuntuacion());
    }

    @Test
    public void testGetDadosGuardados() {
        ArrayList<Integer> dadosGuardados = new ArrayList<>();
        dadosGuardados.add(1);
        dadosGuardados.add(2);
        jugador.setDadosGuardados(dadosGuardados);

        ArrayList<Integer> retrievedDadosGuardados = jugador.getDadosGuardados();

        Assert.assertEquals(dadosGuardados, retrievedDadosGuardados);
    }

    @Test
    public void testSetVidas() {
        jugador.setVidas(2);
        Assert.assertEquals(2, jugador.getVidas());
    }

    @Test
    public void testGetNombre() {
        Assert.assertEquals("John", jugador.getNombre());
    }

    @Test
    public void testSetNombre() {
        jugador.setNombre("Jane");
        Assert.assertEquals("Jane", jugador.getNombre());
    }

    @Test
    public void testSetEscalera() {
        jugador.setEscalera();
        Assert.assertEquals(60, jugador.getPuntuacion());
    }
}