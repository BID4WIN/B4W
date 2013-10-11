package com.bid4win.commons.persistence.entity.collection;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 * Cette classe représente une map de résultats de comparaisons entre deux entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinMatchReferenceMap extends Bid4WinMap<Integer, Boolean>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
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
   * @param entity1 Première entité de la comparaison
   * @param entity2 Deuxième entité de la comparaison
   * @param match Résultat de la comparaison
   */
  public Bid4WinMatchReferenceMap(Bid4WinEntity<?, ?> entity1,
                                  Bid4WinEntity<?, ?> entity2,
                                  boolean match)
  {
    super(1);
    this.match(entity1, entity2, match);
  }

  /**
   * Cette méthode permet de savoir si un résultat de comparaison entre les deux
   * entités en argument est déjà référencé
   * @param entity1 Première entité de la comparaison à rechercher
   * @param entity2 Deuxième entité de la comparaison à rechercher
   * @return True si un résultat de comparaison entre les deux entités en argument
   * est déjà référencé, false le cas échéant
   */
  public boolean isReferenced(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return this.containsKey(this.getKey(entity1, entity2));
  }
  /**
   * Cette méthode permet de récupérer le résultat de la comparaison référencée
   * entre les deux entités en argument
   * @param entity1 Première entité de la comparaison à rechercher
   * @param entity2 Deuxième entité de la comparaison à rechercher
   * @return True si la comparaison référencée entre les deux entités en argument
   * a réussi, false le cas échéant ou si elle n'est pas référencée
   */
  public boolean match(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return this.get(this.getKey(entity1, entity2));
  }

  /**
   * Cette méthode permet de référencer le résultat de la comparaison entre les
   * deux entités en argument
   * @param entity1 Première entité de la comparaison à référencer
   * @param entity2 Deuxième entité de la comparaison à référencer
   * @param match Valeur du résultat de la comparaison entre les deux entités en
   * argument à référencer
   */
  public void match(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2, boolean match)
  {
    this.put(this.getKey(entity1, entity2), match);
  }

  /**
   * Cette méthode permet de définir la clé à utiliser pour référencer dans la map
   * le résultat de la comparaison entre les deux entités en argument
   * @param entity1 Première entité de la comparaison à référencer
   * @param entity2 Deuxième entité de la comparaison à référencer
   * @return La clé à utiliser pour référencer dans la map le résultat de la comparaison
   * entre les deux entités en argument
   */
  public int getKey(Bid4WinEntity<?, ?> entity1, Bid4WinEntity<?, ?> entity2)
  {
    return entity1.hashCodeDefault() + entity2.hashCodeDefault();
  }
}
