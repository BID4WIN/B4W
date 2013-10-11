-- Création de la table CONNECTION
CREATE TABLE `CONNECTION`
(
  -- Définition des champs de la table CONNECTION
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
  -- Définition de la clé primaire de la table CONNECTION
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table CONNECTION vers la table ACCOUNT
  KEY `CONNECTION_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `CONNECTION_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des connexions';
