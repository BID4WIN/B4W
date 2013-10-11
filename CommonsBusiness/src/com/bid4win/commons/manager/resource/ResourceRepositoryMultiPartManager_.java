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
 * @param <STORAGE> D�finition du type de stockages de ressources g�r�s par le manager<BR>
 * @param <USAGE> D�finition du type d'utilisations de ressources g�r�s par le manager<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es
 * par le manager<BR>
 * @param <PART_TYPE> TODO A COMMENTER<BR>
 * @param <PART> TODO A COMMENTER<BR>
 * @param <ACCOUNT> Definition de type de compte utilisateur utilis� par le projet<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Cette m�thode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la portion de ressource � charger
   * @param partType Type de la portion de ressource � charger
   * @param outputStream Flux de sortie dans lequel charger la portion de ressource
   * @return La r�f�rence de la portion de ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de l'�criture
   * de la portion de ressource � fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public PART loadPart(long id, PART_TYPE partType, OutputStream outputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de la portion de ressource � charger
    PART part = this.getResourceDao().lockById(id).getPart(partType);
    // Charge la portion de ressource correspondant
    this.getRepository().retreive(outputStream, part);
    // Retourne la r�f�rence de la ressource charg�e
    return part;
  }
  /**
   * Cette m�thode permet de charger une portion de ressource en fonction de son
   * identifiant. Cette ressource sera bloqu�e le temps du chargement
   * @param id Identifiant de la portion de ressource � charger
   * @param partType Type de la portion de ressource � charger
   * @return La portion de ressource charg�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � fournir
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide
   */
  public InputStream loadPart(long id, PART_TYPE partType)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // Bloque et r�cup�re la r�f�rence de la portion de ressource � charger
    PART part = this.getResourceDao().lockById(id).getPart(partType);
    // Charge et retourne la portion de ressource correspondant
    return this.getRepository().retreive(part);
  }

  /**
   * Cette m�thode permet d'ajouter la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � ajouter
   * @param partType Type de la portion de ressource � ajouter
   * @param type Type de la ressource
   * @param inputStream Portion de ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
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
   */
  public STORAGE addPart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Ajoute la r�f�rence de portion de ressource en base de donn�e
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
   * Cette m�thode permet de modifier la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � modifier
   * @param partType Type de la portion de ressource � modifier
   * @param type Type de la ressource
   * @param inputStream Portion de ressource � stocker
   * @return La r�f�rence de la ressource modifi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws CommunicationException Si un probl�me intervient lors de la lecture
   * de la portion de ressource � stocker
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si l'emplacement d�fini par la portion de ressource est
   * invalide, si le type ne correspond pas � la ressource � modifier ou si celle
   * ci ne r�f�rence pas de portion de m�me type
   */
  public STORAGE updatePart(long id, PART_TYPE partType, TYPE type, InputStream inputStream)
         throws PersistenceException, CommunicationException,
                NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Modifie la r�f�rence de la ressource en base de donn�es
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
   * Cette m�thode permet de retirer la portion de ressource d�finie en argument
   * @param id Identifiant de la portion de ressource � retirer
   * @param partType Type de la portion de ressource � retirer
   * @return La r�f�rence de la ressource modifi�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune portion de ressource n'a pu �tre
   * r�cup�r�e avec l'identifiant en argument
   * @throws UserException Si le type de portion de ressource en argument est nul,
   * si l'emplacement d�fini par la portion de ressource est invalide, si la ressource
   * � modifier ne r�f�rence pas de portion de m�me type ou si cette derni�re correspond
   * � la portion par d�faut (Il faut dans ce cas compl�tement supprimer la ressource)
   */
  public STORAGE removePart(long id, PART_TYPE partType)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    // R�cup�re la r�f�rence de la ressource � modifier
    STORAGE resource = this.getResourceDao().getById(id);
    // Modifie la r�f�rence de la ressource en base de donn�es
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
