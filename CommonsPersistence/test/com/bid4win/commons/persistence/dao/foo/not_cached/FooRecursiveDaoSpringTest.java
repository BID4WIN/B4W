package com.bid4win.commons.persistence.dao.foo.not_cached;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooRecursive;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooRecursiveDaoSpringTest extends FooAbstractDaoSpringTester<FooRecursive>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooRecursiveDao")
  private FooRecursiveDaoSpring dao;

  /**
   * Test of child addition.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testAddChild() throws Bid4WinException
  {
    System.out.println("### Add Child");
    // Ajoute une entité
    FooRecursive foo = this.createFoo();
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooRecursive result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 0, result.getVersion());
    this.checkIdentical(foo, result);
    // Ajoute un enfant
    FooRecursive child = this.createFoo();
    foo.addChild(child);
    FooRecursive update = this.getDao().update(foo);
    // Récupère l'entité modifiée
    result = this.getDao().getById(update.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    assertEquals("Wrong child nb", 1, result.getChildSet().size());
    // Récupère l'enfant ajouté
    child = update.getChildSet().iterator().next();
    result = this.getDao().getById(child.getId());
    assertEquals("Wrong version", 0, result.getVersion());
    this.checkSame(child, result);
    assertTrue("Wrong result", child.same(result));


    /** ne marche pas ... car essaye d'inserer aussi le parent qui existe déjà !!!
    ou alors faire un update de child ...*/
    // Ajoute un enfant
 /*   foo = update;
    child = this.createFoo();
    foo.addChild(child);
    this.getDao().add(child);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 2, result.getVersion());
    assertTrue("Wrong result", foo.same(result));
    assertEquals("Wrong child nb", 2, result.getChildSet().size());
    for(Iterator<FooRecursive> iterator = foo.getChildSet().iterator() ; iterator.hasNext() ;)
    {
      // Récupère l'enfant ajouté
      child = iterator.next();
      result = this.getDao().getById(child.getId());
      assertEquals("Wrong version", 0, result.getVersion());
      assertTrue("Wrong result", child.same(result));
    }

    // Ajoute un enfant
    child = this.createFoo();
    foo.getChildSet().iterator().next().addChild(child);
    this.getDao().add(child);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 2, result.getVersion());
    assertTrue("Wrong result", foo.same(result));
    assertEquals("Wrong child nb", 2, result.getChildSet().size());
    // Récupère l'entité modifiée
    foo = foo.getChildSet().iterator().next();
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    assertTrue("Wrong result", foo.same(result));
    assertEquals("Wrong child nb", 1, result.getChildSet().size());
    for(Iterator<FooRecursive> iterator = foo.getChildSet().iterator() ; iterator.hasNext() ;)
    {
      // Récupère l'enfant ajouté
      child = iterator.next();
      result = this.getDao().getById(child.getId());
      assertEquals("Wrong version", 0, result.getVersion());
      assertTrue("Wrong result", child.same(result));
    }*/
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
    FooRecursive foo = this.createFoo();
    FooRecursive child1 = this.createFoo();
    foo.addChild(child1);
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooRecursive result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 0, result.getVersion());
    this.checkIdentical(foo, result);
    // Retire un enfant
    result.removeChild(result.getChildSet().iterator().next());
    FooRecursive update = this.getDao().update(result);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(update, result);
    assertEquals("Wrong child nb", 0, result.getChildSet().size());
    try
    {
      // Récupère l'entité supprimée
      result = this.getDao().getById(child1.getId());
      fail("No data should have been returned");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }

    // Ajoute des enfants
    child1 = this.createFoo();
    update.addChild(child1);
    FooRecursive child2 = this.createFoo();
    child1.addChild(child2);
    FooRecursive child3 = this.createFoo();
    child2.addChild(child3);
    update = this.getDao().update(update);
    // Récupère l'entité modifiée
    result = this.getDao().getById(update.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 2, result.getVersion());
    this.checkIdentical(update, result);
    // Retire un enfant
    child1 = result.getChildSet().iterator().next();
    child2 = child1.getChildSet().iterator().next();
    child1.removeChild(child2);
    update = this.getDao().update(child1);
    // Récupère l'entité modifiée
    result = this.getDao().getById(result.getId());
    // Effectue les tests
    assertTrue("Wrong ID", 0 < result.getId());
    assertEquals("Wrong version", 2, result.getVersion());
    assertEquals("Wrong child nb", 1, result.getChildSet().size());
    // Récupère l'enfant ajouté
    result = this.getDao().getById(child1.getId());
    assertEquals("Wrong version", 1, result.getVersion());
    this.checkSame(child1, result);
    this.checkIdentical(update, result);
    assertEquals("Wrong child nb", 0, result.getChildSet().size());
    try
    {
      // Récupère l'entité supprimée
      result = this.getDao().getById(child2.getId());
      fail("No data should have been returned");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    try
    {
      // Récupère l'entité supprimée
      result = this.getDao().getById(child2.getChildSet().iterator().next().getId());
      fail("No data should have been returned");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void test()
  {
    try
    {
      // Ajoute une entité
      FooRecursive parent1 = this.createFoo();
      FooRecursive child1 = this.createFoo();
      parent1.addChild(child1);
      // Ajoute le parent et ses enfants car cascade persist
      parent1 = this.getDao().add(parent1);

      // Ne fait rien
      parent1 = this.getDao().update(parent1);
      // Ne fait rien
      child1 = this.getDao().update(child1);

      parent1.setValue(parent1.getValue()+"BIS");
      // Modifie le parent
      parent1 = this.getDao().update(parent1);
      try
      {
        this.getDao().update(child1);
        fail("");
      }
      catch(AssertionError ex)
      {
        throw ex;
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      // Il faut relire l'enfant car le parent a ete modifie
      child1 = this.getDao().getById(child1.getId());
      // Ne fait rien
      child1 = this.getDao().update(child1);
      // Ne fait rien
      parent1 = this.getDao().update(parent1);

      child1.setValue(child1.getValue()+"BIS");
      // Ne fait rien
      parent1 = this.getDao().update(parent1);
      // Modifie l'enfant
      child1 = this.getDao().update(child1);
      try
      {
        this.getDao().update(parent1);
        fail("");
      }
      catch(AssertionError ex)
      {
        throw ex;
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      // Il faut relire le parent car l'enfant a ete modifie
      parent1 = this.getDao().getById(parent1.getId());
      // Ne fait rien
      parent1 = this.getDao().update(parent1);

      FooRecursive child2 = this.createFoo();
      parent1.addChild(child2);
      child2 = this.getDao().add(child2);

      try
      {
        this.getDao().update(parent1);
        fail("");
      }
      catch(AssertionError ex)
      {
        throw ex;
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      // Il faut relire le parent car l'enfant a ete ajoute
      parent1 = this.getDao().getById(parent1.getId());
      // Ne fait rien
      parent1 = this.getDao().update(parent1);
      try
      {
        this.getDao().update(child1);
        fail("");
      }
      catch(AssertionError ex)
      {
        throw ex;
      }
      catch(Throwable th)
      {
        th.printStackTrace();
      }
      // Il faut relire l'enfant car le parent a ete modifie
      child1 = this.getDao().getById(child1.getId());
      // Ne fait rien
      child1 = this.getDao().update(child1);
      // Ne fait rien
      child2 = this.getDao().update(child2);
    }
    catch(AssertionError ex)
    {
      throw ex;
    }
    catch(Throwable th)
    {
      th.printStackTrace();
    }

/*    FooRecursive parent2 = this.createFoo();
    this.getDao().add(parent2);
    this.getDao().switchTo(child, parent2);*/
  }


  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooRecursive createFoo()
  {
    return new FooRecursive("VALUE", new Bid4WinDate());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooRecursiveDaoSpring getDao()
  {
    return this.dao;
  }
}
