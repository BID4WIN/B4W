package com.bid4win.commons.persistence.entity;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.reference.MessageRef;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe représente un objet associé à un type défini, inclus dans une entité<BR>
 * <BR>
 * @param <CLASS> Doit définir la classe réellement instanciée<BR>
 * @param <TYPE> Doit définir la classe du type lié à l'objet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddableWithType<CLASS extends Bid4WinEmbeddableWithType<CLASS, TYPE>,
                                                TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinEmbeddable<CLASS> implements Serializable
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 3313087295659955869L;

  /** Type associé à l'objet */
  @Transient
  private TYPE type = null;

  /**
   * Constructeur pour création par introspection
   */
  protected Bid4WinEmbeddableWithType()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type associé à l'objet
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Bid4WinEmbeddableWithType(TYPE type) throws UserException
  {
    super();
    this.defineType(type);
  }

  /**
   * Redéfini l'égalité interne de deux objets par l'égalité de leur contenu
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#equalsInternal(com.bid4win.commons.persistence.entity.Bid4WinEmbeddable)
   */
  @Override
  protected boolean equalsInternal(CLASS toBeCompared)
  {
    return super.equalsInternal(toBeCompared) &&
           this.getType().equals(toBeCompared.getType());
  }
  /**
   * Redéfini la transformation en chaîne de caractères d'un objet lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEmbeddable#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = super.render();
    buffer.append(this.getType().getCode());
    return buffer;
  }
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public MessageRef getTypeMessageRefBase()
  {
    return this.getMessageRefBase().getPrimitive();
  }
  /**
   *
   * TODO A COMMENTER
   * @param partialCodes TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public MessageRef getTypeMessageRefBase(String ... partialCodes)
  {
    return this.getTypeMessageRefBase().getMessageRef(partialCodes);
  }

  /**
   * Cette méthode permet de définir le type associé à l'objet
   * @param type Définition du type associé à l'objet
   * @throws ProtectionException Si le certificat courant est protégé
   * @throws UserException Si on défini un type nul
   */
  private void defineType(TYPE type) throws ProtectionException, UserException
  {
    this.checkProtection();
    this.setType(UtilObject.checkNotNull(
        "type", type, this.getTypeMessageRefBase(MessageRef.SUFFIX_MISSING_ERROR)));
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du type associé à l'objet
   * @return Le type associé à l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "TYPE", length = 10, nullable = false, unique = false)
  public TYPE getType()
  {
    return this.type;
  }
  /**
   * Setter du type associé à l'objet
   * @param type type associé à positionner
   */
  private void setType(TYPE type)
  {
    this.type = type;
  }
}
