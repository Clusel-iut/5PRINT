package DB;

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

		Thread tFrançois = new Thread(
				new ScenarioStock("francFrançois@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));

		Thread tFrançoise = new Thread(
				new ScenarioStock("francFrançoise@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));

		Thread tFranck = new Thread(
				new ScenarioStock("francFranck@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));

		tFrançois.start();
		tFrançoise.start();
		tFranck.start();

		System.out.println("------------------------- Scénario 2 ------------------------------");

		// Stock s = GestionDB.createStock(type, qualite, format, quantite, prix);

		Stock cadre = GestionDB.getStockById(TypeSupport.CADRE, "SD", "A4");
		Adresse ad2 = GestionDB.getAdresseById(1);

		Thread tBernard = new Thread(
				new ScenarioStock("biancabernard@disney.paris", "mdpbb", cadre, ad2, TypeSupport.CADRE));

		Thread tGerard = new Thread(
				new ScenarioStock("lasourisgerard@petit.trou", "mdplg", cadre, ad2, TypeSupport.CADRE));

		tBernard.start(); // TODO Bernard t'abord obligatoir non ?
		tGerard.start();

	}

}
