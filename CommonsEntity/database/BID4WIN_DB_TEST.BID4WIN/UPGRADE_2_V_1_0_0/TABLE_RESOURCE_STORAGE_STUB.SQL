-- Création de la table RESOURCE_STORAGE_STUB
CREATE TABLE `RESOURCE_STORAGE_STUB`
(
  -- Définition des champs de la table RESOURCE_STORAGE_STUB
  `ID`            int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la ressource',
  `VERSION`       int(3)       NOT NULL                COMMENT 'Version de la ressource',
  `CREATE_DATE`   varchar(23)  NOT NULL                COMMENT 'Date de creation de la ressource',
  `UPDATE_DATE`   varchar(23)  NOT NULL                COMMENT 'Date de modification de la ressource',
  `UPDATE_FORCE`  int(5)       NOT NULL                COMMENT 'Champs permettant le forcage de la modification de la ressource',
  `PATH`          varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de la ressource',
  `NAME`          varchar(50)  NOT NULL                COMMENT 'Nom de la ressource',
  `RESOURCE_TYPE` varchar(5)   NOT NULL                COMMENT 'Type de la ressource',
  `FULL_PATH`     varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a la ressource',
  -- Définition de la clé primaire de la table RESOURCE_STORAGE_STUB
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du trio de champs PATH/NAME/RESOURCE_TYPE
  CONSTRAINT RESOURCE_STORAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`, `RESOURCE_TYPE`),
  -- Définition de l'unicité du champ FULLPATH
  CONSTRAINT RESOURCE_STORAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des ressources';
