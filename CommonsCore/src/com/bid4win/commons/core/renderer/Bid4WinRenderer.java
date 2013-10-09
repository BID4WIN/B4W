package com.bid4win.commons.core.renderer;

/**
 * Cette classe d�fini la prise en charge de base de la transformation d'un objet
 * d'un type donn� en cha�ne de caract�res<BR>
 * <BR>
 * @param <TYPE> D�finition du type de l'objet � transformer en cha�ne de caract�res<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinRenderer<TYPE>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinRenderer<Object> instance = new Bid4WinRenderer<Object>();
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinRenderer<Object> getInstance()
  {
    return Bid4WinRenderer.instance;
  }

  /**
   * Cette m�thode permet d'effectuer le rendu de l'objet en argument en cha�ne
   * de caract�res en g�rant sa possible nullit�. Elle se basera sur le retour de
   * sa m�thode toString()
   * @param toRender Objet dont on souhaite obtenir le rendu
   * @return Le rendu de l'objet en argument en cha�ne de caract�res
   */
  public String toString(TYPE toRender)
  {
    // G�re la possible nullit� de l'objet
    if(toRender == null)
    {
      return "NULL";
    }
    // Effectue le rendu de l'objet
    return toRender.toString();
  }
  /**
   * Cette m�thode permet d'effectuer le rendu de l'objet en argument en cha�ne
   * de caract�res en g�rant sa possible nullit�. Elle se basera sur le retour de
   * la m�thode toString(TYPE)
   * @param toRender Objet dont on souhaite obtenir le rendu
   * @return Le rendu de l'objet en argument en cha�ne de caract�res
   */
  public StringBuffer render(TYPE toRender)
  {
    return new StringBuffer(this.toString(toRender));
  }
}
