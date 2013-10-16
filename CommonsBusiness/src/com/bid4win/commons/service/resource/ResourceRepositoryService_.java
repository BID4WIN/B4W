package com.bid4win.commons.service.resource;

import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.manager.resource.ResourceRepositoryManager_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthenticationException;
import com.bid4win.commons.persistence.entity.account.security.exception.AuthorizationException;
import com.bid4win.commons.persistence.entity.account.security.exception.SessionException;
import com.bid4win.commons.persistence.entity.resource.ResourceStorage;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsage;
import com.bid4win.commons.service.connection.SessionDataAbstract;

/**
 * Service de gestion g�n�rique des ressources divis�es en stockage/utilisation
 * incluant la gestion des transactions ainsi que celle des habilitations<BR>
 * <BR>
 * @param <STORAGE> D�finition du type de stockages de ressources g�r�s par le service<BR>
 * @param <USAGE> D�finition du type d'utilisations de ressources g�r�s par le service<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le service<BR>
 * @param <SESSION> D�finition du type de conteneur de donn�es d'une session utilis�
 * par le projet<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * @param <SERVICE> D�finition du service impl�ment� afin de pouvoir s'autor�f�rencer<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class ResourceRepositoryService_<STORAGE extends ResourceStorage<STORAGE, TYPE, USAGE>,
                                                 USAGE extends ResourceUsage<USAGE, TYPE, STORAGE>,
                                                 TYPE extends ResourceType<TYPE>,
                                                 SESSION extends SessionDataAbstract<ACCOUNT, ?>,
                                                 ACCOUNT extends AccountAbstract<ACCOUNT>,
                                                 SERVICE extends ResourceRepositoryService_<STORAGE, USAGE, TYPE, SESSION, ACCOUNT, SERVICE>>
       extends ResourceService_<STORAGE, TYPE, SESSION, ACCOUNT, SERVICE>
{
  /**
   * Cette m�thode permet de charger une utilisation de ressource en fonction de
   * son chemin d'acc�s complet
   * @param fullPath Chemin d'acc�s complet de l'utilisation de ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la ressource
   * @return L'utilisation de ressource charg�e sans ses relations
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'ecriture
   * de la ressource � fournir
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
 /* @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public USAGE loadUsage(String fullPath, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    return this.getManager().loadUsage(fullPath, outputStream);
  }
  /**
   * Cette m�thode permet de charger dans son magasin une utilisation de ressource
   * en fonction de son chemin d'acc�s complet. Le chargement s'effectuera � partir
   * du stockage d�fini pour l'utilisation de ressource choisie
   * @param fullPath Chemin d'acc�s complet de l'utilisation de ressource � charger
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune utilisation de ressource n'a pu �tre
   * r�cup�r�e avec le chemin d'acc�s complet en argument
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   */
/*  @Transactional(readOnly = true, rollbackFor = {Bid4WinException.class})
  public void loadUsageInStore(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    this.getManager().loadUsageInStore(fullPath);
  }*/
  /**
   * Cette m�thode permet de cr�er l'utilisation de ressource d�finie en argument
   * @param path Emplacement de stockage de l'utilisation de ressource
   * @param name Nom de l'utilisation de ressource
   * @param storageId Identifiant du stockage de la ressource associ�e
   * @return L'utilisation de ressource cr��e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement d�fini par l'utilisation de ressource
   * est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE createUsage(String path, String name, long storageId)
         throws PersistenceException, UserException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().createUsage(path, name, storageId);
  }
  /**
   * Cette m�thode permet de d�placer l'utilisation de ressource d�finie en argument
   * @param id Identifiant de l'utilisation de ressource � d�placer
   * @param path Nouvel emplacement de stockage de l'utilisation de ressource
   * @param name Nouveau nom de l'utilisation de ressource
   * @return L'utilisation de ressource d�plac�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource � d�placer n'a
   * pu �tre r�cup�r�e
   * @throws UserException Si l'emplacement d�fini par la ressource est invalide
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE moveUsage(long id, String path, String name)
         throws PersistenceException, NotFoundEntityException, UserException,
                SessionException, AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().moveUsage(id, path, name);
  }
  /**
   * Cette m�thode permet de supprimer l'utilisation de ressource d�finie en argument
   * @param id Identifiant de l'utilisation de ressource � supprimer
   * @return L'utilisation de ressource supprim�e
   * @throws RuntimeException Si un probl�me intervient lors de la validation de
   * la transaction
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si l'utilisation de ressource � supprimer
   * n'a pu �tre r�cup�r�e
   * @throws AuthenticationException Si aucun compte utilisateur n'est connect�
   * @throws AuthorizationException Si le niveau d'habilitation du compte utilisateur
   * connect� n'est pas suffisant
   */
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public USAGE deleteUsage(long id)
         throws PersistenceException, NotFoundEntityException, SessionException,
                AuthenticationException, AuthorizationException
  {
    // @ V�rifie le niveau d'habilitation de l'utilisateur connect�
    this.checkAdminRole();
    return this.getManager().deleteUsage(id);
  }

  /**
   * Permet de pr�ciser la r�f�rence du manager des ressources g�r�es par le service
   * @return {@inheritDoc}
   * @see com.bid4win.commons.service.resource.ResourceService_#getManager()
   */
  @Override
  protected abstract ResourceRepositoryManager_<STORAGE, USAGE, TYPE, ACCOUNT> getManager();
}
