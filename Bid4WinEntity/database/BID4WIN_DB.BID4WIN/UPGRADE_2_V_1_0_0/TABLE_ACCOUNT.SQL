-- Création de la table ACCOUNT
CREATE TABLE `ACCOUNT`
(
  -- Définition des champs de la table ACCOUNT
  `ID`             varchar(12) NOT NULL     COMMENT 'Identifiant du compte utilisateur',
  `VERSION`        int(5)      NOT NULL     COMMENT 'Version du compte utilisateur',
  `CREATE_DATE`    varchar(23) NOT NULL     COMMENT 'Date de creation du compte utilisateur',
  `UPDATE_DATE`    varchar(23) NOT NULL     COMMENT 'Date de modification du compte utilisateur',

  `LOGIN`          varchar(30) NOT NULL     COMMENT 'Identifiant de connexion du compte utilisateur',
  `PASSWORD`       varchar(30) NOT NULL     COMMENT 'Mot de passe de connexion du compte utilisateur',
  `ROLE`           varchar(5)  NOT NULL     COMMENT 'Role du compte utilisateur',
  `EMAIL`          varchar(50) NOT NULL     COMMENT 'Email du compte utilisateur',

  `GENDER`         varchar(5)               COMMENT 'Genre de l utilisateur du compte',
  `FIRST_NAME`     varchar(40)              COMMENT 'Prenom de l utilisateur du compte',
  `MIDDLE_NAME`    varchar(40)              COMMENT 'Second prenom de l utilisateur du compte',
  `LAST_NAME`      varchar(40)              COMMENT 'Nom de famille de l utilisateur du compte',
  `BIRTH_DATE`     varchar(10)              COMMENT 'Date de naissance de l utilisateur du compte',
  
  `PREF_LANGUAGE`  varchar(2)  NOT NULL     COMMENT 'Preference de langue du compte utilisateur',
  `CREDIT_NB`      INT(5)      NOT NULL     COMMENT 'Nombre de droits a encherir disponibles pour le compte utilisateur',
  `CREDIT_USED_NB` INT(5)      NOT NULL     COMMENT 'Nombre de droits a encherir utilises par le compte utilisateur',
  
  `SPONSOR_ID`     varchar(12) DEFAULT NULL COMMENT 'Identifiant du compte utilisateur parrain',
  -- Définition de la clé primaire de la table ACCOUNT
  PRIMARY KEY (`ID`),
  -- Définition de l'unicité du champ LOGIN
  CONSTRAINT ACCOUNT_UNIQUE_LOGIN UNIQUE (`LOGIN`),
  -- Définition de l'unicité du champ EMAIL
  CONSTRAINT ACCOUNT_UNIQUE_EMAIL UNIQUE (`EMAIL`),
  -- Définition de la clé étrangère de la table ACCOUNT vers la table ACCOUNT
  KEY `ACCOUNT_2_ACCOUNT_SPONSOR_FK` (`SPONSOR_ID`),
  CONSTRAINT `ACCOUNT_2_ACCOUNT_SPONSOR_FK` FOREIGN KEY (`SPONSOR_ID`) REFERENCES `ACCOUNT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='Table des comptes utilisateur';
