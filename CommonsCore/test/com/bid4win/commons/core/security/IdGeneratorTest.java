package com.bid4win.commons.core.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.Bid4WinCoreTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test du générateur d'identifiant<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class IdGeneratorTest extends Bid4WinCoreTester
{
  /**
   * Test of generateId(int), of class IdGenerator.
   */
  @Test
  public void testGenerateId_int()
  {
    assertEquals("Wrong ID", UtilString.EMPTY, IdGenerator.generateId(-1));
    assertEquals("Wrong ID", UtilString.EMPTY, IdGenerator.generateId(0));
    assertEquals("Wrong ID", 1, IdGenerator.generateId(1).length());
    assertEquals("Wrong ID", 10, IdGenerator.generateId(10).length());
    assertEquals("Wrong ID", 100, IdGenerator.generateId(100).length());
  }
  /**
   * Test of generateId(Pattern), of class IdGenerator.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testGenerateId_IdPattern() throws Bid4WinException
  {
    IdPattern pattern = new IdPattern("NNNAAANNNNNANNNA");
    String id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));

    pattern = new IdPattern(UtilString.EMPTY);
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));

    pattern = new IdPattern("---");
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));

    pattern = new IdPattern("BBBBB-BBBBB-BBBBB-BBBBB-BBBBB");
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));

    pattern = new IdPattern("HHHHH-HHHHH-HHHHH-HHHHH-HHHHH");
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
    id = IdGenerator.generateId(pattern);
    assertTrue("Wrong ID", pattern.matches(id));
  }

  /**
   * Test of letterFor(int), of class IdGenerator.
   */
  @Test
  public void testLetterFor_int()
  {
    assertEquals("Wrong letter", "A", IdGenerator.letterFor(0));
    assertEquals("Wrong letter", "A", IdGenerator.letterFor(2));
    assertEquals("Wrong letter", "B", IdGenerator.letterFor(3));
    assertEquals("Wrong letter", "Z", IdGenerator.letterFor(76));
    assertEquals("Wrong letter", "Z", IdGenerator.letterFor(77));
    assertEquals("Wrong letter", "A", IdGenerator.letterFor(78));
    assertEquals("Wrong letter", "B", IdGenerator.letterFor(79));
    assertEquals("Wrong letter", "V", IdGenerator.letterFor(99));
  }

  /**
   * Test of letterOrNumberFor(int), of class IdGenerator.
   */
  @Test
  public void testLetterOrNumberFor_int()
  {
    assertEquals("Wrong letter", "0", IdGenerator.letterOrNumberFor(0));
    assertEquals("Wrong letter", "0", IdGenerator.letterOrNumberFor(2));
    assertEquals("Wrong letter", "9", IdGenerator.letterOrNumberFor(27));
    assertEquals("Wrong letter", "9", IdGenerator.letterOrNumberFor(29));
    assertEquals("Wrong letter", "A", IdGenerator.letterOrNumberFor(30));
    assertEquals("Wrong letter", "A", IdGenerator.letterOrNumberFor(31));
    assertEquals("Wrong letter", "B", IdGenerator.letterOrNumberFor(32));
    assertEquals("Wrong letter", "Z", IdGenerator.letterOrNumberFor(80));
    assertEquals("Wrong letter", "Z", IdGenerator.letterOrNumberFor(81));
    assertEquals("Wrong letter", "A", IdGenerator.letterOrNumberFor(82));
    assertEquals("Wrong letter", "B", IdGenerator.letterOrNumberFor(83));
    assertEquals("Wrong letter", "R", IdGenerator.letterOrNumberFor(99));
  }

  /**
   * Test of hexa(int), of class IdGenerator.
   */
  @Test
  public void testHexa_int()
  {
    assertEquals("Wrong letter", "0", IdGenerator.hexaFor(0));
    assertEquals("Wrong letter", "0", IdGenerator.hexaFor(5));
    assertEquals("Wrong letter", "9", IdGenerator.hexaFor(54));
    assertEquals("Wrong letter", "9", IdGenerator.hexaFor(59));
    assertEquals("Wrong letter", "A", IdGenerator.hexaFor(60));
    assertEquals("Wrong letter", "A", IdGenerator.hexaFor(65));
    assertEquals("Wrong letter", "B", IdGenerator.hexaFor(66));
    assertEquals("Wrong letter", "F", IdGenerator.hexaFor(90));
    assertEquals("Wrong letter", "F", IdGenerator.hexaFor(95));
    assertEquals("Wrong letter", "A", IdGenerator.hexaFor(96));
    assertEquals("Wrong letter", "B", IdGenerator.hexaFor(97));
    assertEquals("Wrong letter", "D", IdGenerator.hexaFor(99));
  }
}
