package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.reference.MessageRef;

/**
 * Cette interface d�fini la base des ressources utilis�es par les magasins de
 * stockage<BR>
 * <BR>
 * @param <TYPE> Doit d�finir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public interface Bid4WinResource<TYPE>
{
  /**
   * Getter du type de la ressource
   * @return Le type de la ressource
   */
  public TYPE getType();
  /**
   * Cette m�thode doit permettre de r�cup�rer une r�f�rence de message � partir
   * des sous-codes en arguments
   * @param partialCodes Sous-codes permettant de parcourir les r�f�rences de messages
   * li�es jusqu'� la sous-r�f�rence choisie
   * @return La r�f�rence de message trouv�e � partir des sous-codes en arguments
   */
  public MessageRef getMessageRef(String ... partialCodes);
}
