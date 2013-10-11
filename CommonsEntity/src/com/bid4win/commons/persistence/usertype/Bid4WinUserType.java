package com.bid4win.commons.persistence.usertype;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

import com.bid4win.commons.core.comparator.Bid4WinComparator;

/**
 * Cette classe défini les principaux fonctionnements des 'type utilisateur' gérant
 * en base de données des types complexes<BR>
 * <BR>
 * @param <TYPE> Définition du type complexe à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinUserType<TYPE extends Serializable>
       implements EnhancedUserType // TODO SEE CustomType !!!
{
  /** Classe du type complexe géré */
  private Class<TYPE> typeClass;

  /**
   * Constructeur
   * @param typeClass Classe du type complexe à gérer
   */
  protected Bid4WinUserType(Class<TYPE> typeClass)
  {
    this.setTypeClass(typeClass);
  }

  /**
   * Getter de la classe du type complexe géré
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.UserType#returnedClass()
   */
  @Override
  public Class<TYPE> returnedClass()
  {
    return this.typeClass;
  }

  /**
   *
   * TODO A COMMENTER
   * @param x TODO A COMMENTER
   * @param y TODO A COMMENTER
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean equals(Object x, Object y)
  {
    return Bid4WinComparator.getInstance().equals(x, y);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws HibernateException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
   */
  @Override
  public int hashCode(Object value) throws HibernateException
  {
    return value.hashCode();
  }

  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public TYPE deepCopy(Object value)
  {
    return (TYPE)value;
  }

  /**
   *
   * TODO A COMMENTER
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.UserType#isMutable()
   */
  @Override
  public boolean isMutable()
  {
    return false;
  }

  /**
   *
   * TODO A COMMENTER
   * @param cached TODO A COMMENTER
   * @param owner TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws HibernateException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
   */
  @Override
  public TYPE assemble(Serializable cached, Object owner) throws HibernateException
  {
    return this.deepCopy(cached);
  }
  /**
   *
   * TODO A COMMENTER
   * @param value TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws HibernateException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
   */
  @Override
  public Serializable disassemble(Object value) throws HibernateException
  {
    return this.deepCopy(value);
  }
  /**
   *
   * TODO A COMMENTER
   * @param original TODO A COMMENTER
   * @param target TODO A COMMENTER
   * @param owner TODO A COMMENTER
   * @return TODO A COMMENTER
   * @throws HibernateException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
   */
  @Override
  public TYPE replace(Object original, Object target, Object owner) throws HibernateException
  {
    return this.deepCopy(original);
  }

  /**
   *
   * TODO A COMMENTER
   * @param typeClass TODO A COMMENTER
   */
  private void setTypeClass(Class<TYPE> typeClass)
  {
    this.typeClass = typeClass;
  }
}
