package com.bid4win.commons.core.comparator;

import java.util.Comparator;

/**
 * Cette classe défini le comparateur par défaut du projet. Elle n'utilise aucune
 * comparaison spécifique et utilisera en fait les fonctions de comparaison des
 * objets à comparer<BR>
 * <BR>
 * @param <TYPE> Définition du type des objets à comparer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinComparator<TYPE> implements Comparator<TYPE>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinComparator<Object> instance = new Bid4WinComparator<Object>();
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinComparator<Object> getInstance()
  {
    return Bid4WinComparator.instance;
  }

  /**
   * Cette fonction permet de tester l'égalité entre les deux objets en paramètre
   * avec la possibilité qu'ils soient nuls
   * @param object1 Premier objet dont il faut tester l'égalité
   * @param object2 Deuxième objet dont il faut tester l'égalité
   * @return True si les deux objets sont considérés égaux, false sinon
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
    // Les objets à comparer ne sont pas de la même classe
    if(!(object1.getClass().equals(object2.getClass())))
    {
      return false;
    }
    return this.equalsInternal(object1, object2);
  }
  /**
   * Cette méthode permet de comparer les objets en argument en utilisant leur
   * valeur de hachage s'ils n'implémentent l'interface Comparable. Elle retournera
   * une valeur négative, positive ou zéro si le premier argument est considéré
   * plus petit que, plus grand que ou égal au second.
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
   * Cette méthode permet de tester l'égalité des contenus entre les deux objets
   * en paramètre sans avoir à vérifier autre chose (nullité, classe) car ces tests
   * doivent être effectués par les méthodes appelantes. La fonction déléguera le
   * test d'égalité des contenus à la méthode equals() du premier objet à tester
   * @param object1 Premier objet dont il faut tester l'égalité
   * @param object2 Deuxième objet dont il faut tester l'égalité
   * @return True si les deux objets sont considérés égaux, false sinon
   */
  protected boolean equalsInternal(TYPE object1, TYPE object2)
  {
    return object1.equals(object2);
  }
}
