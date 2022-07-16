package org.unict.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    Artista a;
    Concerto c;
    Evento e;
    Evento e1;
    Evento e2;
    Biglietto b;
    PromozioneComponent p;


    @BeforeEach
    public void initTest() {
        a = new Artista("Queen", "Rock", 1);
        c = new Concerto(a, "Tour86", 50.0F, 1);
        e = new Evento("Live in Wembley '86", "2022-06-30", "10:20", "Wembley",
                1, 10, 10);
        e1 = new Evento("Live AID", "2022-03-30", "10:20", "Wembley",
                2, 10, 10);
        e2 = new Evento("Live in Manchester", "2022-06-18", "10:20", "Manchester",
                3, 10, 10);
    }

    /**TEST PER VERIFICARE CHE I BIGLIETTI VENEGONO GENERATI CORRETTAMENTE*/
    @Test
    public void testgeneraBiglietto() {
        try {
           b = e.creaBigliettoBackstage(1, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e);
            assertEquals(1, e.getBigliettiVenduti());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }

        try {
            b = e.creaBigliettoNormale(2, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e);
            assertEquals(2, e.getBigliettiVenduti());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }

        //Test per verificare se mettendo una età minore di 7 effettivamente il prezzo va a 0
        try {
            b = e.creaBigliettoNormale(3, "Giuseppe", "Testa", "TSTGPP",
                    7, c.getPrezzoBase(), e);
            assertEquals(3, e.getBigliettiVenduti());
            assertEquals(0.0, b.getPrezzo());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }

        //Test per verificare se mettendo eta compresa tra 8 e 18 applica il 50%
        try {
            b = e.creaBigliettoNormale(4, "Giuseppe", "Testa", "TSTGPP",
                    14, c.getPrezzoBase(), e);
            assertEquals(4, e.getBigliettiVenduti());
            assertEquals((c.getPrezzoBase()/2), b.getPrezzo());
        } catch (Exception e) {
            fail("Unexpected Exception");
        }


    }

    @Test
    public void testresoBiglietti() {
        try {
            e1.creaBigliettoBackstage(1, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e1);
            e1.creaBigliettoNormale(2, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e1);

            e1.resoBiglietto(2);

            //Con la funzione reso biglietto viene rimborsato il biglietto.
            //In questo primo caso il rimborso non verrà effettuato poichè la data dell'evento è passata
            //Rispetto alla data attuale
            assertEquals(2, e1.getBigliettiVenduti());

            //In questo caso il rimborso avviene correttamente e consegnando il massimo possibile di rimborso al cliente
            e.creaBigliettoBackstage(1, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e);
            e.creaBigliettoNormale(2, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e);
            e.resoBiglietto(1);

            assertEquals(1, e.getBigliettiVenduti());

            //In questo caso il rimborso avviene correttamente ma verrà consegnata solo la metà dei soldi poichè
            //mancano meno di 5 giorni all'evento.
            e2.creaBigliettoBackstage(1, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e2);
            e2.creaBigliettoNormale(2, "Giuseppe", "Testa", "TSTGPP",
                    25, c.getPrezzoBase(), e2);
            e2.resoBiglietto(1);

            assertEquals(1, e2.getBigliettiVenduti());

            /**Vedere i risultati tramite la console dei test*/


        } catch (Exception e){
            fail("Unexpected Exception");
        }
    }

}