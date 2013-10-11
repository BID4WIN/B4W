-- Modification de la table ACCOUNT
ALTER TABLE ACCOUNT
ADD BID_RIGHT_NB      INT(5) NOT NULL DEFAULT '0'
    COMMENT 'Nombre de droits a encherir disponibles pour le compte utilisateur' AFTER PREF_LANGUAGE,
ADD BID_RIGHT_USED_NB INT(5) NOT NULL DEFAULT '0'
    COMMENT 'Nombre de droits a encherir utilises par le compte utilisateur';
