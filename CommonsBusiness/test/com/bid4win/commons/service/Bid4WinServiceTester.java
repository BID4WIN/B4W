package com.bid4win.commons.service;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.Bid4WinBusinessTester;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.service.connection.ConnectionAbstractService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;
import com.bid4win.commons.service.connection.SessionHandlerAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinServiceTester<ENTITY extends Bid4WinEntity<ENTITY, ID>,
                                           ID,
                                           SESSION extends SessionDataAbstract<ACCOUNT>,
                                           ACCOUNT extends AccountAbstract<ACCOUNT>,
                                           GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinBusinessTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /** Service de gestion des connexions */
  @Autowired
  @Qualifier("ConnectionService")
  private ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> connectionService;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected SESSION getSession() throws Bid4WinException
  {
    return this.getService().getConnectionService().getSession();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected SESSION startSession() throws Bid4WinException
  {
    return this.startSession(this.createSessionId());
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected SESSION startSession(String sessionId) throws Bid4WinException
  {
    SessionHandlerAbstract<SESSION, ACCOUNT> sessionHandler =
        this.getService().getConnectionService().getSessionHandler();
    if(!sessionHandler.isSessionStarted())
    {
      sessionHandler.startSession(sessionId, this.getGenerator().createIpAddress(true));
    }
    return sessionHandler.getSessionData();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  protected String createSessionId() throws UserException
  {
    return IdGenerator.generateId(UtilString.createRepeatedString('H', 32).toString());
  }

  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void disconnectSessionAccount() throws Bid4WinException
  {
    SessionHandlerAbstract<SESSION, ACCOUNT> sessionHandler =
        this.getService().getConnectionService().getSessionHandler();
    if(sessionHandler.isSessionStarted())
    {
      sessionHandler.disconnect();
    }
  }
  /**
   *
   * TODO A COMMENTER
   */
  protected void stopSession()
  {
    this.getService().getConnectionService().getSessionHandler().stopSession();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getConnectedAccountId()
  {
    try
    {
      return this.getService().getConnectedAccountId();
    }
    catch(Bid4WinException ex)
    {
      return null;
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected String connectAccount(int index) throws Bid4WinException
  {
    this.startSession();
    this.disconnectSessionAccount();
    ACCOUNT account = this.getAccount(index);
    Role role = account.getCredential().getRole();
    this.updateRole(index, Role.ADMIN);
    this.getConnectionService().connect(account.getEmail().getAddress(),
                                        account.getCredential().getPassword(), false);
    this.updateRole(index, role);
    this.disconnectSessionAccount();
    return account.getId();
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void disconnectAccount() throws Bid4WinException
  {
    this.startSession();
    this.getConnectionService().disconnect();
    this.stopSession();
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @param role TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void updateRole(int index, Role role) throws Bid4WinException
  {
    this.getAccountInitializer_().updateRole(index, role);
  }

  /**
   *
   * TODO A COMMENTER
   * @param methodName TODO A COMMENTER
   * @param parametersType TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkAdminRestriction(String methodName, Class<?> ... parametersType)
            throws Bid4WinException
  {
    this.checkRoleRestriction(this.getService().getAdminRole(), methodName, parametersType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param role TODO A COMMENTER
   * @param methodName TODO A COMMENTER
   * @param parametersType TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkRoleRestriction(Role role, String methodName, Class<?> ... parametersType)
            throws Bid4WinException
  {
    this.checkConnectedRestriction(methodName, parametersType);
    if(role.equals(Role.BASIC))
    {
      this.updateRole(0, Role.NONE);
    }
    else if(role.equals(Role.USER))
    {
      this.updateRole(0, Role.ADMIN);
    }
    else
    {
      this.updateRole(0, Role.USER);
    }
    this.connectAccount(0);
    try
    {
      this.invokeServiceMethod(methodName, parametersType);
      fail("Should fail with connected account not having the role " + role.getCode());
    }
    catch(InvocationTargetException ex)
    {
      if(ex.getCause() instanceof AuthorizationException)
      {
        System.out.println(ex.getMessage());
      }
      else
      {
        ex.printStackTrace();
        fail("Wrong error :" + ex.getMessage());
      }
    }
    this.updateRole(0, role);
    this.disconnectSessionAccount();
  }
  /**
   *
   * TODO A COMMENTER
   * @param methodName TODO A COMMENTER
   * @param parametersType TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected void checkConnectedRestriction(String methodName, Class<?> ... parametersType)
            throws Bid4WinException
  {
    this.disconnectAccount();
    this.startSession();
    try
    {
      this.invokeServiceMethod(methodName, parametersType);
      fail("Should fail without any connected account");
    }
    catch(InvocationTargetException ex)
    {
      if(ex.getCause() instanceof AuthenticationException)
      {
        System.out.println(ex.getMessage());
      }
      else
      {
        ex.printStackTrace();
        fail("Wrong error :" + ex.getMessage());
      }
    }
    finally
    {
      this.stopSession();
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param methodName TODO A COMMENTER
   * @param parametersType TODO A COMMENTER
   * @throws InvocationTargetException InvocationTargetException
   */
  protected void invokeServiceMethod(String methodName, Class<?> ... parametersType)
            throws InvocationTargetException
  {
    this.invokeServiceMethod(this.getService(), methodName, parametersType);
  }
  /**
   *
   * TODO A COMMENTER
   * @param methodName TODO A COMMENTER
   * @param parametersType TODO A COMMENTER
   * @throws InvocationTargetException InvocationTargetException
   */
  protected final void invokeServiceMethod(Object object, String methodName, Class<?> ... parametersType)
            throws InvocationTargetException
  {
    Object[] parameters = new Object[parametersType.length];
    for(int i = 0 ; i < parametersType.length ; i++)
    {
      if(parametersType[i].equals(int.class))
      {
        parameters[i] = new Integer(1);
      }
      else if(parametersType[i].equals(long.class))
      {
        parameters[i] = new Long(1);
      }
      else if(parametersType[i].equals(float.class))
      {
        parameters[i] = new Float(1);
      }
      else if(parametersType[i].equals(double.class))
      {
        parameters[i] = new Double(1);
      }
      else if(parametersType[i].equals(boolean.class))
      {
        parameters[i] = new Boolean(true);
      }
      else
      {
        parameters[i] = null;
      }
    }
    try
    {
      Method method = object.getClass().getMethod(methodName, parametersType);
      method.invoke(object, parameters);
    }
    catch(NoSuchMethodException ex)
    {
      ex.printStackTrace();
      fail("Could not find " + methodName + "() to test");
    }
    catch(IllegalAccessException ex)
    {
      ex.printStackTrace();
      fail("Could not access " + methodName + "() to test");
    }
  }

  /**
   * Getter du service de gestion des connexions
   * @return Le service de gestion des connexions
   */
  protected ConnectionAbstractService_<?, ?, ?, SESSION, ACCOUNT, ?> getConnectionService()
  {
    return this.connectionService;
  }
  /**
   * Getter du service à tester
   * @return Le service à tester
   */
  protected abstract Bid4WinService_<SESSION, ACCOUNT, ?> getService();

  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#setUp()
   */
  @Override
  public void setUp() throws Exception
  {
    super.setUp();
    try
    {
      this.disconnectAccount();
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    this.getService().clearCache();
  }
  /**
   *
   * TODO A COMMENTER
   * @throws Exception {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#tearDown()
   */
  @Override
  public void tearDown() throws Exception
  {
    super.tearDown();
    try
    {
      this.disconnectAccount();
    }
    catch(ModelArgumentException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
    this.getService().clearCache();
  }
}
