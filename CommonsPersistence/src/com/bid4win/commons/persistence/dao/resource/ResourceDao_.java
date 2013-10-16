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
 * DAO g�n�rique pour les r�f�rence de ressources<BR>
 * <BR>
 * @param <RESOURCE> Doit d�finir la classe des ressources g�r�es<BR>
 * @param <TYPE> D�finition de la classe des types associ�s aux ressources g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ResourceDao_<RESOURCE extends Resource<RESOURCE, TYPE>,
                          TYPE extends ResourceType<TYPE>>
       extends Bid4WinDaoAutoID_<RESOURCE>
{
  /**
   * Constructeur
   * @param resourceClass Classe de la ressource g�r�e par le DAO
   */
  protected ResourceDao_(Class<RESOURCE> resourceClass)
  {
    super(resourceClass);
  }

  /**
   * Cette fonction permet de verrouiller la r�f�rence de ressource en param�tre
   * et de r�cup�rer son dernier �tat persist�
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
   * Cette fonction permet de verrouiller la r�f�rence de ressource dont l'identifiant
   * est pass� en param�tre
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
   * Cette fonction permet de r�cup�rer l'unique r�f�rence de ressource non nulle
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
   * Cette m�thode permet de r�cup�rer la liste compl�te des diff�rents emplacements
   * de stockage des ressources � partir du chemin parent donn�
   * @param parentPath Chemin parent � partir duquel r�cup�rer la liste compl�te
   * des diff�rents emplacements de stockage des ressources
   * @return La liste compl�te des diff�rents emplacements de stockage des ressources
   * � partir du chemin parent donn�
   * @throws UserException Si le chemin en argument est invalide
   */
  public Bid4WinList<String> getPathList(String parentPath) throws UserException
  {
    CriteriaBuilder builder = this.getCriteriaBuilder();
    String pathAlias = "PATH";
    // Cr�ation de la requ�te
    CriteriaQuery<Tuple> criteria = this.getCriteriaBuilder().createQuery(Tuple.class);
    Root<RESOURCE> resource_ = criteria.from(this.getEntityClass());
    Path<String> path_ = resource_.get(Resource_.path);
    // Cr�ation de la condition sur le path parent
    parentPath = UtilFile.concatRelativePath(ResourceRef.RESOURCE, parentPath, "LIKE");
    parentPath = parentPath.substring(0, parentPath.lastIndexOf("LIKE")) + "%";
    Predicate condition = builder.like(path_, parentPath);
    // Construction de la requ�te
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
   * Cette fonction permet de r�cup�rer l'unique r�f�rence de ressource non nulle
   * en fonction de son chemin d'acc�s complet
   * @param fullPath Chemin d'acc�s complet de la r�f�rence de ressource � rechercher
   * @return Le r�f�rence de ressource trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws NotFoundEntityException Si aucune r�f�rence de ressource ne correspond
   * au chemin d'acc�s complet en argument
   * @throws UserException Si le chemin d'acc�s complet en argument est invalide
   */
  public RESOURCE getOneByFullPath(String fullPath)
         throws PersistenceException, NotFoundEntityException, UserException
  {
    return super.getOne(this.getFullPathData(fullPath));
  }
  /**
   * Cette fonction permet de r�cup�rer l'eventuelle r�f�rence de ressource en
   * fonction de son chemin d'acc�s complet
   * @param fullPath Chemin d'acc�s complet de la r�f�rence de ressource � rechercher
   * @return La r�f�rence de ressource �ventuellement trouv�e
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si le chemin d'acc�s complet en argument est invalide
   */
  public RESOURCE findOneByFullPath(String fullPath)
         throws PersistenceException, UserException
  {
    return super.findOne(this.getFullPathData(fullPath));
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher la
   * r�f�rence de ressource dont le chemin d'acc�s complet est pr�cis� en argument
   * @param fullPath Chemin d'acc�s complet de la r�f�rence de ressource � rechercher
   * @return Les crit�res permettant de rechercher la r�f�rence de ressource en
   * fonction de son chemin d'acc�s complet
   * @throws UserException Si le chemin d'acc�s complet en argument est invalide
   */
  protected Bid4WinData<RESOURCE, String> getFullPathData(String fullPath)
            throws UserException
  {
    fullPath = UtilFile.checkRelativePath(fullPath, ResourceRef.RESOURCE);
    return new Bid4WinData<RESOURCE, String>(Resource_Fields.FULL_PATH, fullPath);
  }

  /**
   * Cette m�thode permet de r�cup�rer la liste des ressources dont l'emplacement
   * de stockage correspond � celui en argument
   * @param path Emplacement de stockage des ressource � r�cup�rer
   * @return La liste des ressources dont l'emplacement de stockage correspond �
   * celui en argument
   * @throws PersistenceException Si un probl�me intervient lors de la manipulation
   * de la couche persistante
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  public Bid4WinList<RESOURCE> findResourceList(String path)
         throws PersistenceException, UserException
  {
    return super.findList(this.getPathData(path), null);
  }
  /**
   * Cette m�thode permet de construire les crit�res permettant de rechercher la
   * liste des ressources dont l'emplacement de stockage correspond � celui en argument
   * @param path Emplacement de stockage des ressource � rechercher
   * @return Les crit�res permettant de rechercher la liste des ressources en fonction
   * de leur emplacement de stockage
   * @throws UserException Si l'emplacement de stockage en argument est invalide
   */
  protected Bid4WinData<RESOURCE, String> getPathData(String path) throws UserException
  {
    path = UtilFile.checkRelativePath(path, ResourceRef.RESOURCE);
    return new Bid4WinData<RESOURCE, String>(Resource_Fields.PATH, path);
  }

  /**
   * Cette fonction permet de r�cup�rer la liste compl�te des r�f�rences de ressource
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
   * Cette fonction permet d'ajouter la r�f�rence de ressource en argument
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
   * Cette fonction permet modifier la r�f�rence de ressource en argument
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
   * Cette fonction permet de forcer la modification de la r�f�rence de ressource
   * d�finie en argument. Elle est utilis�e afin de noter que la ressource associ�e
   * a �t� modifi�e
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
   * Cette fonction permet de supprimer la r�f�rence de ressource en argument
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
