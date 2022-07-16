package org.unict.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicShowFactoryTest {

    static MusicShowFactory musicshowfactory;
    Artista a;

    @BeforeAll
    public static void initTest() {
        musicshowfactory = MusicShowFactory.getInstance();
    }

    @Test
    //Test per verificare il corretto inserimento degli artisti
    public void testinserisciArtista() {
        //Verifichiamo che l'istanza è già stata creata
        musicshowfactory = MusicShowFactory.getInstance();
        try {
            //Verifichiamo il corretto inserimento degli artisti.
            musicshowfactory.inserisciNuovoArtista("Queen", "Rock", 1);

            musicshowfactory.inserisciNuovoArtista("Metallica", "Metal", 2);

            assertEquals(2, musicshowfactory.getNumeroArtisti());
        } catch (Exception e){
            fail("Unexpected Exception");
        }

        try {
            //In questo test ci si aspetta che l'artista non venga inserito poichè già esiste il codice artista
            musicshowfactory.inserisciNuovoArtista("Red Hot Chili Peppers", "Rock", 1);
            assertEquals(2, musicshowfactory.getNumeroArtisti());
        } catch (Exception e){
            fail("Unexpected Exception");
        }

        try {
            //In questo test ci si aspetta che l'artista non venga inserito poichè già presente un artista con quel nome
            musicshowfactory.inserisciNuovoArtista("Queen", "Pop", 3);
            assertEquals(2, musicshowfactory.getNumeroArtisti());
        } catch (Exception e){
            fail("Unexpected Exception");
        }
    }

    @Test
    //Test per verificare la corretta rimozione dell'artista
    public void testrimuoviArtista() {
        //Verifichiamo che l'istanza è già stata creata
        musicshowfactory = MusicShowFactory.getInstance();
        try {
            musicshowfactory.inserisciNuovoArtista("Queen", "Rock", 1);
            musicshowfactory.inserisciNuovoArtista("Metallica", "Metal", 2);
            musicshowfactory.rimuoviArtisti(1);
            assertEquals(1, MusicShowFactory.getNumeroArtisti());
        } catch (Exception e){
            fail("Unexpected Exception");
        }
    }

    @Test
    //Test per la verifica del corretto inserimento dei concerti
    public void testinserisciConcerto() {
        //Verifichiamo che l'istanza è già stata creata
        musicshowfactory = MusicShowFactory.getInstance();
        try {
            a = new Artista("Queen", "Rock", 1);
            musicshowfactory.creaNuovoConcerto(a, "Live in Wembley", 50.F, 1);
            //Verifichiamo che questo concerto con il codice concerto uguale a quello precedente non verrà
            //inserito dentro la mappa
            musicshowfactory.creaNuovoConcerto(a, "Live in Manchester", 60.F, 1);
            assertEquals(1, musicshowfactory.getNumeroConcerti());
        } catch (Exception e){
            fail("Unexpected Exception");
        }
    }

}