package org.unict.domain;

public class MerchandiseMaglietta extends Merchandise {


	private enum Materiale {poliestere, cotone, poliammide}
	private enum Colore {giallo, rosso, blu, bianco, nero}
	private enum Taglia {XS, S, M, L, XL}

	private Materiale materiale;
	private Colore colore;
	private Taglia taglia;

	public MerchandiseMaglietta(Artista artista, int codiceMerchandise, String provenienza, float prezzoBase
			, String taglia, String colore, String materiale) {
		super(artista, codiceMerchandise, provenienza, prezzoBase);
		this.taglia = this.taglia.valueOf(taglia);
		this.colore = this.colore.valueOf(colore);
		this.materiale = this.materiale.valueOf(materiale);
	}


	@Override
	public float getPrezzo() {
		float prezzo = 0;

		switch (materiale) {
			case poliestere:
				prezzo = 10;
				break;
			case cotone:
				prezzo = 20;
				break;
			case poliammide:
				prezzo = 5;
				break;
		}
		return prezzo;
	}

	@Override
	public float getCommissione() {
		float commissione = 0.2F;
		return commissione;
	}

	public void setMateriale(String materiale) {
		this.materiale.valueOf(materiale);
	}

	public void setColore(String colore) {
		this.colore.valueOf(colore);
	}

	public void setTaglia(String taglia) {
		this.taglia.valueOf(taglia);
	}

	public Materiale getMateriale() {
		Materiale materiale;
		return materiale = this.materiale;
	}

	public Colore getColore() {
		Colore colore;
		return colore = this.colore;
	}

	public Taglia getTaglia() {
		Taglia taglia;
		return taglia = this.taglia;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}


	@Override
	public String toString() {
		return "MERCHANDISE" +
				"\nARTISTA: " + this.getArtista().getNomeArtista() +
				"\nCODICE MERCHANDISE: " + this.getCodiceMerchandise() +
				"\nIVA: " + this.getIva() +
				"\nPROVENIENZA: " + this.getProvenienza() + '\'' +
				"\nPREZZO BASE: " + this.getPrezzoBase() +
				"\nMAGLIETTA" +
				"\nMATERIALE: " + materiale +
				"\nCOLORE: " + colore +
				"\nTAGLIA: " + taglia +
				"\nRIMBORSATO: " + this.isRimborsato() + "\n";
	}
}
