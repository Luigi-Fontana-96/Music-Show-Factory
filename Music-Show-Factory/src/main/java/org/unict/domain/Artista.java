package org.unict.domain;

import java.io.Serializable;
import java.util.*;

public class Artista implements Serializable {

	private String nomeArtista;
	private String genereMusicale;
	public Map<Integer, Merchandise> merchandise;
	private int codiceArtista;

	public Artista(String nomeArtista, String genereMusicale, int codiceArtista) {
		this.nomeArtista = nomeArtista;
		this.codiceArtista = codiceArtista;
		this.genereMusicale = genereMusicale;
	}

	/** METODI GET*/
	public int getCodiceArtista() {
		return codiceArtista;
	}
	public String getNomeArtista() {
		return nomeArtista;
	}
	public String getGenereMusicale() {
		return genereMusicale;
	}

	/**  METODI SET */
	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	public void setGenereMusicale(String genereMusicale) {
		this.genereMusicale = genereMusicale;
	}
	public void setCodiceArtista(int codiceArtista) {
		this.codiceArtista = codiceArtista;
	}

	/**TO STRING*/
	@Override
	public String toString(){
		return
				"ARTISTA" + "\n" +
						"NOME ARTISTA: " + this.getNomeArtista() +
						"\nCODICE ARTISTA: " + this.getCodiceArtista() +
						"\nGENERE MUSICALE: " + this.getGenereMusicale() + "\n";
	}
}


















