-- Création de la table ACCOUNT_BASED_MULTIPLE
CREATE TABLE `ACCOUNT_BASED_MULTIPLE`
(
  -- Définition des champs de la table ACCOUNT_BASED_MULTIPLE
  `ID`          int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l entite',
  `VERSION`     int(3)      NOT NULL                COMMENT 'Version de l entite',
  `CREATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de creation de l entite',
  `UPDATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de modification de l entite',
  `ACCOUNT_ID`  varchar(12) NOT NULL                COMMENT 'Lien vers le compte utilisateur de l entite',
  -- Définition de la clé primaire de la table ACCOUNT_BASED_MULTIPLE
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table ACCOUNT_BASED_MULTIPLE vers la table ACCOUNT_STUB
  KEY `ACCOUNT_BASED_M_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `ACCOUNT_BASED_M_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT_STUB` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table de test';
