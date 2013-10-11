package com.bid4win.commons.persistence.entity.account.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.entity.account.security seulement
 *
 * @author Emeric Fillâtre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.account.security.CredentialTest.class,
                     com.bid4win.commons.persistence.entity.account.security.LoginTest.class,
                     com.bid4win.commons.persistence.entity.account.security.PasswordTest.class,
                     com.bid4win.commons.persistence.entity.account.security.RoleTest.class,
                     com.bid4win.commons.persistence.entity.account.security.UtilLoginTest.class,
                     com.bid4win.commons.persistence.entity.account.security.UtilPasswordTest.class,
                     com.bid4win.commons.persistence.entity.account.security.UtilSecurityTest.class})
public class AllTestsPackage
{
  // Pas de définition spécifique
}