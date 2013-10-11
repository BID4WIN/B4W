package com.bid4win.commons.persistence.entity.foo.not_cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.bid4win.commons.core.comparator.Bid4WinComparator;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable;

/**
 * Classe d'objets pouvant �tre inclus<BR>
 * <BR><BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@Embeddable
@Access(AccessType.FIELD)
public class FooEmbeddable extends Bid4WinEmbeddable<FooEmbeddable>
{
  /** Cl� de l'objet */
  @Transient
  private String key;
  /** Valeur de l'objet */
  @Transient
  private String value;

  /**
   * Constructeur
   */
  protected FooEmbeddable()
  {
    super();
  }
  /**
   * Constructeur
   * @param key Cl� de l'objet
   * @param value Valeur de l'objet
   */
  public FooEmbeddable(String key, String value)
  {
    this();
    this.setKey(key);
    this.setValue(value);
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(FooEmbeddable toBeCompared)
  {
    if(!Bid4WinComparator.getInstance().equals(this.getKey(), toBeCompared.getKey()) ||
       !Bid4WinComparator.getInstance().equals(this.getValue(), toBeCompared.getValue()))
    {
      return false;
    }
    return true;
  }
  /**
   * Permet de transformer l'objet en une cha�ne de caract�re lisible
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Key=").append(this.getKey());
    buffer.append(" Value=").append(this.getValue());
    return buffer;
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter de la cl� de l'objet
   * @return La cl� de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "KEY_", length = 20, nullable = false, unique = false)
  public String getKey()
  {
    return this.key;
  }
  /**
   * Setter de la cl� de l'objet
   * @param key Cl� de l'objet � positionner
   */
  public void setKey(String key)
  {
    this.key = key;
  }

  /**
   * Getter de la valeur de l'objet
   * @return La valeur de l'objet
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @Column(name = "VALUE", length = 20, nullable = false, unique = false)
  public String getValue()
  {
    return this.value;
  }
  /**
   * Setter de la valeur de l'objet
   * @param value Valeur de l'objet � positionner
   */
  public void setValue(String value)
  {
    this.value = value;
  }
}
