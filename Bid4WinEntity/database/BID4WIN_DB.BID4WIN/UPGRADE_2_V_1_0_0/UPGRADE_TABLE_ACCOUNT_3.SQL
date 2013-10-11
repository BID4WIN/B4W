-- Modification de la table ACCOUNT
ALTER TABLE ACCOUNT
ADD SPONSOR_ID varchar(12) DEFAULT NULL
    COMMENT 'Identifiant du compte utilisateur parrain',
-- Définition de la clé étrangère de la table ACCOUNT vers la table ACCOUNT
ADD KEY `ACCOUNT_2_ACCOUNT_SPONSOR_FK` (`SPONSOR_ID`),
ADD CONSTRAINT `ACCOUNT_2_ACCOUNT_SPONSOR_FK` FOREIGN KEY (`SPONSOR_ID`) REFERENCES `ACCOUNT` (`ID`);
