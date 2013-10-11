package com.bid4win.commons.service.property;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.manager.property.PropertyAbstractManager_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base interne du projet du projet pour la gestion des propriétés
 * incluant la gestion des transactions ainsi que celle des habilitations. Ses
 * méthodes ne doivent pas être exposées mais être utilisées au travers de processus
 * internes<BR>
 * <BR>
 * @param <PROPERTY> Définition du type de propriétés gérées par le service<BR>
 * @param <PROPERTY_ROOT> Définition du type des propriétés racines gérées par le
 * service<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractInternalService_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                       PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                       SESSION extends SessionDataAbstract<ACCOUNT>,
                                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                       SERVICE extends PropertyAbstractInternalService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PROPERTY getProperty(String key, Role ... roles)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne la propriété demandée en vérifiant son existence. On ne recherche
    // pas directement la propriété en accédant au manager afin de bénéficier
    // du cache interne au service
    PROPERTY_ROOT root = this.self().getRoot(roles);
    PROPERTY property = root.getProperty(key);
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PROPERTY_ROOT getRoot(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // TODO à redéfinir pour utiliser la session et rechercher dedans en premier ...
    return this.load(this.getPropertyRootClass(), this.getPropertyRootId(), roles);
  }
  /**
   * Cette méthode doit définir la classe de la propriété racine
   * @return La classe de la propriété racine
   */
  public Class<PROPERTY_ROOT> getPropertyRootClass()
  {
    return this.getManager().getPropertyRootClass();
  }
  /**
   * Cette méthode permet de récupérer l'identifiant de la propriété racine
   * @return L'identifiant de la propriété racine
   */
  public Integer getPropertyRootId()
  {
    return this.getManager().getPropertyRootId();
  }
  /**
   *
   * TODO A COMMENTER {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#clearCache()
   */
  @Override
  protected void clearCache()
  {
    super.clearCache();
  }

  /**
   * Permet de préciser la référence du manager des propriétés gérées par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract PropertyAbstractManager_<PROPERTY,PROPERTY_ROOT, ACCOUNT> getManager();
}
