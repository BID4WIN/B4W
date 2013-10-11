package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.TypeDef;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.usertype.resource.ResourceTypeStubUserType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@TypeDef(name = "RESOURCE_STUB_TYPE", typeClass = ResourceTypeStubUserType.class, defaultForType = ResourceTypeStub.class)
public class ResourceUsageStub
       extends ResourceUsage<ResourceUsageStub, ResourceTypeStub, ResourceStorageStub>
       implements Bid4WinFileResource<ResourceTypeStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ResourceUsageStub()
  {
    super();
  }

  /**
   * Constructeur
   * @param path Emplacement d'utilisation de la ressource
   * @param name Nom d'utilisation de la ressource
   * @param storage Stockage de la ressource associée à cette utilisation
   * @throws ProtectionException Si le stockage de ressource en argument est protégé
   * @throws ModelArgumentException Si le stockage de ressource en argument est nul
   * @throws UserException Si le nom ou l'emplacement d'utilisation de la ressource
   * est invalide
   */
  public ResourceUsageStub(String path, String name, ResourceStorageStub storage)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(path, name, storage);
  }
}
