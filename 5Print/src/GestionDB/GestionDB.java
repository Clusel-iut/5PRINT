package GestionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import main.Adresse;
import main.Client;

public class GestionDB {
	
	public static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
	public static Connection conn;
	
	public static void configure(String user, String passwd)
	{
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(CONN_URL,user,passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//
	//ADRESSE
	//
	//GET
	public static Adresse getAdresseById(int id)
	{
		String sql = "SELECT * FROM ADRESSE WHERE ID_ADRESSE = ?";
		Adresse adr = null;
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, Integer.toString(id));
			ResultSet result = statement.executeQuery(sql);	
			
			adr =  new Adresse(result.getInt("ID_ADRESSE"),result.getString("PAYS"), result.getString("VILLE"), result.getString("CODE_POSTAL"), result.getString("RUE"), result.getInt("NUMERO"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adr;
	}
	
	//CREATE
	public static boolean createAdresse(String pays, String ville, String code_postal ,String rue, int numero)
	{
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
	
	//UPDATE
		public static boolean updateAdresse(int id_adresse, String pays, String ville, String code_postal ,String rue, int numero)
		{
			String sql = "UPDATE ADRESSE SET PAYS = ?, VILLE = ?, CODE_POSTAL = ?, RUE = ?, numero = ?";			boolean isAdded = false;
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
		
		//DELETE
		public static boolean deleteAdresseById(int id)
		{
			String sql = "DELETE FROM Adresse WHERE id_adresse = ?";
			boolean isDeleted = false;
			
			try {
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, ""+id);
				
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
		
		//
		//Client
		//
		//GET
		public static Client getClientByEmail(String email)
		{
			String sql = "SELECT * FROM Client WHERE email = ?";
			Client cli = null;
			
			try {
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, email);
				ResultSet result = statement.executeQuery(sql);	
				
				cli =  new Client(result.getString("email"),result.getString("nom"), result.getString("prenom"), result.getString("code_postal"), result.getString("rue"), result.getInt("numero"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return adr;
		}
		
		//CREATE
		public static boolean createAdresse(String pays, String ville, String code_postal ,String rue, int numero)
		{
			String sql = "INSERT INTO Adresse (pays, ville, code_postal, rue, numero) VALUES (?,?,?,?,?)";
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
		
		//UPDATE
			public static boolean updateAdresse(int id_adresse, String pays, String ville, String code_postal ,String rue, int numero)
			{
				String sql = "UPDATE Adresse SET pays = ?, ville = ?, code_postal = ?, rue = ?, numero = ?";			boolean isAdded = false;
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
			
			//DELETE
			public static boolean deleteAdresseById(int id)
			{
				String sql = "DELETE FROM Adresse WHERE id_adresse = ?";
				boolean isDeleted = false;
				
				try {
					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, ""+id);
					
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

