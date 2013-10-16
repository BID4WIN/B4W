package com.bid4win.commons.service.resource;

import java.io.InputStream;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.ResourceRepositoryMultiPartManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <STORAGE> Définition du type de stockages de ressources gérés par le service<BR>
 * @param <USAGE> Définition du type d'utilisations de ressources gérés par le service<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le service<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <SESSION> Définition du type de conteneur de données d'une session utilisé
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * @param <SERVICE> Définition du service implémenté afin de pouvoir s'autoréférencer<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryMultiPartService_<STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, USAGE, PART_TYPE, PART>,
                                                          USAGE extends ResourceUsageMultiPart<USAGE, TYPE, STORAGE, PART_TYPE, PART>,
                                                          TYPE extends ResourceType<TYPE>,
                                                          PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                          PART extends ResourcePart<PART, TYPE, PART_TYPE>,
                                                          SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                                          ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                          SERVICE extends ResourceRepositoryMultiPartService_<STORAGE, USAGE, TYPE, PART_TYPE, PART, SESSION, ACCOUNT, SERVICE>>
       extends ResourceRepositoryService_<STORAGE, USAGE, TYPE, SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette méthode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la portion de ressource à charger
   * @param partType Type de la portion de ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la portion de ressource
   * @return La référence de la portion de ressource chargée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'écriture
   * de la portion de ressource à fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PART loadPart(long id, PART_TYPE partType, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().loadPart(id, partType, outputStream);
  }
  /**
   * Cette méthode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la portion de ressource à charger
   * @param partType Type de la portion de ressource à charger
   * @return La portion de ressource chargée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
 /* @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public InputStream loadPart(long id, PART_TYPE partType)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().loadPart(id, partType);
  }*/

  /**
   * Cette méthode permet d'ajouter la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à ajouter
   * @param partType Type de la portion de ressource à ajouter
   * @param type Type de la ressource
   * @param inputStream Portion de ressource à stocker
   * @return La référence de la ressource modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement défini par la portion de ressource est invalide, si le type
   * ne correspond pas à la ressource à modifier ou si celle ci référence déjà une
   * portion de même type
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE addPart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().addPart(id, partType, type, inputStream);
  }
  /**
   * Cette méthode permet de modifier la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à modifier
   * @param partType Type de la portion de ressource à modifier
   * @param type Type de la ressource
   * @param inputStream Portion de ressource à stocker
   * @return La référence de la ressource modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide, si le type ne correspond pas à la ressource à modifier ou si celle
   * ci ne référence pas de portion de même type
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE updatePart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().updatePart(id, partType, type, inputStream);
  }
  /**
   * Cette méthode permet de retirer la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à retirer
   * @param partType Type de la portion de ressource à retirer
   * @return La référence de la ressource modifiée
   * @throws RuntimeException Si un problème intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement défini par la portion de ressource est invalide, si la ressource
   * à modifier ne référence pas de portion de même type ou si cette dernière correspond
   * à la portion par défaut (Il faut dans ce cas complètement supprimer la ressource)
   * @throws AuthenticationException Si aucun compte utilisateur n'est connecté
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connecté n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE removePart(long id, PART_TYPE partType)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ Vérifie le niveau d'habilitation de l'utilisateur connecté
    this.checkAdminRole();
    return this.getManager().removePart(id, partType);
  }

  /**
   * Permet de préciser la référence du manager des ressources gérées par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceService_#getManager()
   */
  @Override
  protected abstract ResourceRepositoryMultiPartManager_<STORAGE, USAGE, TYPE, PART_TYPE, PART, ACCOUNT> getManager();
}
