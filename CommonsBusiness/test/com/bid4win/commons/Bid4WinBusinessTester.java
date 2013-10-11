package com.bid4win.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.dao.account.AccountInitializer_;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> Entité sujet du test<BR>
 * @param <ID> Type de l'identifiant de l'entité sujet du test<BR>
 * @param <ACCOUNT> Définition de type de compte utilisateur utilisé par le projet<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinBusinessTester<ENTITY extends Bid4WinEntity<ENTITY, ID>,
                                           ID,
                                           ACCOUNT extends AccountAbstract<ACCOUNT>,
                                           GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinDaoTester<ENTITY, ID, ACCOUNT, GENERATOR>
{
  /** Référence du TODO A COMMENTER */
  @Autowired
  @Qualifier("AccountInitializer")
  private AccountInitializer_<ACCOUNT, GENERATOR> accountInitializer;

  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected String getAccountId(int index)
  {
    return this.getAccountInitializer_().getId(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected ACCOUNT getAccount(int index) throws Bid4WinException
  {
    return this.getAccountInitializer_().getEntity(index);
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public int getSetupAccountNb()
  {
    return 1;
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected final AccountInitializer_<ACCOUNT, GENERATOR> getAccountInitializer_()
  {
    return this.accountInitializer;
  }

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
    // Ajoute les comptes utilisateurs utilisés pour les tests
    this.getAccountInitializer_().setUp(this.getSetupAccountNb());
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
    // Supprime les comptes utilisateurs utilisés pour les tests
    this.getAccountInitializer_().tearDown();
  }
}
