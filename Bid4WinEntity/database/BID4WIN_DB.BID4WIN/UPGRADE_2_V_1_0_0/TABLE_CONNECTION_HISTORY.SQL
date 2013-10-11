-- Création de la table CONNECTION_HISTORY
CREATE TABLE `CONNECTION_HISTORY`
(
  -- Définition des champs de la table CONNECTION_HISTORY
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la connexion',
  `VERSION`         int(5)      NOT NULL                COMMENT 'Version de la connexion',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation de la connexion',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification de la connexion',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Identifiant du compte utilisateur de connexion',
  `REMAMENT`        int(1)      NOT NULL                COMMENT 'Flag indiquant la remanence de la connexion',
  `SESSION_ID`      varchar(32) NOT NULL                COMMENT 'Identifiant de la session liee a la connexion',
  `IP`              varchar(39) NOT NULL                COMMENT 'Adresse IP de connexion',
  `START_DATE`      varchar(23) NOT NULL                COMMENT 'Date de début de connexion',
  `REASON`          varchar(12) NOT NULL                COMMENT 'Raison de fin de connexion',
  `REUSE_ATTEMPTED` int(1)      NOT NULL                COMMENT 'Flag si une reutilisation a deja ete tentee',
  -- Définition de la clé primaire de la table CONNECTION_HISTORY
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table CONNECTION_HISTORY vers la table ACCOUNT
  KEY `CONNECTION_HISTORY_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `CONNECTION_HISTORY_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des historiques de connexion';
