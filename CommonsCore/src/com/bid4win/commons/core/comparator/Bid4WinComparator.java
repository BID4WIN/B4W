package com.bid4win.commons.core.comparator;

import java.util.Comparator;

/**
 * Cette classe d�fini le comparateur par d�faut du projet. Elle n'utilise aucune
 * comparaison sp�cifique et utilisera en fait les fonctions de comparaison des
 * objets � comparer<BR>
 * <BR>
 * @param <TYPE> D�finition du type des objets � comparer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinComparator<TYPE> implements Comparator<TYPE>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinComparator<Object> instance = new Bid4WinComparator<Object>();
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinComparator<Object> getInstance()
  {
    return Bid4WinComparator.instance;
  }

  /**
   * Cette fonction permet de tester l'�galit� entre les deux objets en param�tre
   * avec la possibilit� qu'ils soient nuls
   * @param object1 Premier objet dont il faut tester l'�galit�
   * @param object2 Deuxi�me objet dont il faut tester l'�galit�
   * @return True si les deux objets sont consid�r�s �gaux, false sinon
   */
  public boolean equals(TYPE object1, TYPE object2)
  {
    // Pour comparer plus rapidement
    if(object1 == object2)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(object1 == null || object2 == null)
    {
      return false;
    }
    // Les objets � comparer ne sont pas de la m�me classe
    if(!(object1.getClass().equals(object2.getClass())))
    {
      return false;
    }
    return this.equalsInternal(object1, object2);
  }
  /**
   * Cette m�thode permet de comparer les objets en argument en utilisant leur
   * valeur de hachage s'ils n'impl�mentent l'interface Comparable. Elle retournera
   * une valeur n�gative, positive ou z�ro si le premier argument est consid�r�
   * plus petit que, plus grand que ou �gal au second.
   * @param object1 {@inheritDoc}
   * @param object2 {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public int compare(TYPE object1, TYPE object2)
  {
    // Pour comparer plus rapidement
    if(object1 == object2)
    {
      return 0;
    }
    // Prend en compte les valeurs nulles
    if(object1 == null)
    {
      return -1;
    }
    else if(object2 == null)
    {
      return 1;
    }
    if(Comparable.class.isInstance(object1) && object1.getClass().equals(object2.getClass()))
    {
      return ((Comparable<TYPE>)object1).compareTo(object2);
    }
    return object1.hashCode() - object2.hashCode();
  }

  /**
   * Cette m�thode permet de tester l'�galit� des contenus entre les deux objets
   * en param�tre sans avoir � v�rifier autre chose (nullit�, classe) car ces tests
   * doivent �tre effectu�s par les m�thodes appelantes. La fonction d�l�guera le
   * test d'�galit� des contenus � la m�thode equals() du premier objet � tester
   * @param object1 Premier objet dont il faut tester l'�galit�
   * @param object2 Deuxi�me objet dont il faut tester l'�galit�
   * @return True si les deux objets sont consid�r�s �gaux, false sinon
   */
  protected boolean equalsInternal(TYPE object1, TYPE object2)
  {
    return object1.equals(object2);
  }
}
