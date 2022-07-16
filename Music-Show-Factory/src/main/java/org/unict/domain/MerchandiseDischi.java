package org.unict.domain;

public class MerchandiseDischi extends Merchandise{

	public enum Versione{normale,vinile,limited};
	public Versione versione;

	public MerchandiseDischi(Artista artista , int codiceMerchandise, String provenienza, float prezzoBase, String versione) {
		super(artista, codiceMerchandise, provenienza, prezzoBase);
		this.versione = this.versione.valueOf(versione);

	}
	@Override
	public float getPrezzo() {
		float prezzo = 0;
		switch(versione){
			case normale:
				prezzo = this.getPrezzoBase();
				break;
			case vinile:
				float v = this.getPrezzoBase() + 30;
				prezzo = v;
				break;
			case limited:
				float l = this.getPrezzoBase() + 50;
				prezzo = l;
				break;
			default:
				System.out.print("Errore in merchandiseDischi");
		}
		return prezzo;
	}

	@Override
	public float getCommissione() {
		float commissione = 0;
		switch(versione){
			case normale:
				break;
			case vinile:
				float v = 0.3F;
				commissione = v;
				break;
			case limited:
				float l =  0.5F;
				commissione = l;
				break;
			default:
				System.out.print("Errore in merchandiseDischi");
		}
		return commissione;
	}
	@Override
	public String toString() {
		return
				"\nARTISTA: " + this.getArtista().getNomeArtista()+
				"\nCODICE MERCHANDISE: " + this.getCodiceMerchandise() +
				"\nIVA:" + this.getIva() +
				"\nPROVENIENZA: " + this.getProvenienza() +
				"\nPREZZO BASE: " + this.getPrezzoBase() +
				"\nDISCO" +
				"\nVERSIONE: " + versione +
				"\nRIMBORSATO: " + this.isRimborsato() + "\n";
	}


	public void setVersione(String versione) {
		this.versione.valueOf(versione);
	}
	public Versione getVersione() {
		Versione versione;
		return versione = this.versione;
	}



	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
