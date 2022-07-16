package org.unict.domain;

public class PromozioneNumeroBigliettiBackstage extends PromozioneComponent{
    private int rimanentiBackstage;
    private float scontoPrezzo;
    private int codicePromozione;

    public PromozioneNumeroBigliettiBackstage(int rimanentiBackstage, float scontoPrezzo, int codicePromozione) {
        this.rimanentiBackstage = rimanentiBackstage;
        this.scontoPrezzo = scontoPrezzo;
        this.codicePromozione = codicePromozione;
    }

    @Override
    public float calcolaTotaleScontato(Evento e, Biglietto b) {
        float sconto = 0;
        if (e.getNumeroBigliettiBackstage() < rimanentiBackstage && b instanceof BigliettoBackstage) {
            sconto = scontoPrezzo;
        }
       //System.out.println("ScontoBackstage" + sconto);
        return sconto;
    }

    @Override
    public int getCodicePromozione() {
        return codicePromozione;
    }

    @Override
    public String toString() {
        return "PromozioneNumeroBigliettiBackstage\nCodicePromozione: " + codicePromozione + "\n" +
                "rimanentiBackstage: " + rimanentiBackstage + "\n" +
                "scontoPrezzo: " + scontoPrezzo + "\n";
    }
}
