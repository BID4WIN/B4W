package com.bid4win.commons.persistence.usertype;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.usertype complet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.usertype.account.AllTests.class,
                     com.bid4win.commons.persistence.usertype.connection.AllTests.class,
                     com.bid4win.commons.persistence.usertype.core.AllTests.class})
public class AllTests
{
  // Pas de d�finition sp�cifique
}