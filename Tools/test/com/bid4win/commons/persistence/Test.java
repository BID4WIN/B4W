package com.bid4win.commons.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.dao.Test.class,
                     com.bid4win.commons.persistence.entity.Test.class,
                     com.bid4win.commons.persistence.usertype.Test.class})
public class Test
{
  // Pas de définition spécifique
}