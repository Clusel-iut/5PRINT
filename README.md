# 5PRINT
Projet ABD - M1 MIAGE

GROUPE 5 : 

- Mathieu CLUSEL
- Sébastien GOURRAT
- Florian PIGNARD
- Oussama OUZZINE
- Jonathan MONBEIG

HIERACHIE DU PROJET :

- Package application : 

   - Client.java : Correspond à l'interface du client
   - Gestion.java : Correspond à l'interface de gestion pour le propriétare du magasin

- Package DB :

  - Classes importantes :
  
    - GestionDB : Contient toutes les méthodes pour accéder à la base de données. Créer, Modifier, Supprimer, et Slectionner les données
    - LocalDataClient : Contient le client qui vient de se connecter
    - Create.sql : Contient le script sql contenant uniquement la création de la base de données
    - Create-with-insert.sql : Contient le script sql de la création de la base avec l'insertion de données.
    - Trigger-procedures-functions.sql : Script contenant tous les triggers, les fonctions et les procédures.
    
- Package DTO : 

  - Contient toutes les classes de la base de données et les enumerations.
  
- Package interfaces.controllers : 

  - Contient tous les controleurs des différentes vues de l'interface graphique

- Package interfaces.views :

  - Contient tous les vues qui composent l'interface graphique
  
- Package Scenario
   
   - Contient des scenarios automatiques à exécuter en java
   
- Package Ressources

   - Contient les images pour l'application (comme le logo fait par Jonathan)
   
- Compilation via un terminal
   - java -jar monappli.jar monPackage.MaClasse 
   - java -classpath classes/ monPackage.MaClasse 
