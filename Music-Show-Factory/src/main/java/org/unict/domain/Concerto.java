package org.unict.domain;

import java.io.Serializable;
import java.util.*;

public class Concerto implements Serializable {

	private Artista artista;
	private String nomeConcerto;
	private Float prezzoBase;
	private int codiceConcerto;
	public Map<Integer, Evento> eventi;
	public Evento eventoCorrente;
	public Evento eventoSelezionato;

	private static Scanner in = new Scanner(System.in); //Scanner per interi
	private static Scanner scan = new Scanner(System.in); //Scanner per stringe

	public Concerto(Artista artista, String nomeConcerto, Float prezzoBase, int codiceConcerto) {
		this.artista = artista;
		this.nomeConcerto = nomeConcerto;
		this.prezzoBase = prezzoBase;
		this.codiceConcerto = codiceConcerto;
		eventi = new HashMap<Integer, Evento>();
	}

	/** METODO PER INSERIRE L'EVENTO */
	public void inserisciEvento(String nomeEvento, int codiceEvento, String Data,String ora,
								String location, int numeroBigliettiNormali,int numeroBigliettiBackstage) {
		try {
			eventoCorrente = new Evento(nomeEvento, Data, ora, location, codiceEvento, numeroBigliettiNormali, numeroBigliettiBackstage);

			if (eventi.containsKey(codiceEvento)) {
				System.out.println("Codice evento già presente nel Database");
			}
			else if(!controlloDataLocation(Data, location)){
				eventi.put(codiceEvento, eventoCorrente);
				System.out.println("Evento inserito con successo!");
			}

		} catch (Exception e) {
			System.err.println("Impossibile inserire l'evento : " +
					e.getMessage());
		}
	}

	/**METODO PER RIMUOVERE L'EVENTO*/
	public void rimuoviEvento(int codice){
		Evento theEvento = getEventoByTrackingID(codice);
		if ( theEvento != null ) {
			eventi.remove(codice);
			System.out.println("Evento Rimosso Correttamente");
		} else {
			System.err.println( "Nessun Evento trovato con questo ID");
		}

	}

	/**METODO PER MOSTRARE GLI EVENTI*/
	public void mostraEventi(){
		System.out.println("Gli eventi nel sistema sono: ");
		ArrayList listaArray;
		listaArray = new ArrayList(eventi.values());
		List allEvent = listaArray;
		if (allEvent.isEmpty()) {
			System.out.println("La mappa degli eventi è vuota!");
		} else {
			Iterator iter = allEvent.iterator();
			while ( iter.hasNext()) {
				System.out.println( iter.next());
			}
		}
	}

	/**METODO PER SELEZIONARE L'EVENTO DI CUI POI ACQUISTARE IL BIGLIETTO*/
	public void selezionaEvento(Float prezzoBase) {
		mostraEventi(); //Funzione per mostrare gli eventi
		System.out.println("\nInserisci CODICE EVENTO di cui vuoi acquistare il biglietto:");
		int id_evento = in.nextInt();
		eventoSelezionato = eventi.get(id_evento);
		if(eventoSelezionato.getNumeroBigliettiNormali() != 0 || eventoSelezionato.getNumeroBigliettiBackstage() != 0){
			//eventoSelezionato.scriviVendite();
			eventoSelezionato.generaBiglietto(prezzoBase, eventoSelezionato);
		}else{
			System.out.println("Ci dispiace, i biglietto per questo evento sono Sold Out :(");
		}

	}

	/**METODO PER RECUPERARE L'EVENTO PER POI EFFETTUARE IL RIMBORSO DEL BIGLIETTO*/
	public void resoBiglietto() {
		mostraEventi();
		System.out.println("Inserisci CODICE EVENTO di cui vuoi effettuare il reso:");
		int id_evento = in.nextInt();
		eventoSelezionato = eventi.get(id_evento); //gestire eccezione null eventi
		if (eventoSelezionato != null) {
			eventoSelezionato.mostraBigliettiVenduti();
			System.out.println("Inserisci CODICE BIGLIETTO che vuoi rimborsare:");
			int id_biglietto = in.nextInt();
			eventoSelezionato.resoBiglietto(id_biglietto);
		}
	}

