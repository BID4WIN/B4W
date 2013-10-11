package com.bid4win.commons.persistence.dao.property;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.dao.Bid4WinDaoTester;
import com.bid4win.commons.persistence.entity.EntityGenerator;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * @param <GENERATOR> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyRootAbstractDaoTester<PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                    PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                    ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                    GENERATOR extends EntityGenerator<ACCOUNT>>
       extends Bid4WinDaoTester<PROPERTY_ROOT, Integer, ACCOUNT, GENERATOR>
{
  /**
   * Permet de préciser le DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDaoTester#getDao()
   */
  @Override
  protected abstract IPropertyRootAbstractDaoStub<PROPERTY_ROOT, PROPERTY> getDao();
  /**
   * Getter du DAO des propriétés
   * @return Le DAO des propriétés
   */
  protected abstract IPropertyAbstractDaoStub<PROPERTY, PROPERTY_ROOT> getPropertyDao();

  /**
   * Test of getRoot(), of class PropertyRootAbstractDao.
   */
  @Test
  public void testGetRoot()
  {
    try
    {
      this.getById(this.getDao().getUniqueId());
      fail("No root should exist");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    try
    {
      PROPERTY_ROOT root = this.getDao().getRoot();
      assertNotNull("Root should be created", root);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      fail("Should not fail: " + ex.getMessage());
    }
    try
    {
      this.getById(this.getDao().getUniqueId());
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      fail("Root should exist: " + ex.getMessage());
    }
  }

  /**
   * Test of performance, of class PropertyRootAbstractDao.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testPerformance() throws Bid4WinException
  {
    Bid4WinDate begin = new Bid4WinDate();
    PROPERTY_ROOT root = this.getDao().getRoot();
    for(int a = 1 ; a < 4 ; a++)
    {
      for(int b = 1 ; b < 4 ; b++)
      {
        for(int c = 1 ; c < 4 ; c++)
        {
          for(int d = 1 ; d < 4 ; d++)
          {
            this.getPropertyDao().add(this.addProperty(root, a, b, c, d));
            root = this.getDao().getRoot();
          }
        }
      }
    }
    Bid4WinDate intermediate = new Bid4WinDate();
    for(int i = 0 ; i < 100 ; i ++)
    {
      this.getDao().getRoot();
    }
    Bid4WinDate end = new Bid4WinDate();
    System.out.println("CREATE=" + (intermediate.getTime() - begin.getTime()) +
                       " SELECT=" + (end.getTime() - intermediate.getTime()));
    // NO CACHE CREATE=23140 SELECT=49469
  }
  /**
   *
   * TODO A COMMENTER
   * @param root TODO A COMMENTER
   * @param index1 TODO A COMMENTER
   * @param index2 TODO A COMMENTER
   * @param index3 TODO A COMMENTER
   * @param index4 TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  protected PROPERTY addProperty(PROPERTY_ROOT root, int index1, int index2, int index3, int index4)
            throws ModelArgumentException, UserException
  {
    return root.addProperty("a" + index1 + ".b" + index2 + ".c" + index3 + ".d" + index4, "");
  }
}
