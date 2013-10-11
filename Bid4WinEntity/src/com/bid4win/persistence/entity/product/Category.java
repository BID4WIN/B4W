package com.bid4win.persistence.entity.product;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectTypeGroup;

/**
 * Cette classe d�fini une cat�gorie de produit comparable � une �num�ration<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Category extends Bid4WinObjectTypeGroup<Category>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 4667998089772886128L;
  /** Cette cat�gorie correspond aux produits High-Tech et est la cat�gorie par d�faut */
  public final static Category HIGH_TECH = new Category("HIGH_TECH");
  /** Cette cat�gorie correspond aux produits de t�l�communication */
  public final static Category TELECOM = new Category("TELECOM", HIGH_TECH);
  /** Cette cat�gorie correspond aux produits Hi-Fi */
  public final static Category HI_FI = new Category("HI_FI", HIGH_TECH);
  /** Cette cat�gorie correspond aux produits informatiques */
  public final static Category IT = new Category("IT", HIGH_TECH);

  /** Cette cat�gorie correspond aux produits de luxe */
  public final static Category LUXURY = new Category("LUXURY");
  /** Cette cat�gorie correspond aux produits de mode */
  public final static Category FASHION = new Category("FASHION", LUXURY);
  /** Cette cat�gorie correspond aux produits de bijouterie */
  public final static Category JEWELRY = new Category("JEWELRY", LUXURY);

  /**
   * Constructeur
   * @param code Code de la cat�gorie de produit
   * @param parents Parents de la cat�gorie de produit
   */
  private Category(String code, Category ... parents)
  {
    super(code, parents);
  }
}
