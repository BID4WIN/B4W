package com.bid4win.commons.persistence.entity.connection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.entity.connection seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.connection.ConnectionAbstractTest.class,
                     com.bid4win.commons.persistence.entity.connection.ConnectionDataTest.class,
                     com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstractTest.class,
                     com.bid4win.commons.persistence.entity.connection.IpAddressTest.class,
                     com.bid4win.commons.persistence.entity.connection.PasswordReinitAbstractTest.class,
                     com.bid4win.commons.persistence.entity.connection.UtiIpTest.class})
public class AllTestsPackage
{
  // Pas de d�finition sp�cifique
}