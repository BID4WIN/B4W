package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini la prise en charge de base de la transformation d'une collection
 * d'objets sp�cifiques au projet en cha�ne de caract�res<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets de la collection � transformer
 * en cha�ne de caract�res<BR>
 * @param <RENDERER> D�finition de la classe en charge du rendu des objets de la
 * collection<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectCollectionRenderer<OBJECT extends Bid4WinObject<?>,
                                             RENDERER extends Bid4WinObjectRenderer<OBJECT>>
       extends Bid4WinCollectionRenderer<OBJECT, RENDERER>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinObjectCollectionRenderer<Bid4WinObject<?>,
                                                       Bid4WinObjectRenderer<Bid4WinObject<?>>>
      instance = new Bid4WinObjectCollectionRenderer<Bid4WinObject<?>,
                                                     Bid4WinObjectRenderer<Bid4WinObject<?>>>(
          Bid4WinObjectRenderer.getInstanceObject());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer#getInstanceCollection()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
         getInstanceCollection() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinObjectCollectionRenderer<Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>> getInstanceObjectCollection()
  {
    return Bid4WinObjectCollectionRenderer.instance;
  }

  /**
   * Constructeur
   * @param objectRenderer Classe de rendu par d�faut des objets de la collection
   */
  public Bid4WinObjectCollectionRenderer(RENDERER objectRenderer)
  {
    super(objectRenderer);
  }
}
