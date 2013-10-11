package com.bid4win.commons.persistence.entity.property;

import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ROOT> TODO A COMMENTER<BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyRootAbstractTester<ROOT extends PropertyRootAbstract<ROOT, PROPERTY>,
                                                 PROPERTY extends PropertyAbstract<PROPERTY, ROOT>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                 GENERATOR extends EntityGenerator<ACCOUNT>>
       extends PropertyBaseTester<ROOT, ROOT, PROPERTY, ACCOUNT, GENERATOR>
{
  // Pas de test spécifique
}
