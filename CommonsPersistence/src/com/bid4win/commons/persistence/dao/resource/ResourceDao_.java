package com.bid4win.commons.persistence.dao.resource;

import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.dao.Bid4WinDaoAutoID_;
import com.bid4win.commons.persistence.dao.exception.NotFoundEntityException;
import com.bid4win.commons.persistence.dao.exception.NotPersistedEntityException;
import com.bid4win.commons.persistence.entity.resource.Resource;
import com.bid4win.commons.persistence.entity.resource.ResourceType;
import com.bid4win.commons.persistence.entity.resource.Resource_;
import com.bid4win.commons.persistence.entity.resource.Resource_Fields;
import com.bid4win.commons.persistence.request.data.Bid4WinData;

/**
 * DAO générique pour les référence de ressources<BR>
 * <BR>
 * @param <RESOURCE> Doit définir la classe des ressources gérées<BR>
 * @param <TYPE> Définition de la classe des types associés aux ressources gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceDao_<RESOURCE extends Resource<RESOURCE, TYPE>,
                          TYPE extends ResourceType<TYPE>>
       extends Bid4WinDaoAutoID_<RESOURCE>
{
  /**
   * Constructeur
   * @param resourceClass Classe de la ressource gérée par le DAO
   */
  protected ResourceDao_(Class<RESOURCE> resourceClass)
  {
    super(resourceClass);
  }

  /**
   * Cette fonction permet de verrouiller la référence de ressource en paramètre
   * et de récupérer son dernier état persisté
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lock(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public RESOURCE lock(RESOURCE resource) throws PersistenceException
  {
    return super.lock(resource);
  }
  /**
   * Cette fonction permet de verrouiller la référence de ressource dont l'identifiant
   * est passé en paramètre
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#lockById(java.lang.Object)
   */
  @Override
  public RESOURCE lockById(Long id) throws PersistenceException, NotFoundEntityException
  {
    return super.lockById(id);
  }

  /**
   * Cette fonction permet de récupérer l'unique référence de ressource non nulle
   * en fonction de son identifiant
   * @param id {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotFoundEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#getById(java.lang.Object)
   */
  @Override
  public RESOURCE getById(Long id) throws PersistenceException, NotFoundEntityException
  {
    return super.getById(id);
  }

  /**
   * Cette méthode permet de récupérer la liste complète des différents emplacements
   * de stockage des ressources à partir du chemin parent donné
   * @param parentPath Chemin parent à partir duquel récupérer la liste complète
   * des différents emplacements de stockage des ressources
   * @return La liste complète des différents emplacements de stockage des ressources
   * à partir du chemin parent donné
   * @throws UserException Si le chemin en argument est invalide
   */
  public Bid4WinList<String> getPathList(String parentPath) throws UserException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();
    String pathAlias = "PATH";
    // Création de la requête
    CriteriaQuery<Tuple> criteria = this.getCriteriaBuilder().createQuery(Tuple.class);
    Root<RESOURCE> resource_ = criteria.from(this.getEntityClass());
    Path<String> path_ = resource_.get(Resource_.path);
    // Création de la condition sur le path parent
    parentPath = UtilFile.concatRelativePath(ResourceRef.RESOURCE, parentPath, "LIKE");
    parentPath = parentPath.substring(0, parentPath.lastIndexOf("LIKE")) + "%";
    Predicate condition = builder.like(path_, parentPath);
    // Construction de la requête
    criteria.where(condition);
    criteria.multiselect(path_.alias(pathAlias));
    criteria.distinct(true);
    criteria.orderBy(builder.asc(path_));
    TypedQuery<Tuple> query = this.getEntityManager().createQuery(criteria);
    List<Tuple> result = query.getResultList();
    Bid4WinList<String> pathList = new Bid4WinList<String>(result.size());
    for(Tuple tuple : result)
    {
      pathList.add((String)tuple.get(pathAlias));
    }
    return pathList;
  }

  /**
   * Cette fonction permet de récupérer l'unique référence de ressource non nulle
   * en fonction de son chemin d'accès complet
   * @param fullPath Chemin d'accès complet de la référence de ressource à rechercher
   * @return Le référence de ressource trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune référence de ressource ne correspond
   * au chemin d'accès complet en argument
   * @throws UserException Si le chemin d'accès complet en argument est invalide
   */
  public RESOURCE getOneByFullPath(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return super.getOne(this.getFullPathData(fullPath));
  }
  /**
   * Cette fonction permet de récupérer l'eventuelle référence de ressource en
   * fonction de son chemin d'accès complet
   * @param fullPath Chemin d'accès complet de la référence de ressource à rechercher
   * @return La référence de ressource éventuellement trouvée
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le chemin d'accès complet en argument est invalide
   */
  public RESOURCE findOneByFullPath(String fullPath)
         throws PersistenceException, UserException
  {
    return super.findOne(this.getFullPathData(fullPath));
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher la
   * référence de ressource dont le chemin d'accès complet est précisé en argument
   * @param fullPath Chemin d'accès complet de la référence de ressource à rechercher
   * @return Les critères permettant de rechercher la référence de ressource en
   * fonction de son chemin d'accès complet
   * @throws UserException Si le chemin d'accès complet en argument est invalide
   */
  protected Bid4WinData<RESOURCE, String> getFullPathData(String fullPath)
            throws UserException
  {
    fullPath = UtilFile.checkRelativePath(fullPath, ResourceRef.RESOURCE);
    return new Bid4WinData<RESOURCE, String>(Resource_Fields.FULL_PATH, fullPath);
  }

  /**
   * Cette méthode permet de récupérer la liste des ressources dont l'emplacement
   * de stockage correspond à celui en argument
   * @param path Emplacement de stockage des ressource à récupérer
   * @return La liste des ressources dont l'emplacement de stockage correspond à
   * celui en argument
   * @throws PersistenceException Si un problème intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  public Bid4WinList<RESOURCE> findResourceList(String path)
         throws PersistenceException, UserException
  {
    return super.findList(this.getPathData(path), null);
  }
  /**
   * Cette méthode permet de construire les critères permettant de rechercher la
   * liste des ressources dont l'emplacement de stockage correspond à celui en argument
   * @param path Emplacement de stockage des ressource à rechercher
   * @return Les critères permettant de rechercher la liste des ressources en fonction
   * de leur emplacement de stockage
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  protected Bid4WinData<RESOURCE, String> getPathData(String path) throws UserException
  {
    path = UtilFile.checkRelativePath(path, ResourceRef.RESOURCE);
    return new Bid4WinData<RESOURCE, String>(Resource_Fields.PATH, path);
  }

  /**
   * Cette fonction permet de récupérer la liste complète des références de ressource
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#findAll()
   */
  @Override
  public Bid4WinList<RESOURCE> findAll() throws PersistenceException
  {
    return super.findAll();
  }

  /**
   * Cette fonction permet d'ajouter la référence de ressource en argument
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#add(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public RESOURCE add(RESOURCE resource) throws PersistenceException
  {
    return super.add(resource);
  }
  /**
   * Cette fonction permet modifier la référence de ressource en argument
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @throws NotPersistedEntityException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#update(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public RESOURCE update(RESOURCE resource) throws PersistenceException, NotPersistedEntityException
  {
    // Modifie la ressource
    return super.update(resource);
  }
  /**
   * Cette fonction permet de forcer la modification de la référence de ressource
   * définie en argument. Elle est utilisée afin de noter que la ressource associée
   * a été modifiée
   * @param resource {@inheritDoc}
   * @return {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#forceUpdate(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public RESOURCE forceUpdate(RESOURCE resource) throws PersistenceException
  {
    return super.forceUpdate(resource);
  }
  /**
   * Cette fonction permet de supprimer la référence de ressource en argument
   * @param resource {@inheritDoc}
   * @throws PersistenceException {@inheritDoc}
   * @see com.bid4win.commons.persistence.dao.Bid4WinDao_#remove(com.bid4win.commons.persistence.entity.Bid4WinEntity)
   */
  @Override
  public RESOURCE remove(RESOURCE resource) throws PersistenceException
  {
    return super.remove(resource);
  }

}
