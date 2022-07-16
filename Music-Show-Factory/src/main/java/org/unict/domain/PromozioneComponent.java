package org.unict.domain;

import java.io.Serializable;

public abstract class PromozioneComponent implements Serializable {

    public abstract float calcolaTotaleScontato(Evento e, Biglietto b);

    //Metodi per gestire i componenti
    public void add(PromozioneComponent promozione, int codice) {};
    //public void remove(PromozioneComponent promozione, int codice) {};
    public void remove(int codice) {};
    //Metodo per gestire la generazione automatica del codice delle promozioni
    public int conta() {
        return 0;
    }
    public void getPromozione() {
    }

    public int getCodicePromozione() {
        return 0;
    }
}
