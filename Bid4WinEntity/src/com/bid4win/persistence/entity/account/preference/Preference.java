package com.bid4win.persistence.entity.account.preference;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Preference extends PropertyAbstract<Preference, PreferenceRoot>
{
  /**
   * Constructeur complet
   * @param key Cl� de la pr�f�rence
   * @param value Valeur de la pr�f�rence
   * @throws UserException Si les param�tres entr�s sont invalides
   */
  public Preference(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la pr�f�rence parent
   * @param key Cl� de la pr�f�rence
   * @param value Valeur de la pr�f�rence
   * @param preference Pr�f�rence parent de la pr�f�rence
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws UserException Si la pr�f�rence parent en argument est nulle ou r�f�rence
   * d�j� la pr�f�rence en construction ou siSi les param�tres entr�s sont invalides
   */
  public Preference(String key, String value, Preference preference)
         throws ProtectionException, UserException
  {
    super(key, value, preference);
  }
  /**
   * Constructeur complet avec positionnement de la pr�f�rence racine
   * @param key Cl� de la pr�f�rence
   * @param value Valeur de la pr�f�rence
   * @param root Pr�f�rence racine de la pr�f�rence
   * @throws ProtectionException Si la propri�t� racine en argument est prot�g�e
   * @throws UserException Si la pr�f�rence racine en argument est nulle ou r�f�rence
   * d�j� la pr�f�rence en construction ou siSi les param�tres entr�s sont invalides
   */
  public Preference(String key, String value, PreferenceRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Pr�f�rence dont on va reprendre tous les �l�ments sauf son
   * potentiel lien vers une pr�f�rence parent (ou racine)
   * @param preference Pr�f�rence parent de la pr�f�rence
   * @throws ProtectionException Si la propri�t� parent en argument est prot�g�e
   * @throws ModelArgumentException Si la pr�f�rence � copier ou la pr�f�rence
   * parent en argument est nulle
   * @throws UserException Si les param�tres entr�s sont invalides ou si la pr�f�rence
   * parent r�f�rence d�j� la pr�f�rence en construction
   */
  public Preference(Preference toBeCopied, Preference preference)
         throws ProtectionException, ModelArgumentException, UserException
  {
    super(toBeCopied, preference);
  }

  /**
   *
   * TODO A COMMENTER
   * @param toBeCopied {@inheritDoc}
   * @param preference {@inheritDoc}
   * @return {@inheritDoc}
   * @throws ProtectionException {@inheritDoc}
   * @throws ModelArgumentException {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyAbstract#createProperty(com.bid4win.commons.persistence.entity.property.PropertyAbstract, com.bid4win.commons.persistence.entity.property.PropertyAbstract)
   */
  @Override
  protected Preference createProperty(Preference toBeCopied, Preference preference)
      throws ProtectionException, ModelArgumentException, UserException
  {
    return new Preference(toBeCopied, preference);
  }

  /**
   *
   * TODO A COMMENTER
   * @param key {@inheritDoc}
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @throws UserException {@inheritDoc}
   * @see com.bid4win.commons.persistence.entity.property.PropertyBase#createProperty(java.lang.String, java.lang.String)
   */
  @Override
  protected Preference createProperty(String key, String value) throws UserException
  {
    return new Preference(key, value);
  }
}
