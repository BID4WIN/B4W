package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
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
public class Bid4WinObjectMapRenderer<KEY, KEY_RENDERER extends Bid4WinRenderer<KEY>,
                                      VALUE extends Bid4WinObject<?>,
                                      VALUE_RENDERER extends Bid4WinObjectRenderer<VALUE>>
       extends Bid4WinMapRenderer<KEY, KEY_RENDERER, VALUE, VALUE_RENDERER>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                                Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>
      instance = new Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                              Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>(
          Bid4WinRenderer.getInstance(), Bid4WinObjectRenderer.getInstanceObject());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.renderer.Bid4WinMapRenderer#getInstanceMap()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                   Object, Bid4WinRenderer<Object>>
         getInstanceMap() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                         Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>
         getInstanceObjectMap()
  {
    return Bid4WinObjectMapRenderer.instance;
  }

  /**
   * Constructeur
   * @param keyRenderer Classe de rendu par défaut des clés de la map
   * @param valueRenderer Classe de rendu par défaut des valeurs de la map
   */
  public Bid4WinObjectMapRenderer(KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    super(keyRenderer, valueRenderer);
  }
}
