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
 * Cette entit� repr�sente la racine de base des propri�t�s<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <PROPERTY> Doit d�finir la classe des propri�t�s g�r�es<BR>
 * <BR>
 * @author Emeric Fill�tre
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
   * Constructeur avec pr�cision de l'identifiant car celui-ci doit �tre d�fini
   * de mani�re unique
   * @param id Identifiant de la propri�t� racine de base
   */
  public PropertyRootAbstract(int id)
  {
    super(id);
  }

  /**
   * Cette m�thode permet d'ajouter une propri�t� � la propri�t� racine courante
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
   * Getter de l'identifiant de la propri�t� racine de base
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
   * Getter du champs permettant le for�age de la modification de la propri�t�
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
   * Getter de la map interne de sous-propri�t�s de la propri�t� racine courante
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
  // A partir d'Hibernate 4.1.1, l'entit� parent n'est pas mise � jour par d�faut
  @OptimisticLock(excluded = false)
  protected Map<String, PROPERTY> getPropertyMapInternal()
  {
    return super.getPropertyMapInternal();
  }
}
