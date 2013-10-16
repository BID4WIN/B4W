-- Création de la table INNER_CONTENT_STORAGE
CREATE TABLE `INNER_CONTENT_STORAGE`
(
  -- Définition des champs de la table INNER_CONTENT_STORAGE
  `ID`           int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la reference du stockage de contenu',
  `VERSION`      int(3)       NOT NULL                COMMENT 'Version de la reference du stockage de contenu',
  `CREATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de creation de la reference du stockage de contenu',
  `UPDATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de modification de la reference du stockage de contenu',
  `UPDATE_FORCE` int(5)       NOT NULL                COMMENT 'Champ permettant le forcage de la modification de la reference du stockage de contenu',
  `PATH`         varchar(150) NOT NULL                COMMENT 'Emplacement de stockage du contenu reference',
  `NAME`         varchar(50)  NOT NULL                COMMENT 'Nom du contenu reference',
  `CONTENT_TYPE` varchar(5)   NOT NULL                COMMENT 'Type de contenu',
  `FULL_PATH`    varchar(250) NOT NULL                COMMENT 'Chemin complet d acces au contenu reference',
  `LANGUAGES`    varchar(30)  NOT NULL                COMMENT 'Langues de l utilisation de contenu',
  -- Définition de la clé primaire de la table INNER_CONTENT_STORAGE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple de champs PATH/NAME
  CONSTRAINT INNER_CONTENT_STORAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`),
  -- Définition de l'unicité du champ FULLPATH
  CONSTRAINT INNER_CONTENT_STORAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des references des stockages de contenu';
