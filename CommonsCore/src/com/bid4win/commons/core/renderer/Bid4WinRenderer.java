package com.bid4win.commons.core.renderer;

/**
 * Cette classe défini la prise en charge de base de la transformation d'un objet
 * d'un type donné en chaîne de caractères<BR>
 * <BR>
 * @param <TYPE> Définition du type de l'objet à transformer en chaîne de caractères<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinRenderer<TYPE>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinRenderer<Object> instance = new Bid4WinRenderer<Object>();
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinRenderer<Object> getInstance()
  {
    return Bid4WinRenderer.instance;
  }

  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en argument en chaîne
   * de caractères en gérant sa possible nullité. Elle se basera sur le retour de
   * sa méthode toString()
   * @param toRender Objet dont on souhaite obtenir le rendu
   * @return Le rendu de l'objet en argument en chaîne de caractères
   */
  public String toString(TYPE toRender)
  {
    // Gère la possible nullité de l'objet
    if(toRender == null)
    {
      return "NULL";
    }
    // Effectue le rendu de l'objet
    return toRender.toString();
  }
  /**
   * Cette méthode permet d'effectuer le rendu de l'objet en argument en chaîne
   * de caractères en gérant sa possible nullité. Elle se basera sur le retour de
   * la méthode toString(TYPE)
   * @param toRender Objet dont on souhaite obtenir le rendu
   * @return Le rendu de l'objet en argument en chaîne de caractères
   */
  public StringBuffer render(TYPE toRender)
  {
    return new StringBuffer(this.toString(toRender));
  }
}
