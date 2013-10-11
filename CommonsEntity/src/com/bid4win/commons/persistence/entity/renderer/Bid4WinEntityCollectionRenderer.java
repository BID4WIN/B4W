package com.bid4win.commons.persistence.entity.renderer;

import java.util.Collection;
import java.util.Iterator;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.renderer.Bid4WinObjectCollectionRenderer;
import com.bid4win.commons.core.renderer.Bid4WinObjectRenderer;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinEntityReferenceSet;

/**
*
* TODO A COMMENTER<BR>
* <BR>
 * @param <ENTITY> Définition du type des entités de la collection à transformer
 * en chaîne de caractères<BR>
 * @param <RENDERER> Définition de la classe en charge du rendu des entités de la
* <BR>
* @author Emeric Fillâtre
*/
public class Bid4WinEntityCollectionRenderer<ENTITY extends Bid4WinEntity<?, ?>,
                                             RENDERER extends Bid4WinEntityRenderer<ENTITY>>
       extends Bid4WinObjectCollectionRenderer<ENTITY, RENDERER>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinEntityCollectionRenderer<Bid4WinEntity<?, ?>,
                                                       Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntityCollectionRenderer<Bid4WinEntity<?, ?>,
                                                     Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>(
          Bid4WinEntityRenderer.getInstanceEntity());
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.renderer.Bid4WinObjectCollectionRenderer#getInstanceObjectCollection()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectCollectionRenderer<Bid4WinObject<?>,
                                                Bid4WinObjectRenderer<Bid4WinObject<?>>>
         getInstanceObjectCollection()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityCollectionRenderer<Bid4WinEntity<?, ?>,
                                                Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>
         getInstanceEntityCollection()
  {
    return Bid4WinEntityCollectionRenderer.instance;
  }

  /**
   * Constructeur
   * @param objectRenderer Classe de rendu par défaut des entités de la collection
   */
  public Bid4WinEntityCollectionRenderer(RENDERER objectRenderer)
  {
    super(objectRenderer);
  }

  /**
   * Cette méthode est redéfinie afin de transformer la collection d'entités en
   * argument en chaîne de caractères utilisant la classe de rendu en paramètre
   * pour chaque entité de la collection et les affichant une par ligne
   * @param toRender {@inheritDoc}
   * @param renderer {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer#render(java.util.Collection, com.bid4win.commons.core.renderer.Bid4WinRenderer)
   */
  @Override
  public StringBuffer render(Collection<? extends ENTITY> toRender, RENDERER renderer)
  {
    return this.renderMultipleLine(toRender, renderer);
  }
  /**
   *
   * TODO A COMMENTER
   * @param toRender {@inheritDoc}
   * @param renderer {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer#renderUniqueLine(java.util.Collection, java.lang.String, com.bid4win.commons.core.renderer.Bid4WinRenderer)
   */
  @Override
  public final StringBuffer  renderUniqueLine(Collection<? extends ENTITY> toRender,
                                              String objectSeparator, RENDERER renderer)
  {
    // En ne précisant pas les relations de l'entité participant au rendu, c'est
    // l'entité qui les précise
    return this.render(toRender, null, renderer);
  }
  /**
   *
   * TODO A COMMENTER
   * @param toRender {@inheritDoc}
   * @param renderer {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer#renderMultipleLine(java.util.Collection, com.bid4win.commons.core.renderer.Bid4WinRenderer)
   */
  @Override
  public final StringBuffer renderMultipleLine(Collection<? extends ENTITY> toRender,
                                               RENDERER renderer)
  {
    // En ne précisant pas les relations de l'entité participant au rendu, c'est
    // l'entité qui les précise
    return this.render(toRender, null, renderer);
  }

  /**
   * Cette méthode permet de transformer la collection d'entités en argument en
   * chaîne de caractères la représentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu par défaut pour chaque entité de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entités dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de la collection d'entitée en argument en chaîne de caractères
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.render(toRender, nodeList, this.getObjectRenderer());
  }
  /**
   * Cette méthode permet de transformer la collection d'entités en argument en
   * chaîne de caractères la représentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu en paramètre pour chaque entité de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entités dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param renderer Classe de rendu à utiliser pour chaque entité de la collection
   * @return Le rendu de la collection d'entitée en argument en chaîne de caractères
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             RENDERER renderer)
  {
    // Le set d'entités référencées sera construit automatiquement à la création du rendu
    return this.render(toRender, nodeList, null, renderer);
  }
  /**
   * Cette méthode permet de transformer la collection d'entités en argument en
   * chaîne de caractères la représentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu par défaut pour chaque entité de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entités dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @return Le rendu de la collection d'entitée en argument en chaîne de caractères
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced)
  {
    return this.render(toRender, nodeList, referenced, this.getObjectRenderer());
  }
  /**
   * Cette méthode permet de transformer la collection d'entités en argument en
   * chaîne de caractères la représentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu en paramètre pour chaque entité de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entités dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entités référencées comme ayant déjà participé au rendu
   * @param renderer Classe de rendu à utiliser pour chaque entité de la collection
   * @return Le rendu de la collection d'entitée en argument en chaîne de caractères
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced,
                             RENDERER renderer)
  {
    // Gère la possible nullité de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entrées pour les afficher une par une
    for(Iterator<? extends ENTITY> iterator = toRender.iterator() ; iterator.hasNext() ;)
    {
      UtilString.addParagraph(
          buffer, this.renderObject(iterator.next(), nodeList, referenced, renderer), 1);
      if(iterator.hasNext())
      {
        UtilString.addParagraph(buffer, "################################", 1);
      }
    }
    return UtilString.addParagraph(buffer, "]");
  }
  /**
   *
   * TODO A COMMENTER
   * @param toRender TODO A COMMENTER
   * @param renderer TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected StringBuffer renderObject(ENTITY toRender,
                                      Bid4WinList<Bid4WinRelationNode> nodeList,
                                      Bid4WinEntityReferenceSet referenced,
                                      RENDERER renderer)
  {
    return renderer.render(toRender, nodeList, referenced);
  }
}
