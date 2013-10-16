package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilString;

/**
 * Cette classe sert de définition pour une relation entre deux entités<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRelation extends Bid4WinObject<Bid4WinRelation>
{
  /** Représente une absence de relation */
  public final static Bid4WinRelation NO_RELATION = new Bid4WinRelation();

  /** Nom de la relation */
  private String name;
  /** Type de la relation */
  private Type type;

  /**
   * Constructeur d'une absence de relation
   */
  public Bid4WinRelation()
  {
    this(UtilString.EMPTY, null);
  }
  /**
   * Constructeur
   * @param name Nom de la relation
   * @param type Type de la relation
   */
  public Bid4WinRelation(String name, Type type)
  {
    this.setName(name);
    this.setType(type);
  }

  /**
   * Redéfini l'égalité interne de deux relations par l'égalité de leur code de
   * hachage
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinRelation toBeCompared)
  {
    return this.hashCode() == toBeCompared.hashCode();
  }
  /**
   * Redéfini la valeur du code de hachage en utilisant celle défini par défaut
   * par la classe Object
   * @return {@inheritDoc} {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  {
    return this.hashCodeDefault();
  }
  /**
   * Défini le rendu d'une relation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer("NAME=" + this.getName() + " ").append(this.getType().render());
  }

  /**
   * Getter du nom de la relation
   * @return Le nom de la relation
   */
  public String getName()
  {
    return this.name;
  }
  /**
   * Setter du nom de la relation
   * @param name Nom de la relation à positionner
   */
  private void setName(String name)
  {
    this.name = UtilString.trimNotNull(name);
  }

  /**
   * Getter du type de la relation
   * @return Le type de la relation
   */
  public Type getType()
  {
    return this.type;
  }
  /**
   * Setter du type de la relation
   * @param type Type de la relation à positionner
   */
  private void setType(Type type)
  {
    if(type == null)
    {
      type = Type.UNKNOWN;
    }
    this.type = type;
  }

  //*************************************************************************//
  //******************* Définition des types de relations *******************//
  //*************************************************************************//
  /**
   * Cette classe défini un type de relation comparable à une énumération ayant
   * la notion d'appartenance à un type supérieur<BR>
   * <BR>
   * @author Emeric Fillâtre
   */
  public static class Type extends Bid4WinObjectType<Type>
  {
    /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
     * valeur doit être modifiée si une évolution de la classe la rend incompatible
     * avec les versions précédentes */
    private static final long serialVersionUID = -3431146150849906519L;

    /** Relation de type simple */
    public final static Type SIMPLE = new Type("SIMPLE");
    /** Relation de type collection */
    public final static Type COLLECTION = new Type("COLLECTION");
    /** Relation de type set */
    public final static Type SET = new Type("SET", COLLECTION);
    /** Relation de type liste */
    public final static Type LIST = new Type("LIST", COLLECTION);
    /** Relation de type map */
    public final static Type MAP = new Type("MAP");
    /** Relation de type inconnue */
    public final static Type UNKNOWN = new Type("UNKNOWN");

    /**
     * Constructeur
     * @param code Code du type
     */
    protected Type(String code)
    {
      super(code);
    }

    /**
     * Constructeur
     * @param code Code du type
     * @param type Type parent du type
     */
    protected Type(String code, Type type)
    {
      super(code, type);
    }

    /**
     * Défini le rendu d'un type de relation
     * @return {@inheritDoc}
     * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#render()
     */
    @Override
    public StringBuffer render()
    {
      return new StringBuffer("TYPE=" + this.getCode());
    }
}
}
