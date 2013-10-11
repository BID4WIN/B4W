package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe défini un noeud d'arbre de relations entre entités ainsi que ceux
 * qui lui sont directement rattachés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRelationNode extends Bid4WinObject<Bid4WinRelationNode> implements Cloneable
{
  /** Relation associée au noeud */
  private Bid4WinRelation relation;
  /** Liste de noeuds de relations rattachés au noeud courant */
  private Bid4WinList<Bid4WinRelationNode> nodeList = new Bid4WinList<Bid4WinRelationNode>();

  /**
   * Constructeur d'un noeud vide
   */
  public Bid4WinRelationNode()
  {
    this(null);
  }
  /**
   * Constructeur de noeud de relations
   * @param relation Relation associée au noeud
   */
  public Bid4WinRelationNode(Bid4WinRelation relation)
  {
    this.setRelation(relation);
  }

  /**
   * Getter de la relation associée au noeud
   * @return La relation associée au noeud
   */
  public Bid4WinRelation getRelation()
  {
    return this.relation;
  }
  /**
   * Setter de la relation associée au noeud
   * @param relation Relation à associer au noeud
   */
  private void setRelation(Bid4WinRelation relation)
  {
    if(relation == null)
    {
      relation = Bid4WinRelation.NO_RELATION;
    }
    this.relation = relation;
  }

  /**
   * Getter de la liste de noeuds de relations rattachés au noeud courant
   * @return La liste de noeuds de relations rattachés au noeud courant
   */
  public Bid4WinList<Bid4WinRelationNode> getNodeList()
  {
    return this.nodeList.clone();
  }
  /**
   * Permet d'ajouter un noeud de relation au noeud courant
   * @param node Noeud de relation à ajouter au noeud courant
   * @return True si l'ajout a réussi, false sinon
   * @throws ProtectionException Si le noeud de relation courant est protégé
   */
  public boolean addNode(Bid4WinRelationNode node) throws ProtectionException
  {
    this.checkProtection();
    return this.nodeList.add(node);
  }

  /**
   * Redéfini l'égalité interne de deux noeuds par l'égalité de leur relation
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#equalsInternal(com.bid4win.commons.core.Bid4WinObject)
   */
  @Override
  protected boolean equalsInternal(Bid4WinRelationNode toBeCompared)
  {
    return this.getRelation().equals(toBeCompared.getRelation());
  }
  /**
   * Redéfini la valeur du code de hachage en utilisant celle de sa relation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  { // TODO faire méthode render complète (avec tous les noeuds) et baser
    // equalsInternal et hashCodeInternal dessus
    return this.getRelation().hashCode();
  }
  /**
   * Défini le rendu d'un noeud de relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return this.getRelation().render();
  }

  /**
   * Cette méthode permet de cloner un noeud. Attention, le clone sera rempli avec
   * les même noeuds que celui de base
   * @return {@inheritDoc}
   * @see java.lang.Object#clone()
   */
  @Override
  public Bid4WinRelationNode clone() throws CloneNotSupportedException
  {
    try
    {
      Bid4WinRelationNode clone = this.getClass().newInstance();
      clone.relation = this.getRelation();
      clone.nodeList = this.getNodeList();
      return clone;
    }
    catch(IllegalAccessException ex)
    {
      throw new CloneNotSupportedException(ex.getMessage());
    }
    catch(InstantiationException ex)
    {
      throw new CloneNotSupportedException(ex.getMessage());
    }
  }
}
