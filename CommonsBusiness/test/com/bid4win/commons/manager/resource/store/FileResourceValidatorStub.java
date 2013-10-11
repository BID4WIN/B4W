package com.bid4win.commons.manager.resource.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.io.resource.Bid4WinFileResourceValidator;
import com.bid4win.commons.persistence.entity.resource.FileResourceStub;
import com.bid4win.commons.persistence.entity.resource.ResourceTypeStub;

/**
 * Classe de validation des ressources bouchonn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("FileResourceValidatorStub")
@Scope("singleton")
public class FileResourceValidatorStub
       extends Bid4WinFileResourceValidator<FileResourceStub, ResourceTypeStub>
{
  /** R�f�rence du magasin de ressources � valider */
  @Autowired
  @Qualifier("OutwardlyManagedFileResourceStoreStub")
  private OutwardlyManagedFileResourceStoreStub store;

  /**
   * Getter du magasin des ressources � valider
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.io.resource.Bid4WinResourceValidator#getStore()
   */
  @Override
  public OutwardlyManagedFileResourceStoreStub getStore()
  {
    return this.store;
  }
}
