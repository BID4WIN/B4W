package com.bid4win.commons.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.account.AccountAbstractStub;
import com.bid4win.commons.testing.Bid4WinJUnit4ClassRunner;

/**
 * Classe de test des entités de base du projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@RunWith(Bid4WinJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/config/spring-test-commons.xml")
public class Bid4WinEntityTest extends Bid4WinEntityTester<AccountAbstractStub, EntityGeneratorStub>
{
  static
  {
    Bid4WinEntityStub_.loadRelations();
  }
  /** TODO A COMMENTER */
  private Bid4WinList<Bid4WinRelationNode> nodeList = new Bid4WinList<Bid4WinRelationNode>(
      Bid4WinEntityStub_Relations.NODE_PARENT, Bid4WinEntityStub_Relations.NODE_SON,
      Bid4WinEntityStub_Relations.NODE_BOSS, Bid4WinEntityStub_Relations.NODE_EMPLOYE);
  /**
   * Test of equals(Object) method, of class Bid4WinEntity.
   */
  @Test
  public void testEquals_Object()
  {
    Bid4WinEntityStub stub1 = new Bid4WinEntityStub("id", "name");
    Bid4WinEntityStub stub2 = new Bid4WinEntityStub("id", "name");
    assertTrue(stub1.equals(stub1));
    assertTrue(stub1.equals(stub2));
    assertTrue(stub2.equals(stub1));
    assertFalse(stub1.equals(null));
    assertFalse(stub1.equals("TOTO"));
    stub2 = new Bid4WinEntityStub(stub1.getId(), "name2");
    assertTrue(stub1.equals(stub2));
    assertTrue(stub2.equals(stub1));
    stub2 = new Bid4WinEntityStub("id2", stub1.getName());
    assertFalse(stub1.equals(stub2));
    assertFalse(stub2.equals(stub1));
  }
  /**
   * Test of same(Bid4WinEntity) method, of class Bid4WinEntity.
   */
  @Test
  public void testSame_Bid4WinEntity()
  {
    Bid4WinEntityStub stub1 = new Bid4WinEntityStub("id", "name");
    Bid4WinEntityStub stub2 = new Bid4WinEntityStub("id", "name");
    assertTrue(stub1.same(stub1));
    assertTrue(stub1.same(stub2));
    assertTrue(stub2.same(stub1));
    assertFalse(stub1.same(null));
    stub2 = new Bid4WinEntityStub(stub1.getId(), "name2");
    assertFalse(stub1.same(stub2));
    assertFalse(stub2.same(stub1));
    stub2 = new Bid4WinEntityStub("id2", stub1.getName());
    assertTrue(stub1.same(stub2));
    assertTrue(stub2.same(stub1));
  }
  /**
   * Test of same(Bid4WinEntity, Bid4WinList<Bid4WinRelationNode>) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testSame_Bid4WinEntity_Bid4WinListOfBid4WinRelationNode() throws Bid4WinException
  {
    Bid4WinEntityStub stub1 = new Bid4WinEntityStub("id1", "name1");
    Bid4WinEntityStub stub2 = new Bid4WinEntityStub("id1", "name1");
    assertTrue(stub1.same(stub1, this.nodeList));
    assertTrue(stub1.same(stub2, this.nodeList));
    assertTrue(stub2.same(stub1, this.nodeList));
    assertFalse(stub1.same(null));

    stub1.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub2);
    assertFalse(stub1.same(stub2, this.nodeList));
    assertFalse(stub2.same(stub1, this.nodeList));

    stub2.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub1);
    assertTrue(stub1.same(stub2, this.nodeList));
    assertTrue(stub2.same(stub1, this.nodeList));

    Bid4WinEntityStub stub1_1 = new Bid4WinEntityStub("id2", "name2");
    Bid4WinEntityStub stub1_2 = new Bid4WinEntityStub("id3", "name3");
    Bid4WinEntityStub stub2_1 = new Bid4WinEntityStub("id2", "name2");
    Bid4WinEntityStub stub2_2 = new Bid4WinEntityStub("id3", "name3");

    stub1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                 Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub1_1);
    assertFalse(stub1.same(stub2, this.nodeList));
    assertFalse(stub2.same(stub1, this.nodeList));

    stub2.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                 Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub2_1);
    assertTrue(stub1.same(stub2, this.nodeList));
    assertTrue(stub2.same(stub1, this.nodeList));

    stub1_1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                   Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub1_2);
    assertFalse(stub1.same(stub2, this.nodeList));
    assertFalse(stub2.same(stub1, this.nodeList));

    stub2_1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                   Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub2_2);
    assertTrue(stub1.same(stub2, this.nodeList));
    assertTrue(stub2.same(stub1, this.nodeList));

    stub1 = new Bid4WinEntityStub("id", "name");
    stub2 = new Bid4WinEntityStub(stub1.getId(), "name2");
    assertFalse(stub1.same(stub2));
    assertFalse(stub2.same(stub1));
    stub2 = new Bid4WinEntityStub("id2", stub1.getName());
    assertTrue(stub1.same(stub2));
    assertTrue(stub2.same(stub1));
  }

  /**
   * Test of identical(Bid4WinEntity) method, of class Bid4WinEntity.
   */
  @Test
  public void testIdentical_Bid4WinEntity()
  {
    Bid4WinEntityStub stub1 = new Bid4WinEntityStub("id", "name");
    Bid4WinEntityStub stub2 = new Bid4WinEntityStub("id", "name");
    this.checkSame(stub1, stub1);
    this.checkSame(stub1, stub2);
    this.checkSame(stub2, stub1);
    this.checkIdentical(stub1, stub2);
    this.checkIdentical(stub2, stub1);
    assertFalse(stub1.same(null));
    assertFalse(stub2.same(null));
    stub2 = new Bid4WinEntityStub(stub1.getId(), "name2");
    this.checkNotSame(stub1, stub2);
    this.checkNotSame(stub2, stub1);
    this.checkNotIdentical(stub1, stub2);
    this.checkNotIdentical(stub2, stub1);
    stub2 = new Bid4WinEntityStub("id2", stub1.getName());
    this.checkSame(stub1, stub2);
    this.checkSame(stub2, stub1);
    this.checkNotIdentical(stub1, stub2);
    this.checkNotIdentical(stub2, stub1);
  }

  /**
   * Test of render(Bid4WinList<Bid4WinRelationNode>) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testRender_Bid4WinListOfBid4WinRelationNode() throws Bid4WinException
  {
    Bid4WinEntityStub stub1 = new Bid4WinEntityStub("key 1", "value 1");
    Bid4WinEntityStub stub2 = new Bid4WinEntityStub("key 2", "value 2");
    Bid4WinEntityStub stub3 = new Bid4WinEntityStub("key 3", "value 3");
    Bid4WinEntityStub stub4 = new Bid4WinEntityStub("key 4", "value 4");
    stub1.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub2);
    stub2.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub3);
    stub3.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub4);
    stub4.linkTo(Bid4WinEntityStub_Relations.RELATION_PARENT,
                 Bid4WinEntityStub_Relations.RELATION_SON, stub1);
    stub2.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                 Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub1);
    stub3.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                 Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub1);
    stub4.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                 Bid4WinEntityStub_Relations.RELATION_EMPLOYE, stub1);

    System.out.println(stub1.render(this.nodeList));
    System.out.println("###########");
    System.out.println(stub2.render(this.nodeList));
    System.out.println("###########");
    System.out.println(stub3.render(this.nodeList));
  }

  /**
   * Test of linkTo(Bid4WinRelation, Bid4WinEntity) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLinkTo_Bid4WinRelation_Bid4WinEntity() throws Bid4WinException
  {
    Bid4WinEntityStub parent = new Bid4WinEntityStub("parent");
    Bid4WinEntityStub son1 = new Bid4WinEntityStub("son1");
    Bid4WinEntityStub son2 = new Bid4WinEntityStub("son2");
    // Test d'une création d'un lien nul
    try
    {
      parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON, null);
      fail("Should fail with null son");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertNull("Wrong result", parent.getSon());
    }
    // Test d'une création d'un lien valide
    parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON, son1);
    assertTrue("Wrong result", son1 == parent.getSon());
    // Test d'une création d'un lien déjà utilisé
    try
    {
      parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON, son2);
      fail("Should fail with defined son");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong result", son1 == parent.getSon());
    }
  }
  /**
   * Test of unlinkFrom(Bid4WinRelation) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFrom_Bid4WinRelation() throws Bid4WinException
  {
    Bid4WinEntityStub parent = new Bid4WinEntityStub("parent");
    Bid4WinEntityStub son = new Bid4WinEntityStub("son");
    // Test d'une suppression d'un lien nul
    try
    {
      parent.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON);
      fail("Should fail with null son");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Test d'une suppression d'un lien valide
    parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON, son);
    assertTrue("Wrong result", son == parent.getSon());
    parent.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON);
    assertNull("Wrong result", parent.getSon());
  }
  /**
   * Test of linkTo(Bid4WinRelation, Bid4WinRelation, Bid4WinEntity) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testLinkTo_Bid4WinRelation_Bid4WinRelation_Bid4WinEntity() throws Bid4WinException
  {
    Bid4WinEntityStub parent = new Bid4WinEntityStub("parent");
    Bid4WinEntityStub son = new Bid4WinEntityStub("son");
    // Test d'une création d'un lien nul
    try
    {
      parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON,
                     Bid4WinEntityStub_Relations.RELATION_PARENT, null);
      fail("Should fail with null son");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertNull("Wrong result", parent.getSon());
    }
    // Test d'une création d'un lien valide
    parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON,
                   Bid4WinEntityStub_Relations.RELATION_PARENT, son);
    assertTrue("Wrong result", son == parent.getSon());
    assertTrue("Wrong result", parent == son.getParent());
    // Test d'une création d'un lien cassé
    try
    {
      parent.setSon(null);
      parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON,
                     Bid4WinEntityStub_Relations.RELATION_PARENT, son);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertNull("Wrong result", parent.getSon());
      assertTrue("Wrong result", parent == son.getParent());
    }
    // Test d'une création d'un lien déjà utilisé
    try
    {
      parent.setSon(son);
      son.setParent(null);
      parent.linkTo(Bid4WinEntityStub_Relations.RELATION_SON,
                     Bid4WinEntityStub_Relations.RELATION_PARENT, son);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong result", son == parent.getSon());
      assertNull("Wrong result", son.getParent());
    }

    Bid4WinEntityStub boss = new Bid4WinEntityStub("boss");
    Bid4WinEntityStub employe1 = new Bid4WinEntityStub("employe1");
    Bid4WinEntityStub employe2 = new Bid4WinEntityStub("employe2");
    // Test d'une création d'un lien nul
    try
    {
      employe1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                      Bid4WinEntityStub_Relations.RELATION_EMPLOYE, null);
      fail("Should fail with null boss");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertNull("Wrong result", employe1.getBoss());
    }
    // Test d'une création d'un lien valide
    employe1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                    Bid4WinEntityStub_Relations.RELATION_EMPLOYE, boss);
    employe2.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                    Bid4WinEntityStub_Relations.RELATION_EMPLOYE, boss);
    assertEquals("Wrong result", new Bid4WinSet<Bid4WinEntityStub>(employe1, employe2),
                                 boss.getEmployeSet());
    assertTrue("Wrong result", boss == employe1.getBoss());
    assertTrue("Wrong result", boss == employe2.getBoss());
    // Test d'une création d'un lien cassé
    try
    {
      employe1.setBoss(null);
      employe1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                      Bid4WinEntityStub_Relations.RELATION_EMPLOYE, boss);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong result", new Bid4WinSet<Bid4WinEntityStub>(employe1, employe2),
                   boss.getEmployeSet());
      assertNull("Wrong result", employe1.getBoss());
    }
    // Test d'une création d'un lien déjà utilisé
    try
    {
      employe1.setBoss(boss);
      boss.getEmployeSet().remove(employe1);
      employe1.linkTo(Bid4WinEntityStub_Relations.RELATION_BOSS,
                      Bid4WinEntityStub_Relations.RELATION_EMPLOYE, boss);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertEquals("Wrong result", new Bid4WinSet<Bid4WinEntityStub>(employe2),
                   boss.getEmployeSet());
      assertTrue("Wrong result", boss == employe1.getBoss());
    }
  }
  /**
   * Test of unlinkFrom(Bid4WinRelation, Bid4WinRelation) method, of class Bid4WinEntity.
   * @throws Bid4WinException Issue not expected during this test
   */
  @Test
  public void testUnlinkFrom_Bid4WinRelation_Bid4WinRelation() throws Bid4WinException
  {
    Bid4WinEntityStub parent1 = new Bid4WinEntityStub("parent1");
    Bid4WinEntityStub parent2 = new Bid4WinEntityStub("parent2");
    Bid4WinEntityStub son = new Bid4WinEntityStub("son");
    // Test d'une suppression d'un lien nul
    try
    {
      parent1.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON);
      fail("Should fail with null son");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
    }
    // Test d'une suppression d'un lien valide
    parent1.linkTo(Bid4WinEntityStub_Relations.RELATION_SON,
                  Bid4WinEntityStub_Relations.RELATION_PARENT, son);
    assertTrue("Wrong result", son == parent1.getSon());
    assertTrue("Wrong result", parent1 == son.getParent());
    parent1.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON,
                      Bid4WinEntityStub_Relations.RELATION_PARENT);
    assertNull("Wrong result", parent1.getSon());
    assertNull("Wrong result", son.getParent());
    // Test d'une suppression d'un lien cassé
    try
    {
      parent1.setSon(son);
      parent1.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON,
                        Bid4WinEntityStub_Relations.RELATION_PARENT);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong result", son == parent1.getSon());
      assertNull("Wrong result", son.getParent());
    }
    // Test d'une suppression d'un lien cassé
    try
    {
      son.setParent(parent2);
      parent1.unlinkFrom(Bid4WinEntityStub_Relations.RELATION_SON,
                        Bid4WinEntityStub_Relations.RELATION_PARENT);
      fail("Should fail");
    }
    catch(UserException ex)
    {
      System.out.println(ex.getMessage());
      assertTrue("Wrong result", son == parent1.getSon());
      assertTrue("Wrong result", parent2 == son.getParent());
    }
  }
}
