-- Création de la table SUBSCRIPTION
CREATE TABLE `SUBSCRIPTION`
(
  -- Définition des champs de la table SUBSCRIPTION
  `ID`              varchar(16) NOT NULL COMMENT 'Identifiant de la reference d inscription',
  `VERSION`         int(1)      NOT NULL COMMENT 'Version de la reference d inscription',
  `CREATE_DATE`     varchar(23) NOT NULL COMMENT 'Date de creation de la reference d inscription',
  `UPDATE_DATE`     varchar(23) NOT NULL COMMENT 'Date de modification de la reference d inscription',
  `ACCOUNT_ID`      varchar(12) NOT NULL COMMENT 'Identifiant du compte utilisateur de l inscription',
  `VALIDATION_DATE` varchar(23)          COMMENT 'Date de validation de l inscription',
  -- Définition de la clé primaire de la table SUBSCRIPTION
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du champ ACCOUNT_ID
  CONSTRAINT SUBSCRIPTION_UNIQUE_ACCOUNT_ID UNIQUE (`ACCOUNT_ID`),
  -- Définition de la clé étrangère de la table SUBSCRIPTION vers la table ACCOUNT
  KEY `SUBSCRIPTION_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `SUBSCRIPTION_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des references d inscription';
