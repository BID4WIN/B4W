package com.bid4win.commons.core.collection;

import java.util.Iterator;
import java.util.ListIterator;

import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <OBJECT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinListIterator<OBJECT> extends Bid4WinIterator<OBJECT> implements ListIterator<OBJECT>
{
  /** Itérateur interne sur lequel se base l'itérateur courant */
  private ListIterator<OBJECT> internal;

  /**
   * NON PROTEGE PAR DEFAUT
   * TODO A COMMENTER
   * @param internal TODO A COMMENTER
   */
  public Bid4WinListIterator(ListIterator<OBJECT> internal)
  {
    super(internal);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private ListIterator<OBJECT> getInternal()
  {
    return this.internal;
  }
  /**
   *
   * TODO A COMMENTER
   * @param internal {@inheritDoc}
   * @see com.bid4win.commons.core.collection.Bid4WinIterator#setInternal(java.util.Iterator)
   */
  @Override
  protected void setInternal(Iterator<OBJECT> internal)
  {
    if(internal instanceof ListIterator)
    {
      super.setInternal(internal);
      this.internal = (ListIterator<OBJECT>)internal;
    }
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeAdded {@inheritDoc}
   * @throws ProtectionException Si l'itérateur est protégé contre les modifications
   * @see java.util.ListIterator#add(java.lang.Object)
   */
  @Override
  public void add(OBJECT toBeAdded) throws ProtectionException
  {
    this.checkProtection();
    this.getInternal().add(toBeAdded);
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeSet {@inheritDoc}
   * @throws ProtectionException Si l'itérateur est protégé contre les modifications
   * @see java.util.ListIterator#set(java.lang.Object)
   */
  @Override
  public void set(OBJECT toBeSet) throws ProtectionException
  {
    this.checkProtection();
    this.getInternal().set(toBeSet);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.ListIterator#hasPrevious()
   */
  @Override
  public boolean hasPrevious()
  {
    return this.getInternal().hasPrevious();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.ListIterator#previous()
   */
  @Override
  public OBJECT previous()
  {
    return this.getInternal().previous();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.ListIterator#previousIndex()
   */
  @Override
  public int previousIndex()
  {
    return this.getInternal().previousIndex();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.ListIterator#nextIndex()
   */
  @Override
  public int nextIndex()
  {
    return this.getInternal().nextIndex();
  }
}
