package com.bid4win.commons.persistence.entity.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.account.security.Credential;
import com.bid4win.commons.persistence.entity.contact.Email;

/**
 * Metamodel de la classe Account<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(AccountAbstractStub.class)
public abstract class AccountAbstractStub_ extends AccountAbstract_
{
  /** D�finition du certificat de connexion du compte utilisateur */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<AccountAbstractStub, Credential> credential;
  /** D�finition de l'email du compte utilisateur */
  // Suite � un bug Hibernate, les @Embedded de @MapedSuperClass ne sont pas pris
  // en compte si d�fini dans le metamodel de la super class : bug HHH-5024
  // TODO suivre http://opensource.atlassian.com/projects/hibernate/browse/HHH-5024
  public static volatile SingularAttribute<AccountAbstractStub, Email> email;
}
