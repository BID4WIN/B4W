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
 * Cette classe repr�sente un objet associ� � un type d�fini, inclus dans une entit�<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * @param <TYPE> Doit d�finir la classe du type li� � l'objet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Bid4WinEmbeddableWithType<CLASS extends Bid4WinEmbeddableWithType<CLASS, TYPE>,
                                                TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinEmbeddable<CLASS> implements Serializable
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 3313087295659955869L;

  /** Type associ� � l'objet */
  @Transient
  private TYPE type = null;

  /**
   * Constructeur pour cr�ation par introspection
   */
  protected Bid4WinEmbeddableWithType()
  {
    super();
  }
  /**
   * Constructeur
   * @param type Type associ� � l'objet
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Bid4WinEmbeddableWithType(TYPE type) throws UserException
  {
    super();
    this.defineType(type);
  }

  /**
   * Red�fini l'�galit� interne de deux objets par l'�galit� de leur contenu
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
   * Red�fini la transformation en cha�ne de caract�res d'un objet lisiblement
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
   * Cette m�thode permet de d�finir le type associ� � l'objet
   * @param type D�finition du type associ� � l'objet
   * @throws ProtectionException Si le certificat courant est prot�g�
   * @throws UserException Si on d�fini un type nul
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
   * Getter du type associ� � l'objet
   * @return Le type associ� � l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "TYPE", length = 10, nullable = false, unique = false)
  public TYPE getType()
  {
    return this.type;
  }
  /**
   * Setter du type associ� � l'objet
   * @param type type associ� � positionner
   */
  private void setType(TYPE type)
  {
    this.type = type;
  }
}
