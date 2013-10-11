-- Création de la table NORMAL_BID
CREATE TABLE `NORMAL_BID`
(
  -- Définition des champs de la table NORMAL_BID
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l enchere normale',
  `VERSION`         int(3)      NOT NULL                COMMENT 'Version de l enchere normale',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation de l enchere normale',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification de l enchere normale',
  `POSITION`        int(5)      NOT NULL                COMMENT 'Position de l enchere dans la vente normale',
  `AUCTION_ID`      varchar(12) NOT NULL                COMMENT 'Vente de l enchere normale',
  `AUCTION_ID_LINK` varchar(12) DEFAULT NULL            COMMENT 'Lien vers la vente de l enchere normale',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Compte utilisateur de l enchere normale',
  -- Définition de la clé primaire de la table NORMAL_BID
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_BID vers la table NORMAL_AUCTION
  KEY `NORMAL_BID_2_NORMAL_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `NORMAL_BID_2_NORMAL_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `NORMAL_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_BID vers la table NORMAL_AUCTION
  KEY `NORMAL_BID_LINK_2_NORMAL_AUCTION_FK` (`AUCTION_ID_LINK`),
  CONSTRAINT `NORMAL_BID_LINK_2_NORMAL_AUCTION_FK` FOREIGN KEY (`AUCTION_ID_LINK`) REFERENCES `NORMAL_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_BID vers la table ACCOUNT
  KEY `NORMAL_BID_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_BID_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des encheres normales';
