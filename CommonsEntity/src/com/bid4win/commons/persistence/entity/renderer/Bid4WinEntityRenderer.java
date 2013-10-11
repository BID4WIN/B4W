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
 * @author Emeric Fillâtre
 */
public class Bid4WinEntityRenderer<ENTITY extends Bid4WinEntity<?, ?>>
       extends Bid4WinObjectRenderer<ENTITY>
{
  /** C'est l'instance utilisée comme singleton */
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
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityRenderer<Bid4WinEntity<?, ?>> getInstanceEntity()
  {
    return Bid4WinEntityRenderer.instance;
  }

  /**
   * Cette méthode permet d'effectuer le rendu de l'entité en argument en chaîne
   * de caractères et de choisir ses relations qui y participeront ainsi que leur
   * profondeur tout en gérant sa possible nullité
   * @param toRender Entité dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de l'entité en argument en chaîne de caractères
   */
  public final StringBuffer render(ENTITY toRender, Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    // Le set d'entités référencées sera construit automatiquement à la création du rendu
    return this.render(toRender, nodeList, null);
  }

  /**
   * Cette méthode permet d'effectuer le rendu de l'entité en argument en chaîne
   * de caractères et de choisir ses relations qui y participeront ainsi que leur
   * profondeur tout en gérant sa possible nullité
   * @param toRender Entité dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de l'entité en argument en chaîne de caractères
   */
  public final StringBuffer render(ENTITY toRender,
                                   Bid4WinList<Bid4WinRelationNode> nodeList,
                                   Bid4WinEntityReferenceSet referenced)
  {
    // Gère la possible nullité de l'entité
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'entité
    return toRender.render(nodeList, referenced);
  }
}
