-- Cr�ation de la table CREDIT_BUNDLE_HISTORY
CREATE TABLE `CREDIT_BUNDLE_HISTORY`
(
  -- D�finition des champs de la table CREDIT_BUNDLE_HISTORY
  `ID`            int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l historique de lot de credits',
  `VERSION`       int(3)      NOT NULL                COMMENT 'Version de l historique de lot de credits',
  `CREATE_DATE`   varchar(23) NOT NULL                COMMENT 'Date de creation de l historique de lot de credits',
  `UPDATE_DATE`   varchar(23) NOT NULL                COMMENT 'Date de modification de l historique de lot de credits',
  `CREDIT_NB`     int(3)      NOT NULL                COMMENT 'Nombre de credits du lot',
  `ORIGIN`        varchar(15) NOT NULL                COMMENT 'Type de provenance des cr�dits du lot',
  `REFERENCE`     varchar(12) NOT NULL                COMMENT 'Reference de provenance des cr�dits du lot',
  `UNIT_VALUE`    float(10)   NOT NULL                COMMENT 'Valeur unitaire des credits du lot',
  `UNIT_CURRENCY` varchar(10) NOT NULL                COMMENT 'Monnaie de la valeur unitaire des credits du lot',
  `ACCOUNT_ID`    varchar(12) NOT NULL                COMMENT 'Identifiant du compte utilisateur du lot de credits',
  -- D�finition de la cl� primaire de la table CREDIT_BUNDLE_HISTORY
  PRIMARY KEY (`ID`),
  -- D�finition de l'unicit� du couple ORIGIN/REFERENCE
  CONSTRAINT BUNDLE_UNIQUE_ORIGIN_REFERENCE UNIQUE (`ORIGIN`, `REFERENCE`),
  -- D�finition de la cl� �trang�re de la table CREDIT_BUNDLE_HISTORY vers la table ACCOUNT
  KEY `CREDIT_BUNDLE_HISTORY_2_ACCOUNT_FK` (`ACCOUNT_ID`),
  CONSTRAINT `CREDIT_BUNDLE_HISTORY_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des historiques de lot de credits';
