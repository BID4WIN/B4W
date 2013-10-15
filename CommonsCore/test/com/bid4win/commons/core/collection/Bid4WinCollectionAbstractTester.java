package com.bid4win.commons.core.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

import com.bid4win.commons.core.security.ProtectableObjectTester;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <COLLECTION> TODO A COMMENTER<BR>
 * @param <ELEMENT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinCollectionAbstractTester<COLLECTION extends Bid4WinCollectionAbstract<ELEMENT, ?, COLLECTION>,
                                                      ELEMENT>
       extends ProtectableObjectTester
{
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract COLLECTION createCollection();
  /**
   *
   * TODO A COMMENTER
   * @param index TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract ELEMENT createElement(int index);

  /**
   * Test of equals(Object), of class Bid4WinCollection.
   */
  @Test
  public void testEquals_Object()
  {
    COLLECTION collection1 = this.createCollection();
    COLLECTION collection2 = this.createCollection();
    collection1.add(this.createElement(1));
    collection2.add(this.createElement(1));
    collection1.add(this.createElement(2));
    collection2.add(this.createElement(2));
    collection1.add(this.createElement(3));
    collection2.add(this.createElement(3));
    assertTrue("Should be equal", collection1.equals(collection2));
    assertTrue("Should be equal", collection2.equals(collection1));
    collection1.add(this.createElement(4));
    assertFalse("Should not be equal", collection1.equals(collection2));
    assertFalse("Should not be equal", collection2.equals(collection1));
  }
  /**
   * Test of remove(Object), of class Bid4WinCollection.
   */
  @Test
  public void testRemove_Object()
  {
    COLLECTION collection = this.createCollection();
    ELEMENT element = this.createElement(1);
    collection.add(element);
    assertTrue("Should have been removed", collection.remove(element));
    assertTrue("Should have been removed", collection.isEmpty());
  }
  /**
   * Test of clone(boolean), of class Bid4WinCollection.
   */
  @Test
  public void testClone_boolean()
  {
    COLLECTION collection = this.createCollection();
    COLLECTION clone1 = collection.clone(false);
    assertTrue("Clone should be the same", collection.equals(clone1));
    int i = 0;
    collection.add(this.createElement(++i));
    collection.add(this.createElement(++i));
    collection.add(this.createElement(++i));
    collection.add(this.createElement(++i));
    COLLECTION clone2 = collection.clone(false);
    assertEquals("Old clone should be the same", 0, clone1.size());
    assertFalse("Old clone should be the same", collection.equals(clone1));
    assertEquals("Clone should be the same", i, clone2.size());
    assertTrue("Clone should be the same", collection.equals(clone2));
    ELEMENT element5 = this.createElement(++i);
    ELEMENT element6 = this.createElement(++i);
    clone1.add(element5);
    clone2.add(element6);
    assertEquals("Collection should not have changed", i - 2, collection.size());
    assertFalse("Collection should not have changed", collection.contains(element5));
    assertFalse("Collection should not have changed", collection.contains(element6));
    assertEquals("Clone should have changed", 1, clone1.size());
    assertTrue("Clone should have changed", clone1.contains(element5));
    assertFalse("Clone should have changed", clone1.contains(element6));
    assertEquals("Clone should have changed", i - 1, clone2.size());
    assertFalse("Clone should have changed", clone2.contains(element5));
    assertTrue("Clone should have changed", clone2.contains(element6));

    COLLECTION clone3 = collection.clone(true);
    ELEMENT element7 = this.createElement(++i);
    collection.add(element7);
    assertEquals("Collection should have changed", i - 2, collection.size());
    assertTrue("Collection should have changed", collection.contains(element7));
    assertTrue("Clone should be the same", collection.equals(clone3));

    ELEMENT element8 = this.createElement(++i);
    try
    {
      clone3.add(element8);
      fail("Collection should be protected");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    assertEquals("Collection not should have changed", i - 3, collection.size());
    assertFalse("Collection not should have changed", collection.contains(element8));
  }
  /**
   * Test of "for each", of class Bid4WinCollection.
   */
  @Test
  public void testForEach_0args()
  {
    System.out.println("### \"for each\"");
    COLLECTION collection = this.createCollection();
    collection.add(this.createElement(1));
    collection.add(this.createElement(2));
    collection.add(this.createElement(3));
    collection.add(this.createElement(4));

    for(ELEMENT element : new HashSet<ELEMENT>(collection))
    {
      System.out.println(element);
    }
    System.out.println("### \"for each\"");
  }
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.core.security.ProtectableObjectTester#testCheckProtection()
   */
  @SuppressWarnings("unchecked")
  @Override
  @Test
  public void testCheckProtection()
  {
    COLLECTION collection = this.createCollection();

    int i = 0;
    ELEMENT element1 = this.createElement(i++);
    assertTrue("Element should have been added", collection.add(element1));
    assertTrue("Element should have been added", collection.contains(element1));

    ELEMENT element2 = this.createElement(i++);
    collection.protectFromNothing();
    try
    {
      collection.add(element2);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.addAll(new Bid4WinSet<ELEMENT>(element2));
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.addAll(element2, element2);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.clear();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.remove(element1);
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.removeAll(new Bid4WinSet<ELEMENT>(element1));
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    try
    {
      collection.retainAll(new Bid4WinSet<ELEMENT>());
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    Bid4WinIterator<ELEMENT> iterator = collection.iterator();
    assertTrue("Iterator should also be protected",
               iterator.getProtection() == collection.getProtection());
    iterator.next();
    try
    {
      iterator.remove();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }
    iterator = collection.iteratorProtected();
    assertNotNull("Iterator should also be protected", iterator.getProtection());
    assertFalse("Iterator be protected on its own",
                iterator.getProtection() == collection.getProtection());
    iterator.next();
    try
    {
      iterator.remove();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    try
    {
      collection.getInternal();
      fail("Should fail with protected collection");
    }
    catch(ProtectionException ex)
    {
      System.out.println(ex.getMessage());
    }

    assertEquals("Collection should not have been modified", 1, collection.size());
    assertTrue("Collection should not have been modified", collection.contains(element1));
  }
}
