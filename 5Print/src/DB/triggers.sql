-- -------------------------trigger stock -------------------------------------------

CREATE OR REPLACE TRIGGER stock_trigger
AFTER UPDATE OR INSERT 
ON Commande
FOR EACH ROW

DECLARE
	v_total_page NUMBER;
	v_id_impression NUMBER;
	v_type_support varchar2(30);
	v_qualite varchar2(30);
	v_format varchar2(30);
	v_quantite NUMBER;
	v_total_exp NUMBER;
	v_nb_impression NUMBER;
	v_montant_total_cmd NUMBER;
  	imp NUMBER;
	var NUMBER;
	usedpromo NUMBER := 0;
	nbr NUMBER;

    CURSOR C1 IS SELECT MAX(P.NUMERO_PAGE) as total_page, SUM(p.nb_exemplaire) as total_exemplaire, I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT FROM IMPRESSION I, PHOTO P, STOCK S 
	WHERE NUMERO = :NEW.NUMERO and P.ID_IMPRESSION = I.ID_IMPRESSION and I.TYPE_SUPPORT = S.TYPE_SUPPORT and I.QUALITE = S.QUALITE and I.FORMAT = S.FORMAT
	group by  I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT;
	
BEGIN
IF (:new.STATUT = 'Payee' and :old.STATUT = 'En_cours') OR (:new.STATUT = 'Pret_a_l_envoi' and :old.STATUT = 'Payee') then
    OPEN C1; 
		LOOP  
			FETCH C1 INTO v_total_page,v_total_exp,v_nb_impression,v_id_impression,v_type_support,v_qualite,v_quantite,v_format;
			EXIT WHEN C1%NOTFOUND;
			IF v_type_support = 'tirage' then
			    var := v_total_exp*v_nb_impression;
                IF var > v_quantite then
                    raise_application_error(-20001, 'Pas assez de stock pour le tirage, var = '||var||' stock ='|| v_quantite); 			
			    END IF;
			ELSE 
			    IF  v_type_support = 'album' then 
					var := v_total_page*v_nb_impression;
					IF var > v_quantite then
						raise_application_error(-20001, 'Pas assez de stock pour l album, var = '||var||' stock ='|| v_quantite); 			
					END IF;
                ELSE 
					IF  v_type_support = 'cadre' then 
						var := v_nb_impression;
						IF var > v_quantite then
							raise_application_error(-20001, 'Pas assez de stock pour cadre, var = '||var||' stock ='|| v_quantite); 			
						END IF;
					ELSE
						IF  v_type_support = 'calendrier' then 
							var := v_nb_impression;
							IF var > v_quantite then
								raise_application_error(-20001, 'Pas assez de stock pour calendrier, var = '||var||' stock ='|| v_quantite); 			
							END IF;
						ELSE
							var := v_nb_impression;
							IF var > v_quantite then
								raise_application_error(-20001, 'Pas assez de stock pour agenda, var = '||var||' stock ='|| v_quantite); 			
							END IF;
						END IF;
					END IF;
                END IF;				 
			END IF;

		END LOOP; 
	CLOSE C1;
	IF :new.STATUT = 'Pret_a_l_envoi' and :old.STATUT = 'Payee' then
		OPEN C1; 
			LOOP  
				FETCH C1 INTO v_total_page,v_total_exp,v_nb_impression,v_id_impression,v_type_support,v_qualite,v_quantite,v_format;
				EXIT WHEN C1%NOTFOUND;
				IF v_type_support = 'tirage' then
					var := v_total_exp*v_nb_impression;              
				ELSE 
					IF  v_type_support = 'album' then 
						var := v_total_page*v_nb_impression;							
					ELSE 
						var := v_nb_impression;	
					END IF;				 
				END IF;
				
				UPDATE STOCK set QUANTITE = QUANTITE-var where TYPE_SUPPORT=v_type_support,QUALITE=v_qualite,FORMAT=v_format;
				
			END LOOP; 
		CLOSE C1; 
	END IF;
	
	IF :new.CODE_BON <> '' then
          select count(*) into usedpromo from BON_ACHAT B where B.CODE_BON = :new.CODE_BON AND B.NUMERO <> :new.NUMERO;
          IF usedpromo > 0 then
             raise_application_error(-20001, 'Bon d achat deja utilise'); 
          END IF;
	END IF;
      
    IF :new.STATUT = 'Pret_a_l_envoi' and :old.STATUT = 'Payee' then
		IF :new.MONTANT_TOTAL_CMD > 100 then	
			select count(*) into nbr from BON_ACHAT where NUMERO = :NEW.NUMERO;
			IF nbr = 0 then        				 
				ajoutpromo(:new.EMAIl, 0.05 , :new.NUMERO);
			END IF;
		END IF;
		IF :new.CODE_BON <> '' then
          DELETE FROM BON_ACHAT BA where BA.CODE_BON = :new.CODE_BON;
		END IF;
	END IF;
END IF;		
END ;
/


-- ------------------------------- procedure creation bon achat --------------------------

CREATE OR REPLACE PROCEDURE ajoutpromo (user_mail VARCHAR2, pourcentage NUMBER,NUMEROC NUMBER) AS
	codepromo VARCHAR2(10) ;
	nbrpromo NUMBER :=0;
BEGIN
	LOOP
		SELECT dbms_random.string('X', 10) str into codepromo FROM dual;
		select count(*) into nbrpromo from BON_ACHAT where CODE_BON=codepromo;
		EXIT WHEN nbrpromo = 0;
	END LOOP;
	--Update commande set CODE_BON_GENERE = codepromo where NUMERO =NUMEROC;
	insert into BON_ACHAT values(codepromo,null,NUMEROC,user_mail,pourcentage,'a');
    
