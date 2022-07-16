package org.unict.domain;

import java.io.Serializable;


public abstract class Merchandise implements Serializable, Comparable {

	private Artista artista;
	private int codiceMerchandise;
	private float iva = (float) 0.22;
	private enum Provenienza {ITALIA,CINA,INDIA,VIETNAM}
	private float prezzoBase;
	private boolean rimborsato;
	private float prezzoEffettivo;
	private Provenienza provenienza;

	public Merchandise(Artista artista, int codiceMerchandise, String provenienza ,float prezzoBase) {
		this.artista = artista;
		this.codiceMerchandise = codiceMerchandise;
		this.provenienza = this.provenienza.valueOf(provenienza);
		this.prezzoBase = prezzoBase;
		this.rimborsato = false;
	}

	/** TEMPLATE METHOD */
	public void prezzoEffettivo() {
		switch(provenienza){
			case ITALIA:
				this.prezzoEffettivo = (getPrezzo()*2) + getCommissione();
				break;
			case CINA:
				this.prezzoEffettivo = (getPrezzo()/2) + getCommissione();
				break;
			case INDIA:
				this.prezzoEffettivo = (getPrezzo()/3) + getCommissione();
				break;
			case VIETNAM:
				this.prezzoEffettivo = (getPrezzo()/4) + getCommissione();
				break;
			default:
				System.out.print("Errore in Merchandise");
		}
		System.out.println("Il prezzo effettivo è: " + prezzoEffettivo);

	}


	/** METODI GET E TO STRING */
	@Override
	public abstract String toString();

	public abstract float getPrezzo();
	public abstract float getCommissione();
	public boolean isRimborsato() {
		return rimborsato;
	}
	public Artista getArtista() {return artista;}
	public int getCodiceMerchandise() {return codiceMerchandise;}
	public float getIva() {return iva;}
	public float getPrezzoBase() {return prezzoBase;}
	public Provenienza getProvenienza() {
		Provenienza provenienza;
		return provenienza = this.provenienza;
	}

	/** METODI SET */
	public void setArtista(Artista artista) {this.artista = artista;}
	public void setCodiceMerchandise(int codiceMerchandise) {this.codiceMerchandise = codiceMerchandise;}
	public void setIva(float iva) {this.iva = iva;}
	public void setPrezzoBase(float prezzoBase) {this.prezzoBase = prezzoBase;}
	public void setRimborsato(boolean rimborsato) {
		this.rimborsato = rimborsato;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza.valueOf(provenienza);
	}

}
