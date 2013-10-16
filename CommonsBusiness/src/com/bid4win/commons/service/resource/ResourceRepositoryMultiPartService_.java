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
 * @param <STORAGE> D�finition du type de stockages de ressources g�r�s par le service<BR>
 * @param <USAGE> D�finition du type d'utilisations de ressources g�r�s par le service<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le service<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Cette m�thode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la portion de ressource � charger
   * @param partType Type de la portion de ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la portion de ressource
   * @return La r�f�rence de la portion de ressource charg�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'�criture
   * de la portion de ressource � fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public PART loadPart(long id, PART_TYPE partType, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadPart(id, partType, outputStream);
  }
  /**
   * Cette m�thode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la portion de ressource � charger
   * @param partType Type de la portion de ressource � charger
   * @return La portion de ressource charg�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
 /* @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public InputStream loadPart(long id, PART_TYPE partType)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadPart(id, partType);
  }*/

  /**
   * Cette m�thode permet d'ajouter la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � ajouter
   * @param partType Type de la portion de ressource � ajouter
   * @param type Type de la ressource
   * @param inputStream Portion de ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement d�fini par la portion de ressource est invalide, si le type
   * ne correspond pas � la ressource � modifier ou si celle ci r�f�rence d�j� une
   * portion de m�me type
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE addPart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().addPart(id, partType, type, inputStream);
  }
  /**
   * Cette m�thode permet de modifier la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � modifier
   * @param partType Type de la portion de ressource � modifier
   * @param type Type de la ressource
   * @param inputStream Portion de ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide, si le type ne correspond pas � la ressource � modifier ou si celle
   * ci ne r�f�rence pas de portion de m�me type
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE updatePart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().updatePart(id, partType, type, inputStream);
  }
  /**
   * Cette m�thode permet de retirer la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � retirer
   * @param partType Type de la portion de ressource � retirer
   * @return La r�f�rence de la ressource modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement d�fini par la portion de ressource est invalide, si la ressource
   * � modifier ne r�f�rence pas de portion de m�me type ou si cette derni�re correspond
   * � la portion par d�faut (Il faut dans ce cas compl�tement supprimer la ressource)
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public STORAGE removePart(long id, PART_TYPE partType)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().removePart(id, partType);
  }

  /**
   * Permet de pr�ciser la r�f�rence du manager des ressources g�r�es par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceService_#getManager()
   */
  @Override
  protected abstract ResourceRepositoryMultiPartManager_<STORAGE, USAGE, TYPE, PART_TYPE, PART, ACCOUNT> getManager();
}
