-- Création de la table PREBOOKED_BID_HISTORY
CREATE TABLE `PREBOOKED_BID_HISTORY`
(
  -- Définition des champs de la table PREBOOKED_BID_HISTORY
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l enchere avec pre-inscription historisee',
  `VERSION`         int(3)      NOT NULL                COMMENT 'Version de l enchere avec pre-inscription historisee',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation de l enchere avec pre-inscription historisee',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification de l enchere avec pre-inscription historisee',
  `BID_DATE`        varchar(23) NOT NULL                COMMENT 'Date de l enchere avec pre-inscription historisee',
  `POSITION`        int(5)      NOT NULL                COMMENT 'Position de l enchere historisee dans la vente avec pre-inscription',
  `AUCTION_ID`      varchar(12) NOT NULL                COMMENT 'Vente avec pre-inscription de l enchere historisee',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Compte utilisateur de l enchere avec pre-inscription historisee',
  -- Définition de la clé primaire de la table PREBOOKED_BID_HISTORY
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_BID_HISTORY vers la table PREBOOKED_AUCTION
  KEY `PREBOOKED_BID_HISTORY_2_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `PREBOOKED_BID_HISTORY_2_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `PREBOOKED_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_BID_HISTORY vers la table ACCOUNT
  KEY `PREBOOKED_BID_HISTORY_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `PREBOOKED_BID_HISTORY_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des historiques d encheres avec pre-inscription';
