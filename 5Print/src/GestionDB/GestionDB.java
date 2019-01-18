package GestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.spi.DirStateFactory.Result;

import DTO.Adresse;
import DTO.BonAchat;
import DTO.Client;
import DTO.Commande;

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
	public static boolean updateAdresse(int id_adresse, String pays, String ville, String code_postal, String rue,
			int numero) {
		String sql = "UPDATE ADRESSE SET PAYS = ?, VILLE = ?, CODE_POSTAL = ?, RUE = ?, NUMERO = ?";
		boolean isAdded = false;
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id_adresse);
			statement.setString(2, pays);
			statement.setString(3, ville);
			statement.setString(4, code_postal);
			statement.setString(5, rue);
			statement.setInt(6, numero);

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

			cli = new Client(result.getString("EMAIL"), result.getString("NOM"), result.getString("PRENOM"), null, null, null, null);
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
	public static boolean updateClient(String email, String nom, String prenom, String motdepasse, Adresse adr) {
		String sql = "UPDATE CLIENT SET EMAIL = ?, NOM = ?, PRENOM = ?, MOT_DE_PASSE = ?";
		boolean isAdded = false;
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, nom);
			statement.setString(3, prenom);
			statement.setString(4, motdepasse);

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
					result.getDate("DATE_COMMANDE"), result.getString("STATUT"), result.getBoolean("ETAT_PAIEMENT"), result.getFloat("MONTANT_TOTAL_CMD"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmd;
	}

	// CREATE
	public static boolean createCommande(int numero, Adresse adresse, Client client, String mode_livraison,
			Date date_commande, String statut, boolean etat_paiement, float montant_total_cmd) {
		String sql = "INSERT INTO COMMANDE (NUMERO, ID_ADRESSE, EMAIL, MODE_LIVRAISON, DATE_COMMANDE, STATUT, ETAT_PAIEMENT, MONTANT_TOTAL_CMD) VALUES (?,?,?,?,?,?,?,?)";
		boolean isAdded = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, numero);
			statement.setInt(2, adresse.getId_adresse());
			statement.setString(3, client.getEmail());
			statement.setString(4, mode_livraison);
			statement.setDate(5, java.sql.Date.valueOf(date_commande.toString()));
			statement.setString(6, statut);
			statement.setBoolean(7, etat_paiement);
			statement.setFloat(8, montant_total_cmd);

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
	public static boolean updateClient(String email, String nom, String prenom, String motdepasse, Adresse adr) {
		String sql = "UPDATE CLIENT SET EMAIL = ?, NOM = ?, PRENOM = ?, MOT_DE_PASSE = ?";
		boolean isAdded = false;
		boolean isUpdated = false;

		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, nom);
			statement.setString(3, prenom);
			statement.setString(4, motdepasse);

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
}
