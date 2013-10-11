package com.bid4win.persistence.entity.account.preference;

import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.exception.ProtectionException;
import com.bid4win.commons.persistence.entity.property.PropertyAbstract;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Preference extends PropertyAbstract<Preference, PreferenceRoot>
{
  /**
   * Constructeur complet
   * @param key Clé de la préférence
   * @param value Valeur de la préférence
   * @throws UserException Si les paramètres entrés sont invalides
   */
  public Preference(String key, String value) throws UserException
  {
    super(key, value);
  }
  /**
   * Constructeur complet avec positionnement de la préférence parent
   * @param key Clé de la préférence
   * @param value Valeur de la préférence
   * @param preference Préférence parent de la préférence
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws UserException Si la préférence parent en argument est nulle ou référence
   * déjà la préférence en construction ou siSi les paramètres entrés sont invalides
   */
  public Preference(String key, String value, Preference preference)
         throws ProtectionException, UserException
  {
    super(key, value, preference);
  }
  /**
   * Constructeur complet avec positionnement de la préférence racine
   * @param key Clé de la préférence
   * @param value Valeur de la préférence
   * @param root Préférence racine de la préférence
   * @throws ProtectionException Si la propriété racine en argument est protégée
   * @throws UserException Si la préférence racine en argument est nulle ou référence
   * déjà la préférence en construction ou siSi les paramètres entrés sont invalides
   */
  public Preference(String key, String value, PreferenceRoot root)
         throws ProtectionException, UserException
  {
    super(key, value, root);
  }
  /**
   * Constructeur par copie
   * @param toBeCopied Préférence dont on va reprendre tous les éléments sauf son
   * potentiel lien vers une préférence parent (ou racine)
   * @param preference Préférence parent de la préférence
   * @throws ProtectionException Si la propriété parent en argument est protégée
   * @throws ModelArgumentException Si la préférence à copier ou la préférence
   * parent en argument est nulle
   * @throws UserException Si les paramètres entrés sont invalides ou si la préférence
   * parent référence déjà la préférence en construction
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
