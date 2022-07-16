package org.unict.domain;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

public class MusicShowFactory implements Serializable {

    private static MusicShowFactory musicshowfactory; //Singleton

    public static Map<Integer, Artista> artisti = new HashMap<Integer, Artista>();
    public static Artista artistaCorrente;
    public static Artista artistaSelezionato;

    public static Map<Integer, Concerto> concerti = new HashMap<Integer, Concerto>();
    ;
    public static Concerto concertoCorrente;
    public static Concerto concertoSelezionato;

    public static Map<Integer, Merchandise> merchandise = new HashMap<Integer, Merchandise>();
    ;
    public static Map<Integer, Merchandise> merchandiseVenduto = new HashMap<Integer, Merchandise>();
    public static Merchandise merchandiseCorrente;
    public static Merchandise merchandiseSelezionato;

    private static int codiceMerch = 0;
    private static Scanner in = null;
    private static Scanner scan = new Scanner(System.in); //Scanner per stringe
    private static Scanner scanner = new Scanner(System.in);
    private static Scanner password = new Scanner(System.in); //Scanner per password
    private static int pass;


    public static void main(String[] args) {
        getInstance().start();
    }

    /**
     * Singleton
     */
    public static MusicShowFactory getInstance() {
        if (musicshowfactory == null)
            musicshowfactory = new MusicShowFactory();
        else
            System.out.println("Instanza già creata");
        return musicshowfactory;
    }

    /**
     * Visualizzazione del menu iniziale
     */
    public static void start() {
        loadFromFile("Artisti.bin"); //carichiamo gli artisti in maniera preventiva
        loadFromFile("Concerti.bin"); //caricamento dei concerti
        loadFromFile("Merchandise.bin"); //caricamento del merchandise
        in = new Scanner(System.in);
        System.out.println("********MUSICSHOWFACTORY********");
        System.out.println("Benvenuti su MusicShowFactory");
        boolean quit = false;
        while (!quit) {
            System.out.println("++++++++MENU PRINCIPALE++++++++");
            int actorChoice = choiceActor();
            switch (actorChoice) {
                case 1:
                    System.out.println("Inserisci Password Cashier: ");
                    System.out.print("->");
                    pass = password.nextInt();
                    if (pass == 123) {
                        cashierActor();
                    } else {
                        System.out.println("Password Errata!!");
                    }
                    break;
                case 2:
                    System.out.println("Inserisci Password Market Manager: ");
                    System.out.print("->");
                    pass = password.nextInt();
                    if (pass == 456) {
                        marketManagerActor();
                    } else {
                        System.out.println("Password Errata!!");
                    }
                    break;
                case 3:
                    System.out.println("Inserisci Password Concert Planner: ");
                    System.out.print("->");
                    pass = password.nextInt();
                    if (pass == 789) {
                        concertPlannerActor();
                    } else {
                        System.out.println("Password Errata!!");
                    }
                    break;
                case 4:
                    System.out.print("Chiusura in corso! \nBuona Giornata :)");
                    System.out.print("Professore quanto ci mette? ;)");
                    quit = true;
                    break;
                default:
                    System.err.println("Errore: "
                            + "scelta non riconosciuta");
            }
        }
        saveToFile("Artisti.bin", artisti);
        saveToFile("Concerti.bin", concerti);
        saveToFile("Merchandise.bin", merchandise);
    }

