-- Création de la table ACCOUNT_STUB
CREATE TABLE `ACCOUNT_STUB`
(
  -- Définition des champs de la table ACCOUNT_STUB
  `ID`            varchar(12) NOT NULL COMMENT 'Identifiant du compte utilisateur',
  `VERSION`       int(5)      NOT NULL COMMENT 'Version du compte utilisateur',
  `CREATE_DATE`   varchar(23) NOT NULL COMMENT 'Date de creation du compte utilisateur',
  `UPDATE_DATE`   varchar(23) NOT NULL COMMENT 'Date de modification du compte utilisateur',
  `LOGIN`         varchar(30) NOT NULL COMMENT 'Identifiant de connexion du compte utilisateur',
  `PASSWORD`      varchar(30) NOT NULL COMMENT 'Mot de passe de connexion du compte utilisateur',
  `ROLE`          varchar(5)  NOT NULL COMMENT 'Role du compte utilisateur',
  `EMAIL`         varchar(50) NOT NULL COMMENT 'Email du compte utilisateur',
  -- Définition de la clé primaire de la table ACCOUNT_STUB
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du champ LOGIN
  CONSTRAINT ACCOUNT_STUB_UNIQUE_LOGIN UNIQUE (`LOGIN`),
  -- Définition de l'unicité du champ EMAIL
  CONSTRAINT ACCOUNT_STUB_UNIQUE_EMAIL UNIQUE (`EMAIL`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des comptes utilisateur';
