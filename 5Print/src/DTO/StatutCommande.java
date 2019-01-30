package DTO;

public enum StatutCommande {
	En_cours("En cours"), Payee("Payée"), Pret_a_l_envoi("Prêt à l'envoi"), Envoyee("Envoyée");
	
	private String abreviation ;  
    
    private StatutCommande(String abreviation) {  
        this.abreviation = abreviation ;  
   }
     
    public String getAbreviation() {  
        return  this.abreviation ;  
   }
}
