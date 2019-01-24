package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DTO.Adresse;
import DTO.Agenda;
import DTO.Album;
import DTO.BonAchat;
import DTO.Cadre;
import DTO.Calendrier;
import DTO.Catalogue;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Impression;
import DTO.Photo;
import DTO.PointRelais;
import DTO.Stock;
import DTO.Stock.Type;
import DTO.Tirage;

public class GestionDB {

	public static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	public static Connection conn;

	public static void configure(String user, String passwd) {
		try {
			System.out.println("gege");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("gege");
			conn = DriverManager.getConnection(CONN_URL, user, passwd);
			System.out.println("gege");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//
	// ADRESSE
	//
	// GET
	public static Adresse getAdresseById(int id) {
		String sql = "SELECT * FROM ADRESSE WHERE ID_ADRESSE = ?";
		Adresse adr = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, Integer.toString(id));
			ResultSet result = statement.executeQuery(sql);

			Client client = null;
			if (result.getString("EMAIL") != null) {
				client = getClientByEmail(result.getString("EMAIL"));
			}
			adr = new Adresse(result.getInt("ID_ADRESSE"), result.getString("PAYS"), result.getString("VILLE"),
					result.getString("CODE_POSTAL"), result.getString("RUE"), result.getString("NUMERO"), client);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adr;
	}

	// CREATE
	public static boolean createAdresse(String pays, String email, String ville, String code_postal, String rue,
			String numero) {
		String sql = "INSERT INTO ADRESSE (PAYS, EMAIL, VILLE, CODE_POSTAL, RUE, NUMERO) VALUES (?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, pays);
			statement.setString(2, email);
			statement.setString(3, ville);
			statement.setString(4, code_postal);
			statement.setString(5, rue);
			statement.setString(6, numero);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean updateAdresse(Adresse adresse) {
		String sql = "UPDATE ADRESSE SET PAYS = ?, VILLE = ?, CODE_POSTAL = ?, RUE = ?, NUMERO = ? WHERE ID_ADRESSE = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, adresse.getPays());
			statement.setString(2, adresse.getVille());
			statement.setString(3, adresse.getCode_postal());
			statement.setString(4, adresse.getRue());
			statement.setString(5, adresse.getNumero());
			statement.setInt(6, adresse.getId_adresse());

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteAdresseById(int id) {
		String sql = "DELETE FROM ADRESSE WHERE ID_ADRESSE = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// Client
	//
	// GET
	public static Client getClientByEmail(String email) {
		String sql = "SELECT * FROM CLIENT WHERE EMAIL = ?";
		Client cli = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			ResultSet result = statement.executeQuery(sql);

			cli = new Client(result.getString("EMAIL"), result.getString("NOM"), result.getString("PRENOM"),
					getAllAdresseByClientId(email), result.getString("MOT_DE_PASSE"), getAllPhotosByClientId(email),
					getAllPhotosPartageesByClientId(email), getAllImpressionsByClientId(email));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cli;
	}

	private static <T extends Impression> ArrayList<Impression> getAllImpressionsByClientId(String email) {
		ArrayList<Impression> impressions = new ArrayList<Impression>();
		String sql = "SELECT ID_IMPRESSION FROM IMPRESSION WHERE EMAIL = " + email;
		PreparedStatement statement;
		T impression;
		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				impression = getImpressionById(result.getInt("ID_IMPRESSION"));
				if(impression != null) {
					impressions.add(impression);
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static ArrayList<FichierPhoto> getAllPhotosPartageesByClientId(String email) {
		ArrayList<FichierPhoto> photos = new ArrayList<FichierPhoto>();
		String sql = "SELECT CHEMIN FROM PARTAGE WHERE EMAIL = " + email;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				photos.add(getFichierPhotoById(result.getString("CHEMIN")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return photos;
	}

	private static ArrayList<FichierPhoto> getAllPhotosByClientId(String email) {
		ArrayList<FichierPhoto> photos = new ArrayList<FichierPhoto>();
		String sql = "SELECT CHEMIN FROM FICHIERPHOTO WHERE EMAIL = " + email;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				photos.add(getFichierPhotoById(result.getString("CHEMIN")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return photos;
	}

	private static ArrayList<Adresse> getAllAdresseByClientId(String email) {
		ArrayList<Adresse> adresses = new ArrayList<Adresse>();
		String sql = "SELECT ID_ADRESSE FROM ADRESSE WHERE EMAIL = " + email;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				adresses.add(getAdresseById(result.getInt("ID_ADRESSE")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return adresses;
	}

	// CREATE
	public static boolean createClient(String email, String nom, String prenom, String motdepasse) {
		String sql = "INSERT INTO CLIENT (EMAIL, NOM, PRENOM, MOT_DE_PASSE) VALUES (?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, nom);
			statement.setString(3, prenom);
			statement.setString(4, motdepasse);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	public static boolean createClient(Client client) {
		boolean isAddedC = createClient(client.getEmail(), client.getNom(), client.getPrenom(), client.getMotDePasse());
		for (Adresse a : client.getAdresse()) {
			createAdresse(a.getPays(), client.getEmail(), a.getVille(), a.getCode_postal(), a.getRue(), a.getNumero());
		}
		return isAddedC;
	}

	// UPDATE
	public static boolean updateClient(Client client) {
		String sql = "UPDATE CLIENT SET NOM = ?, PRENOM = ?, MOT_DE_PASSE = ? WHERE EMAIL = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, client.getNom());
			statement.setString(2, client.getPrenom());
			statement.setString(3, client.getMotDePasse());
			statement.setString(4, client.getEmail());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteClientByEmail(String email) {
		String sql = "DELETE FROM CLIENT WHERE EMAIL = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, email);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	// TODO CONNECTION????????
	public static boolean connectionClient(String email, String motDePasse) {
		String sql = "SELECT * FROM CLIENT WHERE EMAIL = ? AND MOT_DE_PASSE = ?";
		boolean isConnected = false;
		

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, motDePasse);

			ResultSet result = statement.executeQuery();
			if (result.next()) {
				isConnected = true;

			}
		} catch (SQLException e) {
			isConnected = false;

		}

		return isConnected;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// Commande
	//
	// GET
	public static Commande getCommandeById(int id) {
		String sql = "SELECT * FROM COMMANDE WHERE NUMERO = ?";
		Commande cmd = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(sql);

			BonAchat bon_achat = null;
			BonAchat bon_achat_genere = null;
			Client client = getClientByEmail(result.getString("EMAIL"));
			Adresse adresse = getAdresseById(result.getInt("ID_ADRESSE"));

			if (result.getString("CODE_BON") != null) {
				bon_achat = getBonAchatById(result.getString("CODE_BON"));
			}
			if (result.getString("CODE_BON_GENERE") != null) {
				bon_achat = getBonAchatById(result.getString("CODE_BON_GENERE"));
			}

			cmd = new Commande(result.getInt("NUMERO"), bon_achat, bon_achat_genere, adresse, client,
					result.getString("MODE_LIVRAISON"), result.getDate("DATE_COMMANDE"), result.getString("STATUT"),
					result.getBoolean("ETAT_PAIEMENT"), result.getFloat("MONTANT_TOTAL_CMD"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cmd;
	}

	// CREATE
	public static boolean createCommande(int adresse, String email, String mode_livraison, Date date_commande,
			String statut, boolean etat_paiement, float montant_total_cmd) {
		String sql = "INSERT INTO COMMANDE (ID_ADRESSE, EMAIL, MODE_LIVRAISON, DATE_COMMANDE, STATUT, ETAT_PAIEMENT, MONTANT_TOTAL_CMD) VALUES (?,?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, adresse);
			statement.setString(2, email);
			statement.setString(3, mode_livraison);
			statement.setDate(4, java.sql.Date.valueOf(date_commande.toString()));
			statement.setString(5, statut);
			statement.setBoolean(6, etat_paiement);
			statement.setFloat(7, montant_total_cmd);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean updateCommande(Commande commande) {
		String sql = "UPDATE COMMANDE SET ID_ADRESSE = ?, CODE_BON = ?, MODE_LIVRAISON = ?, STATUT = ?, ETAT_PAIEMENT = ?, MONTANT_TOTAL_CMD =? WHERE NUMERO = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, commande.getAdresse().getId_adresse());
			statement.setString(2, commande.getBon_achat().getCode_bon());
			statement.setString(3, commande.getMode_livraison());
			statement.setString(4, commande.getStatut());
			statement.setBoolean(5, commande.getEtat_paiement());
			statement.setFloat(6, commande.getMontant_total_cmd());
			statement.setInt(6, commande.getNumero());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteCommande(int numero) {
		String sql = "DELETE FROM COMMANDE WHERE NUMERO = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, numero);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// FichierPhoto
	//
	// GET
	public static FichierPhoto getFichierPhotoById(String chemin) {
		String sql = "SELECT * FROM FICHIERPHOTO WHERE CHEMIN = ?";
		FichierPhoto fiPhoto = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);
			ResultSet result = statement.executeQuery(sql);
			fiPhoto = new FichierPhoto(result.getString("CHEMIN"), getClientByEmail(result.getString("EMAIL")),
					result.getString("RESOLUTION"), result.getDate("DATE_AJOUT"),result.getDate("DATE_NO_PHOTO"),result.getString("INFO_PRISE_VUE"),
					result.getBoolean("EST_PARTAGE"), getClientsByFichierId(chemin), getPhotoByFichierId(chemin));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fiPhoto;
	}

	private static ArrayList<Photo> getPhotoByFichierId(String chemin) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		String sql = "SELECT ID_PHOTO FROM PHOTO WHERE CHEMIN = " + chemin;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				photos.add(getPhotoById(result.getInt("ID_PHOTO")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return photos;
	}

	private static ArrayList<Client> getClientsByFichierId(String chemin) {
		ArrayList<Client> clients = new ArrayList<Client>();
		String sql = "SELECT EMAIL FROM PARTAGE " + "WHERE CHEMIN = " + chemin;
		PreparedStatement statement;

		try {
			statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				clients.add(getClientByEmail(result.getString("EMAIL")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	// CREATE
	public static boolean createFichierPhoto(String chemin, String email, String resolution, String info_vue, boolean est_partage) {
		String sql = "INSERT INTO FICHIERPHOTO (CHEMIN, EMAIL, RESOLUTION, DATE_AJOUT, DATE_NO_PHOTO, INFO_PRISE_VUE, EST_PARTAGE) VALUES (?,?,?,?,?,?,?)";
		boolean isAdded = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date_no_photo = new Date();
		Date date_ajout = new Date();
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);
			statement.setString(2, email);
			statement.setString(3, resolution);
			statement.setDate(4, java.sql.Date.valueOf(dateFormat.format(date_ajout)));
			statement.setDate(5, java.sql.Date.valueOf(dateFormat.format(date_no_photo)));
			statement.setString(6, info_vue);
			statement.setBoolean(7, est_partage);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean updateFichierPhoto(FichierPhoto fiPhoto) {
		String sql = "UPDATE FICHIERPHOTO SET RESOLUTION = ?, INFO_PRISE_VUE = ?, EST_PARTAGE = ? WHERE CHEMIN = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, fiPhoto.getResolution());
			statement.setString(2, fiPhoto.getInfo_prise_de_vue());
			statement.setBoolean(3, fiPhoto.getEst_partage());
			statement.setString(4, fiPhoto.getChemin());
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteFichierPhoto(String chemin) {
		String sql = "DELETE FICHIERPHOTO WHERE CHEMIN = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// PHOTO
	//
	// GET
	public static Photo getPhotoById(int id) {
		String sql = "SELECT * FROM PHOTO WHERE ID_PHOTO = ?";
		Photo photo = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(sql);
			photo = new Photo(result.getInt("ID_PHOTO"), getFichierPhotoById(result.getString("CHEMIN")), getImpressionById(result.getInt("ID_IMPRESSION")), result.getString("DESCRIPTION"),
					result.getString("RETOUCHE"), result.getInt("NUMERO_PAGE"), result.getInt("POSITION_X"),
					result.getInt("POSITION_Y"), result.getInt("NB_EXEMPLAIRE"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photo;
	}

	private static <T extends Impression> T getImpressionById(int id) {
		T impression = null;
		String[] types = { "AGENDA", "ALBUM", "CADRE", "CALENDRIER", "TIRAGE" };
		String select = "SELECT ID_IMPRESSION FROM";
		String where = "WHERE ID_IMPRESSION = " + id;
		String sql = "";
		PreparedStatement statement;

		try {
			ResultSet result = null;
			int cpt = 0;
			while (result == null && cpt < 5) {
				sql = select + types[cpt] + where;
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery(sql);
				cpt++;

			}
			if (cpt < 5) {
				impression = getImpressionByIdAndType(types[cpt], id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return impression;
	}


	// CREATE
	public static boolean createPhoto(String chemin, Impression impression, String description, int position_x,
			int position_y, int numero_page, int nb_exemplaire, String retouche) {
		String sql = "INSERT INTO PHOTO (CHEMIN, ID_IMPRESSION, DESCRIPTION, RETOUCHE, POSITION_X, POSITION_Y, NUMERO_PAGE, NB_EXEMPLAIRE) VALUES (?,?,?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);
			statement.setString(2, description);
			statement.setString(3, retouche);
			statement.setInt(4, position_x);
			statement.setInt(5, position_y);
			statement.setInt(5, numero_page);
			statement.setInt(6, nb_exemplaire);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				sql = "UPDATE FICHIERPHOTO SET DATE_NO_PHOTO = null WHERE CHEMIN = " + chemin;
				statement = conn.prepareStatement(sql);
				statement.executeUpdate();
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean updatePhoto(Photo photo) {
		String sql = "UPDATE PHOTO SET DESCRIPTION = ?, RETOUCHE = ?, POSITION_X = ?, POSITION_Y = ?, NB_EXEMPLAIRE = ? WHERE ID_PHOTO = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, photo.getDescription());
			statement.setString(2, photo.getRetouche());
			statement.setInt(3, photo.getPosition_X());
			statement.setInt(4, photo.getPosition_Y());
			statement.setInt(5, photo.getNb_exemplaire());
			statement.setInt(5, photo.getId_photo());
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deletePhoto(int id_photo) {
		String sql = "DELETE PHOTO WHERE ID_PHOTO = ?";
		String sqlChemin = "SELECT CHEMIN FROM PHOTO WHERE ID_PHOTO = " + id_photo;
		boolean isDeleted = false;
		String chemin;
		try {
			PreparedStatement statementChemin = conn.prepareStatement(sqlChemin);
			ResultSet resultChemin = statementChemin.executeQuery();
			chemin = resultChemin.getString("CHEMIN");
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id_photo);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
			if(getPhotoByFichierId(chemin).size() == 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();
				String sqlUpdate = "UPDATE FICHIERPHOTO SET DATE_NO_PHOTO = " + java.sql.Date.valueOf(dateFormat.format(date)) + "WHERE CHEMIN = " + chemin;
				PreparedStatement statementUpdate = conn.prepareStatement(sqlUpdate);
				statementUpdate.executeUpdate(sqlUpdate);
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	//
	// PointRelais
	//
	// GET ALL
	public static ArrayList<PointRelais> getAllPointRelais() {
		String sql = "SELECT * FROM POINT_RELAIS";
		ArrayList<PointRelais> list_point_relais = new ArrayList<PointRelais>();

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				list_point_relais.add(new PointRelais(result.getString("NOM"),
						GestionDB.getAdresseById(result.getInt("ID_ADRESSE"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list_point_relais;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// IMPRESSIONS
	//
	// GET

	@SuppressWarnings("unchecked")
	public static <T extends Impression> T getImpressionByIdAndType(String type, int id) {
		type = type.toUpperCase();
		String sqlImp = "SELECT * FROM IMPRESSION WHERE ID_IMPRESSION = ?";
		String sqlT = "SELECT * FROM " + type + " WHERE ID_IMPRESSION = ?";
		T t = null;

		try {
			// REQUETE TIRAGE POUR VERIFIER SI EXISTE
			PreparedStatement statementTirage = conn.prepareStatement(sqlT);
			statementTirage.setInt(1, id);
			ResultSet resultT = statementTirage.executeQuery(sqlT);

			int idT = resultT.getInt("ID_IMPRESSION");

			if (resultT.wasNull()) {
				// REQUETE IMPRESSION POUR RECUP DONNEES
				PreparedStatement statementImp = conn.prepareStatement(sqlImp);
				statementImp.setInt(1, idT);
				ResultSet resultImp = statementImp.executeQuery(sqlImp);

				int nb_impression = resultImp.getInt("NB_IMPRESSION");
				int montant_total = resultImp.getInt("MONTANT_TOTAL");
				boolean etat_impression = resultImp.getBoolean("ETAT_IMPRESSION");
				Date date_impression = resultImp.getDate("DATE_IMPRESSION");
				int numero = resultImp.getInt("NUMERO");
				Client client = getClientByEmail(resultImp.getString("EMAIL"));
				Commande commande = getCommandeById(resultImp.getInt("NUMERO"));
				Stock stock = getStockById(Type.valueOf(resultImp.getString("TYPE_SUPPORT")),resultImp.getString("QUALITE"),resultImp.getString("FORMAT"));
				
				if (type == "AGENDA") {
					t = (T) new Agenda(idT, date_impression, nb_impression, client, stock, numero, montant_total,
							etat_impression, getAllPhotoByIdImpression(idT),commande, resultT.getString("MODELE"));
				}
				if (type == "ALBUM") {
					t = (T) new Album(idT, date_impression, nb_impression, client, stock, numero, montant_total,
							etat_impression, getAllPhotoByIdImpression(idT),commande,  resultT.getString("TITRE"), resultT.getString("MISE_EN_PAGE"));
				}
				if (type == "CADRE") {
					t = (T) new Cadre(idT, date_impression, nb_impression, client, stock, numero, montant_total,
							etat_impression, getAllPhotoByIdImpression(idT),commande, resultT.getString("MISE_EN_PAGE"), resultT.getString("MODELE"));
				}
				if (type == "CALENDRIER") {
					t = (T) new Calendrier(idT, date_impression, nb_impression, client, stock, numero, montant_total,
							etat_impression, getAllPhotoByIdImpression(idT),commande,  resultT.getString("MODELE"));
				}
				if (type == "TIRAGE") {
					t = (T) new Tirage(idT, date_impression, nb_impression, client, stock, numero, montant_total,
							etat_impression, getAllPhotoByIdImpression(idT), commande);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return t;
	}

	private static ArrayList<Photo> getAllPhotoByIdImpression(int idT) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		String sql = "SELECT ID_PHOTO FROM PHOTO WHERE ID_IMPRESSION = " + idT;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet result = statement.executeQuery(sql);

			while (result.next()) {
				photos.add(getPhotoById(result.getInt("ID_PHOTO")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photos;
	}

	// CREATE
	public static boolean createImpression(String type, int id_impression, Client client, Catalogue catalogue,
			int numero, Date date_impression, float montant_total, boolean etat_impression, int nb_impression,
			String modele, String titre, String mise_en_page) {
		type = type.toUpperCase();
		String sqlImp = "INSERT INTO IMPRESSION(ID_IMPRESSION, EMAIL, FORMAT, QUALITE, NUMERO, DATE_IMPRESSION, MONTANT_TOTAL, ETAT_IMPRESSION, NB_IMPRESSION) "
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		String sqlImpExt = "";
		boolean isAdded = false;

		PreparedStatement statementImp;
		PreparedStatement statementImpExt;
		try {
			statementImp = conn.prepareStatement(sqlImp);
			statementImp.setInt(1, id_impression);
			statementImp.setString(2, client.getEmail());
			statementImp.setString(3, catalogue.getFormat());
			statementImp.setString(4, catalogue.getQualite());
			statementImp.setInt(5, numero);
			statementImp.setDate(6, java.sql.Date.valueOf(date_impression.toString()));
			statementImp.setFloat(7, montant_total);
			statementImp.setBoolean(8, etat_impression);
			statementImp.setInt(9, nb_impression);

			int rowsInsertedImp = 0;
			int rowsInsertedTirage = 0;

			if (type == "AGENDA") {
				sqlImpExt = "INSERT INTO AGENDA (ID_IMPRESSION, MODELE) " + "VALUES (?,?)";
				statementImpExt = conn.prepareStatement(sqlImpExt);
				statementImpExt.setInt(1, id_impression);
				statementImpExt.setString(2, modele);

				rowsInsertedImp = statementImp.executeUpdate();
				rowsInsertedTirage = statementImpExt.executeUpdate();
			}
			if (type == "ALBUM") {
				sqlImpExt = "INSERT INTO ALBUM (ID_IMPRESSION, TITRE, MISE_EN_PAGE) " + "VALUES (?,?,?)";
				statementImpExt = conn.prepareStatement(sqlImpExt);
				statementImpExt.setInt(1, id_impression);
				statementImpExt.setString(2, titre);
				statementImpExt.setString(3, mise_en_page);

				rowsInsertedImp = statementImp.executeUpdate();
				rowsInsertedTirage = statementImpExt.executeUpdate();
			}
			if (type == "CADRE") {
				sqlImpExt = "INSERT INTO CADRE (ID_IMPRESSION, MISE_EN_PAGE, MODELE)" + " VALUES (?,?,?)";
				statementImpExt = conn.prepareStatement(sqlImpExt);
				statementImpExt.setInt(1, id_impression);
				statementImpExt.setString(2, mise_en_page);
				statementImpExt.setString(3, modele);

				rowsInsertedImp = statementImp.executeUpdate();
				rowsInsertedTirage = statementImpExt.executeUpdate();
			}
			if (type == "CALENDRIER") {
				sqlImpExt = "INSERT INTO CALENDRIER (ID_IMPRESSION, MODELE) " + "VALUES (?,?)";
				statementImpExt = conn.prepareStatement(sqlImpExt);
				statementImpExt.setInt(1, id_impression);
				statementImpExt.setString(2, modele);

				rowsInsertedImp = statementImp.executeUpdate();
				rowsInsertedTirage = statementImpExt.executeUpdate();
			}
			if (type == "TIRAGE") {
				sqlImpExt = "INSERT INTO TIRAGE (ID_IMPRESSION)" + " VALUES (?)";
				statementImpExt = conn.prepareStatement(sqlImpExt);
				statementImpExt.setInt(1, id_impression);

				rowsInsertedImp = statementImp.executeUpdate();
				rowsInsertedTirage = statementImpExt.executeUpdate();
			}

			if (rowsInsertedImp > 0 && rowsInsertedTirage > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static <T extends Impression> boolean updateImpression(String type, T impression) {
		type = type.toUpperCase();
		String sqlImp = "UPDATE IMPRESSION SET FORMAT = ?, QUALITE = ?, NUMERO = ?, MONTANT_TOTAL = ?, ETAT_IMPRESSION = ?, NB_IMPRESSION = ? "
				+ "WHERE ID_IMPRESSION = ?";
		String sqlImpExt = "";
		boolean isUpdated = false;

		PreparedStatement statement;
		PreparedStatement statementImp;
		try {
			statement = conn.prepareStatement(sqlImp);
			statement.setString(1, impression.getCatalogue().getFormat());
			statement.setString(2, impression.getCatalogue().getQualite());
			statement.setInt(3, impression.getCommande().getNumero());
			statement.setFloat(4, impression.getMontant_total());
			statement.setBoolean(5, impression.getEtat_impression());
			statement.setInt(6, impression.getNb_impression());
			statement.setInt(7, impression.getId_impression());

			int rowsInsertedImpEx = 0;
			int rowsInsertedImp = 0;

			if (type == "AGENDA") {
				Agenda agenda = (Agenda) impression;
				sqlImpExt = "UPDATE INTO AGENDA SET MODELE = ? " + "WHERE ID_IMPRESSION = ?";
				statementImp = conn.prepareStatement(sqlImpExt);
				statementImp.setString(1, agenda.getModele());
				statementImp.setInt(2, agenda.getId_impression());

				rowsInsertedImpEx = statementImp.executeUpdate();

			}
			if (type == "ALBUM") {
				Album album = (Album) impression;
				sqlImpExt = "UPDATE INTO ALBUM SET MODELE = ?, MISE_EN_PAGE = ? " + "WHERE ID_IMPRESSION = ?";
				statementImp = conn.prepareStatement(sqlImpExt);
				statementImp.setString(1, album.getTitre());
				statementImp.setString(2, album.getMise_en_page());
				statementImp.setInt(3, album.getId_impression());

				rowsInsertedImpEx = statementImp.executeUpdate();
			}
			if (type == "CADRE") {
				Cadre cadre = (Cadre) impression;
				sqlImpExt = "UPDATE INTO CADRE SET MISE_EN_PAGE = ?, MODELE = ? " + "WHERE ID_IMPRESSION = ?";
				statementImp = conn.prepareStatement(sqlImpExt);
				statementImp.setString(1, cadre.getMise_en_page());
				statementImp.setString(2, cadre.getModele());
				statementImp.setInt(3, cadre.getId_impression());

				rowsInsertedImpEx = statementImp.executeUpdate();
			}
			if (type == "CALENDRIER") {
				Calendrier calendrier = (Calendrier) impression;
				sqlImpExt = "UPDATE INTO CALENDRIER SET MODELE = ?" + " WHERE ID_IMPRESSION = ?";
				statementImp = conn.prepareStatement(sqlImpExt);
				statementImp.setString(1, calendrier.getModele());
				statementImp.setInt(2, calendrier.getId_impression());

				rowsInsertedImpEx = statementImp.executeUpdate();
			}
			if (type == "TIRAGE") {
				// RIEN A UPDATE
			}

			rowsInsertedImp = statement.executeUpdate();
			if (rowsInsertedImp > 0 && rowsInsertedImpEx > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteImpression(int id, String type) {
		String sqlImp = "DELETE IMPRESSION WHERE ID_IMPRESSION = ?";
		String sqlImpExt = "DELETE " + type.toUpperCase() + " WHERE ID_IMPRESSION = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statementImp = conn.prepareStatement(sqlImp);
			PreparedStatement statementImpExt = conn.prepareStatement(sqlImpExt);

			statementImp.setInt(1, id);
			statementImpExt.setInt(2, id);

			int rowsDeletedImp = statementImp.executeUpdate();
			int rowsDeletedImpExt = statementImpExt.executeUpdate();
			if (rowsDeletedImp > 0 && rowsDeletedImpExt > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// BON_ACHAT
	//
	// GET
	public static BonAchat getBonAchatById(String id) {
		String sql = "SELECT * FROM BON_ACHAT WHERE CODE_BON = ?";
		BonAchat bon_achat = null;
		Commande commande = null;
		Commande commandeG = null;
		Client client = null;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet result = statement.executeQuery(sql);
			if(result.getInt("NUMERO") >= 0) {
				commande = getCommandeById(result.getInt("NUMERO"));
			}
			if(result.getInt("NUMERO_GENERE") >= 0) {
				commandeG = getCommandeById(result.getInt("NUMERO_GENERE"));
			}
			if(result.getString("EMAIL") != null) {
				client = getClientByEmail(result.getString("EMAIL"));
			}
			bon_achat = new BonAchat(result.getString("CODE_BON"), commande, commandeG, client, result.getInt("POURCENTAGEREDUC"),
					result.getString("TYPE_BONACHAT"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bon_achat;
	}

	// CREATE
	public static boolean createBonAchat(int numeroC, int numeroCGenere, String email, int pourcentage,
			String type_bon_achat) {
		String sql = "INSERT INTO BON_ACHAT (NUMERO, NUMERO_GENERE, EMAIL, POURCENTAGEREDUC, TYPE_BONACHAT) VALUES (?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, numeroC);
			statement.setInt(2, numeroCGenere);
			statement.setString(3, email);
			statement.setInt(4, pourcentage);
			statement.setString(5, type_bon_achat);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean upadateBonAchat(BonAchat bon) {
		String sql = "UPDATE BON_ACHAT SET NUMERO = ?, NUMERO_GENERE = ?, EMAIL = ?, POURCENTAGEREDUC = ?, TYPE_BONACHAT = ? WHERE CODE_BON = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, bon.getCommande().getNumero());
			statement.setInt(2, bon.getCommandeGeneree().getNumero());
			statement.setString(3, bon.getClient().getEmail());
			statement.setInt(4, bon.getPourcentage_reduc());
			statement.setString(5, bon.getType_bon_achat());
			statement.setString(5, bon.getCode_bon());
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteBonAchat(String code_bon) {
		String sql = "DELETE BON_ACHAT WHERE CODE_BON = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, code_bon);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// STOCK
	//
	// GET
	public static Stock getStockById(Type type, String qualite, String format) {
		String sql = "SELECT * FROM STOCK WHERE TYPE_SUPPORT = ? AND QUALITE = ? AND FORMAT = ?";
		Stock stock = null;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, type.name());
			statement.setString(2, qualite);
			statement.setString(3, format);
			ResultSet result = statement.executeQuery(sql);
			stock = new Stock(Type.valueOf(result.getString("TYPE_SUPPORT")), result.getString("QUALITE"),
					result.getString("FORMAT"), result.getInt("QUANTITE"), result.getInt("PRIX"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stock;
	}

	// CREATE
	public static boolean createStock(Type type, String qualite, String format, int quantite, int prix) {
		String sql = "INSERT INTO STOCK (TYPE_SUPPORT, QUALITE, FORMAT, QUANTITE, PRIX) VALUES (?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, type.name());
			statement.setString(2, qualite);
			statement.setString(3, format);
			statement.setInt(4, quantite);
			statement.setInt(5, prix);

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isAdded = true;
			}

		} catch (SQLException e) {
			isAdded = false;
		}

		return isAdded;
	}

	// UPDATE
	public static boolean updateStock(Stock stock) {
		String sql = "UPDATE STOCK SET QUANTITE = ?, PRIX = ? WHERE TYPE_SUPPORT = ?, QUALITE = ?, FORMAT = ?";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, stock.getQuantite());
			statement.setInt(2, stock.getPrix());
			statement.setString(3, stock.getType_support().name());
			statement.setString(4, stock.getQualite());
			statement.setString(5, stock.getFormat());

			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				isUpdated = true;
			}
		} catch (SQLException e) {
			isUpdated = false;
		}

		return isUpdated;
	}

	// DELETE
	public static boolean deleteStock(Type type, String qualite, String format) {
		String sql = "DELETE STOCK WHERE TYPE_SUPPORT = ? AND QUALITE = ? AND FORMAT = ?";
		boolean isDeleted = false;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, type.name());
			statement.setString(2, qualite);
			statement.setString(3, format);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			isDeleted = false;
		}
		return isDeleted;
	}

}
