DROP TABLE TIRAGE CASCADE CONSTRAINTS;

DROP TABLE POINT_RELAIS CASCADE CONSTRAINTS;

DROP TABLE COMMANDE CASCADE CONSTRAINTS;

DROP TABLE BON_ACHAT CASCADE CONSTRAINTS;

DROP TABLE ADRESSE CASCADE CONSTRAINTS;

DROP TABLE ALBUM CASCADE CONSTRAINTS;

DROP TABLE PHOTO CASCADE CONSTRAINTS;

DROP TABLE IMPRESSION CASCADE CONSTRAINTS;

DROP TABLE CLIENT CASCADE CONSTRAINTS;

DROP TABLE CALENDRIER CASCADE CONSTRAINTS;

DROP TABLE AGENDA CASCADE CONSTRAINTS;

DROP TABLE STOCK CASCADE CONSTRAINTS;

DROP TABLE FICHIERPHOTO CASCADE CONSTRAINTS;

DROP TABLE CADRE CASCADE CONSTRAINTS;

DROP TABLE PARTAGE CASCADE CONSTRAINTS;


-- -----------------------------------------------------------------------------
--       TABLE : TIRAGE
-- -----------------------------------------------------------------------------

CREATE TABLE TIRAGE
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    CONSTRAINT PK_TIRAGE PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : POINT_RELAIS
-- -----------------------------------------------------------------------------

