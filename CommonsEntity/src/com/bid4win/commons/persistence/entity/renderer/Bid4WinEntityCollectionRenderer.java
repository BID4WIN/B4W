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
 * @param <ENTITY> D�finition du type des entit�s de la collection � transformer
 * en cha�ne de caract�res<BR>
 * @param <RENDERER> D�finition de la classe en charge du rendu des entit�s de la
* <BR>
* @author Emeric Fill�tre
*/
public class Bid4WinEntityCollectionRenderer<ENTITY extends Bid4WinEntity<?, ?>,
                                             RENDERER extends Bid4WinEntityRenderer<ENTITY>>
       extends Bid4WinObjectCollectionRenderer<ENTITY, RENDERER>
{
  /** C'est l'instance utilis�e comme singleton */
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
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
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
   * @param objectRenderer Classe de rendu par d�faut des entit�s de la collection
   */
  public Bid4WinEntityCollectionRenderer(RENDERER objectRenderer)
  {
    super(objectRenderer);
  }

  /**
   * Cette m�thode est red�finie afin de transformer la collection d'entit�s en
   * argument en cha�ne de caract�res utilisant la classe de rendu en param�tre
   * pour chaque entit� de la collection et les affichant une par ligne
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
    // En ne pr�cisant pas les relations de l'entit� participant au rendu, c'est
    // l'entit� qui les pr�cise
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
    // En ne pr�cisant pas les relations de l'entit� participant au rendu, c'est
    // l'entit� qui les pr�cise
    return this.render(toRender, null, renderer);
  }

  /**
   * Cette m�thode permet de transformer la collection d'entit�s en argument en
   * cha�ne de caract�res la repr�sentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu par d�faut pour chaque entit� de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de la collection d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.render(toRender, nodeList, this.getObjectRenderer());
  }
  /**
   * Cette m�thode permet de transformer la collection d'entit�s en argument en
   * cha�ne de caract�res la repr�sentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu en param�tre pour chaque entit� de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param renderer Classe de rendu � utiliser pour chaque entit� de la collection
   * @return Le rendu de la collection d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             RENDERER renderer)
  {
    // Le set d'entit�s r�f�renc�es sera construit automatiquement � la cr�ation du rendu
    return this.render(toRender, nodeList, null, renderer);
  }
  /**
   * Cette m�thode permet de transformer la collection d'entit�s en argument en
   * cha�ne de caract�res la repr�sentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu par d�faut pour chaque entit� de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la collection d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced)
  {
    return this.render(toRender, nodeList, referenced, this.getObjectRenderer());
  }
  /**
   * Cette m�thode permet de transformer la collection d'entit�s en argument en
   * cha�ne de caract�res la repr�sentant lisiblement et de choisir les relations
   * qui y participeront ainsi que leur profondeur. Elle utilisera la classe de
   * rendu en param�tre pour chaque entit� de la collection en les affichant une
   * par ligne
   * @param toRender Collection d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @param renderer Classe de rendu � utiliser pour chaque entit� de la collection
   * @return Le rendu de la collection d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Collection<? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced,
                             RENDERER renderer)
  {
    // G�re la possible nullit� de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entr�es pour les afficher une par une
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
