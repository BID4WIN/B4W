package com.bid4win.commons.persistence.usertype.collection;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;
import com.bid4win.commons.persistence.usertype.Bid4WinEmbeddableWithTypeUserType;

/**
 * Cette classe pr�cise les principaux fonctionnements des 'types utilisateur'
 * g�rant en base de donn�es des maps d'objets associ�s � des types d�finis<BR>
 * <BR>
 * @param <EMBEDDED> D�finition des valeurs de la map � g�rer en base de donn�es<BR>
 * @param <TYPE> D�finition du type associ� aux valeurs de la map � g�rer en base
 * de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinEmbeddableWithTypeMapUserType<EMBEDDED extends Bid4WinEmbeddableWithType<EMBEDDED, TYPE>,
                                                           TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinMapUserType<TYPE, EMBEDDED>
{
  /** Type utilisateur en charge de la gestion des valeurs de la map */
  private Bid4WinEmbeddableWithTypeUserType<EMBEDDED, TYPE> userType = null;

  /**
   * Constructeur
   * @param userType Type utilisateur en charge de la gestion des valeurs de la map
   */
  protected Bid4WinEmbeddableWithTypeMapUserType(
      Bid4WinEmbeddableWithTypeUserType<EMBEDDED, TYPE> userType)
  {
    super();
    this.setUserType(userType);
  }

  /**
   * Red�fini la r�cup�ration de la map correspondant � la cha�ne de caract�res
   * en argument afin de v�rifier la pr�sence de l'objet associ� au type d�fini
   * par d�faut
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinMapUserType#typeFromString(java.lang.String)
   */
  @Override
  public Bid4WinMap<TYPE, EMBEDDED> typeFromString(String string) throws Bid4WinException
  {
    Bid4WinMap<TYPE, EMBEDDED> map = super.typeFromString(string);
    UtilObject.checkNotNull("default",
                            map.get(Bid4WinObjectType.getDefaultType(this.getTypeClass())));
    return map;
  }
  /**
   * Cette fonction est d�finie afin de construire le couple cl�/valeur correspondant
   * � la cha�ne de caract�res en argument grace aux type utilisateur en charge
   * de la gestion des valeurs de la map
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinMapUserType#entryFromString(java.lang.String)
   */
  @Override
  public Entry<TYPE, EMBEDDED> entryFromString(String string) throws Bid4WinException
  {
    EMBEDDED embedded = this.getUserType().typeFromString(string);
    return new SimpleEntry<TYPE, EMBEDDED>(embedded.getType(), embedded);
  }
  /**
   * Cette fonction est d�finie afin de r�cup�rer le couple cl�/valeur en param�tre
   * sous la forme d'une cha�ne de caract�res grace aux type utilisateur en charge
   * de la gestion des valeurs de la map
   * @param entry {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.collection.Bid4WinMapUserType#stringFromEntry(java.util.Map.Entry)
   */
  @Override
  public String stringFromEntry(Entry<TYPE, EMBEDDED> entry) throws Bid4WinException
  {
    return this.getUserType().stringFromType(entry.getValue());
  }

  /**
   * Getter de la classe du type associ� aux valeurs des map g�r�es
   * @return La classe du type associ� aux valeurs des map g�r�es
   */
  public Class<TYPE> getTypeClass()
  {
    return this.getUserType().getTypeClass();
  }
  /**
   * Getter du type utilisateur en charge de la gestion des valeurs de la map
   * @return Le type utilisateur en charge de la gestion des valeurs de la map
   */
  public Bid4WinEmbeddableWithTypeUserType<EMBEDDED, TYPE> getUserType()
  {
    return this.userType;
  }
  /**
   * Setter du type utilisateur en charge de la gestion des valeurs de la map
   * @param userType Type utilisateur en charge de la gestion des valeurs de la map
   */
  private void setUserType(Bid4WinEmbeddableWithTypeUserType<EMBEDDED, TYPE> userType)
  {
    this.userType = userType;
  }
}
