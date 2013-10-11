package com.bid4win.commons.persistence.entity.renderer;

import java.util.Iterator;
import java.util.Map;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;
import com.bid4win.commons.core.renderer.Bid4WinObjectMapRenderer;
import com.bid4win.commons.core.renderer.Bid4WinObjectRenderer;
import com.bid4win.commons.core.renderer.Bid4WinRenderer;
import com.bid4win.commons.persistence.entity.Bid4WinEntity;
import com.bid4win.commons.persistence.entity.Bid4WinRelationNode;
import com.bid4win.commons.persistence.entity.collection.Bid4WinEntityReferenceSet;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <KEY> TODO A COMMENTER<BR>
 * @param <KEY_RENDERER> TODO A COMMENTER<BR>
 * @param <ENTITY> TODO A COMMENTER<BR>
 * @param <ENTITY_RENDERER> TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinEntityMapRenderer<KEY, KEY_RENDERER extends Bid4WinRenderer<KEY>,
                                      ENTITY extends Bid4WinEntity<?, ?>,
                                      ENTITY_RENDERER extends Bid4WinEntityRenderer<ENTITY>>
       extends Bid4WinObjectMapRenderer<KEY, KEY_RENDERER, ENTITY, ENTITY_RENDERER>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinEntityMapRenderer<Object, Bid4WinRenderer<Object>,
                                                Bid4WinEntity<?, ?>, Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>
      instance = new Bid4WinEntityMapRenderer<Object, Bid4WinRenderer<Object>,
                                              Bid4WinEntity<?, ?>, Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>(
          Bid4WinRenderer.getInstance(), Bid4WinEntityRenderer.getInstanceEntity());
  /**
   *
   * TODO A COMMENTER
   * @return TODO A COMMENTER
   * @see com.bid4win.commons.core.renderer.Bid4WinObjectMapRenderer#getInstanceObjectMap()
   * @deprecated TODO A COMMENTER
   */
  @Deprecated
  public static Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                         Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>
         getInstanceObjectMap()
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinEntityMapRenderer<Object, Bid4WinRenderer<Object>,
                                         Bid4WinEntity<?, ?>, Bid4WinEntityRenderer<Bid4WinEntity<?, ?>>>
         getInstanceEntityMap()
  {
    return Bid4WinEntityMapRenderer.instance;
  }

  /**
   * Constructeur
   * @param keyRenderer Classe de rendu par d�faut des cl�s de la map
   * @param entityRenderer Classe de rendu par d�faut des entit�s de la map
   */
  public Bid4WinEntityMapRenderer(KEY_RENDERER keyRenderer, ENTITY_RENDERER entityRenderer)
  {
    super(keyRenderer, entityRenderer);
  }

  /**
   *
   * TODO A COMMENTER
   * @param toRender {@inheritDoc}
   * @param keyRenderer {@inheritDoc}
   * @param entityRenderer {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinMapRenderer#render(java.util.Map, com.bid4win.commons.core.renderer.Bid4WinRenderer, com.bid4win.commons.core.renderer.Bid4WinRenderer)
   */
  @Override
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             KEY_RENDERER keyRenderer, ENTITY_RENDERER entityRenderer)
  {
    // En ne pr�cisant pas les relations de l'entit� participant au rendu, c'est
    // l'entit� qui les pr�cise. Le set d'entit�s r�f�renc�es sera construit
    // automatiquement � la cr�ation du rendu
    return this.render(toRender, null, null, keyRenderer, entityRenderer);
  }

  /**
   * Cette m�thode permet de transformer la map d'entit�s en argument en cha�ne
   * de caract�res la repr�sentant lisiblement et de choisir les relations qui y
   * participeront ainsi que leur profondeur. Elle utilisera les classes de rendu
   * par d�faut pour chaque cl� et chaque valeur de la map
   * @param toRender Map d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @return Le rendu de la map d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList)
  {
    return this.render(toRender, nodeList, this.getValueRenderer());
  }
  /**
   * Cette m�thode permet de transformer la map d'entit�s en argument en cha�ne
   * de caract�res la repr�sentant lisiblement et de choisir les relations qui y
   * participeront ainsi que leur profondeur. Elle utilisera la classe de rendu
   * par d�faut pour chaque cl� et celle en param�tre chaque valeur de la map
   * @param toRender Map d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param entityRenderer Classe de rendu � utiliser pour chaque valeur de la map
   * @return Le rendu de la map d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             ENTITY_RENDERER entityRenderer)
  {
    // Le set d'entit�s r�f�renc�es sera construit automatiquement � la cr�ation
    // du rendu
    return this.render(toRender, nodeList, null, entityRenderer);
  }
  /**
   * Cette m�thode permet de transformer la map d'entit�s en argument en cha�ne
   * de caract�res la repr�sentant lisiblement et de choisir les relations qui y
   * participeront ainsi que leur profondeur. Elle utilisera les classes de rendu
   * par d�faut pour chaque cl� et chaque valeur de la map
   * @param toRender Map d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @return Le rendu de la map d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced)
  {
    return this.render(toRender, nodeList, referenced, this.getValueRenderer());
  }
  /**
   * Cette m�thode permet de transformer la map d'entit�s en argument en cha�ne
   * de caract�res la repr�sentant lisiblement et de choisir les relations qui y
   * participeront ainsi que leur profondeur. Elle utilisera la classe de rendu
   * par d�faut pour chaque cl� et celle en param�tre chaque valeur de la map
   * @param toRender Map d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @param entityRenderer Classe de rendu � utiliser pour chaque valeur de la map
   * @return Le rendu de la map d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced,
                             ENTITY_RENDERER entityRenderer)
  {
    return this.render(toRender, nodeList, referenced, this.getKeyRenderer(), entityRenderer);
  }
  /**
   * Cette m�thode permet de transformer la map d'entit�s en argument en cha�ne
   * de caract�res la repr�sentant lisiblement et de choisir les relations qui y
   * participeront ainsi que leur profondeur. Elle utilisera les classes de rendu
   * en param�tre pour chaque cl� et chaque valeur de la map
   * @param toRender Map d'entit�s dont on souhaite obtenir le rendu
   * @param nodeList Liste de noeuds de relations participants au rendu
   * @param referenced Set d'entit�s r�f�renc�es comme ayant d�j� particip� au rendu
   * @param keyRenderer Classe de rendu � utiliser pour chaque cl� de la map
   * @param entityRenderer Classe de rendu � utiliser pour chaque valeur de la map
   * @return Le rendu de la map d'entit�e en argument en cha�ne de caract�res
   */
  public StringBuffer render(Map<? extends KEY, ? extends ENTITY> toRender,
                             Bid4WinList<Bid4WinRelationNode> nodeList,
                             Bid4WinEntityReferenceSet referenced,
                             KEY_RENDERER keyRenderer, ENTITY_RENDERER entityRenderer)
  {
    // G�re la possible nullit� de la map
    if(toRender == null)
    {
    return new StringBuffer("NULL");
    }
    // Effectue le rendu de la map
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entr�es
    for(Iterator<? extends KEY> iterator = toRender.keySet().iterator() ; iterator.hasNext() ;)
    {
      KEY key = iterator.next();
      ENTITY entity = toRender.get(key);
      UtilString.addParagraph(buffer, keyRenderer.render(key), 1);
      UtilString.addParagraph(buffer, "------------------------------", 1);
      UtilString.addParagraph(
          buffer, this.renderEntity(entity, nodeList, referenced, entityRenderer), 1);
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
   * @param nodeList TODO A COMMENTER
   * @param referenced TODO A COMMENTER
   * @param renderer TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  protected StringBuffer renderEntity(ENTITY toRender,
                                      Bid4WinList<Bid4WinRelationNode> nodeList,
                                      Bid4WinEntityReferenceSet referenced,
                                      ENTITY_RENDERER renderer)
  {
    return renderer.render(toRender, nodeList, referenced);
  }
}
