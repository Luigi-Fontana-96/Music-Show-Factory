package org.unict.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConcertoTest {

    Concerto c;
    Artista a;

    @BeforeEach
    public void initTest() {
        a = new Artista("Queen", "Rock", 1);
        c = new Concerto(a, "Tour86", 50.0F, 1);
    }

    @Test
    public void testInserisciEvento() {
        try {
            c.inserisciEvento("Live in YorkShire", 1, "1988-01-01",
                    "19:30", "Yorkshire", 100, 50);
            assertEquals(1, c.getNumeroEventi());
        }catch (Exception e){
            fail("Unexpected Exception");
        }

        try {
            c.inserisciEvento("Live in Wembley", 2, "1988-02-01",
                    "19:30", "Yorkshireee", 100, 50);
            assertEquals(2, c.getNumeroEventi());
        } catch (Exception e){
        fail("Unexpected Exception");
           }

        try {
            c.inserisciEvento("Live in Manchester", 3, "1988-03-01",
                    "19:30", "Manchester", 100, 50);
            assertEquals(3, c.getNumeroEventi());
        }catch (Exception e){
            fail("Unexpected Exception");
        }

        //Con questi due test è stato testato se questi non venissero inseriti nella mappa dato che
        //non rispettano le condizioni del codice evento univoco e la condizione della DataLocation
        try {
            //Restituisce il messaggio in cui il codice evento è già presente (andare alla fine della console del test)
            c.inserisciEvento("Live in Italy", 1, "1988-01-02",
                    "19:30", "Milano", 100, 50);
            //Restituisce il messaggio in cui il la location è già impegnata in quella data (andare alla fine della console del test)
            c.inserisciEvento("Live in Manchester", 4, "1988-03-01",
                    "19:30", "Manchester", 100, 50);
            assertEquals(3, c.getNumeroEventi());
        }catch (Exception e){
            fail("Unexpected Exception");
        }
    }

    @Test
    void rimuoviEvento() {
        try {
            c.inserisciEvento("Live in YorkShire", 1, "1988-01-01",
                    "19:30", "Yorkshire", 100, 50);
            c.inserisciEvento("Live in Wembley", 2, "1988-02-01",
                    "19:30", "Yorkshireee", 100, 50);
            c.rimuoviEvento(1);
            assertEquals(1, c.getNumeroEventi());
        } catch (Exception e){
            fail("Unexpected Exception");
        }
    }


}