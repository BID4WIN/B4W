package com.bid4win.commons.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.Bid4WinObject;

/**
 * Cette classe est la classe de base de tout objet du projet inclus dans une entit�.
 * Elle d�fini les m�thodes et comportement n�cessaires ou utiles<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddable<CLASS extends Bid4WinEmbeddable<CLASS>>
       extends Bid4WinObject<CLASS>
{
  /**
   * Constructeur par d�faut
   */
  public Bid4WinEmbeddable()
  {
    super();
  }

  /**
   * La fonction est impl�ment�e afin de cr�er le code de hachage � partir de la
   * repr�sentation en cha�ne de caract�res de l'objet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    return this.toString().hashCode();
  }
  /**
   * Red�fini l'�galit� interne de base deux jeux object
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return true;
  }
  /**
   * Permet de transformer l'objet en une cha�ne de caract�re lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer();
  }
}
