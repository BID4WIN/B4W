package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.CoreGenerator;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.account.security.Login;
import com.bid4win.commons.persistence.entity.account.security.Password;
import com.bid4win.commons.persistence.entity.connection.IpAddress;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class EntityGenerator<ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends CoreGenerator
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Login createLogin() throws Bid4WinException
  {
    return this.createLogin(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Login createLogin(int index) throws Bid4WinException
  {
    return this.createLogin("login" + index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Login createLogin(String value) throws Bid4WinException
  {
    return new Login(value);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Password createPassword() throws Bid4WinException
  {
    return this.createPassword(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Password createPassword(int index) throws Bid4WinException
  {
    return this.createPassword("123456a" + index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Password createPassword(String value) throws Bid4WinException
  {
    return new Password(value);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Credential createCredential() throws Bid4WinException
  {
    return this.createCredential(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Credential createCredential(int index) throws Bid4WinException
  {
    return new Credential(this.createLogin(index), this.createPassword(index));
  }
  /**
   *
   * TODO A COMMENTER
   * @param login TODO A COMMENTER
   * @param password TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Credential createCredential(String login, String password) throws Bid4WinException
  {
    return new Credential(this.createLogin(login), this.createPassword(password));
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Email createEmail() throws Bid4WinException
  {
    return this.createEmail(0);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Email createEmail(int index) throws Bid4WinException
  {
    return this.createEmail("a" + index + "@b.cd");
  }
  /**
   *
   * TODO A COMMENTER
   * @param address TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public Email createEmail(String address) throws Bid4WinException
  {
    return new Email(address);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public abstract ACCOUNT createAccount() throws Bid4WinException;
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public abstract ACCOUNT createAccount(int index) throws Bid4WinException;
  /**
   *
   * TODO A COMMENTER
   * @param id TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public abstract ACCOUNT createAccount(String id) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @param v6 TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public IpAddress createIpAddress(boolean v6) throws Bid4WinException
  {
    if(v6)
    {
      return new IpAddress("1:2:3:4:5:6:7:8");
    }
    return new IpAddress("1.2.3.4");
  }
  /**
   *
   * TODO A COMMENTER
   * @param v6 TODO A COMMENTER
   * @param ramdomly TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  public IpAddress createIpAddress(boolean v6, boolean ramdomly) throws Bid4WinException
  {
    if(!ramdomly)
    {
      return this.createIpAddress(v6);
    }
    if(v6)
    {
      String address = IdGenerator.generateId("HHHH-HHHH-HHHH-HHHH-HHHH-HHHH-HHHH-HHHH");
      return new IpAddress(address.replaceAll("-", ":"));
    }
    String address = "";
    for(int i = 0 ; i < 4 ; i++)
    {
      int number = Integer.valueOf(IdGenerator.generateId("NNN"));
      number = number / ((number % 256) + 1);
      if(i != 0)
      {
        address += ".";
      }
      address += number;
    }
    return new IpAddress(address);
  }
}
