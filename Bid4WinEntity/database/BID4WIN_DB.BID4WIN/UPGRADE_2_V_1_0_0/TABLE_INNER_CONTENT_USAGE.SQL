-- Création de la table INNER_CONTENT_USAGE
CREATE TABLE `INNER_CONTENT_USAGE`
(
  -- Définition des champs de la table INNER_CONTENT_USAGE
  `ID`           int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l utilisation de contenu',
  `VERSION`      int(3)       NOT NULL                COMMENT 'Version de l utilisation de contenu',
  `CREATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de creation de l utilisation de contenu',
  `UPDATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de modification de l utilisation de contenu',
  `UPDATE_FORCE` int(3)       NOT NULL                COMMENT 'Champ permettant le forcage de la modification de l utilisation de contenu',
  `POSITION`     int(3)       DEFAULT 0               COMMENT 'Position de l utilisation de contenu',
  `PATH`         varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de l utilisation de contenu',
  `NAME`         varchar(50)  NOT NULL                COMMENT 'Nom de l utilisation de contenu',
  `CONTENT_TYPE` varchar(5)   NOT NULL                COMMENT 'Type de contenu',
  `FULL_PATH`    varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a l utilisation de contenu',
  `LANGUAGES`    varchar(30)  NOT NULL                COMMENT 'Langues de l utilisation de contenu',
  `STORAGE_ID`   int(10)      NOT NULL                COMMENT 'Identifiant de la reference de stockage du contenu',
  `USAGE_TYPE`   varchar(10)  NOT NULL                COMMENT 'Type de l utilisation du contenu',
  `PRODUCT_ID`   varchar(12)  DEFAULT NULL            COMMENT 'Identifiant du produit utilisant potentiellement le contenu',
  -- Définition de la clé primaire de la table INNER_CONTENT_IMAGE_USAGE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple de champs PATH/NAME
  CONSTRAINT INNER_CONTENT_USAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`),
  -- Définition de l'unicité du champ FULL_PATH
  CONSTRAINT INNER_CONTENT_USAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`),
  -- Définition de la clé étrangère de la table INNER_CONTENT_USAGE vers la table INNER_CONTENT_STORAGE
  KEY `INNER_CONTENT_USAGE_2_STORAGE_FK` (`STORAGE_ID`),
  CONSTRAINT `INNER_CONTENT_USAGE_2_STORAGE_FK` FOREIGN KEY (`STORAGE_ID`) REFERENCES `INNER_CONTENT_STORAGE` (`ID`),
  -- Définition de la clé étrangère de la table INNER_CONTENT_USAGE vers la table PRODUCT
  KEY `INNER_CONTENT_USAGE_2_PRODUCT_FK` (`PRODUCT_ID`),
  CONSTRAINT `INNER_CONTENT_USAGE_2_PRODUCT_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des utilisations de contenu';
