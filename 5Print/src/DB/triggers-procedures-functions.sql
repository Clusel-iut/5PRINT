-- -------------------------------------------------------------------------------------------------------------
-- ----------------------------------------- TRIGGER stock_trigger et bon achat sur commande   -------------------
----------------------------------------------------------------------------------------------------------------

create or replace TRIGGER stock_trigger
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

    CURSOR C1 IS SELECT MAX(P.NUMERO_PAGE)  total_page, SUM(p.nb_exemplaire)  total_exemplaire, I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT FROM IMPRESSION I, PHOTO P, STOCK S
	WHERE NUMERO = :NEW.NUMERO and P.ID_IMPRESSION = I.ID_IMPRESSION and I.TYPE_SUPPORT = S.TYPE_SUPPORT and I.QUALITE = S.QUALITE and I.FORMAT = S.FORMAT
	group by  I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT;

    vdata C1%rowtype;

BEGIN
IF :new.STATUT = 'Payee' and :old.STATUT = 'En_cours' then
    OPEN C1;
		LOOP
			FETCH C1 INTO vdata;
			EXIT WHEN C1%NOTFOUND;
			IF vdata.TYPE_SUPPORT = 'tirage' then
			    var := vdata.total_exemplaire*vdata.NB_IMPRESSION;
                IF var > vdata.QUANTITE then
                    raise_application_error(-20001, 'Pas assez de stock pour le tirage, var = '||var||' stock ='|| vdata.QUANTITE);
			    END IF;
			ELSE
			    IF  vdata.TYPE_SUPPORT = 'album' then
					var := vdata.total_page*vdata.NB_IMPRESSION;
					IF var > vdata.QUANTITE then
						raise_application_error(-20001, 'Pas assez de stock pour l album, var = '||var||' stock ='|| vdata.QUANTITE);
					END IF;
                ELSE
					IF  vdata.TYPE_SUPPORT = 'cadre' then
						var := vdata.NB_IMPRESSION;
						IF var > vdata.QUANTITE then
							raise_application_error(-20001, 'Pas assez de stock pour cadre, var = '||var||' stock ='|| vdata.QUANTITE);
						END IF;
					ELSE
						IF  vdata.TYPE_SUPPORT = 'calendrier' then
							var := vdata.NB_IMPRESSION;
							IF var > vdata.QUANTITE then
								raise_application_error(-20001, 'Pas assez de stock pour calendrier, var = '||var||' stock ='|| vdata.QUANTITE);
							END IF;
						ELSE
							var := vdata.NB_IMPRESSION;
							IF var > vdata.QUANTITE then
								raise_application_error(-20001, 'Pas assez de stock pour agenda, var = '||var||' stock ='|| vdata.QUANTITE);
							END IF;
						END IF;
					END IF;
                END IF;
			END IF;

		END LOOP;
	CLOSE C1;
	OPEN C1;
		LOOP
			FETCH C1 INTO vdata;
			EXIT WHEN C1%NOTFOUND;
			IF vdata.TYPE_SUPPORT = 'tirage' then
				var := vdata.total_exemplaire*vdata.NB_IMPRESSION;
			ELSE
				IF  vdata.TYPE_SUPPORT = 'album' then
						var := vdata.total_page*vdata.NB_IMPRESSION;
				ELSE
					var := vdata.NB_IMPRESSION;
				END IF;
			END IF;
				v_type_support:=vdata.TYPE_SUPPORT;
				v_qualite := vdata.QUALITE;
				v_format :=vdata.FORMAT;
				imp := vdata.QUANTITE - var;
				dbms_output.put_line(var || ' supp: ' || vdata.TYPE_SUPPORT || ' qualite: ' || vdata.QUALITE|| ' F: ' || vdata.FORMAT || '  imp :'||imp );
			    UPDATE STOCK set QUANTITE = imp where TYPE_SUPPORT = v_type_support and  QUALITE = v_qualite and FORMAT = v_format;
            
		END LOOP;
	CLOSE C1;

	IF :new.CODE_BON <> '' then
          select count(*) into usedpromo from BON_ACHAT B where B.CODE_BON = :new.CODE_BON AND B.NUMERO <> :new.NUMERO;
          IF usedpromo > 0 then
             raise_application_error(-20001, 'Bon d achat deja utilise');
          END IF;
	END IF;
	
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
END ;
/


