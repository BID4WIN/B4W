package com.bid4win.commons.core.comparator;

import java.util.Collection;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini le comparateur par défaut utilisé entre collections d'objets
 * spécifiques au projet<BR>
 * <BR>
 * @param <OBJECT> Definition des objets contenus dans les collections à comparer<BR>
 * @param <COLLECTION> Définition des collections à comparer<BR>
 * @param <COMPARATOR> Définition du comparateur utilisé pour comparer les objets
 * des collections<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectCollectionComparator<OBJECT extends Bid4WinObject<?>,
                                               COLLECTION extends Collection<? extends OBJECT>,
                                               COMPARATOR extends Bid4WinObjectComparator<OBJECT>>
       extends Bid4WinCollectionComparator<OBJECT, COLLECTION, COMPARATOR>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectCollectionComparator<Bid4WinObject<?>, Collection<? extends Bid4WinObject<?>>,
                                                         Bid4WinObjectComparator<Bid4WinObject<?>>>
      instance = new Bid4WinObjectCollectionComparator<Bid4WinObject<?>, Collection<? extends Bid4WinObject<?>>,
                                                 Bid4WinObjectComparator<Bid4WinObject<?>>>(
          Bid4WinObjectComparator.getInstanceObject());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.comparator.Bid4WinCollectionComparator#getInstanceCollection()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinCollectionComparator<Object, Collection<? extends Object>,
                                            Bid4WinComparator<Object>>
         getInstanceCollection() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le comparateur est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du comparateur
   */
  public static Bid4WinObjectCollectionComparator<Bid4WinObject<?>, Collection<? extends Bid4WinObject<?>>,
                                                  Bid4WinObjectComparator<Bid4WinObject<?>>>
         getInstanceObjectCollection()
  {
    return Bid4WinObjectCollectionComparator.instance;
  }

  /**
   * Constructeur
   * @param objectComparator Classe de comparaison par défaut des objets de la collection
   */
  public Bid4WinObjectCollectionComparator(COMPARATOR objectComparator)
  {
    super(objectComparator);
  }
}
