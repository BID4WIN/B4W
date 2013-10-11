package com.bid4win.persistence;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.persistence complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.persistence.dao.Test.class,
                     com.bid4win.persistence.entity.Test.class,
                     com.bid4win.persistence.usertype.Test.class})
public class Test extends Bid4WinEntityTester
{
  // Definition dans la super classe
}
