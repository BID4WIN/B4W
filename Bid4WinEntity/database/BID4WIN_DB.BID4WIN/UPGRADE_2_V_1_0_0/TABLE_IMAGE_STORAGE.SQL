-- Création de la table IMAGE_STORAGE
CREATE TABLE `IMAGE_STORAGE`
(
  -- Définition des champs de la table IMAGE_STORAGE
  `ID`           int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la reference du stockage d image',
  `VERSION`      int(3)       NOT NULL                COMMENT 'Version de la reference du stockage d image',
  `CREATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de creation de la reference du stockage d image',
  `UPDATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de modification de la reference du stockage d image',
  `UPDATE_FORCE` int(5)       NOT NULL                COMMENT 'Champs permettant le forcage de la modification de la reference du stockage d image',
  `PATH`         varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de l image referencee',
  `NAME`         varchar(50)  NOT NULL                COMMENT 'Nom de l image referencee',
  `IMAGE_TYPE`   varchar(5)   NOT NULL                COMMENT 'Type de l image',
  `FULL_PATH`    varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a l image referencee',
  -- Définition de la clé primaire de la table IMAGE_STORAGE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du trio de champs PATH/NAME/IMAGE_TYPE
  CONSTRAINT IMAGE_STORAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`, `IMAGE_TYPE`),
  -- Définition de l'unicité du champ FULLPATH
  CONSTRAINT IMAGE_STORAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des references des stockages d images';
