package com.bid4win.commons.service.property;

import java.util.Properties;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.property.PropertyAbstractManager_;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.Role;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.persistence.entity.property.UtilProperty;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base du projet pour la gestion des propri�t�s incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
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
public abstract class PropertyAbstractService_<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                               PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                               SESSION extends SessionDataAbstract<ACCOUNT>,
                                               ACCOUNT extends AccountAbstract<ACCOUNT>,
                                               SERVICE extends PropertyAbstractService_<PROPERTY, PROPERTY_ROOT, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette m�thode permet de v�rifier l'existence d'une propri�t� en fonction de
   * sa cl�
   * @param key Cl� de la propri�t� dont il faut v�rifier l'existence
   * @return True si la propri�t� existe, false sinon
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   */
  public boolean hasProperty(String key) throws PersistenceException
  {
    try
    {
      // Recherche si la propri�t� demand�e existe
      return this.getInternal().getRoot().getProperty(key) != null;
    }
    catch(UserException ex)
    {
      // Ne devrais pas arrive car on ne demande aucune validation d'habilitation
      throw new PersistenceException(ex);
    }
  }
  /**
   * Cette m�thode permet de r�cup�rer une propri�t� en fonction de sa cl�
   * @param key Cl� de la propri�t� � r�cup�rer
   * @return La propri�t� r�cup�r�e en fonction de sa cl� li�e r�cursivement �
   * ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� demand�e est inexistante
   */
  public PROPERTY getProperty(String key)
         throws PersistenceException, UserException
  {
    return this.getInternal().getProperty(key);
  }
  /**
   * Cette m�thode permet de r�cup�rer toutes les propri�t�s g�r�es par le service
   * @return Le set de toutes les propri�t�s de premier niveau r�cup�r�es li�es
   * r�cursivement � toutes leurs relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws SessionException
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public Bid4WinSet<PROPERTY> getPropertySet()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // Retourne le set de toutes les propri�t�s. On ne recherche pas directement
    // les propri�t�s en acc�dant au manager afin de b�n�ficier du cache interne
    // au service
    return this.getInternal().getRoot(this.getAdminRole()).getPropertySet();
  }
  /**
   * Cette m�thode permet de r�cup�rer toutes les propri�t�s qui passent, ou dont
   * l'une des filles passe le filtre, � savoir la pr�sence de la cha�ne � rechercher
   * dans la cl� ou la valeur.
   * @param searchString La cha�ne � rechercher
   * @return Le set des propri�t�s filtr�es li�es r�cursivement � toutes leurs relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si la manipulation d'une propri�t� �choue
   * @throws SessionException
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public Bid4WinSet<PROPERTY> getFilteredPropertySet(String searchString)
        throws PersistenceException, ModelArgumentException, UserException,
               SessionException, AuthenticationException, AuthorizationException
  {
    return UtilProperty.getFilteredPropertySet(this.self().getPropertySet(), searchString);
  }

  /**
   * Cette m�thode permet de modifier la valeur d'une propri�t�
   * @param key Cl� de la propri�t� � modifier
   * @param value La nouvelle valeur de la propri�t�
   * @return La propri�t� une fois modifi�e r�cursivement li�e � toutes ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si la propri�t� � modifier est inexistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public PROPERTY updateProperty(String key, String value)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Modifie la propri�t� et charge enti�rement ses relations
    return this.getManager().updateProperty(key, value).loadRelation();
  }
  /**
   * Cette m�thode permet d'importer toutes les propri�t�s en param�tre. Celles
   * d�j� existantes verront leur valeur mise � jour
   * @param properties Propri�t�s � importer
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws ModelArgumentException Si un probl�me intervient lors de la manipulation
   * des propri�t�s
   * @throws UserException Si une cl� est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void importProperties(Properties properties)
         throws PersistenceException, ModelArgumentException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // Importe les propri�t�s en param�tre
    this.getManager().importProperties(properties);
  }
  /**
   * Cette m�thode permet d'exporter toutes les propri�t�s g�r�es par le service
   * @return L'export de toutes les propri�t�s g�r�es par le service
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public Properties exportProperties()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    // La transformation en propri�t�s chargera enti�rement la propri�t� racine
    return this.getManager().getRoot().toProperties();
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
    this.getInternal().clearCache();
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getAdminRole()
   */
  @Override
  public final Role getAdminRole()
  {
    return this.getInternal().getAdminRole();
  }
  /**
   * Permet de pr�ciser la r�f�rence du manager des propri�t�s g�r�es par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected PropertyAbstractManager_<PROPERTY,PROPERTY_ROOT, ACCOUNT> getManager()
  {
    return this.getInternal().getManager();
  }
  /**
   * Permet de pr�ciser la r�f�rence du service interne de gestion des propri�t�s
   * @return La r�f�rence du service interne de gestions des propri�t�s
   */
  protected abstract PropertyAbstractInternalService_<PROPERTY,PROPERTY_ROOT, SESSION, ACCOUNT, ?> getInternal();
}
