-- Création de la table HTML_STORAGE
CREATE TABLE `HTML_STORAGE`
(
  -- Définition des champs de la table HTML_STORAGE
  `ID`           int(10)      NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de la reference du stockage de page HTML',
  `VERSION`      int(3)       NOT NULL                COMMENT 'Version de la reference du stockage de page HTML',
  `CREATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de creation de la reference du stockage de page HTML',
  `UPDATE_DATE`  varchar(23)  NOT NULL                COMMENT 'Date de modification de la reference du stockage de page HTML',
  `UPDATE_FORCE` int(5)       NOT NULL                COMMENT 'Champ permettant le forcage de la modification de la reference du stockage de page HTML',
  `PATH`         varchar(150) NOT NULL                COMMENT 'Emplacement de stockage de la page HTML referencee',
  `NAME`         varchar(50)  NOT NULL                COMMENT 'Nom de la page HTML referencee',
  `FULL_PATH`    varchar(250) NOT NULL                COMMENT 'Chemin complet d acces a la page HTML referencee',
  `LANGUAGES`    varchar(30)  NOT NULL                COMMENT 'Langues de l utilisation de page HTML',
  -- Définition de la clé primaire de la table HTML_STORAGE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple de champs PATH/NAME
  CONSTRAINT HTML_STORAGE_UNIQUE_PATHDEF UNIQUE (`PATH`, `NAME`),
  -- Définition de l'unicité du champ FULLPATH
  CONSTRAINT HTML_STORAGE_UNIQUE_FULLPATH UNIQUE (`FULL_PATH`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des references des stockages de pages HTML';
