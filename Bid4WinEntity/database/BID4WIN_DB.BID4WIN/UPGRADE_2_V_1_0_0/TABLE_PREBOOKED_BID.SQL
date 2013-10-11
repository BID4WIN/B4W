-- Création de la table PREBOOKED_BID
CREATE TABLE `PREBOOKED_BID`
(
  -- Définition des champs de la table PREBOOKED_BID
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l enchere avec pre-inscription',
  `VERSION`         int(3)      NOT NULL                COMMENT 'Version de l enchere avec pre-inscription',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation de l enchere avec pre-inscription',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification de l enchere avec pre-inscription',
  `POSITION`        int(5)      NOT NULL                COMMENT 'Position de l enchere dans la vente avec pre-inscription',
  `AUCTION_ID`      varchar(12) NOT NULL                COMMENT 'Vente de l enchere avec pre-inscription',
  `AUCTION_ID_LINK` varchar(12) DEFAULT NULL            COMMENT 'Lien vers la vente de l enchere avec pre-inscription',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Compte utilisateur de l enchere avec pre-inscription',
  -- Définition de la clé primaire de la table PREBOOKED_BID
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_BID vers la table PREBOOKED_AUCTION
  KEY `PREBOOKED_BID_2_PREBOOKED_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `PREBOOKED_BID_2_PREBOOKED_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `PREBOOKED_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_BID vers la table PREBOOKED_AUCTION
  KEY `PREBOOKED_BID_LINK_2_PREBOOKED_AUCTION_FK` (`AUCTION_ID_LINK`),
  CONSTRAINT `PREBOOKED_BID_LINK_2_PREBOOKED_AUCTION_FK` FOREIGN KEY (`AUCTION_ID_LINK`) REFERENCES `PREBOOKED_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_BID vers la table ACCOUNT
  KEY `PREBOOKED_BID_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `PREBOOKED_BID_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des encheres avec pre-inscription';
