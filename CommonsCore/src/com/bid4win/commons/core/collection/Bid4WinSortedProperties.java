package com.bid4win.commons.core.collection;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Cette classe permet de définir des propriétés triables en fonction de leur clé<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinSortedProperties extends Properties
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 5816619832847568150L;

  /**
   * Constructeur
   */
  public Bid4WinSortedProperties()
  {
    super();
  }
  /**
   * Constructeur par copie
   * @param copy Set de propriétés avec lequel remplir le set à construire
   */
  public Bid4WinSortedProperties(Properties copy)
  {
    super();
    for(Object key : copy.keySet())
    {
      this.put(key, copy.get(key));
    }
  }

  /**
   * Cette fonction permet de récupérer la liste triée de clés de propriétés
   * @return {@inheritDoc}
   * @see java.util.Hashtable#keys()
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public synchronized Enumeration keys()
  {
    Enumeration<Object> keysEnum = super.keys();
    Vector<String> keyList = new Vector<String>();
    while(keysEnum.hasMoreElements())
    {
      keyList.add((String)keysEnum.nextElement());
    }
    Collections.sort(keyList);
    return keyList.elements();
 }
}
