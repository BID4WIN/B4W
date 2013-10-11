-- Création de la table CREDIT_BUNDLE
CREATE TABLE `CREDIT_BUNDLE`
(
  -- Définition des champs de la table CREDIT_BUNDLE
  `ID`              int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant du lot de credits',
  `VERSION`         int(3)      NOT NULL                COMMENT 'Version du lot de credits',
  `CREATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de creation du lot de credits',
  `UPDATE_DATE`     varchar(23) NOT NULL                COMMENT 'Date de modification du lot de credits',
  `INITIAL_NB`      int(3)      NOT NULL                COMMENT 'Nombre initial de credits du lot',
  `CURRENT_NB`      int(3)      NOT NULL                COMMENT 'Nombre courant de credits du lot',
  `ORIGIN`          varchar(15) NOT NULL                COMMENT 'Type de provenance des crédits du lot',
  `REFERENCE`       varchar(12) NOT NULL                COMMENT 'Reference de provenance des crédits du lot',
  `UNIT_VALUE`      float(10)   NOT NULL                COMMENT 'Valeur unitaire des credits du lot',
  `UNIT_CURRENCY`   varchar(10) NOT NULL                COMMENT 'Monnaie de la valeur unitaire des credits du lot',
  `ACCOUNT_ID`      varchar(12) NOT NULL                COMMENT 'Identifiant du compte utilisateur du lot de credits',
  `ACCOUNT_ID_LINK` varchar(12) DEFAULT NULL            COMMENT 'Lien vers le compte utilisateur du lot de credits',
  `HISTORY_ID`      int(10)     DEFAULT NULL            COMMENT 'Lien vers l historisation du lot de credits',
  -- Définition de la clé primaire de la table CREDIT_BUNDLE
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du couple ORIGIN/REFERENCE
  CONSTRAINT BUNDLE_UNIQUE_ORIGIN_REFERENCE UNIQUE (`ORIGIN`, `REFERENCE`),
  -- Définition de la clé étrangère de la table CREDIT_BUNDLE vers la table ACCOUNT
  KEY `CREDIT_BUNDLE_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `CREDIT_BUNDLE_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`),
  -- Définition de la clé étrangère de la table CREDIT_BUNDLE vers la table ACCOUNT
  KEY `CREDIT_BUNDLE_LINK_2_ACCOUNT_FK` (`ACCOUNT_ID_LINK`),
  CONSTRAINT `CREDIT_BUNDLE_LINK_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID_LINK`) REFERENCES `ACCOUNT` (`ID`),
  -- Définition de la clé étrangère de la table CREDIT_BUNDLE vers la table CREDIT_BUNDLE_HISTORY
  KEY `CREDIT_BUNDLE_LINK_2_HISTORY_FK` (`HISTORY_ID`),
  CONSTRAINT `CREDIT_BUNDLE_LINK_2_HISTORY_FK` FOREIGN KEY (`HISTORY_ID`) REFERENCES `CREDIT_BUNDLE_HISTORY` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des lots de credits';
