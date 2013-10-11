package com.bid4win.commons.caching;

import com.bid4win.commons.persistence.entity.Bid4WinEntity;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <CLASS> Doit d�finir la classe r�ellement instanci�e<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class CachedEntity<CLASS extends CachedEntity<CLASS>> extends Bid4WinEntity<CLASS, String>
{
  /** Version de l'entit� */
  private int version = -1;

  /**
   *
   * TODO A COMMENTER
   * @param id TODO A COMMENTER
   */
  public CachedEntity(String id)
  {
    super(id);
  }
  /**
   * Getter de la version de l'entit�
   * @return {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.Bid4WinEntity#getVersion()
   */
  @Override
  public int getVersion()
  {
    return this.version;
  }
  /**
   * Setter de la version de l'entit�
   * @param version Version de l'entit� � positionner
   */
  public void setVersion(int version)
  {
    this.version = version;
  }
}
