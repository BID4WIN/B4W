package com.bid4win.commons.service.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.property.IPropertyRootAbstractDaoStub;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.connection.ConnectionAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.service.Bid4WinServiceTester;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <SESSION> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <CONNECTION> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractInternalServiceTester<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                            PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                            SESSION extends SessionDataAbstract<ACCOUNT, CONNECTION>,
                                                            ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                            CONNECTION extends ConnectionAbstract<CONNECTION, ?, ACCOUNT>,
                                                            GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinServiceTester<PROPERTY_ROOT, Integer, SESSION, ACCOUNT, CONNECTION, GENERATOR>
{
  /**
   *
   * TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  @Test
  public void testGetRoot_Role_etc() throws Bid4WinException
  {
    PROPERTY_ROOT expected = this.getDao().getRoot();
    PROPERTY_ROOT root1 = this.getService().getRoot();
    this.checkIdentical(expected, root1);
    assertFalse("Wrong root", root1 == expected);
    PROPERTY_ROOT root2 = this.getService().getRoot();
    assertTrue("Wrong root", root1 == root2);

    this.getDao().update(expected.forceUpdate());
    expected = this.getDao().getRoot();
    root1 = this.getService().getRoot();
    assertFalse("Wrong root", root1 == expected);
    assertFalse("Wrong root", root1 == root2);
    this.checkIdentical(expected, root1);
    this.checkNotIdentical(root2, root1);
    root2 = this.getService().getRoot();
    assertTrue("Wrong root", root1 == root2);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinServiceTester#getService()
   */
  @Override
  public abstract PropertyAbstractInternalService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, ?> getService();

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IPropertyRootAbstractDaoStub<PROPERTY_ROOT, PROPERTY> getDao();
}
