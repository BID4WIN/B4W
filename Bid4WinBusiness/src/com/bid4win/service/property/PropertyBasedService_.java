package com.bid4win.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.ObjectProtector;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.manager.property.PropertyManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyBasedService_
       extends Bid4WinService_<SessionData, Account, PropertyBasedService_>
{
  /** R�f�rence du service interne de gestion des propri�t�s */
  @Autowired
  @Qualifier("PropertyInternalService")
  private PropertyInternalService service = null;

  /** TODO A COMMENTER */
  private int propertyRootVersion = -1;

  /**
   *
   * TODO A COMMENTER
   * @throws PersistenceException TODO A COMMENTER
   */
  protected void checkForPropertyRootChange() throws PersistenceException
  {
    try
    {
      // R�cup�re la propri�t� racine
      PropertyRoot propertyRoot = this.getService().getRoot();
      // Valide la version de la propri�t� racine
      if(this.getPropertyRootVersion() < propertyRoot.getVersion())
      {
        synchronized(this.getService())
        {
          if(this.getPropertyRootVersion() < propertyRoot.getVersion())
          {
            // D�marre la protection des objets
            String protectionId = ObjectProtector.startProtection();
            try
            {
              // Applique les potentielles modifications des propri�t�s
              this.applyPropertyRootChange(propertyRoot);
              // Prend en compte la nouvelle version des propri�t�s
              this.setPropertyRootVersion(propertyRoot.getVersion());
            }
            catch(Bid4WinException ex)
            {
              ex.printStackTrace();
            }
            finally
            {
              try
              {
                // Arr�te la protection des objets
                ObjectProtector.stopProtection(protectionId);
              }
              catch(ProtectionException ex)
              {
                throw new PersistenceException(ex);
              }
            }
          }
        }
      }
    }
    catch(UserException ex)
    {
      throw new PersistenceException(ex);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param propertyRoot TODO A COMMENTER
   * @throws Bid4WinException TODO A COMMENTER
   */
  protected abstract void applyPropertyRootChange(PropertyRoot propertyRoot) throws Bid4WinException;

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  private int getPropertyRootVersion()
  {
    return this.propertyRootVersion;
  }
  /**
   *
   * TODO A COMMENTER
   * @param propertyRootVersion TODO A COMMENTER
   */
  private void setPropertyRootVersion(int propertyRootVersion)
  {
    this.propertyRootVersion = propertyRootVersion;
  }

  /**
   * Getter de la r�f�rence du service de gestion des propri�t�s
   * @return La r�f�rence du service de gestion des propri�t�s
   */
  protected PropertyInternalService getService()
  {
    return this.service;
  }
  /**
   * Getter de la r�f�rence du manager de gestion des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected PropertyManager getManager()
  {
    return this.getService().getManager();
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return this.getService().getAdminRole();
  }
}
