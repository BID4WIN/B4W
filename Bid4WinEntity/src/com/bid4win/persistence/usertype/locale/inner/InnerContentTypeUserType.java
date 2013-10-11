package com.bid4win.persistence.usertype.locale.inner;

import com.bid4win.commons.persistence.usertype.core.Bid4WinObjectTypeUserType;
import com.bid4win.persistence.entity.locale.inner.InnerContentType;

/**
 * Cette classe défini le 'type utilisateur' gérant les types de contenus internationalisés
 * en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
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
