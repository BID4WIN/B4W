package com.bid4win.commons.core.renderer;

import java.util.Map;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini la prise en charge de base de la transformation d'une map
 * de couple d'objets de types donn�s en cha�ne de caract�res<BR>
 * <BR>
 * @param <KEY> D�finition du type des cl�s de la map � transformer en cha�ne de
 * caract�res<BR>
 * @param <KEY_RENDERER> D�finition de la classe en charge du rendu des cl�s de
 * la map<BR>
 * @param <VALUE> D�finition du type des valeurs de la map � transformer en cha�ne
 * de caract�res<BR>
 * @param <VALUE_RENDERER> D�finition de la classe en charge du rendu des valeurs
 * de la map<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinMapRenderer<KEY, KEY_RENDERER extends Bid4WinRenderer<KEY>,
                                VALUE, VALUE_RENDERER extends Bid4WinRenderer<VALUE>>
       extends Bid4WinRenderer<Map<? extends KEY, ? extends VALUE>>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                          Object, Bid4WinRenderer<Object>>
      instance = new Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                        Object, Bid4WinRenderer<Object>>(
          Bid4WinRenderer.getInstance(), Bid4WinRenderer.getInstance());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#getInstance()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinRenderer<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                   Object, Bid4WinRenderer<Object>> getInstanceMap()
  {
    return Bid4WinMapRenderer.instance;
  }

  /** Classe de rendu par d�faut des cl�s de la map */
  private KEY_RENDERER keyRenderer = null;
  /** Classe de rendu par d�faut des valeurs de la map */
  private VALUE_RENDERER valueRenderer = null;

  /**
   * Constructeur
   * @param keyRenderer Classe de rendu par d�faut des cl�s de la map
   * @param valueRenderer Classe de rendu par d�faut des valeurs de la map
   */
  public Bid4WinMapRenderer(KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    this.keyRenderer = keyRenderer;
    this.valueRenderer = valueRenderer;
  }

  /**
   * Red�fini la m�thode afin de transformer la map en argument en cha�ne de caract�res
   * la repr�sentant lisiblement. Elle utilisera les classes de rendu par d�faut
   * pour chaque cl� et chaque valeur de la map en se basant sur le retour de la
   * m�thode render(TYPE)
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#toString(java.lang.Object)
   */
  @Override
  public final String toString(Map<? extends KEY, ? extends VALUE> toRender)
  {
    return this.render(toRender).toString();
  }
  /**
   * Red�fini la m�thode afin de transformer la map en argument en cha�ne de caract�res
   * la repr�sentant lisiblement. Elle utilisera les classes de rendu par d�faut
   * pour chaque cl� et chaque valeur de la map en se basant sur le retour de la
   * m�thode render(TYPE, KEY_RENDERER, VALUE_RENDERER)
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#render(java.lang.Object)
   */
  @Override
  public StringBuffer render(Map<? extends KEY, ? extends VALUE> toRender)
  {
    return this.render(toRender, this.getKeyRenderer(), this.getValueRenderer());
  }

  /**
   * Cette m�thode permet de transformer la map de couples de cl�/valeur en argument
   * en cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera les classes
   * de rendu en param�tre pour chaque cl� et chaque valeur de la map
   * @param toRender Map de couples de cl�/valeur � transformer en cha�ne de caract�res
   * @param keyRenderer Classe de rendu � utiliser pour chaque cl� de la map
   * @param valueRenderer Classe de rendu � utiliser pour chaque valeur de la map
   * @return La cha�ne de caract�res repr�sentant la map de couples de cl�/valeur
   * en argument
   */
  public StringBuffer render(Map<? extends KEY, ? extends VALUE> toRender,
                             KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    // G�re la possible nullit� de la map
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la map
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les cl�s
    for(KEY key : toRender.keySet())
    {
      StringBuffer entry = new StringBuffer("KEY=");
      UtilString.addLine(entry, keyRenderer.render(key));
      UtilString.addParagraph(entry, "VALUE=");
      UtilString.addLine(entry, valueRenderer.render(toRender.get(key)));
      UtilString.addParagraph(buffer, entry, 1);
    }
    return UtilString.addParagraph(buffer, new StringBuffer("]"));
  }

  /**
   * Getter de la classe de rendu par d�faut des cl�s de la map
   * @return La classe de rendu par d�faut des cl�s de la map
   */
  public KEY_RENDERER getKeyRenderer()
  {
    return this.keyRenderer;
  }
  /**
   * Getter de la classe de rendu par d�faut des valeurs de la map
   * @return La classe de rendu par d�faut des valeurs de la map
   */
  public VALUE_RENDERER getValueRenderer()
  {
    return this.valueRenderer;
  }
}
