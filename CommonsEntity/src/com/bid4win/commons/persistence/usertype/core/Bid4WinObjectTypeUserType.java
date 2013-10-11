package com.bid4win.commons.persistence.usertype.core;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.usertype.Bid4WinStringUserType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les types d'objets en base de
 * donn�es<BR>
 * <BR>
 * @param <TYPE> D�finition du type d'objet � g�rer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectTypeUserType<TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinStringUserType<TYPE>
{
  /**
   * Constructeur
   * @param typeClass Classe du type d'objet � g�rer
   */
  public Bid4WinObjectTypeUserType(Class<TYPE> typeClass)
  {
    super(typeClass);
  }

  /**
   * Cette fonction d�fini la r�cup�ration du type d'objet dont le code  correspond
   * � la cha�ne de caract�res en argument
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
   * Cette fonction d�fini la r�cup�ration de la cha�ne de caract�res correspondant
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
