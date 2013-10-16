package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectTypeGroup;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe défini un rôle comparable à une énumération ayant la notion d'
 * appartenance à un groupe de rôles supérieur<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Role extends Bid4WinObjectTypeGroup<Role>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = 1447365544234114243L;

  /** Aucun rôle */
  public final static Role NONE = new Role("NONE");
  /** Rôle basic */
  public final static Role BASIC = new Role("BASIC");
  /** Rôle utilisateur en attente de validation */
  public final static Role WAIT = new Role("WAIT", Role.BASIC);
  /** Rôle utilisateur */
  public final static Role USER = new Role("USER", Role.BASIC);
  /** Rôle de gestionnaire */
  public final static Role MANAGER = new Role("MNGR", BASIC);
  /** Rôle de linguiste */
  public final static Role I18N = new Role("I18N", Role.MANAGER);
  /** Rôle de gestion métier */
  public final static Role BIZ = new Role("BIZ", Role.I18N);
  /** Rôle technique */
  public final static Role TECH = new Role("TECH", Role.MANAGER);
  /** Rôle administrateur */
  public final static Role ADMIN = new Role("ADMIN", Role.BIZ, Role.TECH);
  /** Rôle super */
  public final static Role SUPER = new Role("SUPER", Role.USER, Role.ADMIN);

  /** Définition du rôle par défaut */
  public final static Role DEFAULT = Bid4WinObjectType.getDefaultType(Role.class);

  /**
   * Cette méthode permet de récupérer tous les rôles existants
   * @return Tous les rôles définis
   */
  public static Bid4WinCollection<Role> getRoles()
  {
    return Bid4WinObjectType.getTypes(Role.class);
  }

  /**
   * Constructeur
   * @param code Code du rôle
   * @param roles Rôles parents du rôle
   */
  protected Role(String code, Role ... roles)
  {
    super(code, roles);
  }

  /**
   * Redéfini la transformation en chaîne de caractères d'un rôle lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer("ROLE=" + this.getCode());
  }
}
