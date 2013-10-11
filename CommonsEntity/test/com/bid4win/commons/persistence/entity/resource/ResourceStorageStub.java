package com.bid4win.commons.persistence.entity.resource;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.TypeDef;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResource;
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
public class ResourceStorageStub
       extends ResourceStorage<ResourceStorageStub, ResourceTypeStub, ResourceUsageStub>
       implements Bid4WinFileResource<ResourceTypeStub>
{
  /**
   * Constructeur pour création par introspection
   */
  protected ResourceStorageStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param path Emplacement de stockage de la ressource
   * @param name Nom de la ressource
   * @param type Type de la ressource
   * @throws UserException Si le nom ou l'emplacement de stockage de la ressource
   * est invalide
   */
  public ResourceStorageStub(String path, String name, ResourceTypeStub type) throws UserException
  {
    super(path, name, type);
  }
}
