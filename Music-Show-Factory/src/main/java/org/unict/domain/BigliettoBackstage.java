package org.unict.domain;

public class BigliettoBackstage extends Biglietto {

    private float prezzoServizioBackstage = 0.4F;

    public BigliettoBackstage(int codiceBiglietto, String nomeCliente, String cognomeCliente, String codiceFiscaleCliente, int eta) {
        super(codiceBiglietto, nomeCliente, cognomeCliente, codiceFiscaleCliente, eta);
    }

    @Override
    public float getPrezzoServizioBackstage() {
        return prezzoServizioBackstage;
    }

    public void setPrezzoServizioBackstage(int prezzoServizioBackstage) {
        this.prezzoServizioBackstage = prezzoServizioBackstage;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
