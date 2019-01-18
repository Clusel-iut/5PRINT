package GestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.spi.DirStateFactory.Result;

import DTO.Adresse;
import DTO.BonAchat;
import DTO.Client;
import DTO.Commande;
import DTO.FichierPhoto;
import DTO.Photo;

public class GestionDB {

	public static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	public static Connection conn;

	public static void configure(String user, String passwd) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(CONN_URL, user, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

			adr = new Adresse(result.getInt("ID_ADRESSE"), result.getString("PAYS"), result.getString("VILLE"),
					result.getString("CODE_POSTAL"), result.getString("RUE"), result.getInt("NUMERO"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adr;
	}

	// CREATE
	public static boolean createAdresse(String pays, String ville, String code_postal, String rue, int numero) {
		String sql = "INSERT INTO ADRESSE (PAYS, VILLE, CODE_POSTAL, RUE, NUMERO) VALUES (?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, pays);
			statement.setString(2, ville);
			statement.setString(3, code_postal);
			statement.setString(4, rue);
			statement.setInt(5, numero);

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
			statement.setInt(5, adresse.getNumero());
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
			// TODO Auto-generated catch block
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

			cli = new Client(result.getString("EMAIL"), result.getString("NOM"), result.getString("PRENOM"), null, null,
					null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cli;
	}

	// CREATE
	public static boolean createClient(String email, String nom, String prenom, String motdepasse, Adresse adr) {
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
			// TODO Auto-generated catch block
			isDeleted = false;
		}
		return isDeleted;
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

			cmd = new Commande(result.getInt("NUMERO"), null, null, null, result.getString("MODE_LIVRAISON"),
					result.getDate("DATE_COMMANDE"), result.getString("STATUT"), result.getBoolean("ETAT_PAIEMENT"),
					result.getFloat("MONTANT_TOTAL_CMD"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmd;
	}

	// CREATE
	public static boolean createCommande(Adresse adresse, Client client, String mode_livraison, Date date_commande,
			String statut, boolean etat_paiement, float montant_total_cmd) {
		String sql = "INSERT INTO COMMANDE (ID_ADRESSE, EMAIL, MODE_LIVRAISON, DATE_COMMANDE, STATUT, ETAT_PAIEMENT, MONTANT_TOTAL_CMD) VALUES (?,?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, adresse.getId_adresse());
			statement.setString(2, client.getEmail());
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
		String sql = "UPDATE COMMANDE SET ID_ADRESSE = ?,  = ?, MODE_LIVRAISON = ?, DATE_COMMANDE = ?, STATUT = ?, ETAT_PAIEMENT = ?, MONTANT_TOTAL_CMD";
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, commande.getNumero());
			statement.setInt(2, commande.getAdresse().getId_adresse());
			statement.setString(4, commande.getMode_livraison());
			statement.setDate(5, java.sql.Date.valueOf(commande.getDate_commande().toString()));
			statement.setString(6, commande.getStatut());
			statement.setBoolean(7, commande.getEtat_paiement());
			statement.setFloat(8, commande.getMontant_total_cmd());

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
			// TODO Auto-generated catch block
			isDeleted = false;
		}
		return isDeleted;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////

	//
	// FichierPhoto
	//
	// GET
	public static FichierPhoto getFichierPhotoById(int id) {
		String sql = "SELECT * FROM FICHIERPHOTO WHERE CHEMIN = ?";
		FichierPhoto fiPhoto = null;

		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(sql);

			fiPhoto = new FichierPhoto(result.getString("CHEMIN"), null, result.getString("RESOLUTION"),
					result.getString("INFO_PRISE_VUE"), result.getBoolean("EST_PARTAGE"), null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fiPhoto;
	}

	// CREATE
	public static boolean createFichierPhoto(String chemin, Client client, String resolution, Date date_ajout,
			String info_vue, boolean est_partage) {
		String sql = "INSERT INTO FICHIERPHOTO (CHEMIN, EMAIL, RESOLUTION, DATE_AJOUT, INFO_PRISE_VUE, EST_PARTAGE) VALUES (?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);
			statement.setString(2, client.getEmail());
			statement.setString(3, resolution);
			statement.setDate(4, java.sql.Date.valueOf(date_ajout.toString()));
			statement.setString(5, info_vue);
			statement.setBoolean(6, est_partage);

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
			// TODO Auto-generated catch block
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
			photo = new Photo(result.getInt("ID_PHOTO"), null, result.getString("DESCRIPTION"),
					result.getString("RETOUCHE"), result.getInt("NUMERO_PAGE"), result.getInt("POSITION_X"),
					result.getInt("POSITION_Y"), result.getInt("NB_EXEMPLAIRE"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return photo;
	}

	// CREATE
	public static boolean createPhoto(String chemin, Impression impression, String description, int position_x, int position_y,
			int numero_page, int nb_exemplaire, String retouche) {
		String sql = "INSERT INTO PHOTO (CHEMIN, ID_IMPRESSION, DESCRIPTION, RETOUCHE, POSITION_X, POSITION_Y, NUMERO_PAGE, NB_EXEMPLAIRE) VALUES (?,?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, chemin);
			statement.setString(2, client.getEmail());
			statement.setString(3, resolution);
			statement.setDate(4, java.sql.Date.valueOf(date_ajout.toString()));
			statement.setString(5, info_vue);
			statement.setBoolean(6, est_partage);

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
			// TODO Auto-generated catch block
			isDeleted = false;
		}
		return isDeleted;
	}
}
