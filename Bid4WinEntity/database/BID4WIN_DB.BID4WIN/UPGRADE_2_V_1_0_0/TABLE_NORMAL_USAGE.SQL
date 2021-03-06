-- Cr�ation de la table NORMAL_USAGE
CREATE TABLE `NORMAL_USAGE`
(
  -- D�finition des champs de la table NORMAL_USAGE
  `ID`             int(10)     NOT NULL AUTO_INCREMENT COMMENT 'Identifiant de l utilisation de credits',
  `VERSION`        int(3)      NOT NULL                COMMENT 'Version de l utilisation de credits',
  `CREATE_DATE`    varchar(23) NOT NULL                COMMENT 'Date de creation de l utilisation de credits',
  `UPDATE_DATE`    varchar(23) NOT NULL                COMMENT 'Date de modification de l utilisation de credits',
  `USED_NB`        int(3)      NOT NULL                COMMENT 'Nombre de credits utilises',
  `BUNDLE_ID`      int(10)     NOT NULL                COMMENT 'Lot d origine des credits utilises',
  `INVOLVEMENT_ID` int(10)     NOT NULL                COMMENT 'Implication des credits utilises',
  -- D�finition de la cl� primaire de la table NORMAL_USAGE
  PRIMARY KEY (`ID`),
  -- D�finition de l'unicit� du couple BUNDLE_ID/INVOLVEMENT_ID
  CONSTRAINT NORMAL_USAGE_UNIQUE_BUNDLE_INVOLVEMENT UNIQUE (`BUNDLE_ID`, `INVOLVEMENT_ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_USAGE vers la table CREDIT_BUNDLE
  KEY `NORMAL_USAGE_2_BUNDLE_FK` (`BUNDLE_ID`),
  CONSTRAINT `NORMAL_USAGE_2_BUNDLE_FK` FOREIGN KEY (`BUNDLE_ID`) REFERENCES `CREDIT_BUNDLE` (`ID`),
  -- D�finition de la cl� �trang�re de la table NORMAL_USAGE vers la table NORMAL_INVOLVEMENT
  KEY `NORMAL_USAGE_2_INVOLVEMENT_FK` (`INVOLVEMENT_ID`),
  CONSTRAINT `NORMAL_USAGE_2_INVOLVEMENT_FK` FOREIGN KEY (`INVOLVEMENT_ID`) REFERENCES `NORMAL_INVOLVEMENT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table d utilisatations de credits sur une vente aux ench�res';
