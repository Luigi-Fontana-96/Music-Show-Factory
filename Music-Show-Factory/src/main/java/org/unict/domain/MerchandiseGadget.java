package org.unict.domain;

public class MerchandiseGadget extends Merchandise{

	public enum Tipo{cappello,sciarpa,spilla};
	public Tipo tipo;

	public MerchandiseGadget(Artista artista, int codiceMerchandise, String provenienza, float prezzoBase, String tipologia) {
		super(artista, codiceMerchandise, provenienza, prezzoBase);
		this.tipo = this.tipo.valueOf(tipologia);

	}

	@Override
	public float getPrezzo() {
		return this.getPrezzoBase();
	}

	@Override
	public  float getCommissione() {
		float commissione = 0;
		switch (tipo) {
			case cappello:
				float c = 0.15F;
				commissione = c;
				break;
			case sciarpa:
				float s = 0.22F;
				commissione = s;
				break;
			case spilla:
				float sp = 0.05F;
				commissione = sp;
				break;
		}
		return commissione;
	}



	@Override
	public String toString() {
		return "MERCHANDISE" +
				"\nARTISTA: " + this.getArtista().getNomeArtista()+
				"\nCODICE MERCHANDISE: " + this.getCodiceMerchandise() +
				"\nIVA: " + this.getIva() +
				"\nPROVENIENZA: " + this.getProvenienza() +
				"\nPREZZO BASE: " + this.getPrezzoBase() +
				"\nGADGET" +
				"\nTIPO: " + tipo +
				"\nRIMBORSATO: " + this.isRimborsato() + "\n";
	}


	public void setTipo(String tipo){
		this.tipo.valueOf(tipo);
	}

	public Tipo getTipo(){
		Tipo tipo;
		return tipo = this.tipo;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}
}
