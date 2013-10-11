package com.bid4win.commons.persistence.entity.renderer;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.renderer.Bid4WinObjectRenderer;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinEntityReferenceSet;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityRenderer<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinObjectRenderer<ENTITY>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>
      instance = new Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>();
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.renderer.Bid4WinObjectRenderer#getInstanceObject()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectRenderer<Bid4WinObject<?>> getInstanceObject()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityRenderer<Bid4WinEntity<?, ?>> getInstanceEntity()
  {
    return Bid4WinEntityRenderer.instance;
  }

  /**
   * Cette m�thode permet d'effectuer le rendu de l'entit� en argument en cha�ne
   * de caract�res et de choisir ses relations qui y participeront ainsi que leur
   * profondeur tout en g�rant sa possible nullit�
   * @param toRender Entit� dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de l'entit� en argument en cha�ne de caract�res
   */
  public final StringBuffer render(ENTITY toRender, Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    // Le set d'entit�s r�f�renc�es sera construit automatiquement � la cr�ation du rendu
    return this.render(toRender, nodeList, null);
  }

  /**
   * Cette m�thode permet d'effectuer le rendu de l'entit� en argument en cha�ne
   * de caract�res et de choisir ses relations qui y participeront ainsi que leur
   * profondeur tout en g�rant sa possible nullit�
   * @param toRender Entit� dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de l'entit� en argument en cha�ne de caract�res
   */
  public final StringBuffer render(ENTITY toRender,
                                   Bid4WinList<Bid4WinRelationNode> nodeList,
                                   Bid4WinEntityReferenceSet referenced)
  {
    // G�re la possible nullit� de l'entit�
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'entit�
    return toRender.render(nodeList, referenced);
  }
}
