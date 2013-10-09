package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe permet de prendre en charge la transformation d'un objet de base
 * du projet d'un type donné en chaîne de caractères<BR>
 * <BR>
 * @param <TYPE> Définition du type de l'objet de base du projet à transformer en
 * chaîne de caractères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinObjectRenderer<TYPE extends Bid4WinObject<?>>
       extends Bid4WinRenderer<TYPE>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinObjectRenderer<Bid4WinObject<?>>
      instance = new Bid4WinObjectRenderer<Bid4WinObject<?>>();
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
  public static Bid4WinObjectRenderer<Bid4WinObject<?>> getInstanceObject()
  {
    return Bid4WinObjectRenderer.instance;
  }

  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en argument en chaîne
   * de caractères en gérant sa possible nullité. Elle se basera sur le retour de
   * sa méthode render()
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#render(java.lang.Object)
   */
  @Override
  public StringBuffer render(TYPE toRender)
  {
    // Gère la possible nullité de l'objet
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'objet
    return toRender.render();
  }
}
