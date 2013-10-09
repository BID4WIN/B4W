package com.bid4win.commons.core.io.resource;

import com.bid4win.commons.core.reference.MessageRef;

/**
 * Cette interface défini la base des ressources utilisées par les magasins de
 * stockage<BR>
 * <BR>
 * @param <TYPE> Doit définir la classe des types de ressource<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public interface Bid4WinResource<TYPE>
{
  /**
   * Getter du type de la ressource
   * @return Le type de la ressource
   */
  public TYPE getType();
  /**
   * Cette méthode doit permettre de récupérer une référence de message à partir
   * des sous-codes en arguments
   * @param partialCodes Sous-codes permettant de parcourir les références de messages
   * liées jusqu'à la sous-référence choisie
   * @return La référence de message trouvée à partir des sous-codes en arguments
   */
  public MessageRef getMessageRef(String ... partialCodes);
}
