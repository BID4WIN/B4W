package com.bid4win.commons.persistence.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.entity seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMapTest.class,
                     com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeTest.class,
                     com.bid4win.commons.persistence.entity.Bid4WinEntityTest.class})
public class AllTestsPackage
{
  // Pas de d�finition sp�cifique
}