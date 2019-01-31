package Scenario;

import DB.GestionDB;
import DTO.Adresse;
import DTO.Stock;
import DTO.TypeSupport;

public class DemoScenario {

	public static void main(String[] args) {

		GestionDB.configure();
		System.out.println("------------------------- Scénario 1 ------------------------------");

		// Stock s = GestionDB.createStock(type, qualite, format, quantite, prix);

		Stock tirageSDA4 = GestionDB.getStockById(TypeSupport.TIRAGE, "SD", "A4");
		Adresse ad = GestionDB.getAdresseById(2);
		if (tirageSDA4 != null && ad != null) {
			Thread tFrançois = new Thread(new ScenarioStock("francFran¿¿ois@jooj.hu", "mdpff", tirageSDA4, ad));

			Thread tFrançoise = new Thread(new ScenarioStock("francFran¿¿oise@jooj.hu", "mdpff", tirageSDA4, ad));

			Thread tFranck = new Thread(new ScenarioStock("francFranck@jooj.hu", "mdpff", tirageSDA4, ad));

			tFrançois.start();
			tFrançoise.start();
			tFranck.start();
		}

		System.out.println("------------------------- Scénario 2 ------------------------------");

		// Stock s = GestionDB.createStock(type, qualite, format, quantite, prix);

		Stock cadre = GestionDB.getStockById(TypeSupport.CADRE, "SD", "A3");
		Adresse ad2 = GestionDB.getAdresseById(1);
		if (cadre != null && ad2 != null) {
			Thread tBernard = new Thread(new ScenarioBernard("biancabernard@disney.paris", "mdpbb"));

			Thread tGerard = new Thread(new ScenarioGerard("lasourisgerard@petit.trou", "mdplg", cadre, ad2));

			tBernard.start(); // TODO Bernard t'abord obligatoir non ?
			tGerard.start();
		}
	}

}
