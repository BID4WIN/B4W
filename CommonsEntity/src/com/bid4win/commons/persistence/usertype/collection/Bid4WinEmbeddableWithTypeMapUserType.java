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
 * Cette classe précise les principaux fonctionnements des 'types utilisateur'
 * gérant en base de données des maps d'objets associés à des types définis<BR>
 * <BR>
 * @param <EMBEDDED> Définition des valeurs de la map à gérer en base de données<BR>
 * @param <TYPE> Définition du type associé aux valeurs de la map à gérer en base
 * de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
   * Redéfini la récupération de la map correspondant à la chaîne de caractères
   * en argument afin de vérifier la présence de l'objet associé au type défini
   * par défaut
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
   * Cette fonction est définie afin de construire le couple clé/valeur correspondant
   * à la chaîne de caractères en argument grace aux type utilisateur en charge
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
   * Cette fonction est définie afin de récupérer le couple clé/valeur en paramètre
   * sous la forme d'une chaîne de caractères grace aux type utilisateur en charge
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
   * Getter de la classe du type associé aux valeurs des map gérées
   * @return La classe du type associé aux valeurs des map gérées
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
