package com.bid4win.commons.persistence.dao.foo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.foo.FooAbstract;

/**
 * 
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <FOO_PARENT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class FooParentAbstractDaoSpringTester<FOO_PARENT extends FooAbstract<FOO_PARENT>>
       extends FooAbstractDaoSpringTester<FOO_PARENT>
{
  /**
   * Test of child addition.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddChild() throws Bid4WinException
  {
    System.out.println("### Add Child");
    // Ajoute une entité
    FOO_PARENT foo = this.createFoo();
    this.addChild(foo, 1);
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO_PARENT result = this.getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 0, result.getVersion());
    this.checkIdentical(foo, result);
    // Ajoute un enfant
    this.addChild(foo, 2);
    FOO_PARENT update = this.update(foo);
    // Récupère l'entité modifiée
    result = this.getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    // Ajoute un enfant à la place d'un déjà existant
    foo = result;
    this.addChild(foo, 3);
    update = this.update(foo);
    // Récupère l'entité modifiée
    result = this.getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 2, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    System.out.println("### Add Child");
  }

  /**
   * Test of child removal.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRemoveChild() throws Bid4WinException
  {
    System.out.println("### Remove Child");
    // Ajoute une entité
    FOO_PARENT foo = this.createFoo();
    this.addChild(foo, 1);
    this.addChild(foo, 2);
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO_PARENT result = this.getById(foo.getId());
    // Effectue les tests
    this.checkIdentical(foo, result);
    // Retire un enfant
   this.removeChild(foo, 1);
    FOO_PARENT update = this.update(foo);
    // Récupère l'entité modifiée
    result = this.getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    System.out.println("### Remove Child");
  }

  /**
   * Test of child update.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateChild() throws Bid4WinException
  {
    System.out.println("### Update Child");
    // Ajoute une entité
    FOO_PARENT foo = this.createFoo();
    this.addChild(foo, 1);
    this.addChild(foo, 2);
    this.add(foo);
    // Récupère l'entité ajoutée
    FOO_PARENT result = this.getById(foo.getId());
    // Effectue les tests
    this.checkIdentical(foo, result);
    // Modifie un enfant
    this.updateChild(foo, 1);
    FOO_PARENT update = this.update(foo);
    // Récupère l'entité modifiée
    result = this.getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    assertEquals("Wrong child version", 1, this.getChildVersion(result, 1));
    System.out.println("### Update Child");
  }

  /**
   * Test of performance of linked classe, of class FooAbstractDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testPerformanceLinkedClass() throws Bid4WinException
  {
    System.out.println("### Performance linked class test");
    Bid4WinList<FOO_PARENT> stubList = new Bid4WinList<FOO_PARENT>(10);
    Bid4WinDate begin = new Bid4WinDate();
    for(int i = 0 ; i < 10 ; i ++)
    {
      FOO_PARENT parent = this.add(this.createFoo());
      stubList.add(parent);
      this.addChilds(parent, i, 5);
      this.update(parent);
    }
    Bid4WinDate intermediate = new Bid4WinDate();
    for(int i = 0 ; i < 1000; i ++)
    {
      FOO_PARENT parent = this.getById(stubList.get(i % 10).getId());
      assertEquals("Wrong child NB", 5, this.getChildNb(parent));
    }
    Bid4WinDate end = new Bid4WinDate();
    System.out.println("CREATE=" + (intermediate.getTime() - begin.getTime()) +
                       " SELECT=" + (end.getTime() - intermediate.getTime()));
    System.out.println("### Performance linked class test");
  }

  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public abstract void addChild(FOO_PARENT parent, int index) throws ModelArgumentException;
  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @param nb TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public abstract void addChilds(FOO_PARENT parent, int index, int nb) throws ModelArgumentException;
  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @param index TODO A COMMENTER
   */
  public abstract void updateChild(FOO_PARENT parent, int index);
  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @param index TODO A COMMENTER
   */
  public abstract void removeChild(FOO_PARENT parent, int index);
  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract int getChildNb(FOO_PARENT parent);
  /**
   * 
   * TODO A COMMENTER
   * @param parent TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract int getChildVersion(FOO_PARENT parent, int index);
}
