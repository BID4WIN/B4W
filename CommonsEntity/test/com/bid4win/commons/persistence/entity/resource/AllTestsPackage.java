package com.bid4win.commons.persistence.entity.resource;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe de test du package com.bid4win.commons.persistence.entity.resource seulement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({com.bid4win.commons.persistence.entity.resource.ResourcePartTest.class,
                     com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPartTest.class,
                     com.bid4win.commons.persistence.entity.resource.ResourceStorageTest.class,
                     com.bid4win.commons.persistence.entity.resource.ResourceTest.class,
                     com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPartTest.class,
                     com.bid4win.commons.persistence.entity.resource.ResourceUsageTest.class})
public class AllTestsPackage
{
  // Pas de d�finition sp�cifique
}