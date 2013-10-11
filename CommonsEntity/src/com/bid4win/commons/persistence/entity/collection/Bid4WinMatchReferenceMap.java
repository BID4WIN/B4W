package com.bid4win.commons.persistence.entity.collection;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe repr�sente une map de r�sultats de comparaisons entre deux entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinMatchReferenceMap extends Bid4WinMap<Integer, Boolean>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 1729548384022526500L;

  /**
   * Constructeur
   */
  public Bid4WinMatchReferenceMap()
  {
    super();
  }
  /**
   * Constructeur
   * @param entity1 Premi�re entit� de la comparaison
   * @param entity2 Deuxi�me entit� de la comparaison
   * @param match R�sultat de la comparaison
   */
  public Bid4WinMatchReferenceMap(Bid4WinEntity<?, ?> entity1,
                                  Bid4WinEntity<?, ?> entity2,
                                  boolean match)
  {
    super(1);
    this.match(entity1, entity2, match);
  }

  /**
   * Cette m�thode permet de savoir si un r�sultat de comparaison entre les deux
   * entit�s en argument est d�j� r�f�renc�
   * @param entity1 Premi�re entit� de la comparaison � rechercher
   * @param entity2 Deuxi�me entit� de la comparaison � rechercher
   * @return True si un r�sultat de comparaison entre les deux entit�s en argument
   * est d�j� r�f�renc�, false le cas �ch�ant
   */
  public boolean isReferenced(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return this.containsKey(this.getKey(entity1, entity2));
  }
  /**
   * Cette m�thode permet de r�cup�rer le r�sultat de la comparaison r�f�renc�e
   * entre les deux entit�s en argument
   * @param entity1 Premi�re entit� de la comparaison � rechercher
   * @param entity2 Deuxi�me entit� de la comparaison � rechercher
   * @return True si la comparaison r�f�renc�e entre les deux entit�s en argument
   * a r�ussi, false le cas �ch�ant ou si elle n'est pas r�f�renc�e
   */
  public boolean match(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return this.get(this.getKey(entity1, entity2));
  }

  /**
   * Cette m�thode permet de r�f�rencer le r�sultat de la comparaison entre les
   * deux entit�s en argument
   * @param entity1 Premi�re entit� de la comparaison � r�f�rencer
   * @param entity2 Deuxi�me entit� de la comparaison � r�f�rencer
   * @param match Valeur du r�sultat de la comparaison entre les deux entit�s en
   * argument � r�f�rencer
   */
  public void match(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2, boolean match)
  {
    this.put(this.getKey(entity1, entity2), match);
  }

  /**
   * Cette m�thode permet de d�finir la cl� � utiliser pour r�f�rencer dans la map
   * le r�sultat de la comparaison entre les deux entit�s en argument
   * @param entity1 Premi�re entit� de la comparaison � r�f�rencer
   * @param entity2 Deuxi�me entit� de la comparaison � r�f�rencer
   * @return La cl� � utiliser pour r�f�rencer dans la map le r�sultat de la comparaison
   * entre les deux entit�s en argument
   */
  public int getKey(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return entity1.hashCodeDefault() + entity2.hashCodeDefault();
  }
}
