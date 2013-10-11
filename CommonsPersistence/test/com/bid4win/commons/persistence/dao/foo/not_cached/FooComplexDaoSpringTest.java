package com.bid4win.commons.persistence.dao.foo.not_cached;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooComplex;
import com.bid4win.commons.persistence.entity.foo.not_cached.FooEmbeddable;

/**
 * Test du DAO de la classe FooComplex<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class FooComplexDaoSpringTest extends FooAbstractDaoSpringTester<FooComplex>
{
  /** Référence du DAO à tester */
  @Autowired
  @Qualifier("FooComplexDao")
  private FooComplexDaoSpring dao;

  /**
   * Test of findListByEmbeddedKey(String), of class FooComplexDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByEmbeddedKey_String() throws Bid4WinException
  {
    System.out.println("### findListByEmbeddedKey(String)");
    FooComplex foo = this.createFoo();
    // Effectue les tests
    Bid4WinList<FooComplex> result = this.getDao().findListByEmbeddedKey(foo.getEmbedded().getKey());
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooComplex expected1 = this.getDao().getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByEmbeddedKey(foo.getEmbedded().getKey());
    assertEquals("Wrong result size", 1, result.size());
    this.checkIdentical(expected1, result.get(0));
    result = this.getDao().findListByEmbeddedKey(foo.getEmbedded().getKey() + "1");
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    foo = this.createFoo();
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooComplex expected2 = this.getDao().getById(foo.getId());
    // Effectue les tests
    result = this.getDao().findListByEmbeddedKey(foo.getEmbedded().getKey());
    assertEquals("Wrong result size", 2, result.size());
    this.checkIdentical(expected1, result.get(0));
    this.checkIdentical(expected2, result.get(1));
    System.out.println("### findListByEmbeddedKey(String)");
  }

  /**
   * Test of findListByEmbeddedSetKey(String), of class FooComplexDaoSpring.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testFindListByEmbeddedSetKey_String() throws Bid4WinException
  {
    System.out.println("### findListByEmbeddedSetKey(String)");
    FooComplex foo1 = this.createFoo();
    foo1.getEmbeddedSet().add(new FooEmbeddable("1", "value1"));
    foo1.getEmbeddedSet().add(new FooEmbeddable("2", "value2"));
    // Effectue les tests
    Bid4WinList<FooComplex> result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 0, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 0, result.size());
    // Ajoute une entité
    this.getDao().add(foo1);
    // Effectue les tests
    result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 1, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 1, result.size());
    // Ajoute une entité
    FooComplex foo2 = this.createFoo();
    this.getDao().add(foo2);
    // Effectue les tests
    result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 1, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 1, result.size());
    // Modifie une entité
    foo2.getEmbeddedSet().add(new FooEmbeddable("1", "value3"));
    foo2 = this.getDao().update(foo2);
    // Effectue les tests
    result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 2, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 1, result.size());
    // Modifie une entité
    foo1.getEmbeddedSet().add(new FooEmbeddable("1", "value4"));
    foo1 = this.getDao().update(foo1);
    // Effectue les tests
    result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 2, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 1, result.size());
    // Modifie une entité
    foo2.getEmbeddedSet().add(new FooEmbeddable("2", "value4"));
    foo2 = this.getDao().update(foo2);
    // Effectue les tests
    result = this.getDao().findListByEmbeddedSetKey("1");
    assertEquals("Wrong result size", 2, result.size());
    result = this.getDao().findListByEmbeddedSetKey("2");
    assertEquals("Wrong result size", 2, result.size());
    System.out.println("### findListByEmbeddedSetKey(String)");
  }

  /**
   * Test of embedded set update.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateEmbeddedSet() throws Bid4WinException
  {
    System.out.println("### Update Embedded Set");
    // Ajoute une entité
    FooComplex foo = this.createFoo();
    foo.getEmbeddedSet().add(new FooEmbeddable("1", "value1"));
    foo.getEmbeddedSet().add(new FooEmbeddable("2", "value2"));
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooComplex result = this.getDao().getById(foo.getId());
    // Effectue les tests
    this.checkIdentical(foo, result);
    // Modifie une entité
    foo.getEmbeddedSet().add(new FooEmbeddable("1", "value3"));
    FooComplex update = this.getDao().update(foo);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertEquals("Wrong ID", foo.getId(), result.getId());
    assertEquals("Wrong version", foo.getVersion() + 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    // Modifie une entité
    foo = update;
    foo.getEmbeddedSet().remove(foo.getEmbeddedSet().iterator().next());
    update = this.getDao().update(foo);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertEquals("Wrong ID", foo.getId(), result.getId());
    assertEquals("Wrong version", foo.getVersion() + 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    System.out.println("### Update Embedded Set");
  }

  /**
   * Test of embedded list update.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUpdateEmbeddedList() throws Bid4WinException
  {
    System.out.println("### Update Embedded List");
    // Ajoute une entité
    FooComplex foo = this.createFoo();
    foo.getEmbeddedList().add(new FooEmbeddable("1", "value1"));
    foo.getEmbeddedList().add(new FooEmbeddable("2", "value2"));
    this.getDao().add(foo);
    // Récupère l'entité ajoutée
    FooComplex result = this.getDao().getById(foo.getId());
    // Effectue les tests
    this.checkIdentical(foo, result);
    // Modifie une entité
    foo.getEmbeddedList().add(foo.getEmbeddedList().remove(0));
    FooComplex update = this.getDao().update(foo);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertEquals("Wrong ID", foo.getId(), result.getId());
    assertEquals("Wrong version", foo.getVersion() + 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    // Modifie une entité
    foo = update;
    foo.getEmbeddedList().remove(1);
    update = this.getDao().update(foo);
    // Récupère l'entité modifiée
    result = this.getDao().getById(foo.getId());
    // Effectue les tests
    assertEquals("Wrong ID", foo.getId(), result.getId());
    assertEquals("Wrong version", foo.getVersion() + 1, result.getVersion());
    this.checkSame(foo, result);
    this.checkIdentical(update, result);
    System.out.println("### Update Embedded List");
  }

  /**
   * Permet de créer l'entité à utiliser pour les tests
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#createFoo()
   */
  @Override
  protected FooComplex createFoo()
  {
    FooEmbeddable embedded = new FooEmbeddable("KEY", "EMBEDDED VALUE");
    return new FooComplex("VALUE", new Bid4WinDate(), embedded);
  }
  /**
   * Permet de modifier l'entité à utiliser pour les tests
   * @param fooComplex {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#updateFoo(com.bid4win.commons.persistence.entity.foo.FooAbstract)
   */
  @Override
  protected void updateFoo(FooComplex fooComplex) throws ModelArgumentException
  {
    super.updateFoo(fooComplex);
    fooComplex.getEmbedded().setKey("KEY2");
    fooComplex.getEmbedded().setValue("EMBEDDED VALUE2");
  }
  /**
   * Getter du DAO à tester
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.foo.FooAbstractDaoSpringTester#getDao()
   */
  @Override
  public FooComplexDaoSpring getDao()
  {
    return this.dao;
  }
}
