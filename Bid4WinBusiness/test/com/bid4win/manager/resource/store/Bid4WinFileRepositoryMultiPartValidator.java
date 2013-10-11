package com.bid4win.manager.resource.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.PersistenceException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPart;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourceMultiPartValidator;
import com.bid4win.commons.core.io.resource.Bid4WinFileResourcePart;
import com.bid4win.commons.core.reference.PropertyRef;
import com.bid4win.manager.resource.IRootPathPropertyInitializer;
import com.bid4win.persistence.dao.property.PropertyDao;
import com.bid4win.persistence.dao.property.PropertyRootDao;
import com.bid4win.persistence.entity.property.Property;
import com.bid4win.persistence.entity.property.PropertyRoot;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <RESOURCE> TODO A COMMENTER
 * @param <TYPE> TODO A COMMENTER
 * @param <PART_TYPE> TODO A COMMENTER
 * @param <PART> TODO A COMMENTER
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinFileRepositoryMultiPartValidator<RESOURCE extends Bid4WinFileResourceMultiPart<TYPE, PART_TYPE, PART>,
                                                              TYPE, PART_TYPE extends Bid4WinObjectType<PART_TYPE>,
                                                              PART extends Bid4WinFileResourcePart<PART, TYPE, PART_TYPE>>
       extends Bid4WinFileResourceMultiPartValidator<RESOURCE, TYPE, PART_TYPE, PART>
       implements IRootPathPropertyInitializer
{
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PropertyRootDao")
  private PropertyRootDao rootDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("PropertyDao")
  private PropertyDao propertyDao;
  /** TODO A COMMENTER */
  @Autowired
  @Qualifier("FileRepositoryRootPath")
  private String rootPath;

  /**
   *
   * TODO A COMMENTER
   * @throws PersistenceException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.manager.resource.IRootPathPropertyInitializer#setUpProperty()
   */
  @Override
  @Transactional(readOnly = false, rollbackFor = {Bid4WinException.class})
  public void setUpProperty() throws PersistenceException, ModelArgumentException, UserException
  {
    PropertyRoot root = this.getPropertyRootDao().getRoot();
    Property property = root.getProperty(PropertyRef.SERVER_STORE_ROOT_LOCATION.getCode());
    if(property == null)
    {
      this.getPropertyDao().add(root.addProperty(PropertyRef.SERVER_STORE_ROOT_LOCATION.getCode(),
                                                 this.getRootPath()));
    }
    else
    {
      property.defineValue(this.getRootPath());
      this.getPropertyDao().update(property);
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public PropertyRootDao getPropertyRootDao()
  {
    return this.rootDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public PropertyDao getPropertyDao()
  {
    return this.propertyDao;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getRootPath()
  {
    return this.rootPath;
  }
}
