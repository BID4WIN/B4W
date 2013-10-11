package com.bid4win.tools.property;

import java.io.File;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.io.UtilFile;
import com.bid4win.commons.core.reference.MessageRef.ResourceRef;
import com.bid4win.commons.persistence.entity.account.AccountAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;
import com.bid4win.commons.persistence.entity.property.PropertyRootAbstract;
import com.bid4win.commons.service.property.PropertyAbstractService_;
import com.bid4win.tools.console.Menu;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <PROPERTY> TODO A COMMENTER<BR>
 * @param <PROPERTY_ROOT> TODO A COMMENTER<BR>
 * @param <ACCOUNT> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class PropertyAbstractManager<PROPERTY extends PropertyAbstract<PROPERTY, PROPERTY_ROOT>,
                                              PROPERTY_ROOT extends PropertyRootAbstract<PROPERTY_ROOT, PROPERTY>,
                                              ACCOUNT extends AccountAbstract<ACCOUNT>>
       extends Menu<Menu<?>>
{
  /**
   * Constructeur d'un menu principal
   */
  public PropertyAbstractManager()
  {
    this(null);
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel le menu est initialisé
   */
  public PropertyAbstractManager(Menu<?> menu)
  {
    super("Manage ", menu);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#getWording()
   */
  @Override
  public String getWording()
  {
    return super.getWording() + this.getPropertyType().getWording();
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  @SuppressWarnings("unchecked")
  protected PropertyAbstractService_<PROPERTY, PROPERTY_ROOT, ?, ACCOUNT, ?> getPropertyService()
            throws UserException
  {
    return this.getBean(this.getPropertyServiceBeanName(), PropertyAbstractService_.class);
  }

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String getPropertyServiceBeanName()
  {
    return this.getPropertyType().getWording() + "Service";
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public abstract Type getPropertyType();

  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public String getResourcesPath() throws UserException
  {
    return UtilFile.concatAbsolutePath(ResourceRef.RESOURCE,
                                       this.getProjectRootDir().getAbsolutePath(),
                                       "resources",
                                       this.getPropertyType().getCode().toLowerCase());
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws UserException TODO A COMMENTER
   */
  public File getResourcesPathFile() throws UserException
  {
    return new File(this.getResourcesPath());
  }

  //*************************************************************************//
  //***************** Définition des types de propriété *********************//
  //*************************************************************************//

  /**
   * Cette classe défini un type de propriété comparable à une énumération ayant
   * la notion d'appartenance à un type supérieur<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Type extends Bid4WinObjectType<Type>
  {
    /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
     * valeur doit être modifiée si une évolution de la classe la rend incompatible
     * avec les versions précédentes */
    private static final long serialVersionUID = -193111614551953221L;

    /** Propriété simple */
    public final static Type PROPERTY = new Type("PROPERTY", "Property");
    /** Propriété simple */
    public final static Type I18N = new Type("I18N", "I18n");

    /**
     * Constructeur
     * @param code Code du type de propriété
     * @param wording Libellé du type de propriété
     */
    protected Type(String code, String wording)
    {
      super(code, wording);
    }
  }
}
