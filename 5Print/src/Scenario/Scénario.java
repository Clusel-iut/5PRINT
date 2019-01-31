package Scenario;

import java.util.ArrayList;
import java.util.Iterator;

import DB.GestionDB;
import DTO.Client;
import DTO.StatutCommande;
import DTO.Stock;
import DTO.TypeSupport;

public class Scénario {

	public static void main(String[] args) {

		/*
		 * François, Françoise et Franck vont en même temps au PhotoNum. 
		 * Chacun commande une impression tirage. Chacun sélectionne un format A4 normal. 
		 * Chacun sélectionne 20 feuilles pour son tirage.
		 * Chacun confirme en même temps sa commande. François reçoit confirmation de sa commande. 
		 * Il sélectionne une adresse de livraison et finalise sa commande.
		 * Françoise et Franck voient que le stock n’est plus valide.
		 * Françoise et Franck rentrent chez eux. 
		 * 
		 * Commentaire sur ce scénario :
		 * Le stock étant de 30 feuilles, qu’une seule commande ne devra être
		 * imprimée. Le problème est que chacun voit un stock suffisant pour
		 * leur impression.
		 * 
		 * 
		 * INTO CLIENT (EMAIL, NOM, PRENOM, MOT_DE_PASSE) VALUES ('francFrançois@jooj.hu', 'FRANC', 'François', 'mdpff')
		 * INTO CLIENT (EMAIL, NOM, PRENOM, MOT_DE_PASSE) VALUES ('francFrançoise@jooj.hu', 'FRANC', 'Françoise', 'mdpff')
		 * INTO CLIENT (EMAIL, NOM, PRENOM, MOT_DE_PASSE) VALUES ('francFranck@jooj.hu', 'FRANC', 'Franck', 'mdpff')
		 */

		GestionDB.configure();
		
		System.out.println("-------------------------------------------------------");
		
		// THREAD POUR CONCURENCE
		
		GestionDB.connectionClient("francFrançois@jooj.hu", "mdpff"); // return boolean isCon
		GestionDB.connectionClient("francFrançoise@jooj.hu", "mdpff");
		GestionDB.connectionClient("francFranck@jooj.hu", "mdpff");
		
		Stock tirageSDA4 = GestionDB.getStockById(TypeSupport.TIRAGE, "SD", "A4");
		
		Client françois = GestionDB.getClientByEmail("francFrançois@jooj.hu");
		Client françoise = GestionDB.getClientByEmail("francFrançoise@jooj.hu");
		Client franck = GestionDB.getClientByEmail("francFrançois@jooj.hu");
		
		GestionDB.createImpression(TypeSupport.TIRAGE, françois, tirageSDA4, 0, 0, false, 0, null, null, null);
		GestionDB.createImpression(TypeSupport.TIRAGE, françoise, tirageSDA4, 0, 0, false, 0, null, null, null);
		GestionDB.createImpression(TypeSupport.TIRAGE, franck, tirageSDA4, 0, 0, false, 0, null, null, null);
		
		
		
		GestionDB.createCommande(0, "francFrançois@jooj.hu", null, StatutCommande.En_cours, false, 0);
		GestionDB.createCommande(0, "francFrançoise@jooj.hu", null, StatutCommande.En_cours, false, 0);
		GestionDB.createCommande(0, "francFranck@jooj.hu", null, StatutCommande.En_cours, false, 0);
		
	}

}