CREATE TABLE POINT_RELAIS
   (
    NOM VARCHAR2(40)  NOT NULL,
    ID_ADRESSE NUMBER(4)  NOT NULL,
    CONSTRAINT PK_POINT_RELAIS PRIMARY KEY (NOM)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE POINT_RELAIS
-- -----------------------------------------------------------------------------

CREATE UNIQUE INDEX I_FK_POINT_RELAIS_ADRESSE
     ON POINT_RELAIS (ID_ADRESSE ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : COMMANDE
-- -----------------------------------------------------------------------------

CREATE TABLE COMMANDE
   (
    NUMERO NUMBER(4)  NOT NULL,
    CODE_BON VARCHAR2(20)  NULL,
    ID_ADRESSE NUMBER(4)  NOT NULL,
    CODE_BON_GENERE VARCHAR2(20)  NULL,
    EMAIL VARCHAR2(40)  NOT NULL,
    MODE_LIVRAISON VARCHAR2(20)  NULL,
    DATE_COMMANDE DATE  NULL,
    STATUT VARCHAR2(20)  NULL,
    ETAT_PAIEMENT NUMBER(1)  NULL,
    MONTANT_TOTAL_CMD NUMBER(5,2)   NULL
,   CONSTRAINT PK_COMMANDE PRIMARY KEY (NUMERO)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE COMMANDE
-- -----------------------------------------------------------------------------

CREATE UNIQUE INDEX I_FK_COMMANDE_BON_ACHAT
     ON COMMANDE (CODE_BON ASC)
    ;

CREATE  INDEX I_FK_COMMANDE_ADRESSE
     ON COMMANDE (ID_ADRESSE ASC)
    ;

CREATE UNIQUE INDEX I_FK_COMMANDE_BON_ACHAT1
     ON COMMANDE (CODE_BON_GENERE ASC)
    ;

CREATE  INDEX I_FK_COMMANDE_CLIENT
     ON COMMANDE (EMAIL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : BON_ACHAT
-- -----------------------------------------------------------------------------

CREATE TABLE BON_ACHAT
   (
    CODE_BON VARCHAR2(20)  NOT NULL,
    NUMERO NUMBER(4)  NULL,
    NUMERO_GENERE NUMBER(4)  NULL,
    EMAIL VARCHAR2(40)  NOT NULL,
    POURCENTAGEREDUC NUMBER(1,2)  NULL,
    TYPE_BONACHAT VARCHAR2(20)  NULL
,   CONSTRAINT PK_BON_ACHAT PRIMARY KEY (CODE_BON)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE BON_ACHAT
-- -----------------------------------------------------------------------------

CREATE UNIQUE INDEX I_FK_BON_ACHAT_COMMANDE
     ON BON_ACHAT (NUMERO ASC)
    ;

CREATE UNIQUE INDEX I_FK_BON_ACHAT_COMMANDE1
     ON BON_ACHAT (NUMERO_GENERE ASC)
    ;

CREATE  INDEX I_FK_BON_ACHAT_CLIENT
     ON BON_ACHAT (EMAIL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : ADRESSE
-- -----------------------------------------------------------------------------

CREATE TABLE ADRESSE
   (
    ID_ADRESSE NUMBER(4)  NOT NULL,
    EMAIL VARCHAR2(40)  NULL,
    PAYS VARCHAR2(30)  NULL,
    VILLE VARCHAR2(30)  NULL,
    CODE_POSTAL VARCHAR2(10)  NULL,
    RUE VARCHAR2(40)  NULL,
    NUMERO VARCHAR(10)  NULL
,   CONSTRAINT PK_ADRESSE PRIMARY KEY (ID_ADRESSE)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE ADRESSE
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_ADRESSE_CLIENT
     ON ADRESSE (EMAIL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : ALBUM
-- -----------------------------------------------------------------------------

CREATE TABLE ALBUM
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    TITRE VARCHAR2(40)  NULL,
    MISE_EN_PAGE VARCHAR2(128)  NULL,
    CONSTRAINT PK_ALBUM PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : PHOTO
-- -----------------------------------------------------------------------------

CREATE TABLE PHOTO
   (
    ID_PHOTO NUMBER(4)  NOT NULL,
    CHEMIN VARCHAR2(60)  NOT NULL,
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    DESCRIPTION VARCHAR2(128)  NULL,
    RETOUCHE VARCHAR2(128)  NULL,
    POSITION_X CHAR(32)  NULL,
    POSITION_Y CHAR(32)  NULL,
    NUMERO_PAGE NUMBER(4)  NULL,
    NB_EXEMPLAIRE NUMBER(4)  NULL
,   CONSTRAINT PK_PHOTO PRIMARY KEY (ID_PHOTO)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PHOTO
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PHOTO_FICHIERPHOTO
     ON PHOTO (CHEMIN ASC)
    ;

CREATE  INDEX I_FK_PHOTO_IMPRESSION
     ON PHOTO (ID_IMPRESSION ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : IMPRESSION
-- -----------------------------------------------------------------------------

CREATE TABLE IMPRESSION
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    EMAIL VARCHAR2(40)  NOT NULL,
    FORMAT VARCHAR2(20)  NULL,
    QUALITE VARCHAR2(20)  NULL,
    TYPE_SUPPORT VARCHAR(20) NULL,
    NUMERO NUMBER(4)  NULL,
    DATE_IMPRESSION DATE  NULL,
    MONTANT_TOTAL NUMBER(5,2)   NULL,
    ETAT_IMPRESSION NUMBER(1)  NULL,
    NB_IMPRESSION NUMBER(4)  NULL
,   CONSTRAINT PK_IMPRESSION PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE IMPRESSION
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_IMPRESSION_CLIENT
     ON IMPRESSION (EMAIL ASC)
    ;

CREATE  INDEX I_FK_IMPRESSION_STOCK
     ON IMPRESSION (FORMAT ASC, QUALITE ASC, TYPE_SUPPORT ASC)
    ;

CREATE  INDEX I_FK_IMPRESSION_COMMANDE
     ON IMPRESSION (NUMERO ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : CLIENT
-- -----------------------------------------------------------------------------

CREATE TABLE CLIENT
   (
    EMAIL VARCHAR2(40)  NOT NULL,
    NOM VARCHAR2(40)  NULL,
    PRENOM VARCHAR2(40)  NULL,
    MOT_DE_PASSE VARCHAR2(40)  NULL,
    DATE_CONNECT DATE NULL
,   CONSTRAINT PK_CLIENT PRIMARY KEY (EMAIL)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : CALENDRIER
-- -----------------------------------------------------------------------------

CREATE TABLE CALENDRIER
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    MODELE VARCHAR2(20)  NULL,
    CONSTRAINT PK_CALENDRIER PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : AGENDA
-- -----------------------------------------------------------------------------

CREATE TABLE AGENDA
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    MODELE VARCHAR2(40)  NULL,
    CONSTRAINT PK_AGENDA PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : STOCK
-- -----------------------------------------------------------------------------

CREATE TABLE STOCK
   (
    TYPE_SUPPORT VARCHAR2(20)  NOT NULL,
    QUALITE VARCHAR2(20)  NOT NULL,
    FORMAT VARCHAR2(20)  NOT NULL,
    QUANTITE NUMBER(4)  NULL,
    PRIX NUMBER(5,2)  NULL
,   CONSTRAINT PK_STOCK PRIMARY KEY (TYPE_SUPPORT, QUALITE, FORMAT)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : FICHIERPHOTO
-- -----------------------------------------------------------------------------

CREATE TABLE FICHIERPHOTO
   (
    CHEMIN VARCHAR2(60)  NOT NULL,
    EMAIL VARCHAR2(40)  NOT NULL,
    RESOLUTION VARCHAR2(20)  NULL,
    DATE_AJOUT DATE  NULL,
    DATE_NO_PHOTO DATE NULL,
    INFO_PRISE_VUE VARCHAR2(128)  NULL,
    EST_PARTAGE NUMBER(1)  NULL
,   CONSTRAINT PK_FICHIERPHOTO PRIMARY KEY (CHEMIN)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE FICHIERPHOTO
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_FICHIERPHOTO_CLIENT
     ON FICHIERPHOTO (EMAIL ASC)
    ;

-- -----------------------------------------------------------------------------
--       TABLE : CADRE
-- -----------------------------------------------------------------------------

CREATE TABLE CADRE
   (
    ID_IMPRESSION NUMBER(4)  NOT NULL,
    MISE_EN_PAGE VARCHAR2(128)  NULL,
    MODELE VARCHAR2(20)  NULL,
    CONSTRAINT PK_CADRE PRIMARY KEY (ID_IMPRESSION)  
   ) ;

-- -----------------------------------------------------------------------------
--       TABLE : PARTAGE
-- -----------------------------------------------------------------------------

CREATE TABLE PARTAGE
   (
    EMAIL VARCHAR2(40)  NOT NULL,
    CHEMIN VARCHAR2(60)  NOT NULL
,   CONSTRAINT PK_PARTAGE PRIMARY KEY (EMAIL, CHEMIN)  
   ) ;

-- -----------------------------------------------------------------------------
--       INDEX DE LA TABLE PARTAGE
-- -----------------------------------------------------------------------------

CREATE  INDEX I_FK_PARTAGE_CLIENT
     ON PARTAGE (EMAIL ASC)
    ;

CREATE  INDEX I_FK_PARTAGE_FICHIERPHOTO
     ON PARTAGE (CHEMIN ASC)
    ;


-- -----------------------------------------------------------------------------
--       CREATION DES REFERENCES DE TABLE
-- -----------------------------------------------------------------------------


ALTER TABLE TIRAGE ADD (
     CONSTRAINT FK_TIRAGE_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE POINT_RELAIS ADD (
     CONSTRAINT FK_POINT_RELAIS_ADRESSE
          FOREIGN KEY (ID_ADRESSE)
               REFERENCES ADRESSE (ID_ADRESSE))   ;

ALTER TABLE COMMANDE ADD (
     CONSTRAINT FK_COMMANDE_BON_ACHAT
          FOREIGN KEY (CODE_BON)
               REFERENCES BON_ACHAT (CODE_BON))   ;

ALTER TABLE COMMANDE ADD (
     CONSTRAINT FK_COMMANDE_ADRESSE
          FOREIGN KEY (ID_ADRESSE)
               REFERENCES ADRESSE (ID_ADRESSE))   ;

ALTER TABLE COMMANDE ADD (
     CONSTRAINT FK_COMMANDE_BON_ACHAT1
          FOREIGN KEY (CODE_BON_GENERE)
               REFERENCES BON_ACHAT (CODE_BON))   ;

ALTER TABLE COMMANDE ADD (
     CONSTRAINT FK_COMMANDE_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;

ALTER TABLE BON_ACHAT ADD (
     CONSTRAINT FK_BON_ACHAT_COMMANDE
          FOREIGN KEY (NUMERO)
               REFERENCES COMMANDE (NUMERO))   ;

ALTER TABLE BON_ACHAT ADD (
     CONSTRAINT FK_BON_ACHAT_COMMANDE1
          FOREIGN KEY (NUMERO_GENERE)
               REFERENCES COMMANDE (NUMERO))   ;

ALTER TABLE BON_ACHAT ADD (
     CONSTRAINT FK_BON_ACHAT_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;

ALTER TABLE ADRESSE ADD (
     CONSTRAINT FK_ADRESSE_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;

ALTER TABLE ALBUM ADD (
     CONSTRAINT FK_ALBUM_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE PHOTO ADD (
     CONSTRAINT FK_PHOTO_FICHIERPHOTO
          FOREIGN KEY (CHEMIN)
               REFERENCES FICHIERPHOTO (CHEMIN))   ;

ALTER TABLE PHOTO ADD (
     CONSTRAINT FK_PHOTO_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE IMPRESSION ADD (
     CONSTRAINT FK_IMPRESSION_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;


ALTER TABLE IMPRESSION ADD (
     CONSTRAINT FK_IMPRESSION_COMMANDE
          FOREIGN KEY (NUMERO)
               REFERENCES COMMANDE (NUMERO))   ;

ALTER TABLE IMPRESSION ADD (
     CONSTRAINT FK_IMPRESSION_STOCK
          FOREIGN KEY (FORMAT, QUALITE, TYPE_SUPPORT)
               REFERENCES STOCK (FORMAT, QUALITE, TYPE_SUPPORT))   ;

ALTER TABLE CALENDRIER ADD (
     CONSTRAINT FK_CALENDRIER_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE AGENDA ADD (
     CONSTRAINT FK_AGENDA_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE FICHIERPHOTO ADD (
     CONSTRAINT FK_FICHIERPHOTO_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;

ALTER TABLE CADRE ADD (
     CONSTRAINT FK_CADRE_IMPRESSION
          FOREIGN KEY (ID_IMPRESSION)
               REFERENCES IMPRESSION (ID_IMPRESSION))   ;

ALTER TABLE PARTAGE ADD (
     CONSTRAINT FK_PARTAGE_CLIENT
          FOREIGN KEY (EMAIL)
               REFERENCES CLIENT (EMAIL))   ;

ALTER TABLE PARTAGE ADD (
     CONSTRAINT FK_PARTAGE_FICHIERPHOTO
          FOREIGN KEY (CHEMIN)
               REFERENCES FICHIERPHOTO (CHEMIN))   ;


-- -----------------------------------------------------------------------------
--                FIN DE GENERATION
-- -----------------------------------------------------------------------------