END;
/


-- --------------------------------- procedure supprime fichier ---------------------------

CREATE OR REPLACE PROCEDURE SUPRIME_FICHIER AS
  date_deff NUMBER(20);
BEGIN
   FOR rec IN (SELECT CHEMIN,DATE_NO_PHOTO from FICHIERPHOTO)
		LOOP
			select (trunc(sysdate) - rec.DATE_NO_PHOTO) into date_deff from dual;
			IF date_deff > 30 then 
				delete from FICHIERPHOTO where CHEMIN=rec.CHEMIN;
			END IF;	
		END LOOP;
  
END;
/

-- ----------------------------------- trrigger supprime fichier 10 jours ----
CREATE OR REPLACE TRIGGER tr_supp_photo
AFTER UPDATE
ON CLIENT
BEGIN
  SUPRIME_FICHIER();
END;
/

-- -------------------------------------- trigger delete client 

CREATE OR REPLACE TRIGGER client_photo
AFTER DELETE
ON CLIENT
FOR EACH ROW
BEGIN
update FICHIERPHOTO set EST_PARTAGE =0 where EMAIL=:old.EMAIL;
END;
/

-- --------- Commande impossible si fichier photo utilisé n est plus partagé -------
CREATE OR REPLACE TRIGGER impression_update
  BEFORE UPDATE ON IMPRESSION
  FOR EACH ROW
DECLARE
    nb_photo_plus_partage number;
BEGIN
    IF :new.NUMERO != null AND :old.NUMERO = null then
		select count(*) into nb_photo_plus_partage from PHOTO P
		where P.ID_IMPRESSION = :new.ID_IMPRESSION
		AND P.CHEMIN IN (select chemin from FICHIERPHOTO FP 
							WHERE FP.EMAIL != :new.EMAIL
							AND FP.EST_PARTAGE = 0);
    END IF;
    IF nb_photo_plus_partage > 0 then
		raise_application_error(-20001, 'Une photo utilise un fichier photo qui n est plus partagé'); 
    END IF;
END;
/

-- --------- Impossible d'utiliser un fichier partagé, si l'ont n'en partage pas ------
CREATE OR REPLACE TRIGGER photo_create
  BEFORE CREATE ON PHOTO
  FOR EACH ROW
DECLARE
    nb_photo_partage number;
    estPartage number;
    emailP VARCHAR2(40);
BEGIN
	SELECT EMAIL into emailP FROM IMPRESSION IM WHERE IM.ID_IMPRESSION = :new.ID_IMPRESSION;
    SELECT EST_PARTAGE into estPartage from FICHIERPHOTO FP
    where :new.CHEMIN = FP.CHEMIN;

    IF estPartage = 1
		SELECT count(*) into nb_photo_partage FROM FICHIERPHOTO FIP WHERE FIP.EMAIL = emailP AND FIP.EST_PARTAGE = 1;
		IF nb_photo_partage = 0 then
			raise_application_error(-20001, 'Tu ne peux pas utiliser un fichier photo partagé, si tu n en partage pas'); 
		END IF;
    END IF;
END;
/

-- ---------Calcul des montants ----
CREATE OR REPLACE PROCEDURE CALCUL_MONTANT_IMPRESSION (id_impression VARCHAR2) AS
	v_total_page NUMBER;
	v_id_impression NUMBER;
	v_type_support varchar2(30);
	v_qualite varchar2(30);
	v_format varchar2(30);
	v_quantite NUMBER;
	v_total_exp NUMBER;
	v_nb_impression NUMBER;
	v_prix NUMBER;
BEGIN
	SELECT MAX(P.NUMERO_PAGE) into v_total_page, SUM(p.nb_exemplaire) into total_exemplaire, I.TYPE_SUPPORT into v_type_support,
	 I.QUALITE into v_qualite, S.QUANTITE into v_quantite, I.FORMAT into v_format, S.PRIX into v_prix; 
	FROM IMPRESSION I, PHOTO P, STOCK S 
	WHERE I.ID_IMPRESSION = id_impression, P.ID_IMPRESSION = I.ID_IMPRESSION and I.TYPE_SUPPORT = S.TYPE_SUPPORT and I.QUALITE = S.QUALITE and I.FORMAT = S.FORMAT
	group by  I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT;
	
	IF v_type_support = 'tirage' then
			var := v_total_exp*v_nb_impression*v_prix;              
	ELSE 
		IF  v_type_support = 'album' then 
			var := v_total_page*v_nb_impression*v_prix;							
		ELSE 
			var := v_nb_impression*v_prix;	
		END IF;				 
	END IF;
				
	UPDATE IMPRESSION set MONTANT_TOTAL = var where ID_IMPRESSION = id_impression;
END;
/

CREATE OR REPLACE PROCEDURE CALCUL_MONTANT_COMMANDE (numero_cmd VARCHAR2) AS
	v_total NUMBER := 0;
	v_id_imp NUMBER;
	CURSOR C1 IS SELECT I.ID_IMPRESSION FROM IMPRESSION I
	WHERE NUMERO = numero_cmd;
	
BEGIN
    OPEN C1; 
		LOOP  
			FETCH C1 INTO v_id_imp;
			EXIT WHEN C1%NOTFOUND;
			CALCUL_MONTANT_IMPRESSION(v_id_imp);
			v_total := v_total + (select MONTANT_TOTAL FROM IMPRESSION WHERE ID_IMPRESSION = v_id_imp);

		END LOOP; 
	CLOSE C1;
	
	UPDATE COMMANDE SET MONTANT_TOTAL_CMD = v_total where NUMERO = numero_cmd;
END;
/
