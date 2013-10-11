package com.bid4win.commons.persistence.entity.foo.cached;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bid4win.commons.core.Bid4WinDate;

/**
 * Classe de test simple<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("CACHED")
public class FooCachedStub extends FooCached<FooCachedStub>
{
  /**
   * Constructeur
   */
  protected FooCachedStub()
  {
    super();
  }
  /**
   * Constructeur
   * @param value Valeur de l'objet
   * @param date Date de l'objet
   */
  public FooCachedStub(String value, Bid4WinDate date)
  {
    super(value, date);
  }
}
