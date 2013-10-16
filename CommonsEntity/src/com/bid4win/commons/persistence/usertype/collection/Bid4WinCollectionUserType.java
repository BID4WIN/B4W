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
 * Cette classe pr�cise les principaux fonctionnements des 'types utilisateur'
 * g�rant en base de donn�es des collections sous la forme d'une seule colonne
 * de type cha�ne de caract�res<BR>
 * <BR>
 * @param <ELEMENT> D�finition des �l�ments de la collection � g�rer en base de
 * donn�es<BR>
 * @param <COLLECTION> D�finition de la collection � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinCollectionUserType<ELEMENT extends Serializable,
                                                COLLECTION extends Bid4WinCollectionAbstract<ELEMENT, ?, COLLECTION>>
       extends Bid4WinStringUserType<COLLECTION>
{
  /** S�parateur utilis� entre les diff�rents �l�ments de la collection */
  public final static String SEPARATOR = "#|#";

  /**
   * Constructeur
   * @param collectionClass Classe de la collection � g�rer
   */
  protected Bid4WinCollectionUserType(Class<COLLECTION> collectionClass)
  {
   super(collectionClass);
  }

  /**
   * Cette fonction d�fini la r�cup�ration de la collection correspondant � la
   * cha�ne de caract�res en argument
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
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
   * � la collection en argument
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
   * Cette fonction doit �tre impl�ment� afin de construire l'objet correspondant
   * � la cha�ne de caract�res en argument
   * @param string Cha�ne de caract�res correspondant aux diff�rents champs de
   * l'objet � construire
   * @return L'objet correspondant � la cha�ne de caract�res en argument
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * de l'objet � retourner
   */
  public abstract ELEMENT elementFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit �tre impl�ment�e afin de r�cup�rer l'objet en param�tre
   * sous la forme d'une cha�ne de caract�res
   * @param element Objet qu'il faut r�cup�rer sous la forme d'une cha�ne de caract�res
   * @return La cha�ne de caract�res repr�sentant l'objet en param�tre
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * de la cha�ne de caract�res
   */
  public abstract String stringFromElement(ELEMENT element) throws Bid4WinException;

  /**
   * Cette m�thode permet de cr�er les collections g�r�es par le 'type utilisateur'
   * @param size Taille initiale de la collection � cr�er
   * @return La collection nouvellement cr��e
   * @throws RuntimeArgumentException Si un probl�me intervient � l'instanciation
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
