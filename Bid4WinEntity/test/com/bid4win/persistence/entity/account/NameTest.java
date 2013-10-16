package com.bid4win.persistence.entity.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.Bid4WinEntityTester;
import com.bid4win.persistence.entity.EntityGenerator;

/**
 * Classe de test d'un nom complet d'utilisateur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class NameTest extends Bid4WinEntityTester<Account, EntityGenerator>
{
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @Override
  @Test
  public void testCheckProtection() throws Bid4WinException
  {
    this.startProtection();
    Gender gender = Gender.MISS;
    String firstName = "firstName";
    String middleName = "middleName";
    String lastName = "lastName";
    Name name = new Name(gender, firstName, middleName, lastName);
    this.stopProtection();

    try
    {
      name.defineGender(Gender.MR);
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      name.defineFirstName("");
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      name.defineMiddleName("");
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      name.defineLastName("");
      fail("Should fail with protected object");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Wrong gender", gender, name.getGender());
    assertEquals("Wrong first name", firstName, name.getFirstName());
    assertEquals("Wrong middle name", middleName, name.getMiddleName());
    assertEquals("Wrong last name", lastName, name.getLastName());
  }

}
