package com.bid4win.commons.core.security;

import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 * Cette classe d�fini la protection d'un objet<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class ObjectProtection
{
  /** Identifiant de protection */
  private String id = null;

  /**
   * Constructeur utilisant l'identifiant de protection donn�
   */
  protected ObjectProtection(String protectionId)
  {
    this.setId(protectionId);
  }

  /**
   * Cette m�thode permet de v�rifier si la protection est active
   * @return True si la protection est active, false sinon
   */
  public boolean isEnabled()
  {
    return this.getId() != null;
  }
  /**
   * Cette m�thode permet de v�rifier la protection de l'objet
   * @return True si la protection est v�rifi�e, false sinon
   */
  public boolean check()
  {
    // V�rifie si l'objet est prot�g� et la protection v�rifi�e
    return !this.isEnabled() || ObjectProtector.hasProtectionId(this.getId());
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeCompared {@inheritDoc}
   * @return {@inheritDoc}
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object toBeCompared)
  {
    // Pour comparer plus rapidement
    if(this == toBeCompared)
    {
      return true;
    }
    // Prend en compte les valeurs nulles
    if(toBeCompared == null)
    {
      return false;
    }
    // Les objets � comparer ne sont pas de la m�me classe
    if(!(this.getClass().equals(toBeCompared.getClass())))
    {
      return false;
    }
    return Bid4WinComparator.getInstance().equals(this.getId(),
                                                  ((ObjectProtection)toBeCompared).getId());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return (this.getId() == null ? 0 : this.getId().hashCode());
  }
  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "PROTECTION ID=" + this.getId();
  }

  /**
   * Getter de l'identifiant de protection
   * @return L'identifiant de protection
   */
  private String getId()
  {
    return this.id;
  }
  /**
   * Setter de l'identifiant de protection
   * @param id Identifiant de protection � positionner
   */
  private void setId(String id)
  {
    this.id = id;
  }
}
