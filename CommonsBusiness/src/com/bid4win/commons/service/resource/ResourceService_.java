package com.bid4win.commons.service.resource;

import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinStringRecursiveMap;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.manager.resource.ResourceManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.service.Bid4WinService_;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de base du projet pour la gestion des ressources incluant la gestion
 * des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <RESOURCE> D�finition du type de ressources g�r�es par le service<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le service<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceService_<RESOURCE extends Resource<RESOURCE, TYPE>,
                                       TYPE extends ResourceType<TYPE>,
                                       SESSION extends SessionDataAbstract<ACCOUNT>,
                                       ACCOUNT extends AccountAbstract<ACCOUNT>,
                                       SERVICE extends ResourceService_<RESOURCE, TYPE, SESSION, ACCOUNT, SERVICE>>
       extends Bid4WinService_<SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette m�thode permet de charger une ressource en fonction de son chemin d'acc�s
   * complet
   * @param fullPath Chemin d'acc�s complet de la ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La r�f�rence de la ressource charg�e sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * le chemin d'acc�s complet en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public RESOURCE loadResource(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadResource(fullPath, outputStream);
  }
  /**
   * Cette m�thode permet de charger une ressource en fonction de son identifiant
   * @param id Identifiant de la ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return La r�f�rence de la ressource charg�e sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public RESOURCE loadResource(long id, OutputStream outputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadResource(id, outputStream);
  }
  /**
   * Cette m�thode permet de charger une ressource en fonction de son identifiant.
   * Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la ressource � charger
   * @return La ressource charg�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune ressource n'a pu �tre r�cup�r�e avec
   * l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public InputStream loadResource(long id)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().loadResource(id);
  }

  /**
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une liste vide sera retourn�e si l'emplacement donn�
   * ne r�f�rence pas un r�pertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif � la racine du magasin des sous
   * r�pertoires � recherche
   * @return Les sous-r�pertoires de la ressource d�finie par le chemin en param�tre
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  public Bid4WinStringRecursiveMap getSubdirectories(String parentPath)
      throws PersistenceException, UserException, SessionException,
             AuthenticationException, AuthorizationException
  {
    return UtilFile.computeSubdirectories(this.self().getSubdirectoryList(parentPath),
                                          parentPath);
  }
  /**
   * Cette m�thode permet de r�cup�rer les sous-r�pertoires de celui d�fini par
   * le chemin en param�tre. Une liste vide sera retourn�e si l'emplacement donn�
   * ne r�f�rence pas un r�pertoire ou n'existe pas
   * @param parentPath Emplacement parent relatif � la racine du magasin des sous
   * r�pertoires � recherche
   * @return Les sous-r�pertoires de la ressource d�finie par le chemin en param�tre
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de la ressource en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<String> getSubdirectoryList(String parentPath)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().getSubdirectories(parentPath);
  }
  /**
   * Cette fonction permet de r�cup�rer la liste des r�f�rences de resources existantes
   * @return La liste des r�f�rences de resources existantes sans leurs relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<RESOURCE> getResourceList()
         throws PersistenceException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().getResourceList();
  }
  /**
   * Cette m�thode permet de r�cup�rer la liste des ressources dont l'emplacement
   * de stockage correspond � celui en argument
   * @param path Emplacement de stockage des ressource � r�cup�rer
   * @return La liste des ressources dont l'emplacement de stockage correspond �
   * celui en argument
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public Bid4WinList<RESOURCE> getResourceList(String path)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().getResourceList(path);
  }
  /**
   * Cette m�thode permet de cr�er la ressource d�finie en argument
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @param inputStream Ressource � stocker
   * @return La r�f�rence de la ressource cr��e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE createResource(String path, String name, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().createResource(path, name, type, inputStream);
  }
  /**
   * Cette m�thode permet de copier une ressource
   * @param id Identifiant de la ressource � copier
   * @param path Emplacement de stockage de la ressource copi�e
   * @param name Nom de la ressource copi�e
   * @return La reference de la ressource copi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � copier n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource � copier est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE copyResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().copyResource(id, path, name);
  }
  /**
   * Cette m�thode permet de modifier une ressource
   * @param id Identifiant de la r�f�rence de la ressource � modifier
   * @param type Nouveau type de la ressource
   * @param inputStream Ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la ressource � stocker
   * @throws NotFoundEntityException Si la ressource � modifier n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource � modifier est
   * invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE updateResource(long id, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException, NotFoundEntityException,
                UserException, SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().updateResource(id, type, inputStream);
  }
  /**
   * Cette m�thode permet de d�placer la ressource d�finie en argument
   * @param id Identifiant de la ressource � d�placer
   * @param path Nouvel emplacement de stockage de la ressource
   * @param name Nouveau nom de la ressource
   * @return La r�f�rence de la ressource d�plac�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � d�placer n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE moveResource(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().moveResource(id, path, name);
  }
  /**
   * Cette m�thode permet de supprimer la ressource d�finie en argument
   * @param id Identifiant de la ressource � supprimer
   * @return La r�f�rence de la ressource supprim�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si la ressource � supprimer n'a pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public RESOURCE deleteResource(long id)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().deleteResource(id);
  }

  /**
   * Permet de pr�ciser la r�f�rence du manager des ressources g�r�es par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.Bid4WinService_#getManager()
   */
  @Override
  protected abstract ResourceManager_<RESOURCE, TYPE, ACCOUNT> getManager();
}
