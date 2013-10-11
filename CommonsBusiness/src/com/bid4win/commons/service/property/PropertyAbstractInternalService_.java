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
 * Service de base interne du projet du projet pour la gestion des propri�t�s
 * incluant la gestion des transactions ainsi que celle des habilitations. Ses
 * m�thodes ne doivent pas �tre expos�es mais �tre utilis�es au travers de processus
 * internes<BR>
 * <BR>
 * @param <PROPERTY> D�finition du type de propri�t�s g�r�es par le service<BR>
 * @param <PROPERTY_ROOT> D�finition du type des propri�t�s racines g�r�es par le
 * service<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class PropertyAbstractInternalService_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                                       PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                                       SESSION extends SessionDataAbstract<ACCOUNT>,
                                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                       SERVICE extends PropertyAbstractInternalService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PROPERTY getProperty(String key, Role ... roles)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne la propri�t� demand�e en v�rifiant son existence. On ne recherche
    // pas directement la propri�t� en acc�dant au manager afin de b�n�ficier
    // du cache interne au service
    PROPERTY_ROOT root = this.self().getRoot(roles);
    PROPERTY property = root.getProperty(key);
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
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PROPERTY_ROOT getRoot(Role ... roles)
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // TODO � red�finir pour utiliser la session et rechercher dedans en premier ...
    return this.load(this.getPropertyRootClass(), this.getPropertyRootId(), roles);
  }
  /**
   * Cette m�thode doit d�finir la classe de la propri�t� racine
   * @return La classe de la propri�t� racine
   */
  public Class<PROPERTY_ROOT> getPropertyRootClass()
  {
    return this.getManager().getPropertyRootClass();
  }
  /**
   * Cette m�thode permet de r�cup�rer l'identifiant de la propri�t� racine
   * @return L'identifiant de la propri�t� racine
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
   * Permet de pr�ciser la r�f�rence du manager des propri�t�s g�r�es par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract PropertyAbstractManager_<PROPERTY,PROPERTY_ROOT, ACCOUNT> getManager();
}
