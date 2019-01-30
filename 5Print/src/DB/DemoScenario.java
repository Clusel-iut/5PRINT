package DB;

import DTO.Adresse;
import DTO.Stock;
import DTO.TypeSupport;

public class DemoScenario {

	public static void main(String[] args) {
		
		GestionDB.configure();
		System.out.println("-------------------------------------------------------");

		//Stock s = GestionDB.createStock(type, qualite, format, quantite, prix);
		
		Stock tirageSDA4 = GestionDB.getStockById(TypeSupport.TIRAGE, "SD", "A4");
		Adresse ad = GestionDB.getAdresseById(2);

		Thread tFrançois = new Thread(
				new ScenarioStock("francFrançois@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));

		Thread tFrançoise = new Thread(
				new ScenarioStock("francFrançoise@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));

		Thread tFranck = new Thread(new ScenarioStock("francFranck@jooj.hu", "mdpff", tirageSDA4, ad, TypeSupport.TIRAGE));
		
		tFrançois.start();
		tFrançoise.start();
		tFranck.start();
		
	}

}
