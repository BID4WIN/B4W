package com.bid4win.commons.core.reference;

import org.junit.Test;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class PropertyRefTest
{
  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void test()
  {
    Bid4WinList<PropertyRef> list = new Bid4WinList<PropertyRef>(
        Bid4WinObjectType.getTypes(PropertyRef.class));
    TestComparator comparator = new TestComparator();
    list.sort(comparator);
    for(PropertyRef ref : list)
    {
      if(!ref.hasParent())
      {
        this.printRef(ref);
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param ref TODO A COMMENTER
   */
  public void printRef(PropertyRef ref)
  {
    System.out.println(ref.getCode());
    Bid4WinList<PropertyRef> list = new Bid4WinList<PropertyRef>(ref.getSubtypes());
    TestComparator comparator = new TestComparator();
    list.sort(comparator);
    for(PropertyRef subref : list)
    {
      this.printRef(subref);
    }
  }


  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class TestComparator extends Bid4WinComparator<PropertyRef>
  {
    /**
     *
     * TODO A COMMENTER
     * @param ref1 {@inheritDoc}
     * @param ref2 {@inheritDoc}
     * @return {@inheritDoc}
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(PropertyRef ref1, PropertyRef ref2)
    {
      return ref1.getCode().compareTo(ref2.getCode());
    }
  }
}