    /**
     * Visualizzazione per la scelta dell'attore
     */
    private static int choiceActor() {
        boolean quit = false;
        int choice = 0;
        while (!quit) {
            try {
                System.out.print("Seleziona l'attore:\n");
                System.out.print(
                        "1.Cashier\n" +
                                "2.MarketManager\n" +
                                "3.ConcertPlanner\n" +
                                "4.Esci\n" +
                                "> ");
                choice = getIntChoice(1, 4);
                quit = true;
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
        return choice;
    }

    /**
     * ---------------------Inizio CASHIER--------------------------
     */
    private static void cashierActor() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nBen Tornato Cashier!\n" + "Che cosa vuoi fare?");
                System.out.print(
                        "1.-> Compra Biglietto\n" +
                                "2.-> Reso Biglietto\n" +
                                "3.-> Compra Merchandise\n" +
                                "4.-> Reso Merchandise\n" +
                                "5.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 5); //la scelta verrà memorizzata qui
                switch (choice) {
                    case 1:
                        compraBiglietto();
                        break;
                    case 2:
                        resoBiglietto();
                        break;
                    case 3:
                        compraMerchandise();
                        break;
                    case 4:
                        resoMerchandise();
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        // Should never happen
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    /**
     * METODI COMPRA/RESO PER MERCHANDISE E BIGLIETTO
     */
    private static void compraBiglietto() {
        mostraArtisti();
        System.out.println("Inserisci CODICE ARTISTA di cui vuoi vedere i concerti");
        int id_artista = in.nextInt();
        artistaSelezionato = getArtistaByTrackingID(id_artista);
        if(artistaSelezionato != null){
            if(stampaConcertiArtista(id_artista)){
                System.out.println("Inserisci CODICE CONCERTO di cui vuoi vedere gli eventi:");
                int id_concerto = in.nextInt();
                concertoSelezionato = getConcertoByTrackingID(id_concerto);
                if (concertoSelezionato != null) {
                    concertoSelezionato.selezionaEvento(concertoSelezionato.getPrezzoBase());
                }
            }else{
                System.out.print("Per questo artista non ci sono concerti in programma");
            }
        }else
        {
            System.out.println("Nessun artista presente con questo CODICE ARTISTA:");
        }
    }

    private static void resoBiglietto(){
        mostraConcerti();
        System.out.println("Inserisci CODICE CONCERTO di cui vuoi effettuare il reso del biglietto:");
        int id_concerto = in.nextInt();
        concertoSelezionato = getConcertoByTrackingID(id_concerto);
        if (concertoSelezionato != null) {
            concertoSelezionato.resoBiglietto();
        }
    }

    private static void compraMerchandise() {
        mostraArtisti();
        System.out.println("Inserisci CODICE ARTISTA di cui vuoi comprare il merchandise");
        int id_artista = in.nextInt();
        artistaSelezionato = getArtistaByTrackingID(id_artista);
        if (artistaSelezionato != null) {
            //stampaMerchadiseArtista(artistaSelezionato.getCodiceArtista());
            if(selezionaTipologiaMerchandise(artistaSelezionato.getCodiceArtista())){
                System.out.println("Inserisci CODICE MERCHANDISE: ");
                int id_Merchandise = in.nextInt();
                selezionaMerchandise(id_Merchandise);
            }
            System.out.println("Sessione di vendita completata");

        }
    }

    public static void selezionaMerchandise(int id_Merchandise) {
        merchandiseSelezionato = merchandise.get(id_Merchandise);
        merchandiseSelezionato.prezzoEffettivo();
        merchandiseVenduto.put(id_Merchandise, merchandiseSelezionato);
        merchandise.remove(id_Merchandise, merchandiseSelezionato);
        //System.out.println(merchandiseVenduto.toString());
    }

    private static void resoMerchandise() {
        mostraMerchandiseVenduto();
        System.out.println("Inserisci ID Merchandise: ");
        int id_merchandise = in.nextInt();
        merchandiseSelezionato = getMerchandiseVendutiByTrackingID(id_merchandise);
        if (merchandiseSelezionato != null) {
            merchandiseSelezionato.prezzoEffettivo();
            boolean quit = false;
            int choice = 0;
            System.out.print("Vuoi continuare il reso?\n");
            System.out.print(
                    "1.SI\n" +
                            "2.NO\n" +
                            "> ");
            choice = getIntChoice(1, 2);
            switch (choice) {
                case 1:
                    merchandiseSelezionato.setRimborsato(true);
                    merchandiseSelezionato.setCodiceMerchandise(generaCodiceMerchadise());
                    merchandise.put(generaCodiceMerchadise(), merchandiseSelezionato);
                    merchandiseVenduto.remove(id_merchandise);
                    System.out.println("Il reso è stato memorizzato correttamente");
                    break;
                case 2:
                    System.out.println("Nessun reso effettuato");
                    quit = true;
                    break;
            }

        }

    }
    /** ---------------------------Fine CASHIER---------------------------*/

    /**
     * ----------------------- INIZIO MARKET MANAGER----------------------
     */
    private static void marketManagerActor() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nBentornato market manager! Che cosa vuoi fare? :)");
                System.out.print(
                        "1.-> Gestisci Merchandises\n" +
                                "2.-> Mostra Merchandise per artista\n" +
                                "3.-> Gestisci Promozioni Evento\n" +
                                "4.-> Mostra Promozioni Evento\n" +
                                "5.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 5); // la scelta verrà memorizzata qui
                switch (choice) {
                    case 1:
                        manageMerchandise();
                        break;
                    case 2:
                        mostraArtisti();
                        if (artisti.values().size() != 0) {
                            System.out.println("Inserisci CODICE ARTISTA di cui vuoi visualizzare il Merchandise:");
                            int id_artista = in.nextInt();
                            stampaMerchadiseArtista(id_artista);
                        }
                        break;
                    case 3:
                        managePromotion();
                        break;
                    case 4:
                        try{
                            mostraConcerti();
                            if (concerti.values().size() != 0) {
                                System.out.println("Inserisci CODICE CONCERTO: ");
                                int codiceConcerto = in.nextInt();
                                concertoSelezionato = getConcertoByTrackingID(codiceConcerto);
                                concertoSelezionato.mostraEventi();
                                concertoSelezionato.mostraPromozioni();
                            }
                        }catch (NullPointerException e){
                            System.out.println("Codice concerto non valido!");
                        }
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    /**
     * METODI MERCHANDISE
     */
    private static void manageMerchandise() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nChe cosa vuoi fare? :)");
                System.out.print(
                        "1.-> Inserisci Merchandise\n" +
                                "2.-> Rimuovi Merchandise\n" +
                                "3.-> Mostra il Merchandise di tutti gli artisti \n" +
                                "4.-> Mostra Merchandise Venduto\n" +
                                "5.-> Salva Merchandise\n" +
                                "6.-> Carica Merchandise\n" +
                                "7.-> Quit\n" +
                                "> ");
                int choice = getIntChoice(1, 7); // la scelta verrà memorizzata qui
                switch (choice) {
                    case 1:
                        mostraArtisti();
                        if (artisti.values().size() != 0) {
                            System.out.println("Inserisci NOME ARTISTA di cui vuoi inserire il merchandise: ");
                            String nomeArtista = in.nextLine();
                            inserisciNuovoMerchandise(nomeArtista);
                        }
                        break;
                    case 2:
                        mostraMerchandise();
                        if (merchandise.values().size() != 0) {
                            System.out.println("Inserisci il CODICE MERCHANDISE che vuoi rimuovere");
                            int codice = in.nextInt();
                            rimuoviMerchandise(codice);
                        }
                        break;
                    case 3:
                        mostraMerchandise();
                        break;
                    case 4:
                        mostraMerchandiseVenduto();
                        break;
                    case 5:
                        saveToFile("Merchandise.bin", merchandise);
                        System.out.println("Salvato su file Merchandise.bin");
                        break;
                    case 6:
                        loadFromFile("Merchandise.bin");
                        System.out.println("Caricato da file Merchandise.bin");
                        break;
                    case 7:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    public static void inserisciNuovoMerchandise(String nomeArtista) {
        Scanner inf = new Scanner(System.in);
        int merch = 0;
        String menumerch =
                "Seleziona un tipo:\n" +
                        "1.  Gadget\n" +
                        "2.  Maglietta\n" +
                        "3.  Dischi\n" +
                        "> ";
        System.out.print(menumerch);
        for (Artista a : artisti.values()) {
            if (a.getNomeArtista().equals(nomeArtista)) {
                merch = getIntChoice(1, 3);
                artistaCorrente = a;
                try {
                    System.out.println("Prezzo Base: ");
                    Float prezzoBase = Float.parseFloat(scanner.next());
                    System.out.println("Provenienza (ITALIA,CINA,INDIA,VIETNAM): ");
                    String provenienza = in.nextLine();
                    switch (merch) {
                        case 1:
                            System.out.println("Tipologia(cappello,sciarpa,spilla): ");
                            String tipologia = in.nextLine();
                            System.out.println("Quanti ne vuoi inserire?:");
                            int quantitaG = in.nextInt();
                            for (int i = 0; i < quantitaG; i++) {
                                merchandiseCorrente = new MerchandiseGadget(a, generaCodiceMerchadise(), provenienza.toUpperCase(), prezzoBase, tipologia);
                                merchandise.put(codiceMerch, merchandiseCorrente);
                                merchandiseCorrente = null;
                            }
                            System.out.println("Merchandise inserito correttamente!!!");
                            break;
                        case 2:
                            System.out.println("Taglia(XS,S,M,L,XL): ");
                            String taglia = in.nextLine();
                            System.out.println("Colore(giallo,rosso,blu,bianco,nero): ");
                            String colore = in.nextLine();
                            System.out.println("Materiale(poliestere,cotone,poliammide): ");
                            String materiale = in.nextLine();
                            System.out.println("Quanti ne vuoi inserire?:");
                            int quantitaM = in.nextInt();
                            for (int i = 0; i < quantitaM; i++) {
                                merchandiseCorrente = new MerchandiseMaglietta(a, generaCodiceMerchadise(), provenienza.toUpperCase(), prezzoBase, taglia, colore, materiale);
                                merchandise.put(codiceMerch, merchandiseCorrente);
                                merchandiseCorrente = null;
                            }
                            System.out.println("Merchandise inserito correttamente!!!");
                            break;
                        case 3:
                            System.out.println("Versione(normale,vinile,limited): ");
                            String versione = in.nextLine();
                            System.out.println("Quanti ne vuoi inserire?:");
                            int quantitaD = in.nextInt();
                            for (int i = 0; i < quantitaD; i++) {
                                merchandiseCorrente = new MerchandiseDischi(a, generaCodiceMerchadise(), provenienza.toUpperCase(), prezzoBase, versione);
                                merchandise.put(codiceMerch, merchandiseCorrente);
                                merchandiseCorrente = null;
                            }
                            System.out.println("Merchandise inserito correttamente!!!");
                            break;
                        default:
                            System.err.println("Errore durante l'inserimento del merchandise");
                    }

                } catch (Exception e) {
                    System.err.println("Impossibile inserire il merchandise: " +
                            e.getMessage());
                }

            }
        }
    }

    private static void rimuoviMerchandise(int id_merchandise) {
        Merchandise theMerchandise = getMerchandiseByTrackingID(id_merchandise);
        if (theMerchandise != null) {
            merchandise.remove(id_merchandise);
            System.out.println("Merchandise eliminato correttamente!");
        } else {
            System.err.println("Nessun Merchandise trovato con questo ID.");
        }
    }

    public static boolean stampaMerchadise(int codiceArtista , int choice) {
        for (Map.Entry<Integer, Merchandise> merch : merchandise.entrySet()) {
            if (merch.getValue().getArtista().getCodiceArtista() == codiceArtista) {
                if(choice == 1 && merch.getValue() instanceof MerchandiseMaglietta){
                    System.out.println(merch);
                }
                if(choice == 2 && merch.getValue() instanceof MerchandiseDischi){
                    System.out.println(merch);
                }
                if(choice == 3 && merch.getValue() instanceof MerchandiseGadget){
                    System.out.println(merch);
                }
            }
        }
        return true;
    }

    private static boolean selezionaTipologiaMerchandise(int codiceArtista){
        boolean value = false;
        int dischi = 0;
        int gadget = 0;
        int magliette = 0;
        for (Map.Entry<Integer, Merchandise> merch : merchandise.entrySet()) {
            if (merch.getValue().getArtista().getCodiceArtista() == codiceArtista) {
                if(merch.getValue() instanceof MerchandiseDischi){
                    dischi = dischi +1;
                }
                if(merch.getValue() instanceof MerchandiseGadget) {
                    gadget = gadget + 1;
                }
                if(merch.getValue() instanceof MerchandiseMaglietta){
                    magliette = magliette + 1;
                }
            }
        }
        System.out.println("Le magliette disponibili sono: "+ magliette);
        System.out.println("I dischi disponibili sono: "+ dischi);
        System.out.println("I gadget disponibili sono: "+ gadget);
        int choice=0;
        System.out.print("Vuoi continuare con l'acquisto?\n");
        System.out.print(
                "1.SI\n" +
                        "2.NO\n" +
                        "> ");
        choice = getIntChoice(1, 2);
        if (choice == 1) {
            int risposta = 0;
            System.out.print("Cosa Vuoi acquistare?\n");
            System.out.print(
                    "1.Maglietta\n" +
                            "2.Dischi\n" +
                            "3.Gadget\n" +
                            "> ");
            risposta = getIntChoice(1, 3);
            if(risposta == 1 && magliette == 0 || risposta == 2 && dischi == 0 || risposta == 3 && gadget == 0){
                    System.out.println("Categoria non disponibile!");
            }else {
                value = stampaMerchadise(codiceArtista, risposta);
            }
        }
        return value;
    }

    public static void stampaMerchadiseArtista(int codiceArtista) {
        for (Map.Entry<Integer, Merchandise> merch : merchandise.entrySet()) {
            if (merch.getValue().getArtista().getCodiceArtista() == codiceArtista) {
                System.out.println(merch);
            }
        }
    }


    static void mostraMerchandise() {
        System.out.println("Il merchandise nel sistema è:");
        ArrayList listaArray;
        listaArray = new ArrayList(merchandise.values());
        List allMerchandise = listaArray;
        if (allMerchandise.isEmpty()) {
            System.out.print("La mappa del Merchandise è vuota!");
        } else {
            Iterator iter = allMerchandise.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        }
    }

    static void mostraMerchandiseVenduto() {
        System.out.println("Il merchandise venduto nel sistema è:");
        ArrayList listaArray;
        listaArray = new ArrayList(merchandiseVenduto.values());
        List allMerchandise = listaArray;
        if (allMerchandise.isEmpty()) {
            System.out.print("La mappa del merchandise venduto è vuota!");
        } else {
            Iterator iter = allMerchandise.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        }
    }

    /**
     * METODI PROMOZIONI
     */
    private static void managePromotion() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nChe cosa vuoi fare? :)");
                System.out.print(
                        "1.-> Inserisci Promo\n" +
                                "2.-> Rimuovi Promo\n" +
                                "3.-> Mostra tutto il Promo \n" +
                                "4.-> Esci\n" +
                                "> ");

                int choice = getIntChoice(1, 4); // la scelta verrà memorizzata qui


                switch (choice) {
                    case 1:
                        try{
                            mostraConcerti();
                            if (concerti.values().size() != 0) {
                                System.out.println("Inserisci CODICE CONCERTO di cui vuoi inserire la promo: ");
                                int codiceConcerto = in.nextInt();
                                concertoSelezionato = getConcertoByTrackingID(codiceConcerto);
                                concertoSelezionato.mostraEventi();
                                concertoSelezionato.inserisciPromozioni();
                            }
                        }catch (NullPointerException e){
                            System.out.print("Codice Concerto non valido");
                        }
                        break;
                    case 2:
                        try {
                            mostraConcerti();
                            if (concerti.values().size() != 0) {
                                System.out.println("Inserisci CODICE CONCERTO di cui vuoi rimuovere la promo: ");
                                int codiceConcertor = in.nextInt();
                                concertoSelezionato = getConcertoByTrackingID(codiceConcertor);
                                concertoSelezionato.mostraEventi();
                                concertoSelezionato.rimuoviPromozioni();
                            }
                        } catch (NullPointerException e) {
                            System.out.print("Codice Concerto non valido");
                        }
                        break;

                    case 3:
                        try {
                            mostraConcerti();
                            if (concerti.values().size() != 0) {
                                System.out.println("Inserisci CODICE CONCERTO di cui visualizzare le promozioni: ");
                                int codiceConcerto2 = in.nextInt();
                                concertoSelezionato = getConcertoByTrackingID(codiceConcerto2);
                                concertoSelezionato.mostraEventi();
                                concertoSelezionato.mostraPromozioni();
                            }
                        } catch (NullPointerException e) {
                            System.out.print("Codice Concerto non valido");
                        }
                        break;

                    case 4:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }

            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

/**--------------------     FINE MARKET MANAGER------------------------------*/


    /**
     * ------------------------ INIZIO CONCERT PLANNER-----------------------------
     */

    private static void concertPlannerActor() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nBen tornato Concert Planner!\n" + "Che cosa vuoi fare?");
                System.out.print(
                        "1.-> Gestisci Artisti \n" +
                                "2.-> Gestisci Concerti\n" +
                                "3.-> Gestisci Eventi\n" +
                                "4.-> Mostra Biglietti venduti e rimborsati\n" +
                                "5.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 5);
                switch (choice) {
                    case 1:
                        manageArtist();
                        break;
                    case 2:
                        manageConcert();
                        break;
                    case 3:
                        manageEvents();
                        break;
                    case 4:
                        mostraBiglietti();
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    private static void mostraBiglietti() {
        mostraConcerti();
        if (concerti.values().size() != 0) {
            System.out.println("Inserisci CODICE CONCERTO del Concerto di cui visualizzare i biglietti: ");
            int codiceConcerto = in.nextInt();
            concertoSelezionato = getConcertoByTrackingID(codiceConcerto);
            concertoSelezionato.mostraEventi();
            concertoSelezionato.mostraBiglietti();
        }
    }

    /**
     * GESTISCI ARTISTI (CRUD)
     */
    private static void manageArtist() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nWhat you want do?");
                System.out.print(
                        "1.-> Inserisci Artista\n" +
                                "2.-> Rimuovi Artista\n" +
                                "3.-> Mostra Artisti\n" +
                                "4.-> Salva Artisti\n" +
                                "5.-> Carica Artisti\n" +
                                "6.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 6); // la scelta verrà memorizzata qui
                switch (choice) {
                    case 1:
                        System.out.println("Nome Artista: ");
                        String nomeArtista = in.nextLine();
                        System.out.println("Genere Musicale: ");
                        String genereMusicale = in.nextLine();
                        System.out.println("Codice Artista: ");
                        int codiceArtista = in.nextInt();
                        inserisciNuovoArtista(nomeArtista, genereMusicale, codiceArtista);
                        break;
                    case 2:
                        mostraArtisti();
                        if (artisti.values().size() != 0) {
                            System.out.println("Inserisci il CODICE ARTISTA che vuoi rimuovere");
                            int codice = in.nextInt();
                            rimuoviArtisti(codice);
                        }
                        break;
                    case 3:
                        mostraArtisti();
                        break;
                    case 4:
                        saveToFile("Artisti.bin", artisti);
                        System.out.println("Salvato su file Artisti.bin");
                        break;
                    case 5:
                        loadFromFile("Artisti.bin");
                        System.out.println("Caricato da file Artisti.bin");
                        break;
                    case 6:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    public static void inserisciNuovoArtista(String nomeArtista, String genereMusicale, int codiceArtista) {
        try {
            artistaCorrente = new Artista(nomeArtista, genereMusicale, codiceArtista);
            if (artisti.containsKey(codiceArtista)) {
                System.out.println("Errore: esiste già un artista con questo codice");
            } else if(!controlloNomeArtista(nomeArtista)){
                artisti.put(codiceArtista, artistaCorrente);
                System.out.println("L'artista " + nomeArtista + " è stato inserito nel sistema!");
            }
        } catch (Exception e) {
            System.err.println("Impossibile inserire un nuovo artista: " +
                    e.getMessage());
        }
    }

    private static boolean controlloNomeArtista(String nomeArtista) {
        boolean presente = false;
        for(Map.Entry<Integer, Artista> a : artisti.entrySet()) {
            if (a.getValue().getNomeArtista().equals(nomeArtista)) {
                System.out.println("Errore: Artista già presente nel sistema");
                presente = true;
            }
        }
        return presente;
    }

    private static void mostraArtisti() {
        System.out.println("Gli artisti nel sistema sono:\n");
        ArrayList listaArray;
        listaArray = new ArrayList(artisti.values());
        List allArtist = listaArray;
        if (allArtist.isEmpty()) {
            System.out.println("La mappa degli artisti è vuota!\n");
        } else {
            Iterator iter = allArtist.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        }
    }

    public static void rimuoviArtisti(int id_artista) {
        Artista theArtist = getArtistaByTrackingID(id_artista);
        if (theArtist != null) {
            artisti.remove(id_artista);
            System.out.println("L'artista è stato eliminato dal sistema!");
        } else {
            System.out.println("Non esiste nessun Artista con questo ID.");
        }
    }

    public static int getNumeroArtisti() {
        return artisti.values().size();
    }

    /**
     * GESTISCI CONCERTI ED EVENTI (CRUD)
     */

    private static void manageConcert() {
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nChe cosa vuoi fare?");
                System.out.print(
                        "1.-> Inserisci Concerto\n" +
                                "2.-> Rimuovi Concerto\n" +
                                "3.-> Mostra Concerti\n" +
                                "4.-> Salva Concerti\n" +
                                "5.-> Carica Concerti\n" +
                                "6.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 6);
                switch (choice) {
                    case 1:
                        mostraArtisti();
                        if (artisti.values().size() != 0) {
                            System.out.println("Inserisci NOME ARTISTA di cui vuoi inserire i concerti:");
                            String nomeArtista = in.nextLine();
                            inserisciNuovoConcerto(nomeArtista);
                        }
                        break;
                    case 2:
                        mostraConcerti();
                        if (concerti.values().size() != 0) {
                            System.out.println("Inserisci il CODICE CONCERTO che vuoi rimuovere");
                            int codice = in.nextInt();
                            rimuoviConcerto(codice);
                        }
                        break;
                    case 3:
                        mostraConcerti();
                        break;
                    case 4:
                        saveToFile("Concerti.bin", concerti);
                        System.out.println("Salvato su file Concerti.bin");
                        break;
                    case 5:
                        loadFromFile("Concerti.bin");
                        System.out.println("Caricato da file Concerti.bin");
                        break;
                    case 6:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    public static void inserisciNuovoConcerto(String nomeArtista) {
            for (Artista a : artisti.values()) {
                if (a.getNomeArtista().equals(nomeArtista)) {
                    artistaCorrente = a;
                    try {
                        System.out.println("Nome Concerto: ");
                        String nomeConcerto = in.nextLine();
                        System.out.println("Prezzo Base: ");
                        Float prezzoBase = Float.parseFloat(scanner.next());
                        System.out.println("Codice Concerto: ");
                        int codiceConcerto = in.nextInt();
                        if (creaNuovoConcerto(artistaCorrente, nomeConcerto, prezzoBase, codiceConcerto)) {
                            //------------------------------------------------
                            boolean quit = false;
                            int choice = 0;
                            while (!quit) {
                                System.out.print("Vuoi inserire un evento?\n");
                                System.out.print(
                                        "1.SI\n" +
                                                "2.NO\n" +
                                                "> ");
                                choice = getIntChoice(1, 2);
                                switch (choice) {
                                    case 1:
                                        System.out.println("Nome Evento: ");
                                        String nomeEvento = scan.nextLine();

                                        System.out.println("Codice evento: ");
                                        int codiceEvento = in.nextInt();

                                        System.out.println("Data (Formato yyyy-MM-dd)");
                                        String Data = controllaData();

                                        System.out.println("Orario:");
                                        String ora = controllaOrario();

                                        System.out.println("Location: ");
                                        String location = scan.nextLine();

                                        System.out.println("Numero di biglietti normali per l'evento: ");
                                        int numeroBigliettiNormali = in.nextInt();

                                        System.out.println("Numero di biglietti BS per l'evento: ");
                                        int numeroBigliettiBackstage = in.nextInt();
                                        concertoCorrente.inserisciEvento(nomeEvento, codiceEvento, Data,
                                                ora, location, numeroBigliettiNormali, numeroBigliettiBackstage);
                                        break;
                                    case 2:
                                        quit = true;
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Impossibile inserire l'evento: " +
                                e.getMessage());
                    }
                }
            }   System.out.println("Operazione terminata");
        }


    public static boolean creaNuovoConcerto(Artista artistaCorrente, String nomeConcerto,
                                            float prezzoBase, int codiceConcerto) {
        boolean pippo = true;
        concertoCorrente = new Concerto(artistaCorrente, nomeConcerto, prezzoBase, codiceConcerto);
        if (concerti.containsKey(codiceConcerto)) {
            System.out.println("Errore: esiste già un concerto con questo ID");
            pippo = false;
        } else {
            concerti.put(codiceConcerto, concertoCorrente);
        }
        return pippo;
    }

    public static int getNumeroConcerti() {
        return concerti.values().size();
    }

    public static void rimuoviConcerto(int id_concerto) {
        Concerto theConcert = getConcertoByTrackingID(id_concerto);
        if (theConcert != null) {
            concerti.remove(id_concerto);
            System.out.println("Concerto eliminato con successo!");
        } else {
            System.err.println("Nessun concerto trovato con questo ID.");
        }

    }

    static void mostraConcerti() {
        System.out.println("I Concerti nel sistema sono:\n");
        ArrayList listaArray;
        listaArray = new ArrayList(concerti.values());
        List allEvent = listaArray;
        if (allEvent.isEmpty()) {
            System.out.print("La mappa dei concerti è vuota!\n");
        } else {
            Iterator iter = allEvent.iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
        }
    }

    public static boolean stampaConcertiArtista(int codiceArtista) {
        Boolean giacomino = false;
        for (Entry<Integer, Concerto> conc : concerti.entrySet()) {
            if (conc.getValue().getArtista().getCodiceArtista() == codiceArtista) {
                System.out.println(conc);
                giacomino = true;
            }
        }
        return giacomino;
    }

    private static void manageEvents() { //manca il remove
        boolean quit = false;
        while (!quit) {
            try {
                System.out.println("\nChe cosa vuoi fare?");
                System.out.print(
                        "1.-> Inserisci Evento\n" +
                                "2.-> Rimuovi Evento\n" +
                                "3.-> Mostra Eventi\n" +
                                "4.-> Esci\n" +
                                "> ");
                int choice = getIntChoice(1, 4); // la scelta verrà memorizzata qui
                switch (choice) {
                    case 1:
                        mostraConcerti();
                        if (concerti.values().size() != 0) {
                            System.out.println("Inserisci il CODICE CONCERTO di cui inserire l'evento:");
                            int codiceConcertoDaInserire = in.nextInt();
                            concertoSelezionato = getConcertoByTrackingID(codiceConcertoDaInserire);

                            System.out.println("Nome Evento: ");
                            String nomeEvento = scan.nextLine();

                            System.out.println("Codice evento: ");
                            int codiceEvento = in.nextInt();

                            System.out.println("Data (Formato yyyy-MM-dd)");
                            String Data = controllaData();

                            System.out.println("Orario:");
                            String ora = controllaOrario();

                            System.out.println("Location: ");
                            String location = scan.nextLine();

                            System.out.println("Numero di biglietti normali per l'evento: ");
                            int numeroBigliettiNormali = in.nextInt();

                            System.out.println("Numero di biglietti BackStage per l'evento: ");
                            int numeroBigliettiBackstage = in.nextInt();
                            concertoSelezionato.inserisciEvento(nomeEvento, codiceEvento, Data,
                                    ora, location, numeroBigliettiNormali, numeroBigliettiBackstage);
                        }
                        break;
                    case 2:
                        mostraConcerti();
                        if (concerti.values().size() != 0) {
                            System.out.println("Inserisci il CODICE CONCERTO di cui rimuovere l'evento:");
                            int codiceConcertoDaRimuovere = in.nextInt();
                            concertoSelezionato = getConcertoByTrackingID(codiceConcertoDaRimuovere);
                            if (concertoSelezionato == null) {
                                System.out.println("Non esiste un concerto con questo ID");
                            } else {
                                concertoSelezionato.mostraEventi();
                                if (concertoSelezionato.eventi.values().size() != 0) {
                                    System.out.println("\nInserisci CODICE EVENTO che vuoi rimuovere: ");
                                    int codice = in.nextInt();
                                    concertoSelezionato.rimuoviEvento(codice);
                                }
                            }
                        }
                        break;
                    case 3:
                        mostraConcerti();
                        if (concerti.values().size() != 0) {
                            System.out.println("Inserisci il CODICE CONCERTO di cui mostrare gli eventi:");
                            int codiceConcertoDaMostrare = in.nextInt();
                            concertoSelezionato = getConcertoByTrackingID(codiceConcertoDaMostrare);
                            if (concertoSelezionato == null) {
                                System.out.println("Non ci sono concerti con questo ID");
                            } else {
                                concertoSelezionato.mostraEventi();
                            }
                        }
                        break;
                    case 4:
                        quit = true;
                        break;
                    default:
                        System.err.println("Errore: "
                                + "scelta non riconosciuta");
                }
            } catch (InputMismatchException e) {
                in.next();
                System.out.print("\nPer favore inserisci soltanto numeri\n");
            }
        }
    }

    /**------------------------FINE CONCERT PLANNER--------------------------------- */

    /**
     * RECUPERA L'OGGETTO DALL'ID
     */
    public static Concerto getConcertoByTrackingID(int codice) {
        return (Concerto) concerti.get(codice);
    }

    public static Artista getArtistaByTrackingID(int codice) {
        return (Artista) artisti.get(codice);
    }

    public static Merchandise getMerchandiseByTrackingID(int codice) {
        return (Merchandise) merchandise.get(codice);
    }

    public static Merchandise getMerchandiseVendutiByTrackingID(int codice) {
        return (Merchandise) merchandiseVenduto.get(codice);
    }

    protected static int getIntChoice(int low, int high) {
        boolean goodInput = false;
        int choice = low - 1;
        while (!goodInput) {
            choice = in.nextInt();
            in.nextLine(); //ignore the end-of-line character
            goodInput = choice >= low && choice <= high;
            if (!goodInput) {
                System.out.print("La scelta deve essere un intero da"
                        + " " + low + " a " + high + ".");
                System.out.print("> ");
            }
        }
        return choice;
    }

    public static int generaCodiceMerchadise() {
        int conta = merchandise.values().size();
        if (conta == 0) {
            codiceMerch = conta + 1;
        } else {
            for (Map.Entry<Integer, Merchandise> merch : merchandise.entrySet()) {
                codiceMerch = merch.getValue().getCodiceMerchandise() + 1;
            }
        }
        return codiceMerch;
    }

    /**METODI PER IL CONTROLLO DELLA VALIDITA DI DATA E ORARIO*/
    private static boolean controlloValiditaData(Date d1 , Date d2){
        Boolean validity = null;
        if (d2.compareTo(d1) > 0) {
            validity = true;
        } else if (d2.compareTo(d1) < 0) {
            System.out.println("La data è ormai superata per favore riprovare:");
            validity = false;
        } else if (d2.compareTo(d1) == 0) {
            validity = true;
        }
        return validity;
    }

    protected static String controllaData() {
        boolean goodInput = false;
        Date d1 = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String dataOdierna = dtf.format(now);
        SimpleDateFormat sdformat1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdformat1.parse(dataOdierna);
            d1 = d;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String rightData = "000-00-00";
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        while (!goodInput) {
            String date = scan.nextLine();
            try {
                Date d2 = sdformat.parse(date); // data passata dall'utente
                if (sdformat.format(d2).equals(date)) {
                    if(controlloValiditaData(d1 , d2)){
                        rightData = date;
                        goodInput = true;
                    }
                }
            } catch (ParseException e) {
                //e.printStackTrace();
                System.out.print("Per favore formattare la data"
                        + "in questo modo(yyyy-MM-dd): ");
                System.out.print("> ");
            }
        }
        return rightData;
    }

    protected static String controllaOrario() {
        boolean goodInput = false;
        String rightTime = "";
        SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
        while (!goodInput) {
            System.out.print(">");
            String time = scan.nextLine();
            try {
                Date t = sdformat.parse(time);
                if (sdformat.format(t).equals(time)) {
                    rightTime = time;
                    goodInput = true;
                }
            } catch (ParseException e) {
                //e.printStackTrace();
                System.out.print("Per favore formattare l'orario"
                        + "in questo modo(HH:mm): ");
                System.out.print("> ");
            }
        }
        return rightTime;
    }

    /**
     * SALVA E CARICA DA FILE
     */
    private static void saveToFile(String nomeFile, Map mappaFile) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(nomeFile));
            oos.writeObject(mappaFile);
            //System.out.println("\nDatabase salvato sul file "
                    //+ nomeFile + ".");
        } catch (IOException ioe) {
            System.err.println("Impossibile scrivere sul file"
                    + ": " + ioe.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ioe) {
                    System.err.println("Impossibile chiudere correttamente il file."
                            + "\n" + ioe.getMessage());
                }
            }
        }
    }

    private static void loadFromFile(String nomeFile) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(nomeFile));

            switch (nomeFile) {
                case "Artisti.bin":
                    artisti = (Map<Integer, Artista>) ois.readObject();
                    //System.out.println("Database Artisti caricato");
                    break;
                case "Concerti.bin":
                    concerti = (Map<Integer, Concerto>) ois.readObject();
                    //System.out.println("Database Concerti caricato");
                    break;
                case "Merchandise.bin":
                    merchandise = (Map<Integer, Merchandise>) ois.readObject();
                    //System.out.println("Database Merchandise caricato");
                    break;
                default:
                    System.err.println("Errore: "
                            + "scelta non riconosciuta");
            }

            //System.out.println( "Tutti i Database caricati");
        } catch (IOException ioe) {
            System.err.println("Impossibile caricare le informazioni dal file: "
                    + ioe.getMessage());
        } catch (ClassCastException cce) {
            System.err.println("Backup file invalido.");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("La struttura del file non coincide"
                    + " con la richiesta.");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ioe) {
                    System.err.println("Impossibile chiudere correttamente il file."
                            + "\n" + ioe.getMessage());
                }
            }
        }

    }




}