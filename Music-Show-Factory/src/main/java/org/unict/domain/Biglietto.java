package org.unict.domain;

import java.util.*;
import java.io.*;

public abstract class Biglietto implements Serializable , Comparable{

	private int codiceBiglietto;
	private String nomeCliente;
	private String cognomeCliente;
	private String codiceFiscaleCliente;
	private int etaCliente;
	private float prezzo;
	private boolean rimborsato;

	public Biglietto(int codiceBiglietto, String nomeCliente, String cognomeCliente, String codiceFiscaleCliente, int eta) {
		this.codiceBiglietto = codiceBiglietto;
		this.nomeCliente = nomeCliente;
		this.cognomeCliente = cognomeCliente;
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.etaCliente = eta;
		this.rimborsato = false;
		this.prezzo = 0;
	}

    /**METODI GET*/
	public String getNomeCliente() {
		return nomeCliente;
	}
	public String getCognomeCliente() {
		return cognomeCliente;
	}
	public String getCodiceFiscaleCliente() {
		return codiceFiscaleCliente;
	}
	public int getEta() {
		return etaCliente;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public boolean isRimborsato() {
		return rimborsato;
	}
	public int getCodiceBiglietto() {
		return codiceBiglietto;
	}
	public abstract float getPrezzoServizioBackstage();

	/**METODI SET*/
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}
	public void setCodiceFiscaleCliente(String codiceFiscaleCliente) {
		this.codiceFiscaleCliente = codiceFiscaleCliente;
	}
	public void setEtaCliente(int etaCliente) {
		this.etaCliente = etaCliente;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public void setRimborsato(boolean rimborsato) {
		this.rimborsato = rimborsato;
	}
	public void setCodiceBiglietto(int codiceBiglietto) {
		this.codiceBiglietto = codiceBiglietto;
	}

    /**METODO TO STRING*/
	@Override
	public String toString() {
		return "BIGLIETTO\n" +
				"codiceBiglietto=" + codiceBiglietto + "\n" +
				", nomeCliente='" + nomeCliente + '\n' +
				", cognomeCliente='" + cognomeCliente + '\n' +
				", codiceFiscaleCliente='" + codiceFiscaleCliente + '\n' +
				", etaCliente=" + etaCliente + "\n" +
				", prezzo=" + prezzo + "\n"+
				", rimborsato=" + rimborsato + "\n";
	}

}
