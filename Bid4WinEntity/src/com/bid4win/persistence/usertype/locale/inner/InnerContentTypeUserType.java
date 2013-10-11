package com.bid4win.persistence.usertype.locale.inner;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;

/**
 * Cette classe d�fini le 'type utilisateur' g�rant les types de contenus internationalis�s
 * en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class InnerContentTypeUserType extends Bid4WinObjectTypeUserType<InnerContentType>
{
  /**
   * Constructeur
   */
  public InnerContentTypeUserType()
  {
    super(InnerContentType.class);
  }
}
