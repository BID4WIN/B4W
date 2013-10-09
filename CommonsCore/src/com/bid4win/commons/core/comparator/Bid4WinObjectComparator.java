package com.bid4win.commons.core.comparator;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.Bid4WinObject.InternalObjectComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini le comparateur des objets spécifiques au projet. Elle n'
 * utilise aucune comparaison spécifique et utilisera en fait les fonctions de
 * comparaison des objets à comparer<BR>
 * <BR>
 * @param <TYPE> Définition du type des objets à comparer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectComparator<TYPE extends Bid4WinObject<?>>
       extends Bid4WinComparator<TYPE>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectComparator<Bid4WinObject<?>>
      instance = new Bid4WinObjectComparator<Bid4WinObject<?>>();
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
  public static Bid4WinObjectComparator<Bid4WinObject<?>> getInstanceObject()
  {
    return Bid4WinObjectComparator.instance;
  }

  /** Objet donnant accès aux fonctions internes de comparaison des objets spécifiques au projet */
  private InternalObjectComparator internalComparator = new InternalObjectComparator();

  /**
   * Délègue le test de l'égalité des contenus à la méthode equalsInternal() du
   * premier objet de base du projet en paramètre
   * @param object1 {@inheritDoc}
   * @param object2 {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#equalsInternal(java.lang.Object, java.lang.Object)
   */
  @Override
  protected boolean equalsInternal(TYPE object1, TYPE object2)
  {
    return this.getInternalComparator().equalsInternal(object1, object2);
  }

  /**
   * Getter de l'objet donnant accès aux fonctions internes de comparaison des
   * objets spécifiques au projet
   * @return L'objet donnant accès aux fonctions internes de comparaison des objets
   * spécifiques au projet
   */
  protected InternalObjectComparator getInternalComparator()
  {
    return this.internalComparator;
  }
}
