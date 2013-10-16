package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilString;

/**
 * Cette classe sert de d�finition pour une relation entre deux entit�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRelation extends Bid4WinObject<Bid4WinRelation>
{
  /** Repr�sente une absence de relation */
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
   * Red�fini l'�galit� interne de deux relations par l'�galit� de leur code de
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
   * Red�fini la valeur du code de hachage en utilisant celle d�fini par d�faut
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
   * D�fini le rendu d'une relation
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
   * @param name Nom de la relation � positionner
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
   * @param type Type de la relation � positionner
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
  //******************* D�finition des types de relations *******************//
  //*************************************************************************//
  /**
   * Cette classe d�fini un type de relation comparable � une �num�ration ayant
   * la notion d'appartenance � un type sup�rieur<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Type extends Bid4WinObjectType<Type>
  {
    /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
     * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
     * avec les versions pr�c�dentes */
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
     * D�fini le rendu d'un type de relation
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
