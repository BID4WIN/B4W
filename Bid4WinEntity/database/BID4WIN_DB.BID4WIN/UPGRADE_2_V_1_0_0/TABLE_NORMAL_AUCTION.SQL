-- Cr�ation de la table NORMAL_AUCTION
CREATE TABLE `NORMAL_AUCTION`
(
  -- D�finition des champs de la table NORMAL_AUCTION
  `ID`                     varchar(12)  NOT NULL COMMENT 'Identifiant de la vente aux encheres normale',
  `VERSION`                int(5)       NOT NULL COMMENT 'Version de la vente aux encheres normale',
  `CREATE_DATE`            varchar(23)  NOT NULL COMMENT 'Date de creation de la vente aux encheres normale',
  `UPDATE_DATE`            varchar(23)  NOT NULL COMMENT 'Date de modification de la vente aux encheres normale',
  `ACCOUNT_ID`             varchar(12)           COMMENT 'Compte utilisateur ayant remporte la vente aux encheres normale',
  `PRODUCT_ID`             varchar(12)  NOT NULL COMMENT 'Produit vendu aux encheres normale',
  `PRODUCT_PRICE`          varchar(250) NOT NULL COMMENT 'Prix du produit vendu aux encheres normale',
  `EXCHANGE_RATES`         varchar(250)          COMMENT 'Taux de change definis au moment de la validation de la vente',
  `STEP`                   varchar(10)  NOT NULL COMMENT 'Etape courante de la vente aux encheres normale',
  `STATUS`                 varchar(10)  NOT NULL COMMENT 'Status de la vente aux encheres normale',
  `BID_NB`                 int(6)       NOT NULL COMMENT 'Nombre d encheres de la vente',
  `BID_HISTORIZED`         int(1)       NOT NULL COMMENT 'Etat d historisation des ench�res la vente',
  `INVOLVED_CREDIT_NB`     int(6)       NOT NULL COMMENT 'Nombre de credits impliques sur la vente aux encheres',
  `INVOLVEMENT_VALUE`      float(10)    NOT NULL COMMENT 'Valeur totale des implications sur la vente aux encheres',
  `INVOLVEMENT_CURRENCY`   varchar(10)  NOT NULL COMMENT 'Monnaie de la valeur totale des implications sur la vente aux encheres',
  `INVOLVEMENT_HISTORIZED` int(1)       NOT NULL COMMENT 'Etat d historisation des implications sur la vente',
  `START_DATE`             varchar(23)  NOT NULL COMMENT 'Date de demarrage prevue de la vente aux encheres normale',
  `END_DATE`               varchar(23)  NOT NULL COMMENT 'Date de cloture prevue de la vente aux encheres normale',
  `COUNTDOWN_INIT`         int(6)       NOT NULL COMMENT 'Compte � rebours initial de fermeture de la vente aux encheres',
  `COUNTDOWN_ADD`          int(6)       NOT NULL COMMENT 'Compte � rebours additionnel de fermeture de la vente aux encheres',
  `CREDIT_NB_PER_BID`      int(3)       NOT NULL COMMENT 'Nombre de credits a utiliser par l utilisateur a chaque enchere',
  `BID_INCREMENT_VALUE`    float(10)    NOT NULL COMMENT 'Valeur du montant d un incr�ment d enchere',
  `BID_INCREMENT_CURRENCY` varchar(10)  NOT NULL COMMENT 'Monnaie de la valeur du montant d un incr�ment d enchere',
  `CREDIT_NB_THRESHOLD`    int(5)                COMMENT 'Seuil du nombre de credits en dessous duquel la vente sera annulee',
  `CREDIT_NB_PAID_BONUS`   int(1)                COMMENT 'Nombre de credits bonus par credit paye utilise sur une vente annulee',
  -- D�finition de la cl� primaire de la table NORMAL_AUCTION
  PRIMARY KEY (`ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_AUCTION vers la table ACCOUNT
  KEY `NORMAL_AUCTION_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `NORMAL_AUCTION_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_AUCTION vers la table PRODUCT
  KEY `NORMAL_AUCTION_2_PRODUCT_FK` (`PRODUCT_ID`),
  CONSTRAINT `NORMAL_AUCTION_2_PRODUCT_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des ventes aux encheres normales';
