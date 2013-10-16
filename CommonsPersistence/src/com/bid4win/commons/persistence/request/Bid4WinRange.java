package com.bid4win.commons.persistence.request;

import com.bid4win.commons.core.Bid4WinObject;

/**
 * Cette classe d�fini une plage des donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRange extends Bid4WinObject<Bid4WinRange>
{
  /** Position de la premi�re donn�e de la plage */
  private int index;
  /** Nombre de donn�es de la plage */
  private int limit;

  /**
   * Constructeur vide
   */
  public Bid4WinRange()
  {
    this(0);
  }
  /**
   * Constructeur sans position de premi�re donn�e de la plage
   * @param limit Nombre de donn�es de la plage
   */
  public Bid4WinRange(int limit)
  {
    this(-1, limit);
  }
  /**
   * Constructeur
   * @param index Position de la premi�re donn�e de la plage
   * @param limit Nombre de donn�es de la plage
   */
  public Bid4WinRange(int index, int limit)
  {
    this.setIndex(index);
    this.setLimit(limit);
  }

  /**
   * Getter du nombre total des donn�es dont la plage est issue
   * @param nb Nombre total de donn�es � partir de la position de la premi�re
   * donn�e de la plage
   * @return Le nombre total des donn�es dont la plage est issue
   */
  public int getTotalCount(int nb)
  {
    return this.getIndex() + nb;
  }

  /**
   * Permet de savoir si la position de la premi�re donn�e de la plage est d�finie
   * @return True si la position de la premi�re donn�e de la plage est d�finie,
   * false sinon
   */
  public boolean hasIndex()
  {
    return this.getIndex() > 0;
  }
  /**
   * Getter de la position de la premi�re donn�e de la plage
   * @return La position de la premi�re donn�e de la plage
   */
  public int getIndex()
  {
    return this.index;
  }
  /**
   * Setter de la position de la premi�re donn�e de la plage
   * @param index Position de la premi�re donn�e de la plage � positionner
   */
  private void setIndex(int index)
  {
    if(index < 0)
    {
      index = 0;
    }
    this.index = index;
  }

  /**
   * Permet de savoir si le nombre de donn�es de la plage est d�fini
   * @return True si le nombre de donn�es de la plage est d�finie, false sinon
   */
  public boolean hasLimit()
  {
    return this.getLimit() > 0;
  }
  /**
   * Getter du nombre de donn�es de la plage
   * @return Le nombre de donn�es de la plage
   */
  public int getLimit()
  {
    return this.limit;
  }
  /**
   * Setter du nombre de donn�es de la plage
   * @param limit Nombre de donn�es de la plage � positionner
   */
  private void setLimit(int limit)
  {
    if(limit < 0)
    {
      limit = 0;
    }
    this.limit = limit;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    return this.render().hashCode();
  }
  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinRange toBeCompared)
  {
    return this.getIndex() == toBeCompared.getIndex() &&
           this.getLimit() == toBeCompared.getLimit();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer("INDEX=").append(this.getIndex()).append(" LIMIT=").append(this.getLimit());
  }
}
