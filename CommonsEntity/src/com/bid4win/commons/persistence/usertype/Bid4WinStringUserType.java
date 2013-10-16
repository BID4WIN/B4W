package com.bid4win.commons.persistence.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Cette classe précise les principaux fonctionnements des 'type utilisateur' gérant
 * en base de données des types complexes sous la forme d'une seule colonne de type
 * chaîne de caractères<BR>
 * <BR>
 * @param <TYPE> Définition du type complexe à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinStringUserType<TYPE extends Serializable>
       extends Bid4WinUserType<TYPE>
{
  /** Type SQL gérés */
  protected static final int[] SQL_TYPES = {Types.VARCHAR};

  /**
   * Constructeur
   * @param typeClass Classe du type complexe à gérer
   */
  protected Bid4WinStringUserType(Class<TYPE> typeClass)
  {
    super(typeClass);
  }

  /**
   * Getter de la définition des colonnes utilisées en base de données pour stocker
   * le type complexe
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.UserType#sqlTypes()
   */
  @Override
  public final int[] sqlTypes()
  {
    return Bid4WinStringUserType.SQL_TYPES;
  }

  /**
   * Cette méthode permet de récupérer le type complexe dans le résultat de requête
   * en paramètre
   * @param resultSet {@inheritDoc}
   * @param names {@inheritDoc}
   * @param owner {@inheritDoc}
   * @return {@inheritDoc}
   * @throws SQLException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
   */
  @Override
  public final TYPE nullSafeGet(ResultSet resultSet, String[] names, Object owner)
         throws SQLException
  {
    TYPE result = null;
    String string = resultSet.getString(names[0]);
    if(string != null)
    {
      try
      {
        result = this.typeFromString(string);
      }
      catch(Bid4WinException ex)
      {
        throw new SQLException(ex);
      }
    }
    return result;
  }
  /**
   * Cette méthode permet de positionner le type complexe donné dans la requête
   * en paramètre
   * @param statement {@inheritDoc}
   * @param value {@inheritDoc}
   * @param index {@inheritDoc}
   * @throws SQLException {@inheritDoc}
   * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
   */
  @SuppressWarnings("unchecked")
  @Override
  public final void nullSafeSet(PreparedStatement statement, Object value, int index)
         throws SQLException
  {
    if(value == null)
    {
      statement.setNull(index, Types.VARCHAR);
    }
    else
    {
      try
      {
        statement.setString(index, this.stringFromType((TYPE)value));
      }
      catch(Bid4WinException ex)
      {
        throw new SQLException(ex);
      }
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param value {@inheritDoc}
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.EnhancedUserType#objectToSQLString(java.lang.Object)
   */
  /*@SuppressWarnings("unchecked")
  @Override
  public String objectToSQLString(Object value)
  {
    try
    {
      return '\'' + this.stringFromType((TYPE)value) + '\'';
    }
    catch(Bid4WinException ex)
    {
      ex.printStackTrace();
      return null;
    }
  }
  /**
   *
   * TODO A COMMENTER
   * @param arg0 {@inheritDoc}
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.EnhancedUserType#fromXMLString(java.lang.String)
   */
  /*@Override
  public Object fromXMLString(String arg0)
  {
    // TODO Auto-generated method stub
    return null;
  }
  /**
   *
   * TODO A COMMENTER
   * @param arg0 {@inheritDoc}
   * @return {@inheritDoc}
   * @see org.hibernate.usertype.EnhancedUserType#toXMLString(java.lang.Object)
   */
  /*@Override
  public String toXMLString(Object arg0)
  {
    // TODO Auto-generated method stub
    return null;
  }*/

  /**
   *
   * TODO A COMMENTER
   * @param string TODO A COMMENTER
   * @param separator TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public String[] split(String string, String separator)
  {
    return UtilString.trimNotNull(string).split("\\Q" + separator + "\\E");
  }
  /**
   * Cette fonction doit être définie de manière à récupérer le type complexe
   * correspondant à la chaîne de caractères en argument
   * @param string Chaîne de caractères en argument à partir de laquelle il faut
   * récupérer le type complexe
   * @return Le type complexe correspondant à la chaîne de caractères en argument
   * @throws Bid4WinException Si un problème intervient lors de la récupération
   * du type complexe
   */
  public abstract TYPE typeFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit être définie de manière à construire la chaîne de caractères
   * correspondant au type complexe en argument
   * @param type Type complexe  à partir duquel il faut construire la chaîne de
   * caractères
   * @return La chaîne de caractères correspondant au type complexe en argument
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * de la chaîne de caractères
   */
  public abstract String stringFromType(TYPE type) throws Bid4WinException;
}
