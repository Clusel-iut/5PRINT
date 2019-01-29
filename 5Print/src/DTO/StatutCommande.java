package DTO;

public enum StatutCommande {
	En_cours("En cours"), Pr�t_�_l_envoi("Pr�t � l'envoi"), Envoy�e("Envoy�e");
	
	private String abreviation ;  
    
    private StatutCommande(String abreviation) {  
        this.abreviation = abreviation ;  
   }
     
    public String getAbreviation() {  
        return  this.abreviation ;  
   }
}
