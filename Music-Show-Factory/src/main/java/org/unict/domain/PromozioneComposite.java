package org.unict.domain;

import java.util.*;

public class PromozioneComposite extends PromozioneComponent{
    private Map<Integer, PromozioneComponent> listaPromozioni;

    public PromozioneComposite() {
        listaPromozioni = new HashMap<Integer, PromozioneComponent>();
    }
    @Override
    public float calcolaTotaleScontato(Evento e, Biglietto b) {
        float sconto = 0;
        for (Map.Entry<Integer, PromozioneComponent> p : listaPromozioni.entrySet()) {
            sconto = sconto + p.getValue().calcolaTotaleScontato(e,b);
            //System.out.println("Sconto" + sconto);
        }
        return sconto;
    }

    public void add(PromozioneComponent p, int codice) {
        listaPromozioni.put(codice, p);
    }

    public void remove(int codice) {
        listaPromozioni.remove(codice);
    }

    public int conta() {
        int conta = listaPromozioni.values().size();
        if (conta == 0) {
            conta = conta + 1;
        } else {
            for (Map.Entry<Integer, PromozioneComponent> p : listaPromozioni.entrySet()) {
                conta = p.getValue().getCodicePromozione() + 1;;
            }
        }
        return conta;
    }

    public void getPromozione() {
        for (Map.Entry<Integer, PromozioneComponent> p : listaPromozioni.entrySet()) {
            System.out.println(p.getValue().toString());
        }
    }


}
