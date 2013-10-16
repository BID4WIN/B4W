package com.bid4win.service.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.service.property.PropertyAbstractInternalService_;
import com.bid4win.manager.property.PropertyManager;
import com.bid4win.persistence.entity.account.Account;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;
import com.bid4win.service.connection.SessionData;

/**
 * Service de gestion interne des propri�t�s incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Component("PropertyInternalService")
@Scope("singleton")
public class PropertyInternalService
       extends PropertyAbstractInternalService_<Property, PropertyRoot, SessionData,
                                                Account, PropertyInternalService>
{
  PropertyRoot root = new PropertyRoot();
  {
    try
    {
      this.root.addProperty(PropertyRef.SERVER_SESSION_TIMEOUT.getCode(), "123456");
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private static PropertyInternalService instance = null;
  public static PropertyInternalService getInstance()
  {
    return PropertyInternalService.instance;
  }

  /** R�f�rence du manager de gestion des propri�t�s */
  @Autowired
  @Qualifier("PropertyManager")
  private PropertyManager manager = null;

  /**
   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl� en v�rifiant
   * le r�le de l'utilisateur connect�
   * @param key Cl� de la propri�t� � r�cup�rer
   * @param roles TODO A COMMENTER
   * @return La propri�t� r�cup�r�e en fonction de sa cl� li�e r�cursivement �
   * ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� demand�e est inexistante
   * @throws SessionException
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Override
  public Property getProperty(String key, Role ... roles)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne la propri�t� demand�e en v�rifiant son existence. On ne recherche
    // pas directement la propri�t� en acc�dant au manager afin de b�n�ficier
    // du cache interne au service
    PropertyRoot root = this.self().getRoot(roles);
    Property property = root.getProperty(key);
    return UtilObject.checkNotNull("property", property,
                                   root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }

  /**
   * Cette m�thode permet de r�cup�rer la racine des propri�t�s. Elle sera d'abord
   * recherch�e dans le cache interne au service apr�s validation de sa version
   * puis en base de donn�es le cas �ch�ant (avec utilisation du cache de second
   * niveau). Cette m�thode est dite interne car elle ne doit pas �tre pr�sent�e
   * directement par le service mais �tre utilis�e par des process internes
   * @param roles TODO A COMMENTER
   * @return La racine des propri�t�s r�cursivement li�e � toutes ses relations
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Override
  public PropertyRoot getRoot(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    return this.root;
  }


  /**
   * L'oscar du r�le d'administration des produits et ressources associ�es est
   * attribu� aux �quipes techniques
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.TECH;
  }
  /**
   * Getter de la r�f�rence du manager interne de gestion des propri�t�s
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.property.PropertyAbstractInternalService_#getManager()
   */
  @Override
  protected PropertyManager getManager()
  {
    return this.manager;
  }
  /**
   *
   * TODO A COMMENTER
   * @param self {@inheritDoc}
   * @see com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean#setSelf(com.bid4win.commons.j2ee.Bid4WinSelfReferencedBean)
   */
  @Override
  protected void setSelf(PropertyInternalService self)
  {
    super.setSelf(self);
    PropertyInternalService.instance = this.self();
  }
}
