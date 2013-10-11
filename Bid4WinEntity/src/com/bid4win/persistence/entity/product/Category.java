package com.bid4win.persistence.entity.product;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectTypeGroup;

/**
 * Cette classe défini une catégorie de produit comparable à une énumération<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Category extends Bid4WinObjectTypeGroup<Category>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 4667998089772886128L;
  /** Cette catégorie correspond aux produits High-Tech et est la catégorie par défaut */
  public final static Category HIGH_TECH = new Category("HIGH_TECH");
  /** Cette catégorie correspond aux produits de télécommunication */
  public final static Category TELECOM = new Category("TELECOM", HIGH_TECH);
  /** Cette catégorie correspond aux produits Hi-Fi */
  public final static Category HI_FI = new Category("HI_FI", HIGH_TECH);
  /** Cette catégorie correspond aux produits informatiques */
  public final static Category IT = new Category("IT", HIGH_TECH);

  /** Cette catégorie correspond aux produits de luxe */
  public final static Category LUXURY = new Category("LUXURY");
  /** Cette catégorie correspond aux produits de mode */
  public final static Category FASHION = new Category("FASHION", LUXURY);
  /** Cette catégorie correspond aux produits de bijouterie */
  public final static Category JEWELRY = new Category("JEWELRY", LUXURY);

  /**
   * Constructeur
   * @param code Code de la catégorie de produit
   * @param parents Parents de la catégorie de produit
   */
  private Category(String code, Category ... parents)
  {
    super(code, parents);
  }
}
