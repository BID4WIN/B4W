package com.bid4win.commons;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.caching.Test.class,
                     com.bid4win.commons.core.Test.class,
                     com.bid4win.commons.logging.Test.class,
                     com.bid4win.commons.mail.Test.class,
                     com.bid4win.commons.manager.Test.class,
                     com.bid4win.commons.persistence.Test.class,
                     com.bid4win.commons.service.Test.class})
public class Test
{
  // Pas de définition spécifique
}