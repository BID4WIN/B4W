package com.bid4win.commons.persistence.entity.collection;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe représente un set de références d'entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityReferenceSet extends Bid4WinSet<Integer>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 49247354881247739L;

  /**
   * Constructeur
   */
  public Bid4WinEntityReferenceSet()
  {
    super();
  }
  /**
   * Constructeur
   * @param entity Entité à référencer
   */
  public Bid4WinEntityReferenceSet(Bid4WinEntity<?, ?> entity)
  {
    super(1);
    this.referenceEntity(entity);
  }
  /**
   * Constructeur
   * @param entitySet Set d'entités à référencer
   */
  public Bid4WinEntityReferenceSet(Bid4WinSet<Bid4WinEntity<?, ?>> entitySet)
  {
    super();
    this.referenceEntitySet(entitySet);
  }
  /**
   * Cette méthode permet de savoir si l'entité en argument est déjà référencée
   * @param entity Entité à rechercher
   * @return True si l'entité en argument est déjà référencée, false le cas échéant
   */
  public boolean isReferenced(Bid4WinEntity<?, ?> entity)
  {
    return this.contains(this.getKey(entity));
  }
  /**
   * Cette méthode permet de référencer l'entité en argument
   * @param entity Entité à référencer
   */
  public void referenceEntity(Bid4WinEntity<?, ?> entity)
  {
    this.add(this.getKey(entity));
  }
  /**
   * Cette méthode permet de référencer le set d'entités en argument
   * @param entitySet Set d'entités à référencer
   */
  public void referenceEntitySet(Bid4WinSet<Bid4WinEntity<?, ?>> entitySet)
  {
    for(Bid4WinEntity<?, ?> entity : entitySet)
    {
      this.referenceEntity(entity);
    }
  }

  /**
   * Cette méthode permet de définir la clé à utiliser pour référencer dans la map
   * l'entité en argument
   * @param entity Entité à référencer
   * @return La clé à utiliser pour référencer dans la map l'entité en argument
   */
  public Integer getKey(Bid4WinEntity<?, ?> entity)
  {
    return entity.hashCodeDefault();
  }
}
