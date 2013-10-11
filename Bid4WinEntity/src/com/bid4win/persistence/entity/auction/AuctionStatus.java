package com.bid4win.persistence.entity.auction;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
//Annotation pour la persistence
@Embeddable
@Access(AccessType.FIELD)
@AttributeOverride(name = "type", column = @Column(name = "STATUS"))
public class AuctionStatus extends Bid4WinEmbeddableWithType<AuctionStatus, Status>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 6372282613166132357L;

  /**
   * Constructeur pour création par introspection
   */
  @SuppressWarnings("unused")
  private AuctionStatus()
  {
    super();
  }
  /**
   * Constructeur
   * @param status Status associé à l'objet
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public AuctionStatus(Status status) throws UserException
  {
    super(status);
  }
}
