package com.bid4win.commons.core.security.collection;

import java.util.Collection;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.IdPattern.Type;

/**
 * Cette classe constitue une liste de types de pattern<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class IdPatternTypeList extends Bid4WinList<Type>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 9140522431785851018L;

  /**
   * Constructeur utilisant la capacité initiale par défaut
   */
  public IdPatternTypeList()
  {
    super();
  }

  /**
   * Constructeur avec précision de la capacité initiale. Si la capacité initiale
   * fournie est inférieure à un, on utilisera la capacité initiale par défaut
   * @param initialCapacity Capacité initiale de la collection
   */
  public IdPatternTypeList(int initialCapacity)
  {
    super(initialCapacity);
  }

  /**
   * Constructeur utilisant le type de pattern en argument pour commencer à remplir
   * la liste
   * @param value Type de pattern à ajouter à la liste
   */
  public IdPatternTypeList(Type value)
  {
    super(value);
  }

  /**
   * Constructeur utilisant la collection de types de pattern en argument pour
   * remplir la liste
   * @param collection Collection de types de pattern à rajouter a la liste
   */
  public IdPatternTypeList(Collection<? extends Type> collection)
  {
    super(collection);
  }

  /**
     * Getter de l'expression régulière de la liste de types de pattern
     * @return L'expression régulière de la liste de types de pattern
   */
  public String getRegexp()
  {
    String result = "";
    String regexp = "";
    int nb = 0;
    for(Type type : this)
    {
      if(type.getRegexp().equals(regexp))
      {
        nb++;
      }
      else
      {
        if(nb != 0)
        {
          result += regexp + "{" + nb + "}";
        }
        regexp = type.getRegexp();
        nb = 1;
      }
    }
    if(nb != 0)
    {
      result += regexp + "{" + nb + "}";
    }
    return result;
  }
}
