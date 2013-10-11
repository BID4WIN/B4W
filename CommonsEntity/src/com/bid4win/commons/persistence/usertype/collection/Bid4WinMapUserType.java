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
 * Cette classe pr�cise les principaux fonctionnements des 'types utilisateur'
 * g�rant en base de donn�es des maps sous la forme d'une seule colonne de type
 * cha�ne de caract�res<BR>
 * <BR>
 * @param <KEY> D�finition des cl�s de la map � g�rer en base de donn�es<BR>
 * @param <VALUE> D�finition des valeurs de la map � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinMapUserType<KEY extends Serializable, VALUE extends Serializable>
       extends Bid4WinStringUserType<Bid4WinMap<KEY, VALUE>>
{
  /** S�parateur utilis� entre les diff�rents couples cl�/valeur de la map */
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
   * Cette fonction d�fini la r�cup�ration de la map correspondant � la cha�ne de
   * caract�res en argument
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
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
   * � la map en argument
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
   * Cette fonction doit �tre impl�ment� afin de construire le couple cl�/valeur
   * correspondant � la cha�ne de caract�res en argument
   * @param string Cha�ne de caract�res correspondant aux diff�rents champs du
   * couple cl�/valeur � construire
   * @return Le couple cl�/valeur correspondant � la cha�ne de caract�res en argument
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * du couple cl�/valeur � retourner
   */
  public abstract Entry<KEY, VALUE> entryFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit �tre impl�ment�e afin de r�cup�rer le couple cl�/valeur
   * en param�tre sous la forme d'une cha�ne de caract�res
   * @param entry Couple cl�/valeur qu'il faut r�cup�rer sous la forme d'une cha�ne
   * de caract�res
   * @return La cha�ne de caract�res repr�sentant le couple cl�/valeur en param�tre
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * de la cha�ne de caract�res
  */
  public abstract String stringFromEntry(Entry<KEY, VALUE> entry) throws Bid4WinException;

  /**
   * Cette m�thode permet de cr�er les maps g�r�es par le 'type utilisateur'
   * @param size Taille initiale de la map � cr�er
   * @return La map nouvellement cr��e
   * @throws RuntimeArgumentException Si un probl�me intervient � l'instanciation
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
