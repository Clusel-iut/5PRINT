package DB;

import DTO.Client;

public class LocalDataClient {

	public static Client client;
	
	public static void refresh()
	{
		String email = LocalDataClient.client.getEmail();
		LocalDataClient.client = GestionDB.getClientByEmail(email);
	}
}
