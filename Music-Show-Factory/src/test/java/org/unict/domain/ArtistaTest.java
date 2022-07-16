package org.unict.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistaTest {

    Artista a;

    @BeforeEach
    public void initTest() {
        a = new Artista("Queen","Rock", 1);
    }

    @AfterEach
    public void clearTest() {a.setNomeArtista(null);a.setCodiceArtista(0);a.setGenereMusicale(null);}

    @Test
    public void testgetCodiceArtista() {
         assertEquals(1, a.getCodiceArtista());
    }

    @Test
    public void testsetCodiceArtista() {
         a.setCodiceArtista(2);
         assertEquals(2,a.getCodiceArtista());
    }

    @Test
    public void testgetNomeArtista() {
        assertEquals("Queen", a.getNomeArtista());
    }

    @Test
    public void testsetNomeArtista() {
        a.setNomeArtista("Metallica");
        assertEquals("Metallica", a.getNomeArtista());
    }

    @Test
    public void testgetGenereMusicale() {
        assertEquals("Rock", a.getGenereMusicale());
    }

    @Test
    public void testsetGenereMusicale() {
        a.setGenereMusicale("TrashMetal");
        assertEquals("TrashMetal", a.getGenereMusicale());
    }
}