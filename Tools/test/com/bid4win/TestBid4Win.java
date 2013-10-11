package com.bid4win;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test du package com.bid4win.commons complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-Bid4WinService.xml")
@Suite.SuiteClasses({com.bid4win.mail.Test.class,
                     com.bid4win.manager.Test.class,
                     com.bid4win.persistence.Test.class,
                     com.bid4win.service.Test.class})
public class TestBid4Win extends Bid4WinEntityTester
{
  // Definition dans la super classe
}
