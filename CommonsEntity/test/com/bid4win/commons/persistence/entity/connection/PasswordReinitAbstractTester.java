package com.bid4win.commons.persistence.entity.connection;

import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.AccountBasedKeyTester;

/**
 * Classe de test d'une ré-initialisation de mot de passe<BR>
 * <BR>
 * @param <REINIT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PasswordReinitAbstractTester<REINIT extends PasswordReinitAbstract<REINIT, ACCOUNT>,
                                                   ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                   GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedKeyTester<REINIT, ACCOUNT, GENERATOR>
{
  // Pas de définition supplémentaire
}
