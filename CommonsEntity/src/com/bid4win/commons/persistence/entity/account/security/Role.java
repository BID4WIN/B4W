package com.bid4win.commons.persistence.entity.account.security;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectTypeGroup;
import com.bid4win.commons.core.collection.Bid4WinCollection;

/**
 * Cette classe d�fini un r�le comparable � une �num�ration ayant la notion d'
 * appartenance � un groupe de r�les sup�rieur<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Role extends Bid4WinObjectTypeGroup<Role>
{
  /** Determine si un objet d�-s�rialis� est compatible avec cette classe. La
   * valeur doit �tre modifi�e si une �volution de la classe la rend incompatible
   * avec les versions pr�c�dentes */
  private static final long serialVersionUID = 1447365544234114243L;

  /** Aucun r�le */
  public final static Role NONE = new Role("NONE");
  /** R�le basic */
  public final static Role BASIC = new Role("BASIC");
  /** R�le utilisateur en attente de validation */
  public final static Role WAIT = new Role("WAIT", Role.BASIC);
  /** R�le utilisateur */
  public final static Role USER = new Role("USER", Role.BASIC);
  /** R�le de gestionnaire */
  public final static Role MANAGER = new Role("MNGR", BASIC);
  /** R�le de linguiste */
  public final static Role I18N = new Role("I18N", Role.MANAGER);
  /** R�le de gestion m�tier */
  public final static Role BIZ = new Role("BIZ", Role.I18N);
  /** R�le technique */
  public final static Role TECH = new Role("TECH", Role.MANAGER);
  /** R�le administrateur */
  public final static Role ADMIN = new Role("ADMIN", Role.BIZ, Role.TECH);
  /** R�le super */
  public final static Role SUPER = new Role("SUPER", Role.USER, Role.ADMIN);

  /** D�finition du r�le par d�faut */
  public final static Role DEFAULT = Bid4WinObjectType.getDefaultType(Role.class);

  /**
   * Cette m�thode permet de r�cup�rer tous les r�les existants
   * @return Tous les r�les d�finis
   */
  public static Bid4WinCollection<Role> getRoles()
  {
    return Bid4WinObjectType.getTypes(Role.class);
  }

  /**
   * Constructeur
   * @param code Code du r�le
   * @param roles R�les parents du r�le
   */
  protected Role(String code, Role ... roles)
  {
    super(code, roles);
  }

  /**
   * Red�fini la transformation en cha�ne de caract�res d'un r�le lisiblement
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType#render()
   */
  @Override
  public StringBuffer render()
  {
    return new StringBuffer("ROLE=" + this.getCode());
  }
}
