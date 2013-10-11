package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;
import java.util.Map.Entry;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe précise les principaux fonctionnements des 'types utilisateur'
 * gérant en base de données des maps sous la forme d'une seule colonne de type
 * chaîne de caractères<BR>
 * <BR>
 * @param <KEY> Définition des clés de la map à gérer en base de données<BR>
 * @param <VALUE> Définition des valeurs de la map à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinMapUserType<KEY extends Serializable, VALUE extends Serializable>
       extends Bid4WinStringUserType<Bid4WinMap<KEY, VALUE>>
{
  /** Séparateur utilisé entre les différents couples clé/valeur de la map */
  public final static String SEPARATOR = "#|#";

  /**
   * Constructeur
   */
  @SuppressWarnings("unchecked")
  protected Bid4WinMapUserType()
  {
    super((Class<Bid4WinMap<KEY, VALUE>>)new Bid4WinMap<KEY, VALUE>().getClass());
  }

  /**
   * Cette fonction défini la récupération de la map correspondant à la chaîne de
   * caractères en argument
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#typeFromString(java.lang.String)
   */
  @Override
  public Bid4WinMap<KEY, VALUE> typeFromString(String string) throws Bid4WinException
  {
    String[] strings = this.split(string, Bid4WinMapUserType.SEPARATOR);
    Bid4WinMap<KEY, VALUE> map = this.createMap(string.length());
    for(String embeddedString : strings)
    {
      Entry<KEY, VALUE> entry = this.entryFromString(embeddedString);
      UtilBoolean.checkFalse("contains", map.containsKey(entry.getKey()));
      map.put(entry.getKey(), entry.getValue());
    }
    return map;
  }
  /**
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * à la map en argument
   * @param map {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(Bid4WinMap<KEY, VALUE> map) throws Bid4WinException
  {
    UtilObject.checkNotNull("map", map);
    StringBuffer result = new StringBuffer();
    boolean first = true;
    for(Entry<KEY, VALUE> entry : map.entrySet())
    {
      if(first)
      {
        first = false;
      }
      else
      {
        result.append(Bid4WinMapUserType.SEPARATOR);
      }
      result.append(UtilString.checkNotContains("entry", this.stringFromEntry(entry),
                                                Bid4WinMapUserType.SEPARATOR));
    }
    return result.toString();
  }

  /**
   * Cette fonction doit être implémenté afin de construire le couple clé/valeur
   * correspondant à la chaîne de caractères en argument
   * @param string Chaîne de caractères correspondant aux différents champs du
   * couple clé/valeur à construire
   * @return Le couple clé/valeur correspondant à la chaîne de caractères en argument
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * du couple clé/valeur à retourner
   */
  public abstract Entry<KEY, VALUE> entryFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit être implémentée afin de récupérer le couple clé/valeur
   * en paramètre sous la forme d'une chaîne de caractères
   * @param entry Couple clé/valeur qu'il faut récupérer sous la forme d'une chaîne
   * de caractères
   * @return La chaîne de caractères représentant le couple clé/valeur en paramètre
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * de la chaîne de caractères
  */
  public abstract String stringFromEntry(Entry<KEY, VALUE> entry) throws Bid4WinException;

  /**
   * Cette méthode permet de créer les maps gérées par le 'type utilisateur'
   * @param size Taille initiale de la map à créer
   * @return La map nouvellement créée
   * @throws RuntimeArgumentException Si un problème intervient à l'instanciation
   * de la map
   */
  private Bid4WinMap<KEY, VALUE> createMap(int size) throws RuntimeArgumentException
  {
    try
    {
      return this.returnedClass().getDeclaredConstructor(int.class).newInstance(size);
    }
    catch(Exception ex)
    {
      throw new RuntimeArgumentException(ex);
    }
  }
}
