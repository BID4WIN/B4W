package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.persistence.entity.Bid4WinRelation;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("TWIN")
public class FooCachedTwin extends FooCached<FooCachedTwin>
{
  /** Jumeau de l'objet courant */
  @Transient
  private FooCachedTwin twin = null;

  /**
   * Constructeur
   */
  protected FooCachedTwin()
  {
    super();
  }

  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet parent
   */
  public FooCachedTwin(String value, Bid4WinDate date)
  {
    super(value, date);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @param date TODO A COMMENTER
   * @param twin TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public FooCachedTwin(String value, Bid4WinDate date, FooCachedTwin twin)
         throws ModelArgumentException
  {
    this(value, date);
    this.defineTwin(twin);
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getFullRelationNodeList()
   */
  @Override
  protected Bid4WinList<Bid4WinRelationNode> getFullRelationNodeList()
  {
    return new Bid4WinList<Bid4WinRelationNode>(FooCachedTwin_Relations.NODE_TWIN);
  }
  /**
   *
   * TODO A COMMENTER
   * @param relation {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getRelationSimple(com.bid4win.commons.persistence.entity.Bid4WinRelation)
   */
  @Override
  protected FooCachedTwin getRelationSimple(Bid4WinRelation relation)
  {
    if(relation.equals(FooCachedTwin_Relations.RELATION_TWIN))
    {
      return this.getTwin();
    }
    return null;
  }

  /**
   *
   * TODO A COMMENTER
   * @param twin TODO A COMMENTER
   * @throws ModelArgumentException TODO A COMMENTER
   */
  public void defineTwin(FooCachedTwin twin) throws ModelArgumentException
  {
    UtilObject.checkNotNull("twin", twin);
    UtilObject.checkNull("twin", this.getTwin());
    UtilObject.checkNull("twin", twin.getTwin());
    twin.setTwin(this);
    this.setTwin(twin);
  }

  /** #################################################################### **/
  /** ########################### PERSISTENCE ############################ **/
  /** #################################################################### **/
  /**
   * Getter du jumeau  de l'objet courant
   * @return Le jumeau  de l'objet courant
   */
  // Annotation pour la persistence
  @Access(AccessType.PROPERTY)
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID", nullable = true, unique = true)
  public FooCachedTwin getTwin()
  {
    return this.twin;
  }
  /**
   * Setter du jumeau de l'objet courant
   * @param twin Jumeau de l'objet courant à positionner
   */
  private void setTwin(FooCachedTwin twin)
  {
    this.twin = twin;
  }
}
