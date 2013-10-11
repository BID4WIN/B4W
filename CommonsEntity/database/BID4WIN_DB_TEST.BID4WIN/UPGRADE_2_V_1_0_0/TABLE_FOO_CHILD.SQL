-- Création de la table FOO_CHILD
CREATE TABLE `FOO_CHILD`
(
  -- Définition des champs de la table FOO_CHILD
  `ID`          varchar(20) NOT NULL COMMENT 'Identifiant de l objet',
  `VERSION`     int(3)      NOT NULL COMMENT 'Version de l objet',
  `CREATE_DATE` varchar(23) NOT NULL COMMENT 'Date de creation de l objet',
  `UPDATE_DATE` varchar(23) NOT NULL COMMENT 'Date de modification de l objet',
  `TYPE`        varchar(20) NOT NULL COMMENT 'Type de l objet',
  `PARENT_ID`   int(10)     NOT NULL COMMENT 'Identifiant de l objet parent de l objet',
  `VALUE`       varchar(20) NOT NULL COMMENT 'Valeur de l objet',
  `DATE_`        varchar(23) NOT NULL COMMENT 'Date de l objet',
  -- Définition de la clé primaire de la table FOO_CHILD
  PRIMARY KEY (`ID`),
  -- Définition de la clé étrangère de la table FOO_CHILD vers la table FOO
  KEY `FOO_CHILD_2_FOO_FK` (`PARENT_ID`),
  CONSTRAINT `FOO_CHILD_2_FOO_FK` FOREIGN KEY (`PARENT_ID`) REFERENCES `FOO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
