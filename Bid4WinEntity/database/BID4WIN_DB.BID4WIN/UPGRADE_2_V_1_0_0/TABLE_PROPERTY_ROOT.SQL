-- Création de la table PROPERTY_ROOT
CREATE TABLE `PROPERTY_ROOT`
(
  -- Définition des champs de la table PROPERTY_ROOT
  `ID`           int(1)      NOT NULL COMMENT 'Identifiant de la propriete racine',
  `VERSION`      int(5)      NOT NULL COMMENT 'Version de la propriete racine',
  `CREATE_DATE`  varchar(23) NOT NULL COMMENT 'Date de creation de la propriete racine',
  `UPDATE_DATE`  varchar(23) NOT NULL COMMENT 'Date de modification de la propriete racine',
  `UPDATE_FORCE` int(5)      NOT NULL COMMENT 'Champs permettant le forcage de la modification de la propriete racine',
  -- Définition de la clé primaire de la table PROPERTY_ROOT
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table racine des proprietes';
