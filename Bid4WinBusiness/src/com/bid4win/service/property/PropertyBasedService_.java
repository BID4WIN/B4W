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
 * @author Emeric Fillâtre
 */
public abstract class PropertyBasedService_
       extends Bid4WinService_<SessionData, Account, PropertyBasedService_>
{
  /** Référence du service interne de gestion des propriétés */
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
      // Récupère la propriété racine
      PropertyRoot propertyRoot = this.getService().getRoot();
      // Valide la version de la propriété racine
      if(this.getPropertyRootVersion() < propertyRoot.getVersion())
      {
        synchronized(this.getService())
        {
          if(this.getPropertyRootVersion() < propertyRoot.getVersion())
          {
            // Démarre la protection des objets
            String protectionId = ObjectProtector.startProtection();
            try
            {
              // Applique les potentielles modifications des propriétés
              this.applyPropertyRootChange(propertyRoot);
              // Prend en compte la nouvelle version des propriétés
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
                // Arrête la protection des objets
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
   * Getter de la référence du service de gestion des propriétés
   * @return La référence du service de gestion des propriétés
   */
  protected PropertyInternalService getService()
  {
    return this.service;
  }
  /**
   * Getter de la référence du manager de gestion des propriétés
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
