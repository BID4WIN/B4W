-- Cr�ation de la table NORMAL_BID_HISTORY
CREATE TABLE `NORMAL_BID_HISTORY`
(
  -- D�finition des champs de la table NORMAL_BID
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l enchere normale historisee',
  `VERSION`         int(3)      NOT NULL                COMMENT 'Version de l enchere normale historisee',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation de l enchere normale historisee',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification de l enchere normale historisee',
  `BID_DATE`        varchar(23) NOT NULL                COMMENT 'Date de l enchere normale historisee',
  `POSITION`        int(5)      NOT NULL                COMMENT 'Position de l enchere historisee dans la vente normale',
  `AUCTION_ID`      varchar(12) NOT NULL                COMMENT 'Vente normale de l enchere historisee',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Compte utilisateur de l enchere normale historisee',
  -- D�finition de la cl� primaire de la table NORMAL_BID_HISTORY
  PRIMARY KEY (`ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_BID_HISTORY vers la table NORMAL_AUCTION
  KEY `NORMAL_BID_HISTORY_2_AUCTION_FK` (`AUCTION_ID`),
  CONSTRAINT `NORMAL_BID_HISTORY_2_AUCTION_FK` FOREIGN KEY (`AUCTION_ID`) REFERENCES `NORMAL_AUCTION` (`ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_BID_HISTORY vers la table ACCOUNT
  KEY `NORMAL_BID_HISTORY_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_BID_HISTORY_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des historiques d encheres normales';
