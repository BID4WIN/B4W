package com.bid4win.commons.core.comparator;

import java.util.Collection;

import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini le comparateur par défaut utilisé entre collections<BR>
 * <BR>
 * @param <OBJECT> Definition des objets contenus dans les collections à comparer<BR>
 * @param <COLLECTION> Définition des collections à comparer<BR>
 * @param <COMPARATOR> Définition du comparateur utilisé pour comparer les objets
 * des collections<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCollectionComparator<OBJECT, COLLECTION extends Collection<? extends OBJECT>,
                                         COMPARATOR extends Bid4WinComparator<OBJECT>>
       extends Bid4WinComparator<COLLECTION>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                                   Bid4WinComparator<Object>>
      instance = new Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                                 Bid4WinComparator<Object>>(
          Bid4WinComparator.getInstance());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#getInstance()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinComparator<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le comparateur est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du comparateur
   */
  public static Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                            Bid4WinComparator<Object>> getInstanceCollection()
  {
    return Bid4WinCollectionComparator.instance;
  }

  /** Classe de comparaison par défaut des objets de la collection */
  private COMPARATOR objectComparator = null;

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par défaut des objets de la collection
   */
  public Bid4WinCollectionComparator(COMPARATOR objectComparator)
  {
    this.objectComparator = objectComparator;
  }

  /**
   * Getter de la classe de comparaison par défaut des objets de la collection
   * @return La classe de comparaison par défaut des objets de la collection
   */
  public COMPARATOR getObjectComparator()
  {
    return this.objectComparator;
  }
}
