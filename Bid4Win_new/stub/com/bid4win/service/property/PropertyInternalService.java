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
 * Service de gestion interne des propriétés incluant la gestion des transactions
 * ainsi que celle des habilitations<BR>
 * <BR>
 * @author Emeric Fillâtre
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

  /** Référence du manager de gestion des propriétés */
  @Autowired
  @Qualifier("PropertyManager")
  private PropertyManager manager = null;

  /**
   * Cette méthode permet de récupérer une propriété en fonction de sa clé en vérifiant
   * le rôle de l'utilisateur connecté
   * @param key Clé de la propriété à récupérer
   * @param roles TODO A COMMENTER
   * @return La propriété récupérée en fonction de sa clé liée récursivement à
   * ses relations
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propriété demandée est inexistante
   * @throws SessionException
   * @throws AuthenticationException TODO A COMMENTER
   * @throws AuthorizationException TODO A COMMENTER
   */
  @Override
  public Property getProperty(String key, Role ... roles)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne la propriété demandée en vérifiant son existence. On ne recherche
    // pas directement la propriété en accédant au manager afin de bénéficier
    // du cache interne au service
    PropertyRoot root = this.self().getRoot(roles);
    Property property = root.getProperty(key);
    return UtilObject.checkNotNull("property", property,
                                   root.getMessageRef(MessageRef.SUFFIX_UNKNOWN_ERROR));
  }

  /**
   * Cette méthode permet de récupérer la racine des propriétés. Elle sera d'abord
   * recherchée dans le cache interne au service après validation de sa version
   * puis en base de données le cas échéant (avec utilisation du cache de second
   * niveau). Cette méthode est dite interne car elle ne doit pas être présentée
   * directement par le service mais être utilisée par des process internes
   * @param roles TODO A COMMENTER
   * @return La racine des propriétés récursivement liée à toutes ses relations
   * @throws PersistenceException Si un problème intervient lors de la manipulation
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
   * L'oscar du rôle d'administration des produits et ressources associées est
   * attribué aux équipes techniques
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public Role getAdminRole()
  {
    return Role.TECH;
  }
  /**
   * Getter de la référence du manager interne de gestion des propriétés
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
