package com.bid4win.commons.persistence.usertype.core;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe défini le 'type utilisateur' gérant les types d'objets en base de
 * données<BR>
 * <BR>
 * @param <TYPE> Définition du type d'objet à gérer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectTypeUserType<TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinStringUserType<TYPE>
{
  /**
   * Constructeur
   * @param typeClass Classe du type d'objet à gérer
   */
  public Bid4WinObjectTypeUserType(Class<TYPE> typeClass)
  {
    super(typeClass);
  }

  /**
   * Cette fonction défini la récupération du type d'objet dont le code  correspond
   * à la chaîne de caractères en argument
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#typeFromString(java.lang.String)
   */
  @Override
  public TYPE typeFromString(String string) throws Bid4WinException
  {
    return UtilObject.checkNotNull("type",
                                   Bid4WinObjectType.getType(this.returnedClass(),
                                                             string));
  }
  /**
   * Cette fonction défini la récupération de la chaîne de caractères correspondant
   * au code du type d'objet en argument
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public String stringFromType(TYPE type) throws Bid4WinException
  {
    return UtilObject.checkNotNull("type", type).getCode();
  }
}
