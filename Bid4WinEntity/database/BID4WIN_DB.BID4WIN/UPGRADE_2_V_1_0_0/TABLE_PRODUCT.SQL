-- Création de la table PRODUCT
CREATE TABLE `PRODUCT`
(
  -- Définition des champs de la table PRODUCT
  `ID`           varchar(12)  NOT NULL COMMENT 'Identifiant du produit',
  `VERSION`      int(3)       NOT NULL COMMENT 'Version du produit',
  `CREATE_DATE`  varchar(23)  NOT NULL COMMENT 'Date de creation du produit',
  `UPDATE_DATE`  varchar(23)  NOT NULL COMMENT 'Date de modification du produit',
  `UPDATE_FORCE` int(3)       NOT NULL COMMENT 'Champs permettant le forcage de la modification du produit',
  `REFERENCE`    varchar(30)  NOT NULL COMMENT 'Reference du produit',
  `NAMES`        varchar(250) NOT NULL COMMENT 'Nom du produit dans differentes langues dont celle par defaut',
  `SUMMARIES`    text         NOT NULL COMMENT 'Resume de la descriptions du produit dans differentes langues dont celle par defaut',
  `PRICES`       varchar(250) NOT NULL COMMENT 'Prix du produit dans differentes monnaies dont celle par defaut',
  -- Définition de la clé primaire de la table PRODUCT
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des produits';
