package com.bid4win.commons.persistence.usertype.collection;

import java.io.Serializable;

import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinCollectionAbstract;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.RuntimeArgumentException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe précise les principaux fonctionnements des 'types utilisateur'
 * gérant en base de données des collections sous la forme d'une seule colonne
 * de type chaîne de caractères<BR>
 * <BR>
 * @param <ELEMENT> Définition des éléments de la collection à gérer en base de
 * données<BR>
 * @param <COLLECTION> Définition de la collection à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinCollectionUserType<ELEMENT extends Serializable,
                                                COLLECTION extends Bid4WinCollectionAbstract<ELEMENT, ?, COLLECTION>>
       extends Bid4WinStringUserType<COLLECTION>
{
  /** Séparateur utilisé entre les différents éléments de la collection */
  public final static String SEPARATOR = "#|#";

  /**
   * Constructeur
   * @param collectionClass Classe de la collection à gérer
   */
  protected Bid4WinCollectionUserType(Class<COLLECTION> collectionClass)
  {
   super(collectionClass);
  }

  /**
   * Cette fonction défini la récupération de la collection correspondant à la
   * chaîne de caractères en argument
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#typeFromString(java.lang.String)
   */
  @Override
  public COLLECTION typeFromString(String string) throws Bid4WinException
  {
    String[] strings = this.split(string, Bid4WinCollectionUserType.SEPARATOR);
    COLLECTION collection = this.createCollection(string.length());
    for(String embeddedString : strings)
    {
      UtilBoolean.checkTrue("add", collection.add(this.elementFromString(embeddedString)));
    }
    return collection;
  }
  /**
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * à la collection en argument
   * @param collection {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(COLLECTION collection) throws Bid4WinException
  {
    UtilObject.checkNotNull("collection", collection);
    StringBuffer result = new StringBuffer();
    boolean first = true;
    for(ELEMENT element : collection)
    {
      if(first)
     {
        first = false;
      }
      else
      {
        result.append(Bid4WinCollectionUserType.SEPARATOR);
      }
      result.append(UtilString.checkNotContains("element", this.stringFromElement(element),
                                                Bid4WinCollectionUserType.SEPARATOR));
    }
    return result.toString();
  }

  /**
   * Cette fonction doit être implémenté afin de construire l'objet correspondant
   * à la chaîne de caractères en argument
   * @param string Chaîne de caractères correspondant aux différents champs de
   * l'objet à construire
   * @return L'objet correspondant à la chaîne de caractères en argument
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * de l'objet à retourner
   */
  public abstract ELEMENT elementFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit être implémentée afin de récupérer l'objet en paramètre
   * sous la forme d'une chaîne de caractères
   * @param element Objet qu'il faut récupérer sous la forme d'une chaîne de caractères
   * @return La chaîne de caractères représentant l'objet en paramètre
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * de la chaîne de caractères
   */
  public abstract String stringFromElement(ELEMENT element) throws Bid4WinException;

  /**
   * Cette méthode permet de créer les collections gérées par le 'type utilisateur'
   * @param size Taille initiale de la collection à créer
   * @return La collection nouvellement créée
   * @throws RuntimeArgumentException Si un problème intervient à l'instanciation
   * de la collection
   */
  private COLLECTION createCollection(int size) throws RuntimeArgumentException
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
