package com.bid4win.commons.core.renderer;

import java.util.Map;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini la prise en charge de base de la transformation d'une map
 * de couple d'objets de types donnés en chaîne de caractères<BR>
 * <BR>
 * @param <KEY> Définition du type des clés de la map à transformer en chaîne de
 * caractères<BR>
 * @param <KEY_RENDERER> Définition de la classe en charge du rendu des clés de
 * la map<BR>
 * @param <VALUE> Définition du type des valeurs de la map à transformer en chaîne
 * de caractères<BR>
 * @param <VALUE_RENDERER> Définition de la classe en charge du rendu des valeurs
 * de la map<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinMapRenderer<KEY, KEY_RENDERER extends Bid4WinRenderer<KEY>,
                                VALUE, VALUE_RENDERER extends Bid4WinRenderer<VALUE>>
       extends Bid4WinRenderer<Map<? extends KEY, ? extends VALUE>>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                          Object, Bid4WinRenderer<Object>>
      instance = new Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                        Object, Bid4WinRenderer<Object>>(
          Bid4WinRenderer.getInstance(), Bid4WinRenderer.getInstance());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#getInstance()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinRenderer<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                   Object, Bid4WinRenderer<Object>> getInstanceMap()
  {
    return Bid4WinMapRenderer.instance;
  }

  /** Classe de rendu par défaut des clés de la map */
  private KEY_RENDERER keyRenderer = null;
  /** Classe de rendu par défaut des valeurs de la map */
  private VALUE_RENDERER valueRenderer = null;

  /**
   * Constructeur
   * @param keyRenderer Classe de rendu par défaut des clés de la map
   * @param valueRenderer Classe de rendu par défaut des valeurs de la map
   */
  public Bid4WinMapRenderer(KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    this.keyRenderer = keyRenderer;
    this.valueRenderer = valueRenderer;
  }

  /**
   * Redéfini la méthode afin de transformer la map en argument en chaîne de caractères
   * la représentant lisiblement. Elle utilisera les classes de rendu par défaut
   * pour chaque clé et chaque valeur de la map en se basant sur le retour de la
   * méthode render(TYPE)
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
   * Redéfini la méthode afin de transformer la map en argument en chaîne de caractères
   * la représentant lisiblement. Elle utilisera les classes de rendu par défaut
   * pour chaque clé et chaque valeur de la map en se basant sur le retour de la
   * méthode render(TYPE, KEY_RENDERER, VALUE_RENDERER)
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
   * Cette méthode permet de transformer la map de couples de clé/valeur en argument
   * en chaîne de caractères la représentant lisiblement. Elle utilisera les classes
   * de rendu en paramètre pour chaque clé et chaque valeur de la map
   * @param toRender Map de couples de clé/valeur à transformer en chaîne de caractères
   * @param keyRenderer Classe de rendu à utiliser pour chaque clé de la map
   * @param valueRenderer Classe de rendu à utiliser pour chaque valeur de la map
   * @return La chaîne de caractères représentant la map de couples de clé/valeur
   * en argument
   */
  public StringBuffer render(Map<? extends KEY, ? extends VALUE> toRender,
                             KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    // Gère la possible nullité de la map
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la map
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les clés
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
   * Getter de la classe de rendu par défaut des clés de la map
   * @return La classe de rendu par défaut des clés de la map
   */
  public KEY_RENDERER getKeyRenderer()
  {
    return this.keyRenderer;
  }
  /**
   * Getter de la classe de rendu par défaut des valeurs de la map
   * @return La classe de rendu par défaut des valeurs de la map
   */
  public VALUE_RENDERER getValueRenderer()
  {
    return this.valueRenderer;
  }
}
