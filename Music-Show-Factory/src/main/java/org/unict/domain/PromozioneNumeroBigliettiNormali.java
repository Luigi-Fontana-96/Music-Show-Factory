package org.unict.domain;

public class PromozioneNumeroBigliettiNormali extends PromozioneComponent {
    private int rimanentiNormali;
    private float scontoPrezzo;
    private int codicePromozione;

    public PromozioneNumeroBigliettiNormali(int rimanentiNormali, float scontoPrezzo, int codicePromozione) {
        this.rimanentiNormali = rimanentiNormali;
        this.scontoPrezzo = scontoPrezzo;
        this.codicePromozione = codicePromozione;
    }

    @Override
    public float calcolaTotaleScontato(Evento e, Biglietto b) {
        float sconto = 0;

        if (e.getNumeroBigliettiNormali() < rimanentiNormali && b instanceof BigliettoNormale) {
            sconto = scontoPrezzo;
        }
        //System.out.println("ScontoNormale" + sconto);
        return sconto;
    }

    @Override
    public int getCodicePromozione() {
        return codicePromozione;
    }

    @Override
    public String toString() {
        return "PromozioneNumeroBigliettiNormali\nCodicePromozione: " + codicePromozione + "\n" +
                "rimanentiNormali: " + rimanentiNormali + "\n" +
                "scontoPrezzo: " + scontoPrezzo + "\n";
    }
}
