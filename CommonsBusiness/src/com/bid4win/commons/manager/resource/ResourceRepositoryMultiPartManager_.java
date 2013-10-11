package com.bid4win.commons.manager.resource;

import java.io.InputStream;
import java.io.OutputStream;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilBoolean;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.CommunicationException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinResourceMultiPartStore;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.resource.ResourceStorageMultiPart;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.ResourceUsageMultiPart;
import com.bid4win.commons.persistence.entity.resource.store.ResourcePart;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <STORAGE> Définition du type de stockages de ressources gérés par le manager<BR>
 * @param <USAGE> Définition du type d'utilisations de ressources gérés par le manager<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées
 * par le manager<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilisé par le projet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class ResourceRepositoryMultiPartManager_<STORAGE extends ResourceStorageMultiPart<STORAGE, TYPE, USAGE, PART_TYPE, PART>,
                                                          USAGE extends ResourceUsageMultiPart<USAGE, TYPE, STORAGE, PART_TYPE, PART>,
                                                          TYPE extends ResourceType<TYPE>,
                                                          PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                          PART extends ResourcePart<PART, TYPE, PART_TYPE>,
                                                          ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends ResourceRepositoryManager_<STORAGE, USAGE, TYPE, ACCOUNT>
{
  /**
   * Cette méthode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la portion de ressource à charger
   * @param partType Type de la portion de ressource à charger
   * @param outputStream Flux de sortie dans lequel charger la portion de ressource
   * @return La référence de la portion de ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de l'écriture
   * de la portion de ressource à fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public PART loadPart(long id, PART_TYPE partType, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de la portion de ressource à charger
    PART part = this.getResourceDao().lockById(id).getPart(partType);
    // Charge la portion de ressource correspondant
    this.getRepository().retreive(outputStream, part);
    // Retourne la référence de la ressource chargée
    return part;
  }
  /**
   * Cette méthode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloquée le temps du chargement
   * @param id Identifiant de la portion de ressource à charger
   * @param partType Type de la portion de ressource à charger
   * @return La portion de ressource chargée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide
   */
  public InputStream loadPart(long id, PART_TYPE partType)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et récupère la référence de la portion de ressource à charger
    PART part = this.getResourceDao().lockById(id).getPart(partType);
    // Charge et retourne la portion de ressource correspondant
    return this.getRepository().retreive(part);
  }

  /**
   * Cette méthode permet d'ajouter la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à ajouter
   * @param partType Type de la portion de ressource à ajouter
   * @param type Type de la ressource
   * @param inputStream Portion de ressource à stocker
   * @return La référence de la ressource modifiée
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
   */
  public STORAGE addPart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Ajoute la référence de portion de ressource en base de donnée
    UtilObject.checkEquals("type", type, resource.getType(),
                           resource.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                  MessageRef.SUFFIX_INVALID_ERROR));
    resource.addPartType(partType);
    this.getResourceDao().forceUpdate(resource);
    this.getResourceDao().flush();
    // Stocke la nouvelle de portion de ressource
    this.getRepository().store(inputStream, resource.getPart(partType));
    return resource;
  }
  /**
   * Cette méthode permet de modifier la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à modifier
   * @param partType Type de la portion de ressource à modifier
   * @param type Type de la ressource
   * @param inputStream Portion de ressource à stocker
   * @return La référence de la ressource modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un problème intervient lors de la lecture
   * de la portion de ressource à stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si l'emplacement défini par la portion de ressource est
   * invalide, si le type ne correspond pas à la ressource à modifier ou si celle
   * ci ne référence pas de portion de même type
   */
  public STORAGE updatePart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Modifie la référence de la ressource en base de données
    UtilBoolean.checkTrue("referenced", resource.hasPartType(partType),
                          resource.getPartTypeMessageRef(MessageRef.SUFFIX_UNDEFINED_ERROR));
    UtilObject.checkEquals("type", type, resource.getType(),
                           resource.getMessageRef(MessageRef.SUFFIX_TYPE,
                                                  MessageRef.SUFFIX_INVALID_ERROR));
    this.getResourceDao().forceUpdate(resource);
    this.getResourceDao().flush();
    // Modifie le contenu de la portion de ressource
    this.getRepository().replace(inputStream, resource.getPart(partType));
    return resource;
  }
  /**
   * Cette méthode permet de retirer la portion de ressource définie en argument
   * @param id Identifiant de la portion de ressource à retirer
   * @param partType Type de la portion de ressource à retirer
   * @return La référence de la ressource modifiée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu être
   * récupérée avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement défini par la portion de ressource est invalide, si la ressource
   * à modifier ne référence pas de portion de même type ou si cette dernière correspond
   * à la portion par défaut (Il faut dans ce cas complètement supprimer la ressource)
   */
  public STORAGE removePart(long id, PART_TYPE partType)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // Récupère la référence de la ressource à modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Modifie la référence de la ressource en base de données
    resource.removePartType(partType);
    this.getResourceDao().forceUpdate(resource);
    this.getResourceDao().flush();
    // Retire de portion de ressource
    this.getRepository().remove(resource.getPart(partType));
    return resource;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceManager_#getRepository()
   */
  @Override
  protected abstract Bid4WinResourceMultiPartStore<STORAGE, TYPE, PART_TYPE, PART> getRepository();
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.manager.resource.ResourceRepositoryManager_#getStore()
   */
  @Override
  protected abstract Bid4WinResourceMultiPartStore<USAGE, TYPE, PART_TYPE, PART> getStore();
}
