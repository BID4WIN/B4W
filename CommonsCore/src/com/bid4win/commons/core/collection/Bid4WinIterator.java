package com.bid4win.commons.core.collection;

import java.util.Iterator;

import com.bid4win.commons.core.security.ProtectableObject;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 *
 * TODO A COMMENTER
 * NON PROTEGE PAR DEFAUT<BR>
 * <BR>
 * @param <OBJECT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinIterator<OBJECT> extends ProtectableObject implements Iterator<OBJECT>
{
  /** TODO A COMMENTER */
  private Iterator<OBJECT> internal = null;

  /**
   * TODO A COMMENTER
   * @param internal TODO A COMMENTER
   */
  public Bid4WinIterator(Iterator<OBJECT> internal)
  {
    super(null);
    this.setInternal(internal);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext()
  {
    return this.getInternal().hasNext();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.util.Iterator#next()
   */
  @Override
  public OBJECT next()
  {
    return this.getInternal().next();
  }
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @throws ProtectionException Si l'itérateur est protégé contre les modifications
   * @see java.util.Iterator#remove()
   */
  @Override
  public void remove() throws ProtectionException
  {
    this.checkProtection();
    this.getInternal().remove();
  }

  /**
   * Retourne le code de hachage de l'itérateur interne
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return this.getInternal().hashCode();
  }
  /**
   * Test l'égalité entre l'itérateur interne et l'objet en argument
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object toBeCompared)
  {
    return this.getInternal().equals(toBeCompared);
  }

  /**
   * Getter de l'itérateur interne sur lequel se base l'itérateur courant
   * @return L'itérateur interne sur lequel se base l'itérateur courant
   */
  private Iterator<OBJECT> getInternal()
  {
    return this.internal;
  }
  /**
   * Setter de l'itérateur interne sur lequel se base l'itérateur courant
   * @param internal Itérateur interne à positionner
   * @throws ProtectionException Si la liste interne est déjà définie
   */
  protected void setInternal(Iterator<OBJECT> internal) throws ProtectionException
  {
    if(this.internal != null)
    {
      ProtectionException.protectedObject(this.getClass(), 1);
    }
    this.internal = internal;
  }
}
