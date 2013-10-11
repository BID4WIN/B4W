package com.bid4win.commons.persistence.entity;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.security.exception.ProtectionException;

/**
 * Cette classe d�fini un noeud d'arbre de relations entre entit�s ainsi que ceux
 * qui lui sont directement rattach�s<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRelationNode extends Bid4WinObject<Bid4WinRelationNode> implements Cloneable
{
  /** Relation associ�e au noeud */
  private Bid4WinRelation relation;
  /** Liste de noeuds de relations rattach�s au noeud courant */
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
   * @param relation Relation associ�e au noeud
   */
  public Bid4WinRelationNode(Bid4WinRelation relation)
  {
    this.setRelation(relation);
  }

  /**
   * Getter de la relation associ�e au noeud
   * @return La relation associ�e au noeud
   */
  public Bid4WinRelation getRelation()
  {
    return this.relation;
  }
  /**
   * Setter de la relation associ�e au noeud
   * @param relation Relation � associer au noeud
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
   * Getter de la liste de noeuds de relations rattach�s au noeud courant
   * @return La liste de noeuds de relations rattach�s au noeud courant
   */
  public Bid4WinList<Bid4WinRelationNode> getNodeList()
  {
    return this.nodeList.clone();
  }
  /**
   * Permet d'ajouter un noeud de relation au noeud courant
   * @param node Noeud de relation � ajouter au noeud courant
   * @return True si l'ajout a r�ussi, false sinon
   * @throws ProtectionException Si le noeud de relation courant est prot�g�
   */
  public boolean addNode(Bid4WinRelationNode node) throws ProtectionException
  {
    this.checkProtection();
    return this.nodeList.add(node);
  }

  /**
   * Red�fini l'�galit� interne de deux noeuds par l'�galit� de leur relation
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
   * Red�fini la valeur du code de hachage en utilisant celle de sa relation
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#hashCodeInternal()
   */
  @Override
  protected int hashCodeInternal()
  { // TODO faire m�thode render compl�te (avec tous les noeuds) et baser
    // equalsInternal et hashCodeInternal dessus
    return this.getRelation().hashCode();
  }
  /**
   * D�fini le rendu d'un noeud de relations
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject#render()
   */
  @Override
  public StringBuffer render()
  {
    return this.getRelation().render();
  }

  /**
   * Cette m�thode permet de cloner un noeud. Attention, le clone sera rempli avec
   * les m�me noeuds que celui de base
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
