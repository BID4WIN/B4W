-- Création de la table CONNECTION_STUB
CREATE TABLE `CONNECTION_STUB`
(
  -- Définition des champs de la table CONNECTION_STUB
  `ID`          varchar(32) NOT NULL      COMMENT 'Identifiant de la connexion',
  `VERSION`     int(5)      NOT NULL     COMMENT 'Version de la connexion',
  `CREATE_DATE` varchar(23) NOT NULL     COMMENT 'Date de creation de la connexion',
  `UPDATE_DATE` varchar(23) NOT NULL     COMMENT 'Date de modification de la connexion',
  `ACCOUNT_ID`  varchar(12) NOT NULL     COMMENT 'Identifiant du compte utilisateur de connexion',
  `IP`          varchar(39) NOT NULL     COMMENT 'Adresse IP de connexion',
  `PROCESS_ID`  varchar(12) NOT NULL     COMMENT 'Identifiant du process a l origine de la connexion',
  `REMAMENT`    int(1)      NOT NULL     COMMENT 'Flag indiquant la remanence de la connexion',
  `ACTIVE`      int(1)      NOT NULL     COMMENT 'Flag indiquant si la connexion est active',
  `REASON`      varchar(12) DEFAULT NULL COMMENT 'Raison de fin de connexion',
  -- Définition de la clé primaire de la table CONNECTION_STUB
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table CONNECTION_STUB vers la table ACCOUNT_STUB
  KEY `CONNECTION_STUB_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `CONNECTION_STUB_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT_STUB` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des connexions';
