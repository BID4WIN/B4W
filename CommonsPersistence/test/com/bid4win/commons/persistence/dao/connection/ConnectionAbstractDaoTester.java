package com.bid4win.commons.persistence.dao.connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.security.IdGenerator;
import com.bid4win.commons.core.security.IdPattern;
import com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionHistoryAbstract;
import com.bid4win.commons.persistence.entity.connection.DisconnectionReason;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <HISTORY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ConnectionAbstractDaoTester<CONNECTION extends ConnectionAbstract<CONNECTION, HISTORY, ACCOUNT>,
                                                  HISTORY extends ConnectionHistoryAbstract<HISTORY, ACCOUNT>,
                                                  ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                  GENERATOR extends EntityGenerator<ACCOUNT>>
       extends AccountBasedEntityMultipleDaoTester<CONNECTION, String, ACCOUNT, GENERATOR>
{
  /**
   * Permet de pr�ciser le DAO � tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityMultipleDaoTester#getDao()
   */
  @Override
  protected abstract IConnectionAbstractDaoStub<CONNECTION, HISTORY, ACCOUNT> getDao();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected IConnectionHistoryAbstractDaoStub<HISTORY, ACCOUNT> getHistoryDao()
  {
    return this.getDao().getHistoryDaoStub();
  }

  /**
   * Test of update(CONNECTION), of class ConnectionDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdate_CONNECTION() throws Bid4WinException
  {
    ACCOUNT account = this.getAccount(0);

    // Cr�ation de la connexion
    CONNECTION connection = this.create(account, false);
    this.add(connection);
    // Arr�t de la connexion
    this.update(connection.endConnection(DisconnectionReason.PASSWORD));
    CONNECTION result = this.findById(connection.getId());
    assertNull("Connection should be removed", result);
    Bid4WinList<HISTORY> oldHistoryList = new Bid4WinList<HISTORY>();
    Bid4WinList<HISTORY> historyList = this.getHistoryDao().findAll();
    historyList.removeAll(oldHistoryList);
    assertEquals("Wrong history nb", 1, historyList.size());
    oldHistoryList.addAll(historyList);
    HISTORY history = historyList.getFirst();
    assertEquals("Wrong history account", connection.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong history IP", connection.getIpAddress(), history.getIpAddress());
    assertEquals("Wrong disconnection reason", connection.getDisconnectionReason(),
                 history.getDisconnectionReason());
    assertEquals("Wrong start date", connection.getStartDate(), history.getStartDate());

    // Cr�ation de la connexion
    connection = this.create(account, false);
    this.add(connection);
    // Invalidation de la connexion
    this.update(connection.invalidate(DisconnectionReason.PASSWORD));
    result = this.getById(connection.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    assertTrue("Wrong result", result.same(connection));
    assertFalse("Wrong result", result.identical(connection));
    historyList = this.getHistoryDao().findAll();
    historyList.removeAll(oldHistoryList);
    assertEquals("Wrong history nb", 1, historyList.size());
    oldHistoryList.addAll(historyList);
    history = historyList.getFirst();
    assertEquals("Wrong history account", connection.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong history IP", connection.getIpAddress(), history.getIpAddress());
    assertEquals("Wrong disconnection reason", connection.getDisconnectionReason(),
                 history.getDisconnectionReason());
    assertEquals("Wrong start date", connection.getStartDate(), history.getStartDate());

    // Arr�t d'une connexion invalid�
    connection = result;
    this.update(connection.endConnection(DisconnectionReason.SECURITY));
    result = this.findById(connection.getId());
    assertNull("Connection should be removed", result);
    historyList = this.getHistoryDao().findAll();
    historyList.removeAll(oldHistoryList);
    assertEquals("Wrong history nb", 0, historyList.size());

    // Cr�ation de la connexion
    connection = this.create(account, true);
    this.add(connection);
    // Invalidation de la connexion
    this.update(connection.endConnection(DisconnectionReason.PASSWORD));
    result = this.getById(connection.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    assertTrue("Wrong result", result.same(connection));
    assertFalse("Wrong result", result.identical(connection));
    historyList = this.getHistoryDao().findAll();
    historyList.removeAll(oldHistoryList);
    assertEquals("Wrong history nb", 1, historyList.size());
    oldHistoryList.addAll(historyList);
    history = historyList.getFirst();
    assertEquals("Wrong history account", connection.getAccount().getId(), history.getAccount().getId());
    assertEquals("Wrong history IP", connection.getIpAddress(), history.getIpAddress());
    assertEquals("Wrong disconnection reason", connection.getDisconnectionReason(),
                 history.getDisconnectionReason());
    assertEquals("Wrong start date", connection.getStartDate(), history.getStartDate());

    // Arr�t de la r�manence de la connexion
    connection = result;
    this.update(connection.stopConnection(DisconnectionReason.SECURITY));
    result = this.findById(connection.getId());
    assertNull("Connection should be removed", result);
    historyList = this.getHistoryDao().findAll();
    historyList.removeAll(oldHistoryList);
    assertEquals("Wrong history nb", 0, historyList.size());

    account = this.getAccount(0);
    assertEquals("Wrong account version", 0, account.getVersion());
  }

  /**
   *
   * TODO A COMMENTER
   * @param account {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.account.AccountBasedEntityDaoTester#create(com.bid4win.commons.persistence.entity.account.AccountAbstract)
   */
  @Override
  protected final CONNECTION create(ACCOUNT account) throws Bid4WinException
  {
    return this.create(account, IdGenerator.generateId(
        IdPattern.createNoCheck(UtilString.createRepeatedString('H', 32).toString())), false);
  }
  /**
   *
   * TODO A COMMENTER
   * @param account TODO A COMMENTER
   * @param remanent TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected final CONNECTION create(ACCOUNT account, boolean remanent) throws Bid4WinException
  {
    return this.create(account, IdGenerator.generateId(
        IdPattern.createNoCheck(UtilString.createRepeatedString('H', 32).toString())), remanent);
  }
  /**
   * Permet de cr�er la connexion � utiliser pour les tests
   * @param account Compte utilisateur de la connexion � utiliser pour les tests
   * @param sessionId Cl� unique de la connexion � utiliser pour les tests
   * @param remanent TODO A COMMENTER
   * @return La connexion � utiliser pour les tests
   * @throws ModelArgumentException Si un probl�me intervient lors de la cr�ation
   * de la connexion � utiliser pour les tests
   */
  protected abstract CONNECTION create(ACCOUNT account, String sessionId, boolean remanent) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#removeAll()
   */
  @Override
  protected Bid4WinList<CONNECTION> removeAll() throws PersistenceException
  {
    this.getHistoryDao().removeAll();
    return super.removeAll();
  }
}
