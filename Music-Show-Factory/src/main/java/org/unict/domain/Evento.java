package org.unict.domain;

import java.io.*;
import java.text.*;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Evento implements Serializable {

	private String data;
	private String ora;
	private String location;
	private int codiceEvento;
	private String nomeEvento;
	private int numeroBigliettiNormali;
	private int numeroBigliettiBackstage;
	private long difference_In_Days; //serve per le date
	private static Scanner in = new Scanner(System.in);
	private static Scanner scan = new Scanner(System.in); //Scanner per stringe
	private static Scanner scanner = new Scanner(System.in);
	private int codiceBiglietto = 0; //gestisce l'autogenerazione del codice del biglietto.
	private Biglietto bigliettoCorrente;

	private Map<Integer, Biglietto> bigliettiVenduti;
	private Map<Integer, Biglietto> bigliettiRimborsati;
	Biglietto newBiglietto = null;

	private PromozioneComponent composite;

	public Evento(String nomeEvento, String Data, String Ora, String location, int codiceEvento,
				  int numeroBigliettiNormali, int numeroBigliettiBackstage) {
		this.data = Data;
		this.ora = Ora;
		this.location = location;
		this.nomeEvento = nomeEvento;
		this.codiceEvento = codiceEvento;
		this.numeroBigliettiNormali = numeroBigliettiNormali;
		this.numeroBigliettiBackstage = numeroBigliettiBackstage;
		bigliettiVenduti = new HashMap<Integer, Biglietto>();
		bigliettiRimborsati = new HashMap<Integer, Biglietto>();

		composite = new PromozioneComposite();

	}

	/**
	 * METODO PER LA GENERAZIONE DI UN BIGLIETTO
	 */
	public void generaBiglietto(Float prezzoBase, Evento eventocorrente) {
		String menu =
				"Seleziona un tipo Biglietto:\n" +
						"1.  Normale\n" +
						"2.  Backstage\n" +
						"> ";
		System.out.print(menu);
		try {
			int choice = getIntChoice(1, 2);

			switch (choice) {
				case 1:
					if (getNumeroBigliettiNormali() == 0) {
						System.out.println("I biglietti normali sono Sold Out");
						break;
					}
					System.out.println("Nome Cliente:");
					String nomeCliente = in.nextLine();
					System.out.println("Cognome Cliente:");
					String cognomeCliente = in.nextLine();
					System.out.println("Codice Fiscale :");
					String codiceFiscale = in.nextLine();
					System.out.println("Eta Cliente: ");
					int etaCliente = getIntChoice(3,90);
					creaBigliettoNormale(generaCodiceBiglietto(), nomeCliente, cognomeCliente,
							codiceFiscale, etaCliente, prezzoBase, eventocorrente);
					break;
				case 2:
					if (getNumeroBigliettiBackstage() == 0) {
						System.out.println("I biglietti Backstage sono Sold Out");
						break;
					}
					System.out.println("Nome Cliente:");
					String nomeCliente1 = in.nextLine();
					System.out.println("Cognome Cliente:");
					String cognomeCliente1 = in.nextLine();
					System.out.println("Codice Fiscale :");
					String codiceFiscale1 = in.nextLine();
					System.out.println("Eta: ");
					int etaCliente1 = getIntChoice(3,90);
					creaBigliettoBackstage(generaCodiceBiglietto(), nomeCliente1,
							cognomeCliente1, codiceFiscale1, etaCliente1, prezzoBase, eventocorrente);
					break;
				default:
					System.err.println("Ritorno alla pagina precedente");
			}
		} catch (Exception e) {
			System.err.println("Impossibile generare il biglietto: " +
					e.getMessage());
		}
	}

	/**
	 * CREAZIONE DEL BIGLIETTO NORMALE
	 */
	public Biglietto creaBigliettoNormale(int codiceBiglietto, String nomeCliente,
										  String cognomeCliente, String codiceFiscale,
										  int etaCliente, float prezzoBase, Evento eventocorrente) {
		setCodiceBiglietto(this.codiceBiglietto + 1);
		newBiglietto = new BigliettoNormale(codiceBiglietto, nomeCliente, cognomeCliente, codiceFiscale, etaCliente);
		setNumeroBigliettiNormali(numeroBigliettiNormali - 1);
		bigliettoCorrente = newBiglietto;
		float sconto = associaPromozione(eventocorrente, bigliettoCorrente);

			bigliettoCorrente.setPrezzo(controlloEta(prezzoBase, etaCliente,sconto));
			bigliettiVenduti.put(bigliettoCorrente.getCodiceBiglietto(), bigliettoCorrente);
			System.out.println("Il prezzo del biglietto è: " + bigliettoCorrente.getPrezzo());
			System.out.println("Vendita del biglietto effettuata con successo!!!");

		return bigliettoCorrente;
	}

	/**
	 * CREAZIONE DEL BIGLIETTO BACKSTAGE
	 */
	public Biglietto creaBigliettoBackstage(int codiceBiglietto, String nomeCliente,
											String cognomeCliente, String codiceFiscale,
											int etaCliente, float prezzoBase, Evento eventocorrente) {
		setCodiceBiglietto(this.codiceBiglietto + 1);
		newBiglietto = new BigliettoBackstage(codiceBiglietto, nomeCliente, cognomeCliente, codiceFiscale, etaCliente);
		setNumeroBigliettiBackstage(numeroBigliettiBackstage - 1);
		bigliettoCorrente = newBiglietto;
			float prezzoBackstage = prezzoBase + (prezzoBase * bigliettoCorrente.getPrezzoServizioBackstage());
			float sconto = associaPromozione(eventocorrente, bigliettoCorrente);
			bigliettoCorrente.setPrezzo(controlloEta(prezzoBackstage, etaCliente,sconto));
			bigliettiVenduti.put(bigliettoCorrente.getCodiceBiglietto(), bigliettoCorrente);
			System.out.print("Il prezzo del biglietto è: " + bigliettoCorrente.getPrezzo());
			System.out.println("Vendita del biglietto effettuata con successo!!!");

		return bigliettoCorrente;
	}

	public int getBigliettiVenduti() {
		int contaBiglietti = bigliettiVenduti.values().size();
		return contaBiglietti;
	}


	/**Funzione per il controllo dell'età tramite pattern strategy*/
	private float controlloEta(float prezzoBase, int eta,float sconto) {
		float prezzoEta = 0;
		if(bigliettoCorrente != null) {
			StrategyFactory sf = StrategyFactory.getInstance();
			StrategyInterface si = sf.getEtaStrategy();
			prezzoEta = si.applicaDominio(prezzoBase, eta,sconto);
			//System.out.println("Il prezzo con l'eta è"+prezzoEta);
		}
		return prezzoEta;
	}

	/**
	 * METODO PER LA GENERAZIONE AUTOMATICA DEL CODICE DEL BIGLIETTO
	 */
	public int generaCodiceBiglietto() {
		int conta = bigliettiVenduti.values().size();
		codiceBiglietto = conta + 1;
		return codiceBiglietto;
	}

	/**
	 * RECUPERA BIGLIETTO PER EFFETTUARE IL RESO
	 */
	public void resoBiglietto(int id_biglietto) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String dataOdierna =dtf.format(now);
		if (controlloValidita(dataOdierna)) {
			bigliettoCorrente = bigliettiVenduti.get(id_biglietto);
			//Implementazione della regola di dominio D2
			if (this.difference_In_Days > 5) {
				System.out.println("Il reso da consegnare al cliente è: " + bigliettoCorrente.getPrezzo());
			} else {
				System.out.println("Il reso da consegnare al cliente è: " + bigliettoCorrente.getPrezzo() / 2);
			}
			if (bigliettoCorrente instanceof BigliettoBackstage) {
				setNumeroBigliettiBackstage(numeroBigliettiBackstage + 1);
			} else {
				setNumeroBigliettiNormali(numeroBigliettiNormali + 1);
			}
			bigliettoCorrente.setRimborsato(true);
			bigliettiRimborsati.put(id_biglietto, bigliettoCorrente);
			bigliettiVenduti.remove(id_biglietto);
			System.out.println("Il rimborso è avvenuto con successo!!!");

		}
	}

	/**
	 * VERIFICA DELLA VALIDITA DELLA DATA PER EFFETTUARE IL RESO
	 */
	private boolean controlloValidita(String dataOdierna) {
		Boolean validity = null;
		try {
			Scanner scanner = new Scanner(System.in);
			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = sdformat.parse(this.data);
			Date d2 = sdformat.parse(dataOdierna);
			if (d1.compareTo(d2) > 0) {
				differenza_in_giorni(d1, d2);
				System.out.println("La differenza in giorni è: " + this.difference_In_Days);
				validity = true;
			} else if (d1.compareTo(d2) < 0) {
				System.out.println("L'evento è ormai scaduto");
				validity = false;
			} else if (d1.compareTo(d2) == 0) {
				this.difference_In_Days = 0;
				System.out.println("Le date sono uguali");
				validity = true;
			}
		} catch (Exception e) {
			System.out.print("Errore in controlloValidità");
		}
		return validity;

	}

	/**
	 * Controlla la data per verificare la possibilità di reso
	 */
	private void differenza_in_giorni(Date d1, Date d2) {
		long difference_In_Time = d1.getTime() - d2.getTime();
		this.difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
	}

	/**
	 * METODO PER INSERIRE LA PROMOZIONE
	 */
	public void inserisciPromozione(Evento eventocorrente) {
		boolean loop = false;
		//Unico difetto è che nel momento in cui dobbiamo aggiungere una nuova promozione allo stesso evento
		//cancella quelle precedenti.
		//Funziona su due eventi diversi

		while (!loop) {
			String menu =
					"Vuoi Inserire una promozione?:\n" +
							"1.  SI\n" +
							"2.  NO\n" +
							"> ";
			System.out.print(menu);
			try {
				int choice = getIntChoice(1, 3);
				switch (choice) {
					case 1:
						String opzioni =
								"Quale promozione vorresti inserire?:\n" +
										"1.  Promozione Numero Biglietti Backstage\n" +
										"2.  Promozione Numero Biglietti Normali\n" +
										"3.  Promozione Data\n" +
										"> ";
						System.out.print(opzioni);
						int option = getIntChoice(1, 4);
						creaPromozione(option, eventocorrente);
						break;

					case 2:
						loop = true;
						break;

					default:
						break;
				}
			} catch (IOError e) {
				System.out.println("Errore di I/O");
			}
		}

	}

	/**
	 * METODO PER LA CREAZIONE DELLA PROMOZIONE
	 */
	public void creaPromozione(int option, Evento eventocorrente) {

		if (option == 1) {
			System.out.println("Inserisci il numero di biglietti backstage rimanenti per applicare lo sconto");
			int numeroBigliettiRimanenti = in.nextInt();
			System.out.println("Inserisci di quanto vuoi diminuire il prezzo: ");
			float scontoPrezzo = Float.parseFloat(scanner.next());
			int codice = generaCodicePromozione();
			PromozioneComponent promozione = new PromozioneNumeroBigliettiBackstage(numeroBigliettiRimanenti,scontoPrezzo, codice);
			composite.add(promozione, codice);
			System.out.println("Promozione Inserita!!!");
		} else if (option == 2) {
			System.out.println("Inserisci il numero di biglietti normali rimanenti per applicare lo sconto");
			int numeroBigliettiRimanenti = in.nextInt();
			System.out.println("Inserisci di quanto vuoi diminuire il prezzo");
			float scontoPrezzo = Float.parseFloat(scanner.next());
			int codice = generaCodicePromozione();
			PromozioneComponent promozione = new PromozioneNumeroBigliettiNormali(numeroBigliettiRimanenti,scontoPrezzo, codice);
			composite.add(promozione, codice);
			System.out.println("Promozione Inserita!!!");
		} else if (option == 3) {
			System.out.println("Inserisci quanti giorni prima dell'evento deve iniziare lo sconto");
			int range = scan.nextInt();
			System.out.println("Inserisci di quanto vuoi diminuire il prezzo");
			float scontoPrezzo = Float.parseFloat(scanner.next());
			int codice = generaCodicePromozione();
			PromozioneComponent promozione = new PromozioneData(range,scontoPrezzo, codice);
			composite.add(promozione, codice);
			System.out.println("Promozione Inserita!!!");
		}
	}

	/**
	 * METODO PER GENERARE AUTOMATICAMENTE IL CODICE DELLE PROMOZIONI
	 * */
	public int generaCodicePromozione() {
		int conta = composite.conta();
		return conta;
	}

	/**
	 * METODO PER MOSTRARE LA PROMOZIONE ATTIVA
	 */
	public void mostraPromozioni() {
		composite.getPromozione();
	}

	/**
	 * METODO PER CALCOLARE LO SCONTO DA APPLICARE IN BASE ALLE PROMOZIONI ATTIVE
	 */

	public float associaPromozione(Evento e, Biglietto b) {
		return composite.calcolaTotaleScontato(e, b);
	}

	/**
	 * METODO PER RIMUOVERE LA PROMOZIONE
	 */
	public void rimuoviPromozione() {
		//Mostriamo le promozioni
		mostraPromozioni();
		System.out.println("Inserisci il CODICE PROMOZIONE che vuoi rimuovere");
		int codice = in.nextInt();
		composite.remove(codice);
		System.out.println("Promozione Rimossa!!!");
	}

	/**
	 * METODO PER MOSTRARE I BIGLIETTI VENDUTI
	 */
	public void mostraBigliettiVenduti() {
		ArrayList listaArray;
		listaArray = new ArrayList(bigliettiVenduti.values());
		List allEvent = listaArray;
		if (allEvent.isEmpty()) {
			System.out.println("La mappa dei biglietti venduti è vuota!");
		} else {
			Iterator iter = allEvent.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
	}

	/**
	 * METODO PER MOSTRARE I BIGLIETTI RIMBORSATI
	 */
	public void mostraBigliettiRimborsati() {
		ArrayList listaArray;
		listaArray = new ArrayList(bigliettiRimborsati.values());
		List allEvent = listaArray;
		if (allEvent.isEmpty()) {
			System.out.println("La mappa dei biglietti rimborsati è vuota!");
		} else {
			Iterator iter = allEvent.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
	}

	/**
	 * METODO PER LA SCELTA DELLA SEZIONE DEL MENU DA APRIRE
	 */
	protected static int getIntChoice(int low, int high) {
		boolean goodInput = false;
		int choice = low - 1;
		while (!goodInput) {
			choice = in.nextInt();
			in.nextLine();
			goodInput = choice >= low && choice <= high;
			if (!goodInput) {
				System.out.print("La scelta deve essere un intero da"
						+ " " + low + " a " + high + ".");
				System.out.print("> ");
			}
		}
		return choice;
	}

	/**
	 * METODI GET
	 */
	public String getLocation() {
		return location;
	}

	public int getCodiceBiglietto() {
		return codiceBiglietto;
	}

	public int getNumeroBigliettiNormali() {
		return numeroBigliettiNormali;
	}

	public int getNumeroBigliettiBackstage() {
		return numeroBigliettiBackstage;
	}

	public int getCodiceEvento() {
		return codiceEvento;
	}

	public String getData() {
		return data;
	}

	public String getOra() {
		return ora;
	}

	public String getNomeEvento() {
		return nomeEvento;
	}

	/**
	 * METODI SET
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public void setCodiceEvento(int codiceEvento) {
		this.codiceEvento = codiceEvento;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	public void setNomeEvento(String nomeEvento) {
		this.nomeEvento = nomeEvento;
	}

	public void setNumeroBigliettiNormali(int numeroBigliettiNormali) {
		this.numeroBigliettiNormali = numeroBigliettiNormali;
	}

	public void setNumeroBigliettiBackstage(int numeroBigliettiBackstage) {
		this.numeroBigliettiBackstage = numeroBigliettiBackstage;
	}

	public void setCodiceBiglietto(int codiceBiglietto) {
		this.codiceBiglietto = codiceBiglietto;
	}

	/**
	 * TO STRING
	 */
	@Override
	public String toString() {
		return
				"\nCODICE EVENTO: " + codiceEvento +
				"\nNOME EVENTO: " + nomeEvento +
				"\nDATA: " + data +
				"\nORA: " + ora +
				"\nLOCATION: " + location +
				"\nNUMERO BIGLIETTI NORMALI: " + numeroBigliettiNormali +
				"\nNUMERO BIGLIETTI BACKSTAGE: " + numeroBigliettiBackstage;
	}
}

