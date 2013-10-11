-- Création de la table FOO_EMBEDDED_LIST
CREATE TABLE `FOO_EMBEDDED_LIST`
(
  -- Définition des champs de la table FOO_CHILD
  `FOO_ID` int(10)     NOT NULL COMMENT 'Identifiant de l objet proprietaire de l objet',
  `INDEX_` int(11)     NOT NULL COMMENT 'Index de l objet dans la liste de son proprietaire',
  `KEY_`   varchar(20) NOT NULL COMMENT 'Cle de l objet',
  `VALUE`  varchar(20) NOT NULL COMMENT 'Valeur de l objet',
  -- Définition de la clé étrangère de la table FOO_EMBEDDED_LIST vers la table FOO
  KEY `FOO_EMBEDDED_LIST_2_FOO_FK` (`FOO_ID`),
  CONSTRAINT `FOO_EMBEDDED_LIST_2_FOO_FK` FOREIGN KEY (`FOO_ID`) REFERENCES `FOO` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
