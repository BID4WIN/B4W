-- Modification de la table ACCOUNT
ALTER TABLE ACCOUNT

ADD GENDER       varchar(5)  COMMENT 'Genre de l utilisateur du compte'
    AFTER EMAIL,
ADD FIRST_NAME  varchar(40) COMMENT 'Prenom de l utilisateur du compte'
    AFTER GENDER,
ADD MIDDLE_NAME varchar(40) COMMENT 'Second prenom de l utilisateur du compte'
    AFTER FIRST_NAME,
ADD LAST_NAME   varchar(40) COMMENT 'Nom de famille de l utilisateur du compte'
    AFTER MIDDLE_NAME,
ADD BIRTH_DATE  varchar(10) COMMENT 'Date de naissance de l utilisateur du compte'
    AFTER LAST_NAME;
