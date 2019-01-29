package DB;

import java.util.ArrayList;
import java.util.Iterator;

import DTO.PointRelais;
import DTO.Stock;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GestionDB.configure();

		ArrayList<Stock> stocks = GestionDB.getAllStock();
		Iterator<Stock> it = stocks.iterator();
		Stock s = null;
		while (it.hasNext()) {
			s = it.next();
			System.out.println(s.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		ArrayList<PointRelais> adsPointRelais = GestionDB.getAllPointRelais();
		Iterator<PointRelais> it2 = adsPointRelais.iterator();
		PointRelais ptR = null;
		while (it2.hasNext()) {
			ptR = it2.next();
			System.out.println(ptR.toString());
		}
		
		System.out.println("-------------------------------------------------------");
		
		//GestionDB
		
	}

}