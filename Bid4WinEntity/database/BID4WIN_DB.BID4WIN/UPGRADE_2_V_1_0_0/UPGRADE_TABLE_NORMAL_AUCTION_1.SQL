-- Modification de la table NORMAL_AUCTION
ALTER TABLE NORMAL_AUCTION

ADD STEP       varchar(10) NOT NULL
    COMMENT 'Etape courante de la vente aux encheres normale'
    AFTER EXCHANGE_RATES,
ADD ACCOUNT_ID varchar(12)
    COMMENT 'Compte utilisateur ayant remporte la vente aux encheres normale'
    AFTER UPDATE_DATE,
-- Définition de la clé étrangère de la table NORMAL_AUCTION vers la table ACCOUNT
ADD KEY `NORMAL_AUCTION_2_ACCOUNT_FK` (`ACCOUNT_ID`),
ADD CONSTRAINT `NORMAL_AUCTION_2_ACCOUNT_FK` FOREIGN KEY (`ACCOUNT_ID`) REFERENCES `ACCOUNT` (`ID`);