-- -------------------------------------------------------------------------------------------------------------
-- -----------------------------------------   procedure d'ajout code promo ------------------------------------
----------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE ajoutpromo (user_mail VARCHAR2, pourcentage NUMBER,NUMEROC NUMBER) AS
	codepromo VARCHAR2(10) ;
	nbrpromo NUMBER :=0;
BEGIN
	LOOP
		SELECT dbms_random.string('X', 10) str into codepromo FROM dual;
		select count(*) into nbrpromo from BON_ACHAT where CODE_BON=codepromo;
		EXIT WHEN nbrpromo = 0;
	END LOOP;
	insert into BON_ACHAT values(codepromo,null,NUMEROC,user_mail,pourcentage,'a');
    
END;
/


-- -------------------------------------------------------------------------------------------------------------
-- -----------------------------------------   procedure supprime fichier ------------------------------------
----------------------------------------------------------------------------------------------------------------

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

-- -------------------------------------------------------------------------------------------------------------
-- -----------------------------------------   Trigger supprimer fichier plus de 10 jours non utilisé -------------
----------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE TRIGGER tr_supp_photo
AFTER UPDATE
ON CLIENT
BEGIN
  SUPRIME_FICHIER();
END;
/


-- -------------------------------------------------------------------------------------------------------------
-- -----------------------------------------   Trigger delete client -------------------------------------------
----------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER client_photo
AFTER DELETE
ON CLIENT
FOR EACH ROW
BEGIN
update FICHIERPHOTO set EST_PARTAGE =0 where EMAIL=:old.EMAIL;
END;
/


-------------------------------------------------------------------------------------------------
-- ------------Trigger commande impossible si fichier photo utilisé n est plus partagé-----------
-------------------------------------------------------------------------------------------------

create or replace TRIGGER impression_update
  AFTER UPDATE ON IMPRESSION
  FOR EACH ROW
DECLARE
    nb_photo_plus_partage number;
BEGIN
    IF :new.NUMERO <> null AND :old.NUMERO = null then
		select count(*) into nb_photo_plus_partage from PHOTO P
		where P.ID_IMPRESSION = :new.ID_IMPRESSION
		AND P.CHEMIN IN (select CHEMIN from FICHIERPHOTO FP
							WHERE FP.EMAIL <> :new.EMAIL
							AND FP.EST_PARTAGE = 0);
    END IF;
    IF nb_photo_plus_partage > 0 then
		raise_application_error(-20001, 'Une photo utilise un fichier photo qui n est plus partag¿¿¿¿');
    END IF;
END;
/


-------------------------------------------------------------------------------------------------
-- ------------Trigger impossible d'utiliser un fichier partagé, si l'ont n'en partage pas-----------
-------------------------------------------------------------------------------------------------

CREATE OR REPLACE TRIGGER photo_create
  BEFORE INSERT ON PHOTO
  FOR EACH ROW
DECLARE
    nb_photo_partage number;
    estPartage number;
    emailP VARCHAR2(40);
BEGIN
	SELECT EMAIL into emailP FROM IMPRESSION I WHERE I.ID_IMPRESSION = :new.ID_IMPRESSION;
    SELECT EST_PARTAGE into estPartage from FICHIERPHOTO F where F.CHEMIN=:new.CHEMIN ;

    IF estPartage = 1 then
		SELECT count(*) into nb_photo_partage FROM FICHIERPHOTO F WHERE F.EMAIL = emailP AND F.EST_PARTAGE = 1;
		IF nb_photo_partage = 0 then
			raise_application_error(-20001, 'Tu ne peux pas utiliser un fichier photo partagé, si tu n en partage pas'); 
		END IF;
    END IF;
END;
/


-------------------------------------------------------------------------------------------------
----------------------- Funtion calcul des montants commandes/impressions-------------------------
-------------------------------------------------------------------------------------------------

-- ----Montant impression
create or replace FUNCTION CALCUL_MONTANT_IMPRESSION (p_id_impression IN NUMBER) RETURN NUMBER
IS
    pragma autonomous_transaction;
	v_total_page NUMBER;
	v_id_impression NUMBER;
	v_type_support varchar2(30);
	v_qualite varchar2(30);
	v_format varchar2(30);
	v_quantite NUMBER;
	v_total_exp NUMBER;
	v_nb_impression NUMBER;
	v_prix NUMBER;
	v_total NUMBER := 0;
	var NUMBER;
