package com.bid4win.commons.persistence.entity.property;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OptimisticLock;

import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette entité représente la racine de base des propriétés<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <PROPERTY> Doit définir la classe des propriétés gérées<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@MappedSuperclass
@Access(AccessType.FIELD)
@AttributeOverride(name = "version", column = @Column(length = 5))
public abstract class PropertyRootAbstract<CLASS extends PropertyRootAbstract<CLASS, PROPERTY>,
                                           PROPERTY extends PropertyAbstract<PROPERTY, CLASS>>
       extends PropertyBase<CLASS, CLASS, PROPERTY>
{
  /**
   * Constructeur avec précision de l'identifiant car celui-ci doit être défini
   * de manière unique
   * @param id Identifiant de la propriété racine de base
   */
  public PropertyRootAbstract(int id)
  {
    super(id);
  }

  /**
   * Cette méthode permet d'ajouter une propriété à la propriété racine courante
   * @param property {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#addProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @SuppressWarnings("unchecked")
  @Override
  public void addProperty(PROPERTY property)
          throws ProtectionException, ModelArgumentException, UserException
  {
    UtilObject.checkNotNull("property", property).linkTo((CLASS)this);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de l'identifiant de la propriété racine de base
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getId()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Id()
  @Column(name = "ID", length = 1, nullable = false, unique = true)
  public Integer getId()
  {
    return super.getId();
  }

  /**
   * Getter du champs permettant le forçage de la modification de la propriété
   * racine
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getUpdateForce()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "UPDATE_FORCE", length = 5, nullable = false, unique = false)
  public int getUpdateForce()
  {
    return super.getUpdateForce();
  }

  /**
   * Getter de la map interne de sous-propriétés de la propriété racine courante
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#getPropertyMapInternal()
   */
  @Override
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToMany(mappedBy = "root", fetch = FetchType.LAZY,
             cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @MapKey(name = "key")
  @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
  // A partir d'Hibernate 4.1.1, l'entité parent n'est pas mise à jour par défaut
  @OptimisticLock(excluded = false)
  protected Map<String, PROPERTY> getPropertyMapInternal()
  {
    return super.getPropertyMapInternal();
  }
}
