-- Création de la table NORMAL_BOT
CREATE TABLE `NORMAL_BOT`
(
  -- Définition des champs de la table NORMAL_BOT
  `ID`                int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant du robot d encheres normales',
  `VERSION`           int(3)      NOT NULL                COMMENT 'Version du robot d encheres normales',
  `CREATE_DATE`       varchar(23) NOT NULL                COMMENT 'Date de creation du robot d encheres normales',
  `UPDATE_DATE`       varchar(23) NOT NULL                COMMENT 'Date de modification du robot d encheres normales',
  `MIN_BID_POSITION`  int(5)      NOT NULL                COMMENT 'Position minimum autorisee des encheres du robot',
  `MAX_BID_POSITION`  int(5)      NOT NULL                COMMENT 'Position maximum autorisee des encheres du robot',
  `LAST_BID_POSITION` int(5)      NOT NULL                COMMENT 'Derniere position d enchere du robot dans la vente',
  `MAX_BID_NB`        int(5)      NOT NULL                COMMENT 'Nombre d encheres maximum autorisees pour le robot',
  `USED_BID_NB`       int(5)      NOT NULL                COMMENT 'Nombre d encheres positionnees par le robot sur la vente',
  `AUCTION_ID`        varchar(12) NOT NULL                COMMENT 'Vente du robot d encheres normales',
  `ACCOUNT_ID`        varchar(12) NOT NULL                COMMENT 'Compte utilisateur du robot d encheres normales',
  -- Définition de la clé primaire de la table NORMAL_BOT
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple ACCOUNT_ID/AUCTION_ID
  CONSTRAINT NORMAL_BOT_UNIQUE_ACCOUNT_AUCTION UNIQUE (`ACCOUNT_ID`, `AUCTION_ID`),
  -- Définition de la clé étrangère de la table NORMAL_BOT vers la table NORMAL_AUCTION
  KEY `NORMAL_BOT_2_NORMAL_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `NORMAL_BOT_2_NORMAL_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `NORMAL_AUCTION` (`ID`),
  -- Définition de la clé étrangère de la table NORMAL_BOT vers la table ACCOUNT
  KEY `NORMAL_BOT_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_BOT_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des robots d encheres normales';
