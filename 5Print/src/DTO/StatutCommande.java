package DTO;

public enum StatutCommande {
	En_cours("En cours"), Terminée("Terminée"), Prêt_à_l_envoi("Prêt à l'envoi"), Envoyée("Envoyée");
	
	private String abreviation ;  
    
    private StatutCommande(String abreviation) {  
        this.abreviation = abreviation ;  
   }
     
    public String getAbreviation() {  
        return  this.abreviation ;  
   }
}
