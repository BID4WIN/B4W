-- Création de la table NORMAL_INVOLVEMENT
CREATE TABLE `NORMAL_INVOLVEMENT`
(
  -- Définition des champs de la table NORMAL_INVOLVEMENT
  `ID`          int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l implication de credits',
  `VERSION`     int(3)      NOT NULL                COMMENT 'Version de l implication de credits',
  `CREATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de creation de l implication de credits',
  `UPDATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de modification de l implication de credits',
  `USED_NB`     int(5)      NOT NULL                COMMENT 'Nombre de credits impliques sur la vente aux encheres',
  `ACCOUNT_ID`  varchar(12) NOT NULL                COMMENT 'Compte utilisateur des credits impliques',
  `AUCTION_ID`  varchar(12) NOT NULL                COMMENT 'Vente aux encheres impliquee dans les credits utilises',
  `HISTORY_ID`  int(10)     DEFAULT NULL            COMMENT 'Lien vers l historisation de l implication de credits',
  -- Définition de la clé primaire de la table NORMAL_INVOLVEMENT
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple ACCOUNT_ID/AUCTION_ID
  CONSTRAINT NORMAL_INVOLVEMENT_UNIQUE_ACCOUNT_AUCTION UNIQUE (`ACCOUNT_ID`, `AUCTION_ID`),
  -- Définition de la clé étrangère de la table NORMAL_INVOLVEMENT vers la table ACCOUNT
  KEY `NORMAL_INVOLVEMENT_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_INVOLVEMENT_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_INVOLVEMENT vers la table NORMAL_AUCTION
  KEY `NORMAL_INVOLVEMENT_2_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `NORMAL_INVOLVEMENT_2_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `NORMAL_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_INVOLVEMENT vers la table NORMAL_INVOLVEMENT_HISTORY
  KEY `NORMAL_INVOLVEMENT_2_HISTORY_FK` (`HISTORY_ID`),
  CONSTRAINT `NORMAL_INVOLVEMENT_2_HISTORY_FK` FOREIGN KEY (`HISTORY_ID`) REFERENCES `NORMAL_INVOLVEMENT_HISTORY` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table d implications de credits sur une vente aux encheres';
