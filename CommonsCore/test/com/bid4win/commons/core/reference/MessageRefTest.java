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
public class MessageRefTest
{
  /**
   *
   * TODO A COMMENTER
   */
  @Test
  public void test()
  {
    Bid4WinList<MessageRef> list = new Bid4WinList<MessageRef>(
        Bid4WinObjectType.getTypeSet(MessageRef.class));
    TestComparator comparator = new TestComparator();
    list.sort(comparator);
    for(MessageRef ref : list)
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
  public void printRef(MessageRef ref)
  {
    System.out.println(ref.getCode());
    Bid4WinList<MessageRef> list = new Bid4WinList<MessageRef>(ref.getSubtypeSet());
    TestComparator comparator = new TestComparator();
    list.sort(comparator);
    for(MessageRef subref : list)
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
  public static class TestComparator extends Bid4WinComparator<MessageRef>
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
    public int compare(MessageRef ref1, MessageRef ref2)
    {
      return ref1.getCode().compareTo(ref2.getCode());
    }
  }
}
