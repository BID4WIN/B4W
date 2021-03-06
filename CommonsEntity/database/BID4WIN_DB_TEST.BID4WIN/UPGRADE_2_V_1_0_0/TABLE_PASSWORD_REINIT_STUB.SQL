-- Cr�ation de la table PASSWORD_REINIT_STUB
CREATE TABLE `PASSWORD_REINIT_STUB`
(
  -- D�finition des champs de la table PASSWORD_REINIT_STUB
  `ID`          varchar(16) NOT NULL COMMENT 'Identifiant de la re-initialisation de mot de passe',
  `VERSION`     int(1)      NOT NULL COMMENT 'Version de la re-initialisation de mot de passe',
  `CREATE_DATE` varchar(23) NOT NULL COMMENT 'Date de creation de la re-initialisation de mot de passe',
  `UPDATE_DATE` varchar(23) NOT NULL COMMENT 'Date de modification de la re-initialisation de mot de passe',
  `ACCOUNT_ID`  varchar(12) NOT NULL COMMENT 'Identifiant du compte utilisateur de la re-initialisation de mot de passe',
  -- D�finition de la cl� primaire de la table PASSWORD_REINIT_STUB
  PRIMARY KEY (`ID`),
  -- D�finition de l'unicit� du champ ACCOUNT_ID
  CONSTRAINT PASS_REINIT_STUB_UNIQUE_ACCOUNT_ID UNIQUE (`ACCOUNT_ID`),
  -- D�finition de la cl� �trang�re de la table PASSWORD_REINIT_STUB vers la table ACCOUNT_STUB
  KEY `PASS_REINIT_STUB_2_ACCOUNT_STUB_FK` (`ACCOUNT_ID`),
  CONSTRAINT `PASS_REINIT_STUB_2_ACCOUNT_STUB_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT_STUB` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des re-initialisations de mot de passe';
