package com.bid4win.commons.core.security.collection;

import java.util.Collection;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.IdPattern.Type;

/**
 * Cette classe constitue une liste de types de pattern<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class IdPatternTypeList extends Bid4WinList<Type>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 9140522431785851018L;

  /**
   * Constructeur utilisant la capacit� initiale par d�faut
   */
  public IdPatternTypeList()
  {
    super();
  }

  /**
   * Constructeur avec pr�cision de la capacit� initiale. Si la capacit� initiale
   * fournie est inf�rieure � un, on utilisera la capacit� initiale par d�faut
   * @param initialCapacity Capacit� initiale de la collection
   */
  public IdPatternTypeList(int initialCapacity)
  {
    super(initialCapacity);
  }

  /**
   * Constructeur utilisant le type de pattern en argument pour commencer � remplir
   * la liste
   * @param value Type de pattern � ajouter � la liste
   */
  public IdPatternTypeList(Type value)
  {
    super(value);
  }

  /**
   * Constructeur utilisant la collection de types de pattern en argument pour
   * remplir la liste
   * @param collection Collection de types de pattern � rajouter a la liste
   */
  public IdPatternTypeList(Collection<? extends Type> collection)
  {
    super(collection);
  }

  /**
     * Getter de l'expression r�guli�re de la liste de types de pattern
     * @return L'expression r�guli�re de la liste de types de pattern
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
