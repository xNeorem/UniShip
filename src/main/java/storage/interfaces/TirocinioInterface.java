package storage.interfaces;

import java.util.ArrayList;
import storage.beans.Azienda;
import storage.beans.Studente;
import storage.beans.Tirocinio;

/*cambiata la signature dei metodi, i retrive prendono come parametri gli oggetti e non
l'identificativo; mentre gli aggiornamenti e inserimenti restituiscono un boolean e non int*/
//il retrieve by key prende come parametro un intero, non una email

public interface TirocinioInterface {

  boolean doChange(Tirocinio tirocinio);

  ArrayList<Tirocinio> doRetrieveByStudente(Studente studente);

  ArrayList<Tirocinio> doRetrieveByAzienda(Azienda azienda);

  Tirocinio doRetrieveByKey(int id);

  boolean doSave(Tirocinio tirocinio);
}
