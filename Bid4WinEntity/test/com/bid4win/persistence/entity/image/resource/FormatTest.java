package com.bid4win.persistence.entity.image.resource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;
import com.bid4win.persistence.entity.Bid4WinEntityTester;

/**
 * Classe de test d'un format<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-bid4win.xml")
public class FormatTest extends Bid4WinEntityTester
{
  /**
   * Test of getDefaultType(Class), of class Format.
   */
  @Test
  public void testGetDefaultType_Class()
  {
    assertEquals("Wrong default", Bid4WinObjectType.getDefaultType(Format.class), Format.SOURCE);
    assertEquals("Wrong default", Format.DEFAULT, Format.SOURCE);
  }
}
