package com.bid4win.commons.persistence.entity;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import com.bid4win.commons.core.UtilString;

/**
 * Cette classe correspondant � la d�finition de base d'un champ et de son accessibilit�
 * en JPA<BR>
 * <BR>
 * @param <FROM> D�finition du type de point de d�part<BR>
 * @param <FROM_PATH> D�finition du point de d�part pour atteindre le champ<BR>
 * @param <TO> D�finition du type du champ � atteindre<BR>
 * @param <TO_PATH> D�finition du point d'arriv�e au champ<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinField<FROM, FROM_PATH extends Path<? extends FROM>,
                              TO, TO_PATH extends Path<TO>>
{
  /**
   * Cette m�thode doit �tre d�finie afin de retourner le nom de la d�finition de
   * champ
   * @return Le nom de la d�finition de champ
   */
  public String getName();
  /**
   * Cette m�thode doit �tre d�finie afin de pouvoir atteindre le champ
   * @param from_ Point de d�part pour atteindre le champ
   * @return Point d'arriv�e au champ
   */
  public TO_PATH getPath(FROM_PATH from_);

  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <FROM_PATH> D�finition du point de d�part pour atteindre le champ<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <TO_PATH> D�finition du point d'arriv�e au champ<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static interface Bid4WinFieldSingle<FROM, FROM_PATH extends Path<? extends FROM>,
                                             TO, TO_PATH extends Path<TO>>
         extends Bid4WinField<FROM, FROM_PATH, TO, TO_PATH>
  {
    //  TODO A COMMENTER
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <FROM_PATH> D�finition du point de d�part pour atteindre le champ<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <TO_PATH> D�finition du point d'arriv�e au champ<BR>
   * @param <GROUP> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static interface Bid4WinFieldMultiple<FROM, FROM_PATH extends Path<? extends FROM>,
                                               TO, TO_PATH extends Path<TO>, GROUP>
         extends Bid4WinField<FROM, FROM_PATH, TO, TO_PATH>
  {
    /**
     *
     * TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public Bid4WinField<? super FROM, ?, GROUP, ?> getGroupField();
  }

  //*************************************************************************//
  //****** Classe de d�finition d'un champ acc�d� via sa d�finition JPA *****//
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation de base d'un champ et de son
   * accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <FROM_PATH> D�finition du point de d�part pour atteindre le champ<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <TO_PATH> D�finition du point d'arriv�e au champ<BR>
   * @param <TO_CONTAINER> D�finition du type de conteneur du champ � atteindre<BR>
   * @param <TO_ATTRIBUTE> D�finition JPA du champ � atteindre<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static abstract class Bid4WinFieldAbstract<FROM, FROM_PATH extends Path<? extends FROM>,
                                                    TO, TO_PATH extends Path<TO>,
                                                    TO_CONTAINER,
                                                    TO_ATTRIBUTE extends Attribute<FROM, TO_CONTAINER>>
         implements Bid4WinField<FROM, FROM_PATH, TO, TO_PATH>
  {
    /** Nom de la d�finition de champ */
    private String name = null;
    /** D�finition JPA du champ � atteindre */
    private TO_ATTRIBUTE attribute = null;

    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � atteindre
     */
    protected Bid4WinFieldAbstract(String name, TO_ATTRIBUTE attribute)
    {
      this.setName(name);
      this.setAttribute(attribute);
    }
    /**
     * Getter du nom de la d�finition de champ
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getName()
     */
    @Override
    public String getName()
    {
      return this.name;
    }
    /**
     * Setter du nom de la d�finition de champ
     * @param name Nom de la d�finition de champ
     */
    private void setName(String name)
    {
      this.name = UtilString.trimNotNull(name);
    }
    /**
     * Getter de la d�finition JPA du champ � atteindre
     * @return La d�finition JPA du champ � atteindre
     */
    public TO_ATTRIBUTE getAttribute()
    {
      return this.attribute;
    }
    /**
     * Setter de la d�finition JPA du champ � atteindre
     * @param attribute D�finition JPA du champ � atteindre
     */
    private void setAttribute(TO_ATTRIBUTE attribute)
    {
      /*if(attribute == null)
      {
        throw new NullPointerException();
      }*/
      this.attribute = attribute;
    }
  }

  //*************************************************************************//
  //** Classe de d�finition d'un champ simple acc�d� via sa d�finition JPA **//
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ simple et de son
   * accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldSimple<FROM, TO>
         extends Bid4WinFieldAbstract<FROM, Path<? extends FROM>, TO, Path<TO>,
                                      TO, SingularAttribute<FROM, TO>>
         implements Bid4WinFieldSingle<FROM, Path<? extends FROM>, TO, Path<TO>>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     */
    public Bid4WinFieldSimple(String name, SingularAttribute<FROM, TO> attribute)
    {
      super(name, attribute);
    }
    /**
     * Cette m�thode permet d'atteindre le champ � partir du point de d�part donn�
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @Override
    public Path<TO> getPath(Path<? extends FROM> from_)
    {
      return from_.get(this.getAttribute());
    }
  }

  //*************************************************************************//
  //**** Classe de d�finition d'un champ li� acc�d� via sa d�finition JPA ***//
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� et de son accessibilit�
   * via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <TO_CONTAINER> D�finition du type de conteneur du champ � atteindre<BR>
   * @param <TO_ATTRIBUTE> D�finition JPA du champ � atteindre<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public abstract static class Bid4WinFieldJoined<FROM, TO, TO_CONTAINER,
                                                  TO_ATTRIBUTE extends Attribute<FROM, TO_CONTAINER>>
         extends Bid4WinFieldAbstract<FROM, From<?, ? extends FROM>,
                                      TO, Join<? extends FROM, TO>,
                                      TO_CONTAINER, TO_ATTRIBUTE>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     */
    public Bid4WinFieldJoined(String name, TO_ATTRIBUTE attribute)
    {
      super(name, attribute);
    }
  }
  //*************************************************************************//
  // Classe de d�finition d'un champ li� simple acc�d� via sa d�finition JPA //
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� simplement et
   * de son accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedSimple<FROM, TO>
         extends Bid4WinFieldJoined<FROM, TO, TO, SingularAttribute<FROM, TO>>
         implements Bid4WinFieldSingle<FROM, From<?, ? extends FROM>, TO, Join<? extends FROM, TO>>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     */
    public Bid4WinFieldJoinedSimple(String name, SingularAttribute<FROM, TO> attribute)
    {
      super(name, attribute);
    }
    /**
     * Cette m�thode permet d'atteindre le champ � partir du point de d�part donn�
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @Override
    public Join<? extends FROM, TO> getPath(From<?, ? extends FROM> from_)
    {
      return from_.join(this.getAttribute());
    }
  }
  //*************************************************************************//
  // Classe de d�finition d'un champ li� multiple acc�d� via sa d�finition JPA  //
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� par une liste
   * et de son accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <GROUP> TODO A COMMENTER<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <TO_CONTAINER> D�finition du type de conteneur du champ � atteindre<BR>
   * @param <TO_ATTRIBUTE> D�finition JPA du champ � atteindre<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static abstract class Bid4WinFieldJoinedMultiple<FROM, TO, TO_CONTAINER,
                                                          TO_ATTRIBUTE extends Attribute<FROM, TO_CONTAINER>,
                                                          GROUP>
         extends Bid4WinFieldJoined<FROM, TO, TO_CONTAINER, TO_ATTRIBUTE>
         implements Bid4WinFieldMultiple<FROM, From<?, ? extends FROM>, TO, Join<? extends FROM, TO>, GROUP>
  {
    /** TODO A COMMENTER */
    private Bid4WinField<? super FROM, ?, GROUP, ?> groupField = null;

    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     * @param groupField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedMultiple(String name, TO_ATTRIBUTE attribute,
                                      Bid4WinField<? super FROM, ?, GROUP, ?> groupField)
    {
      super(name, attribute);
      this.setGroupField(groupField);
    }

    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField.Bid4WinFieldMultiple#getGroupField()
     */
    @Override
    public Bid4WinField<? super FROM, ?, GROUP, ?> getGroupField()
    {
      return this.groupField;
    }
    /**
     *
     * TODO A COMMENTER
     * @param groupField TODO A COMMENTER
     */
    private void setGroupField(Bid4WinField<? super FROM, ?, GROUP, ?> groupField)
    {
      this.groupField = groupField;
    }
  }

  //*************************************************************************//
  // Classe de d�finition d'un champ li� liste acc�d� via sa d�finition JPA  //
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� par une liste
   * et de son accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <GROUP> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedList<FROM, TO, GROUP>
         extends Bid4WinFieldJoinedMultiple<FROM, TO, List<TO>, ListAttribute<FROM, TO>, GROUP>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     * @param groupField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedList(String name, ListAttribute<FROM, TO> attribute,
                                  Bid4WinField<? super FROM, ?, GROUP, ?> groupField)
    {
      super(name, attribute, groupField);
    }
    /**
     * Cette m�thode permet d'atteindre le champ � partir du point de d�part donn�
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @Override
    public Join<? extends FROM, TO> getPath(From<?, ? extends FROM> from_)
    {
      return from_.join(this.getAttribute());
    }
  }
  //*************************************************************************//
  //* Classe de d�finition d'un champ li� set acc�d� via sa d�finition JPA **//
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� par un set et
   * de son accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <GROUP> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedSet<FROM, TO, GROUP>
         extends Bid4WinFieldJoinedMultiple<FROM, TO, Set<TO>, SetAttribute<FROM, TO>, GROUP>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     * @param groupField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedSet(String name, SetAttribute<FROM, TO> attribute,
                                 Bid4WinField<? super FROM, ?, GROUP, ?> groupField)
    {
      super(name, attribute, groupField);
    }
    /**
     * Cette m�thode permet d'atteindre le champ � partir du point de d�part donn�
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @Override
    public Join<? extends FROM, TO> getPath(From<?, ? extends FROM> from_)
    {
      return from_.join(this.getAttribute());
    }
  }
  //*************************************************************************//
  //* Classe de d�finition d'un champ li� map acc�d� via sa d�finition JPA **//
  //*************************************************************************//
  /**
   * Cette classe correspondant � la repr�sentation d'un champ li� par un set et
   * de son accessibilit� via sa d�finition JPA<BR>
   * <BR>
   * @param <FROM> D�finition du type de point de d�part<BR>
   * @param <KEY> Definition du type de cl� de la map<BR>
   * @param <TO> D�finition du type du champ � atteindre<BR>
   * @param <GROUP> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedMap<FROM, KEY, TO, GROUP>
         extends Bid4WinFieldJoinedMultiple<FROM, TO, Map<KEY, TO>, MapAttribute<FROM, KEY, TO>, GROUP>
  {
    /**
     * Constructeur
     * @param name Nom de la d�finition de champ
     * @param attribute D�finition JPA du champ � rechercher
     * @param groupField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedMap(String name, MapAttribute<FROM, KEY, TO> attribute,
                                 Bid4WinField<? super FROM, ?, GROUP, ?> groupField)
    {
      super(name, attribute, groupField);
    }
    /**
     * Cette m�thode permet d'atteindre le champ � partir du point de d�part donn�
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @Override
    public Join<? extends FROM, TO> getPath(From<?, ? extends FROM> from_)
    {
      return from_.join(this.getAttribute());
    }
  }

  //*************************************************************************//
  //* Classe de d�finition d'un champ acc�d� via composition d'autres d�finitions **//
  //*************************************************************************//
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <FROM_PATH> TODO A COMMENTER<BR>
   * @param <VIA_FROM> TODO A COMMENTER<BR>
   * @param <VIA_FROM_PATH> TODO A COMMENTER<BR>
   * @param <VIA_TO> TODO A COMMENTER<BR>
   * @param <VIA_TO_PATH> TODO A COMMENTER<BR>
   * @param <VIA_FIELD> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * @param <TO_PATH> TODO A COMMENTER<BR>
   * @param <TO_FIELD> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public abstract static class Bid4WinFieldComposite<FROM, FROM_PATH extends Path<? extends FROM>,
                                                     VIA_FROM, VIA_FROM_PATH extends Path<? extends VIA_FROM>,
                                                     VIA_TO extends VIA_FROM, VIA_TO_PATH extends Path<VIA_TO>,
                                                     VIA_FIELD extends Bid4WinField<FROM, FROM_PATH, VIA_TO, VIA_TO_PATH>,
                                                     TO, TO_PATH extends Path<TO>,
                                                     TO_FIELD extends Bid4WinField<VIA_FROM, VIA_FROM_PATH, TO, TO_PATH>>
         implements Bid4WinField<FROM, FROM_PATH, TO, TO_PATH>
  {
    /** TODO A COMMENTER */
    private VIA_FIELD viaField;
    /** TODO A COMMENTER */
    private TO_FIELD toField;

    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    public Bid4WinFieldComposite(VIA_FIELD viaField, TO_FIELD toField)
    {
      this.setViaField(viaField);
      this.setToField(toField);
    }

    /**
     *
     * TODO A COMMENTER
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getName()
     */
    @Override
    public String getName()
    {
      return this.getToField().getName();
    }
    /**
     *
     * TODO A COMMENTER
     * @param from_ {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.bid4win.commons.persistence.entity.Bid4WinField#getPath(javax.persistence.criteria.Path)
     */
    @SuppressWarnings("unchecked")
    @Override
    public TO_PATH getPath(FROM_PATH from_)
    {
      return this.getToField().getPath((VIA_FROM_PATH)this.getViaField().getPath(from_));
    }

    /**
     *
     * TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public VIA_FIELD getViaField()
    {
      return this.viaField;
    }
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     */
    private void setViaField(VIA_FIELD viaField)
    {
      this.viaField = viaField;
    }
    /**
     *
     * TODO A COMMENTER
     * @return TODO A COMMENTER
     */
    public TO_FIELD getToField()
    {
      return this.toField;
    }
    /**
     *
     * TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    private void setToField(TO_FIELD toField)
    {
      this.toField = toField;
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldSimpleToSimple<FROM, VIA, TO>
         extends Bid4WinFieldSimpleToSimpleParent<FROM, VIA, VIA, TO>
  {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    public Bid4WinFieldSimpleToSimple(Bid4WinFieldSingle<FROM, Path<? extends FROM>, VIA, Path<VIA>> viaField,
                                      Bid4WinFieldSingle<VIA, Path<? extends VIA>, TO, Path<TO>> toField)
    {
      super(viaField, toField);
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA_FROM> TODO A COMMENTER<BR>
   * @param <VIA_TO> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldSimpleToSimpleParent<FROM, VIA_FROM, VIA_TO extends VIA_FROM, TO>
         extends Bid4WinFieldComposite<FROM, Path<? extends FROM>,
                                       VIA_FROM, Path<? extends VIA_FROM>,
                                       VIA_TO, Path<VIA_TO>,
                                       Bid4WinField<FROM, Path<? extends FROM>, VIA_TO, Path<VIA_TO>>,
                                       TO, Path<TO>,
                                       Bid4WinField<VIA_FROM, Path<? extends VIA_FROM>, TO, Path<TO>>>
         implements Bid4WinFieldSingle<FROM, Path<? extends FROM>, TO, Path<TO>>
  {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    public Bid4WinFieldSimpleToSimpleParent(Bid4WinFieldSingle<FROM, Path<? extends FROM>, VIA_TO, Path<VIA_TO>> viaField,
                                            Bid4WinFieldSingle<VIA_FROM, Path<? extends VIA_FROM>, TO, Path<TO>> toField)
    {
      super(viaField, toField);
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedToSimple<FROM, VIA, TO>
         extends Bid4WinFieldJoinedToSimpleParent<FROM, VIA, VIA, TO>
  {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedToSimple(Bid4WinFieldJoined<FROM, VIA, ?, ?> viaField,
                                      Bid4WinFieldSimple<VIA, TO> toField)
    {
      super(viaField, toField);
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA_FROM> TODO A COMMENTER<BR>
   * @param <VIA_TO> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
  public static class Bid4WinFieldJoinedToSimpleParent<FROM, VIA_FROM, VIA_TO extends VIA_FROM, TO>
         extends Bid4WinFieldComposite<FROM, From<?, ? extends FROM>,
                                       VIA_FROM, Path<? extends VIA_FROM>,
                                       VIA_TO, Join<? extends FROM, VIA_TO>,
                                       Bid4WinFieldJoined<FROM, VIA_TO, ?, ?>,
                                       TO, Path<TO>,
                                       Bid4WinFieldSimple<VIA_FROM, TO>>
  {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
    public Bid4WinFieldJoinedToSimpleParent(Bid4WinFieldJoined<FROM, VIA_TO, ?, ?> viaField,
                                            Bid4WinFieldSimple<VIA_FROM, TO> toField)
    {
      super(viaField, toField);
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
 /* public static class Bid4WinFieldJoinedToJoined<FROM, VIA, TO>
         extends Bid4WinFieldJoinedToJoinedParent<FROM, VIA, VIA, TO>
         /*extends Bid4WinFieldComposite<FROM, From<?, ? extends FROM>,
                                       VIA, From<? extends FROM, ? extends VIA>,
                                       VIA, Join<? extends FROM, VIA>,
                                       Bid4WinFieldComposite<FROM, From<?, ? extends FROM>,
                                                             ?, ?, ?, ?, ?, VIA,
                                                             Join<? extends FROM, VIA>, ?>,
                                       TO, Join<? extends VIA, TO>,
                                       Bid4WinFieldComposite<VIA, From<? extends FROM, ? extends VIA>,
                                                             ?, ?, ?, ?, ?, TO,
                                                             Join<? extends VIA, TO>, ?>>*/
 /* {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
 /*   public Bid4WinFieldJoinedToJoined(Bid4WinFieldJoined<FROM, VIA, ?, ?> viaField,
                                      Bid4WinFieldJoined<VIA, TO, ?, ?> toField)
    {
      super(viaField, toField);
    }
  }
  /**
   *
   * TODO A COMMENTER<BR>
   * <BR>
   * @param <FROM> TODO A COMMENTER<BR>
   * @param <VIA_FROM> TODO A COMMENTER<BR>
   * @param <VIA_TO> TODO A COMMENTER<BR>
   * @param <TO> TODO A COMMENTER<BR>
   * <BR>
   * @author Emeric Fill�tre
   */
 /* public static class Bid4WinFieldJoinedToJoinedParent<FROM, VIA_FROM, VIA_TO extends VIA_FROM, TO>
         extends Bid4WinFieldComposite<FROM, From<?, ? extends FROM>,
                                       VIA_FROM, From<?, ? extends VIA_FROM>,
                                       VIA_TO, Join<? extends FROM, VIA_TO>,
                                       Bid4WinFieldJoined<FROM, VIA_TO, ?, ?>,
                                       TO, Join<? extends VIA_FROM, TO>,
                                       Bid4WinFieldJoined<VIA_FROM, TO, ?, ?>>
  {
    /**
     *
     * TODO A COMMENTER
     * @param viaField TODO A COMMENTER
     * @param toField TODO A COMMENTER
     */
 /*   public Bid4WinFieldJoinedToJoinedParent(Bid4WinFieldJoined<FROM, VIA_TO, ?, ?> viaField,
                                            Bid4WinFieldJoined<VIA_FROM, TO, ?, ?> toField)
    {
      super(viaField, toField);
    }
  }*/
}
