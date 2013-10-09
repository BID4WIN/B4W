package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
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
public class Bid4WinObjectMapRenderer<KEY, KEY_RENDERER extends Bid4WinRenderer<KEY>,
                                      VALUE extends Bid4WinObject<?>,
                                      VALUE_RENDERER extends Bid4WinObjectRenderer<VALUE>>
       extends Bid4WinMapRenderer<KEY, KEY_RENDERER, VALUE, VALUE_RENDERER>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                                Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>
      instance = new Bid4WinObjectMapRenderer<Object, Bid4WinRenderer<Object>,
                                              Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>>(
          Bid4WinRenderer.getInstance(), Bid4WinObjectRenderer.getInstanceObject());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.renderer.Bid4WinMapRenderer#getInstanceMap()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinMapRenderer<Object, Bid4WinRenderer<Object>,
                                   Object, Bid4WinRenderer<Object>>
         getInstanceMap() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
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
   * @param keyRenderer Classe de rendu par d�faut des cl�s de la map
   * @param valueRenderer Classe de rendu par d�faut des valeurs de la map
   */
  public Bid4WinObjectMapRenderer(KEY_RENDERER keyRenderer, VALUE_RENDERER valueRenderer)
  {
    super(keyRenderer, valueRenderer);
  }
}
