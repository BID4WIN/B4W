package com.bid4win.commons.core.io.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Classe de validation des ressources bouchonnées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Component("Bid4WinFileResourceValidatorStub")
@Scope("singleton")
public class Bid4WinFileResourceValidatorStub
       extends Bid4WinFileResourceValidator<Bid4WinFileResourceStub, String>
{
  /** Référence du magasin de ressources à valider */
  @Autowired
  @Qualifier("Bid4WinFileResourceStoreStub")
  private Bid4WinFileResourceStoreStub store;

  /**
   * Getter du magasin des ressources à valider
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public Bid4WinFileResourceStoreStub getStore()
  {
    return this.store;
  }
}
