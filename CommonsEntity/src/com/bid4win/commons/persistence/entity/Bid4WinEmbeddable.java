package com.bid4win.commons.persistence.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.MappedSuperclass;

import com.bid4win.commons.core.Bid4WinObject;

/**
 * Cette classe est la classe de base de tout objet du projet inclus dans une entité.
 * Elle défini les méthodes et comportement nécessaires ou utiles<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddable<CLASS extends Bid4WinEmbeddable<CLASS>>
       extends Bid4WinObject<CLASS>
{
  /**
   * Constructeur par défaut
   */
  public Bid4WinEmbeddable()
  {
    super();
  }

  /**
   * La fonction est implémentée afin de créer le code de hachage à partir de la
   * représentation en chaîne de caractères de l'objet
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    return this.toString().hashCode();
  }
  /**
   * Redéfini l'égalité interne de base deux jeux object
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
   * Permet de transformer l'objet en une chaîne de caractère lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer();
  }
}