	/**CONTROLLO PER LA SOVRAPPOSIZIONE DI UNA LOCATION NELLA STESSA DATA */
	public boolean controlloDataLocation(String data, String locat) {
		boolean presente = false;
		for(Map.Entry<Integer, Evento> e : eventi.entrySet()) {
			if (e.getValue().getLocation().equals(locat) && e.getValue().getData().equals(data)) {
				System.out.println("Location già impegnata in questa data!");
				presente = true;
			}
		}
		return presente;
	}

	/**METODO PER MOSTRARE I BIGLIETTI RIMBORSATI E VENDUTI*/
	public void mostraBiglietti(){
		System.out.println("Inserisci il CODICE EVENTO di cui visualizzare i biglietti:");
		int codiceEvento = in.nextInt();
		eventoSelezionato = getEventoByTrackingID(codiceEvento);
		System.out.println("I biglietti venduti sono:\n");
		eventoSelezionato.mostraBigliettiVenduti();
		System.out.println("I biglietti rimborsati sono:\n");
		eventoSelezionato.mostraBigliettiRimborsati();
	}

	/**
	 * METODI PER LA GESTIONE DELLE PROMOZIONI
	 * */
	public void mostraPromozioni(){
		System.out.println("Inserisci il CODICE EVENTO di cui visualizzare le promozioni:");
		int codiceEvento = in.nextInt();
		eventoSelezionato = getEventoByTrackingID(codiceEvento);
		System.out.println("Le Promozioni per questo evento sono:");
		eventoSelezionato.mostraPromozioni();
	}
	public void rimuoviPromozioni(){
		System.out.println("Inserisci il CODICE EVENTO di cui vuoi visualizzare le promozioni:");
		int codiceEvento = in.nextInt();
		eventoSelezionato = getEventoByTrackingID(codiceEvento);
		eventoSelezionato.rimuoviPromozione();
	}

	public void inserisciPromozioni(){
		System.out.println("Inserisci il CODICE EVENTO di cui inserire l'evento:");
		int codiceEvento = in.nextInt();
		eventoSelezionato = getEventoByTrackingID(codiceEvento);
		eventoSelezionato.inserisciPromozione(eventoSelezionato);
	}

	/**METODO PER RECUPERARE IL NUMERO DI EVENTI PRESENTI NELLA MAPPA (UTILE PER I TEST)*/
	public int getNumeroEventi() {
		int conta = eventi.values().size();
		return conta;
	}

	/**METODO PER RECUPERARE L'EVENTO CON L'ID*/
	public Evento getEventoByTrackingID( int codice ) {
		return (Evento) eventi.get(codice);
	}

	/** METODI GET*/
	public Artista getArtista() {
		return artista;
	}
	public String getNomeConcerto() {
		return nomeConcerto;
	}
	public Float getPrezzoBase() {
		return prezzoBase;
	}
	public int getCodiceConcerto() {
		return codiceConcerto;
	}

	/** METODI SET */
	public void setCodiceConcerto(int codiceConcerto) {
		this.codiceConcerto = codiceConcerto;
	}
	public void setPrezzoBase(Float prezzoBase) {
		this.prezzoBase = prezzoBase;
	}
	public void setNomeConcerto(String nomeConcerto) {
		this.nomeConcerto = nomeConcerto;
	}
	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	/**METODO TO STRING*/
	@Override
	public String toString() {
		return "\nNOME ARTISTA: " + artista.getNomeArtista() + "\n" +
				"NOME CONCERTO: " + nomeConcerto + '\n' +
				"PREZZO BASE: " + prezzoBase + '\n' +
				"CODICE CONCERTO: " + codiceConcerto + '\n';
	}



}

