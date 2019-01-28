package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import DB.GestionDB;

public class SimpleTest {

	private DTO.Client client;

	@Before
	public void avantTest() {
		System.out.println("------------------------");
		// System.out.println("Avant Test");
		GestionDB.configure("ouzzineo", "Oussama123");

		// Initialisation
		this.client = GestionDB.getClientByEmail("jeanChristophe@mail.net");
	}

	@After
	public void apresTest() {
		// System.out.println("Après Test");
		System.out.println("------------------------");
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void connexionBDD() {
		/*
		 * "Client [email=jeanChristophe@mail.net, nom=JEAN, prenom=Christophe," +
		 * "motDePasse=mdpjc, adresses=[243 Rue de la ferme 02120 MarlyGomont FRANCE],"
		 * + "photos=[], photos_partagees=[]," +
		 * "impressions=[Impression [id_impression=33, date_impression=null, nb_impression=1, client=null, "
		 * +
		 * "stock=Stock [type_support=TIRAGE, qualite=HQ, format=A4, prix=2], montant_total=32.0, etat_impression=true, photos=[], commande=null]],"
		 * + "commandes=[DTO.Commande@2df3b89c]]"
		 */
		Assert.assertEquals(this.client, this.client);
	}

	@Test
	public void testClient() {
		Assert.assertEquals("jeanChristophe@mail.net", this.client.getEmail());
		Assert.assertEquals("mdpjc", this.client.getMotDePasse());
		Assert.assertEquals("JEAN", this.client.getNom());
		Assert.assertEquals("Christophe", this.client.getPrenom());

		/*
		 * "Client [email=jeanChristophe@mail.net, nom=JEAN, prenom=Christophe, motDePasse=mdpjc, "
		 * + "adresses=[243 Rue de la ferme 02120 MarlyGomont FRANCE], " +
		 * "photos= , photos_partagees= , impressions= , commandes= ]"
		 */
		Assert.assertEquals(this.client.toString(), this.client.toString());

	}

	@Test
	public void testAdresse() {
		Assert.assertEquals("[243 Rue de la ferme 02120 MarlyGomont FRANCE]", this.client.getAdresse().toString());
	}

	@Test
	public void testPhoto() {
		// this.est_partage ? "Partag�e; " : "Perso; " + "chemin=" + chemin + "
		// resolution=" + resolution + " date_ajout=" + date_ajout;
		Assert.assertEquals(this.client.getPhotos(), this.client.getPhotos());
	}

	@Test
	public void testImpressions() {
		// "Impression [id_impression=, date_impression=, nb_impression= , client= ,
		// stock= , montant_total= , etat_impression= , photos= , commande= ]"
		Assert.assertEquals(this.client.getImpressions().toString(), this.client.getImpressions().toString());
	}

}
