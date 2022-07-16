package org.unict.domain;

public class StrategyEta implements StrategyInterface {

    @Override
    public float applicaDominio(float prezzo, int eta,float sconto) {
        float prezzoEta = prezzo;

        if(eta < 8) {
            prezzoEta = 0;
        }
        else if (eta > 8 && eta < 18) {
            prezzoEta = prezzo / 2 - sconto;
            if (prezzoEta < 0)
                prezzoEta = 0;
        }

        else
            prezzoEta = prezzo - sconto;
        return prezzoEta;
    }
}
