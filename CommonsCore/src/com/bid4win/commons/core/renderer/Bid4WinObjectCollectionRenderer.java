package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini la prise en charge de base de la transformation d'une collection
 * d'objets spécifiques au projet en chaîne de caractères<BR>
 * <BR>
 * @param <OBJECT> Définition du type des objets de la collection à transformer
 * en chaîne de caractères<BR>
 * @param <RENDERER> Définition de la classe en charge du rendu des objets de la
 * collection<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectCollectionRenderer<OBJECT extends Bid4WinObject<?>,
                                             RENDERER extends Bid4WinObjectRenderer<OBJECT>>
       extends Bid4WinCollectionRenderer<OBJECT, RENDERER>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectCollectionRenderer<Bid4WinObject<?>,
                                                       Bid4WinObjectRenderer<Bid4WinObject<?>>>
      instance = new Bid4WinObjectCollectionRenderer<Bid4WinObject<?>,
                                                     Bid4WinObjectRenderer<Bid4WinObject<?>>>(
          Bid4WinObjectRenderer.getInstanceObject());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.renderer.Bid4WinCollectionRenderer#getInstanceCollection()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
         getInstanceCollection() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinObjectCollectionRenderer<Bid4WinObject<?>, Bid4WinObjectRenderer<Bid4WinObject<?>>> getInstanceObjectCollection()
  {
    return Bid4WinObjectCollectionRenderer.instance;
  }

  /**
   * Constructeur
   * @param objectRenderer Classe de rendu par défaut des objets de la collection
   */
  public Bid4WinObjectCollectionRenderer(RENDERER objectRenderer)
  {
    super(objectRenderer);
  }
}