BEGIN

	SELECT MAX(P.NUMERO_PAGE) , SUM(p.nb_exemplaire), I.TYPE_SUPPORT ,I.QUALITE , S.QUANTITE , I.FORMAT , S.PRIX,I.ID_IMPRESSION,I.NB_IMPRESSION
	into v_total_page,v_total_exp,v_type_support, v_qualite, v_quantite,v_format,v_prix,v_id_impression,v_nb_impression
	FROM IMPRESSION I, PHOTO P, STOCK S
	WHERE I.ID_IMPRESSION = p_id_impression AND P.ID_IMPRESSION = I.ID_IMPRESSION and I.TYPE_SUPPORT = S.TYPE_SUPPORT and I.QUALITE = S.QUALITE and I.FORMAT = S.FORMAT
	group by  I.NB_IMPRESSION ,I.ID_IMPRESSION, I.TYPE_SUPPORT, I.QUALITE, S.QUANTITE, I.FORMAT, S.PRIX;
	dbms_output.put_line(v_type_support || ' ' || v_nb_impression || ' ' || v_prix  );
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
	commit;
	v_total := var;
	RETURN v_total;
END CALCUL_MONTANT_IMPRESSION;
/

-- --------Montant commande
create or replace FUNCTION CALCUL_MONTANT_COMMANDE( numero_cmd IN NUMBER )RETURN NUMBER
IS
    pragma autonomous_transaction;
	var NUMBER;
    v_total NUMBER := 0;
	v_id_imp NUMBER;
	CURSOR C1 IS SELECT I.ID_IMPRESSION FROM IMPRESSION I WHERE NUMERO = numero_cmd;
BEGIN
    OPEN C1;
		LOOP
			FETCH C1 INTO v_id_imp;
			EXIT WHEN C1%NOTFOUND;

			v_total := v_total + CALCUL_MONTANT_IMPRESSION(v_id_imp);

		END LOOP;
	CLOSE C1;

	UPDATE COMMANDE SET MONTANT_TOTAL_CMD = v_total where NUMERO = numero_cmd;
	commit;
	RETURN (v_total);

END;
/

-------------------------------------------------------------------------------------------------------------
----------------------------- AUTO INCREMENT ----------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE impression_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE commande_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE adresse_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE photo_sequence START WITH 1 INCREMENT BY 1;
--------------------------------------------------------
--  DDL for Trigger PHOTO_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "OUZZINEO"."PHOTO_INSERT" 
  BEFORE INSERT ON PHOTO
  FOR EACH ROW
BEGIN
  SELECT photo_sequence.nextval
  INTO :new.ID_PHOTO
  FROM dual;
END;
/
ALTER TRIGGER "OUZZINEO"."PHOTO_INSERT" ENABLE;

--------------------------------------------------------
--  DDL for Trigger COMMANDE_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "OUZZINEO"."COMMANDE_INSERT" 
  BEFORE INSERT ON COMMANDE
  FOR EACH ROW
BEGIN
  SELECT commande_sequence.nextval
  INTO :new.numero
  FROM dual;
END;
/
ALTER TRIGGER "OUZZINEO"."COMMANDE_INSERT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger IMPRESSION_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "OUZZINEO"."IMPRESSION_INSERT" 
  BEFORE INSERT ON IMPRESSION
  FOR EACH ROW
BEGIN
  SELECT impression_sequence.nextval
  INTO :new.id_impression
  FROM dual;
END;
/
ALTER TRIGGER "OUZZINEO"."IMPRESSION_INSERT" ENABLE;

--------------------------------------------------------
--  DDL for Trigger ADRESSE_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "OUZZINEO"."ADRESSE_INSERT" 
  BEFORE INSERT ON ADRESSE
  FOR EACH ROW
BEGIN
  SELECT adresse_sequence.nextval
  INTO :new.ID_ADRESSE
  FROM dual;
END;
/
ALTER TRIGGER "OUZZINEO"."ADRESSE_INSERT" ENABLE;
