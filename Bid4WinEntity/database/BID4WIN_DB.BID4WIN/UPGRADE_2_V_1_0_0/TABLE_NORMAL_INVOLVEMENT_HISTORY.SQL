-- Création de la table NORMAL_INVOLVEMENT_HISTORY
CREATE TABLE `NORMAL_INVOLVEMENT_HISTORY`
(
  -- Définition des champs de la table NORMAL_INVOLVEMENT_HISTORY
  `ID`          int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l implication de credits historisee',
  `VERSION`     int(3)      NOT NULL                COMMENT 'Version de l implication de credits historisee',
  `CREATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de creation de l implication de credits historisee',
  `UPDATE_DATE` varchar(23) NOT NULL                COMMENT 'Date de modification de l implication de credits historisee',
  `USED_NB`     int(5)      NOT NULL                COMMENT 'Nombre de credits impliques sur la ventes aux encheres normal',
  `ACCOUNT_ID`  varchar(12) NOT NULL                COMMENT 'Compte utilisateur des credits impliques',
  `AUCTION_ID`  varchar(12) NOT NULL                COMMENT 'Ventes aux encheres normale impliquee dans les credits utilises',
  -- Définition de la clé primaire de la table NORMAL_INVOLVEMENT_HISTORY
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple ACCOUNT_ID/AUCTION_ID
  CONSTRAINT NORMAL_INVOLVEMENT_H_UNIQUE_ACCOUNT_AUCTION UNIQUE (`ACCOUNT_ID`, `AUCTION_ID`),
  -- Définition de la clé étrangère de la table NORMAL_INVOLVEMENT_HISTORY vers la table ACCOUNT
  KEY `NORMAL_INVOLVEMENT_HISTORY_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_INVOLVEMENT_HISTORY_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_INVOLVEMENT_HISTORY vers la table NORMAL_AUCTION
  KEY `NORMAL_INVOLVEMENT_HISTORY_2_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `NORMAL_INVOLVEMENT_HISTORY_2_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `NORMAL_AUCTION` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table d historisation des implications de credits';
