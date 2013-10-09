package com.bid4win.commons.core.collection;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Cette classe permet de d�finir des propri�t�s triables en fonction de leur cl�<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinSortedProperties extends Properties
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
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
   * @param copy Set de propri�t�s avec lequel remplir le set � construire
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
   * Cette fonction permet de r�cup�rer la liste tri�e de cl�s de propri�t�s
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
