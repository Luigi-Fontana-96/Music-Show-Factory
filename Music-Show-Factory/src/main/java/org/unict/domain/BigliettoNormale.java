package org.unict.domain;

public class BigliettoNormale extends Biglietto {

    private int prezzoServizioNormale = 1;

    public BigliettoNormale(int codiceBiglietto, String nomeCliente, String cognomeCliente, String codiceFiscaleCliente, int eta) {
        super(codiceBiglietto, nomeCliente, cognomeCliente, codiceFiscaleCliente, eta);
    }

    @Override
    public float getPrezzoServizioBackstage() {
        return 0;
    }

    public int getPrezzoServizioNormale() {
        return prezzoServizioNormale;
    }

    public void setPrezzoServizioNormale(int prezzoServizioNormale) {
        this.prezzoServizioNormale = prezzoServizioNormale;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
