-- Création de la table RESOURCE_USAGE_STUB
CREATE TABLE `RESOURCE_USAGE_STUB`
(
  -- Définition des champs de la table RESOURCE_USAGE_STUB
  `ID`            int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l utilisation d une ressource',
  `VERSION`       int(3)       NOT NULL                COMMENT 'Version de l utilisation d une ressource',
  `CREATE_DATE`   varchar(23)  NOT NULL                COMMENT 'Date de creation de l utilisation d une ressource',
  `UPDATE_DATE`   varchar(23)  NOT NULL                COMMENT 'Date de modification de l utilisation d une ressource',
  `UPDATE_FORCE`  int(5)       NOT NULL                COMMENT 'Champs permettant le forcage de la modification de l utilisation d une ressource',
  `PATH`          varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de l utilisation d une ressource',
  `NAME`          varchar(50)  NOT NULL                COMMENT 'Nom de l utilisation d une ressource',
  `FULL_PATH`     varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a l utilisation d une ressource',
  `RESOURCE_TYPE` varchar(5)   NOT NULL                COMMENT 'Type de la ressource',
  `STORAGE_ID`    int(10)      NOT NULL                COMMENT 'Identifiant de la reference de la ressource',
  -- Définition de la clé primaire de la table RESOURCE_USAGE_STUB
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du trio de champs PATH/NAME/RESOURCE_TYPE
  CONSTRAINT RESOURCE_USAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`, `RESOURCE_TYPE`),
  -- Définition de l'unicité du champ FULLPATH
  CONSTRAINT RESOURCE_USAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`),
  -- Définition de la clé étrangère de la table RESOURCE_USAGE_STUB vers la table RESOURCE_STORAGE_STUB
  KEY `USAGE_2_STORAGE_FK` (`STORAGE_ID`),
  CONSTRAINT `USAGE_2_STORAGE_FK` FOREIGN KEY (`STORAGE_ID`) REFERENCES `RESOURCE_STORAGE_STUB` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des utilisations de ressources';
