package com.bid4win.commons.core.renderer;

import com.bid4win.commons.core.Bid4WinObject;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe permet de prendre en charge la transformation d'un objet de base
 * du projet d'un type donn� en cha�ne de caract�res<BR>
 * <BR>
 * @param <TYPE> D�finition du type de l'objet de base du projet � transformer en
 * cha�ne de caract�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinObjectRenderer<TYPE extends Bid4WinObject<?>>
       extends Bid4WinRenderer<TYPE>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinObjectRenderer<Bid4WinObject<?>>
      instance = new Bid4WinObjectRenderer<Bid4WinObject<?>>();
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
  public static Bid4WinObjectRenderer<Bid4WinObject<?>> getInstanceObject()
  {
    return Bid4WinObjectRenderer.instance;
  }

  /**
   * Cette m�thode permet d'effectuer le rendu de l'objet en argument en cha�ne
   * de caract�res en g�rant sa possible nullit�. Elle se basera sur le retour de
   * sa m�thode render()
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#render(java.lang.Object)
   */
  @Override
  public StringBuffer render(TYPE toRender)
  {
    // G�re la possible nullit� de l'objet
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de l'objet
    return toRender.render();
  }
}
