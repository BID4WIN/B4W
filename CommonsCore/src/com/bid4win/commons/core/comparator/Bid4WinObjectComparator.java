package com.bid4win.commons.core.comparator;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.Bid4WinObject.InternalObjectComparator;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini le comparateur des objets sp�cifiques au projet. Elle n'
 * utilise aucune comparaison sp�cifique et utilisera en fait les fonctions de
 * comparaison des objets � comparer<BR>
 * <BR>
 * @param <TYPE> D�finition du type des objets � comparer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectComparator<TYPE extends Bid4WinObject<?>>
       extends Bid4WinComparator<TYPE>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinObjectComparator<Bid4WinObject<?>>
      instance = new Bid4WinObjectComparator<Bid4WinObject<?>>();
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.comparator.Bid4WinComparator#getInstance()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinComparator<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le comparateur est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du comparateur
   */
  public static Bid4WinObjectComparator<Bid4WinObject<?>> getInstanceObject()
  {
    return Bid4WinObjectComparator.instance;
  }

  /** Objet donnant acc�s aux fonctions internes de comparaison des objets sp�cifiques au projet */
  private InternalObjectComparator internalComparator = new InternalObjectComparator();

  /**
   * D�l�gue le test de l'�galit� des contenus � la m�thode equalsInternal() du
   * premier objet de base du projet en param�tre
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
   * Getter de l'objet donnant acc�s aux fonctions internes de comparaison des
   * objets sp�cifiques au projet
   * @return L'objet donnant acc�s aux fonctions internes de comparaison des objets
   * sp�cifiques au projet
   */
  protected InternalObjectComparator getInternalComparator()
  {
    return this.internalComparator;
  }
}
