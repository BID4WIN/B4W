-- Création de la table IMAGE_USAGE
CREATE TABLE `IMAGE_USAGE`
(
  -- Définition des champs de la table IMAGE_USAGE
  `ID`           int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l utilisation d image',
  `VERSION`      int(3)       NOT NULL                COMMENT 'Version de l utilisation d image',
  `CREATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de creation de l utilisation d image',
  `UPDATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de modification de l utilisation d image',
  `UPDATE_FORCE` int(3)       NOT NULL                COMMENT 'Champs permettant le forcage de la modification de l utilisation d image',
  `POSITION`     int(3)       DEFAULT 0               COMMENT 'Position de l utilisation d image',
  `PATH`         varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de l utilisation d image',
  `NAME`         varchar(50)  NOT NULL                COMMENT 'Nom de l utilisation d image',
  `IMAGE_TYPE`   varchar(5)   NOT NULL                COMMENT 'Type de l image',
  `FULL_PATH`    varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a l utilisation d image',
  `STORAGE_ID`   int(10)      NOT NULL                COMMENT 'Identifiant de la reference de stockage de l image',
  `USAGE_TYPE`   varchar(10)  NOT NULL                COMMENT 'Type de l utilisation d image',
  `PRODUCT_ID`   varchar(12)  DEFAULT NULL            COMMENT 'Identifiant du produit utilisant potentiellement l image',
  -- Définition de la clé primaire de la table PRODUCT_IMAGE_USAGE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du trio de champs PATH/NAME/IMAGE_TYPE
  CONSTRAINT IMAGE_USAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`, `IMAGE_TYPE`),
  -- Définition de l'unicité du champ FULL_PATH
  CONSTRAINT IMAGE_USAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`),
  -- Définition de la clé étrangère de la table IMAGE_USAGE vers la table IMAGE_STORAGE
  KEY `IMAGE_USAGE_2_IMAGE_STORAGE_FK` (`STORAGE_ID`),
  CONSTRAINT `IMAGE_USAGE_2_IMAGE_STORAGE_FK` FOREIGN KEY (`STORAGE_ID`) REFERENCES `IMAGE_STORAGE` (`ID`),
  -- Définition de la clé étrangère de la table IMAGE_USAGE vers la table PRODUCT
  KEY `IMAGE_USAGE_2_PRODUCT_FK` (`PRODUCT_ID`),
  CONSTRAINT `IMAGE_USAGE_2_PRODUCT_FK` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `PRODUCT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des utilisations d images';
