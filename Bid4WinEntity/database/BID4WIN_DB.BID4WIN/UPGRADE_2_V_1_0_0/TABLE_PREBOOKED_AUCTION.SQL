-- Création de la table PREBOOKED_AUCTION
CREATE TABLE `PREBOOKED_AUCTION`
(
  -- Définition des champs de la table PREBOOKED_AUCTION
  `ID`                     varchar(12)  NOT NULL COMMENT 'Identifiant de la vente aux encheres avec pre-inscription',
  `VERSION`                int(5)       NOT NULL COMMENT 'Version de la vente aux encheres avec pre-inscription',
  `CREATE_DATE`            varchar(23)  NOT NULL COMMENT 'Date de creation de la vente aux encheres avec pre-inscription',
  `UPDATE_DATE`            varchar(23)  NOT NULL COMMENT 'Date de modification de la vente aux encheres avec pre-inscription',
  `ACCOUNT_ID`             varchar(12)           COMMENT 'Compte utilisateur ayant remporte la vente aux encheres avec pre-inscription',
  `PRODUCT_ID`             varchar(12)  NOT NULL COMMENT 'Produit de la vente aux encheres avec pre-inscription',
  `PRODUCT_PRICE`          varchar(250) NOT NULL COMMENT 'Prix du produit vendu aux encheres normale',
  `EXCHANGE_RATES`         varchar(250)          COMMENT 'Taux de change definis au moment de la validation de la vente',
  `STEP`                   varchar(10)  NOT NULL COMMENT 'Etape courante de la vente aux encheres avec pre-inscription',
  `STATUS`                 varchar(10)  NOT NULL COMMENT 'Status de la vente aux encheres avec pre-inscription',
  `BID_NB`                 int(6)       NOT NULL COMMENT 'Nombre d encheres de la vente',
  `BID_HISTORIZED`         int(1)       NOT NULL COMMENT 'Etat d historisation des enchères la vente',
  `INVOLVED_CREDIT_NB`     int(6)       NOT NULL COMMENT 'Nombre de credits impliques sur la vente aux encheres',
  `INVOLVEMENT_VALUE`      float(10)    NOT NULL COMMENT 'Valeur totale des implications sur la vente aux enchères',
  `INVOLVEMENT_CURRENCY`   varchar(10)  NOT NULL COMMENT 'Monnaie de la valeur totale des implications sur la vente aux enchères',
  `INVOLVEMENT_HISTORIZED` int(1)       NOT NULL COMMENT 'Etat d historisation des implications sur la vente',
  `START_DATE`             varchar(23)  NOT NULL COMMENT 'Date de demarrage prevue de la vente aux encheres avec pre-inscription',
  `END_DATE`               varchar(23)  NOT NULL COMMENT 'Date de cloture prevue de la vente aux encheres avec pre-inscription',
  `COUNTDOWN_INIT`         int(6)       NOT NULL COMMENT 'Compte à rebours initial de fermeture de la vente aux encheres',
  `COUNTDOWN_ADD`          int(6)       NOT NULL COMMENT 'Compte à rebours additionnel de fermeture de la vente aux encheres',
  `CREDIT_NB_PER_BID`      int(3)       NOT NULL COMMENT 'Nombre de credits a utiliser par l utilisateur a chaque enchere',
  `BID_INCREMENT_VALUE`    float(10)    NOT NULL COMMENT 'Valeur du montant d un incrément d enchere',
  `BID_INCREMENT_CURRENCY` varchar(10)  NOT NULL COMMENT 'Monnaie de la valeur du montant d un incrément d enchere',
  `CREDIT_NB_THRESHOLD`    int(5)                COMMENT 'Seuil du nombre de credits en dessous duquel la vente sera annulee',
  `CREDIT_NB_PAID_BONUS`   int(1)                COMMENT 'Nombre de credits bonus par credit paye utilise sur une vente annulee',
  -- Définition de la clé primaire de la table PREBOOKED_AUCTION
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_AUCTION vers la table ACCOUNT
  KEY `PREBOOKED_AUCTION_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `PREBOOKED_AUCTION_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
  -- Définition de la clé étrangère de la table PREBOOKED_AUCTION vers la table PRODUCT
  KEY `PREBOOKED_AUCTION_2_PRODUCT_FK` (`PRODUCT_ID`),
  CONSTRAINT `PREBOOKED_AUCTION_2_PRODUCT_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des ventes aux encheres avec pre-inscription';
