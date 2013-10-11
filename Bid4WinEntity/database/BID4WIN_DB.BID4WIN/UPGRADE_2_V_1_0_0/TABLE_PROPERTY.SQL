-- Création de la table PROPERTY
CREATE TABLE `PROPERTY`
(
  -- Définition des champs de la table PROPERTY
  `ID`          int(5)       NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la propriete',
  `VERSION`     int(4)       NOT NULL                COMMENT 'Version de la propriete',
  `CREATE_DATE` varchar(23)  NOT NULL                COMMENT 'Date de creation de la propriete',
  `UPDATE_DATE` varchar(23)  NOT NULL                COMMENT 'Date de modification de la propriete',
  `KEY_`        varchar(40)  NOT NULL                COMMENT 'Cle de la propriete',
  `VALUE`       varchar(100) NOT NULL                COMMENT 'Valeur de la propriete',
  `ROOT_ID`     int(1)       DEFAULT NULL            COMMENT 'Identifiant de la propriete racine',
  `PROPERTY_ID` int(5)       DEFAULT NULL            COMMENT 'Identifiant de la propriete parent',
  -- Définition de la clé primaire de la table PROPERTY
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table PROPERTY vers la table PROPERTY_ROOT
  KEY `PROPERTY_2_PROPERTY_ROOT_FK` (`ROOT_ID`),
  CONSTRAINT `PROPERTY_2_PROPERTY_ROOT_FK` FOREIGN KEY (`ROOT_ID`) REFERENCES `PROPERTY_ROOT` (`ID`),
  -- Définition de la clé étrangère de la table PROPERTY vers la table PROPERTY
  KEY `PROPERTY_2_PROPERTY_FK` (`PROPERTY_ID`),
  CONSTRAINT `PROPERTY_2_PROPERTY_FK` FOREIGN KEY (`PROPERTY_ID`) REFERENCES `PROPERTY` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des proprietes';
