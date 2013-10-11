package com.bid4win.commons.persistence.entity.collection;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe repr�sente un set de r�f�rences d'entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityReferenceSet extends Bid4WinSet<Integer>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
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
   * @param entity Entit� � r�f�rencer
   */
  public Bid4WinEntityReferenceSet(Bid4WinEntity<?, ?> entity)
  {
    super(1);
    this.referenceEntity(entity);
  }
  /**
   * Constructeur
   * @param entitySet Set d'entit�s � r�f�rencer
   */
  public Bid4WinEntityReferenceSet(Bid4WinSet<Bid4WinEntity<?, ?>> entitySet)
  {
    super();
    this.referenceEntitySet(entitySet);
  }
  /**
   * Cette m�thode permet de savoir si l'entit� en argument est d�j� r�f�renc�e
   * @param entity Entit� � rechercher
   * @return True si l'entit� en argument est d�j� r�f�renc�e, false le cas �ch�ant
   */
  public boolean isReferenced(Bid4WinEntity<?, ?> entity)
  {
    return this.contains(this.getKey(entity));
  }
  /**
   * Cette m�thode permet de r�f�rencer l'entit� en argument
   * @param entity Entit� � r�f�rencer
   */
  public void referenceEntity(Bid4WinEntity<?, ?> entity)
  {
    this.add(this.getKey(entity));
  }
  /**
   * Cette m�thode permet de r�f�rencer le set d'entit�s en argument
   * @param entitySet Set d'entit�s � r�f�rencer
   */
  public void referenceEntitySet(Bid4WinSet<Bid4WinEntity<?, ?>> entitySet)
  {
    for(Bid4WinEntity<?, ?> entity : entitySet)
    {
      this.referenceEntity(entity);
    }
  }

  /**
   * Cette m�thode permet de d�finir la cl� � utiliser pour r�f�rencer dans la map
   * l'entit� en argument
   * @param entity Entit� � r�f�rencer
   * @return La cl� � utiliser pour r�f�rencer dans la map l'entit� en argument
   */
  public Integer getKey(Bid4WinEntity<?, ?> entity)
  {
    return entity.hashCodeDefault();
  }
}
