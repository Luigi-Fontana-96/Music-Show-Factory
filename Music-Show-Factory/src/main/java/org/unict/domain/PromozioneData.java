package org.unict.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PromozioneData extends PromozioneComponent{
    private int rangeDataSconto;
    private float scontoPrezzo;
    private long difference_In_Days; //serve per le date
    private int codicePromozione;

    public PromozioneData(int rangeDataSconto, float scontoPrezzo, int codicePromozione) {
        this.rangeDataSconto = rangeDataSconto;
        this.scontoPrezzo = scontoPrezzo;
        this.codicePromozione = codicePromozione;
    }

    @Override
    public float calcolaTotaleScontato(Evento evento, Biglietto b) {
        float sconto = 0;
        try {
            //formattiamo la data odierna
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String dataOdierna = dtf.format(now);
            SimpleDateFormat sdformat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdformat1.parse(dataOdierna);

            //formattiamo la seconda data
            SimpleDateFormat sdformat2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d2 = sdformat2.parse(evento.getData());
            if (d2.compareTo(d1) > 0) {
                differenza_in_giorni(d2, d1);
                //System.out.println("La differenza in giorni è: " + this.difference_In_Days);
                if(difference_In_Days < rangeDataSconto) {
                    //System.out.println("Range " + rangeDataSconto);
                    sconto = scontoPrezzo;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("ScontoData" + sconto);
        return sconto;
    }

    private void differenza_in_giorni(Date d1, Date d2){
        long difference_In_Time = d1.getTime() - d2.getTime();
        this.difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
    }

    @Override
    public int getCodicePromozione() {
        return codicePromozione;
    }

    public void setCodicePromozione(int codicePromozione) {
        this.codicePromozione = codicePromozione;
    }

    @Override
    public String toString() {
        return "PromozioneData\nCodicePromozione: " + codicePromozione +
                "\nrangeDataSconto: " + rangeDataSconto + "\n" +
                "scontoPrezzo: " + scontoPrezzo + "\n";
    }
}
