package com.bid4win.commons.persistence.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinException;

/**
 * Cette classe pr�cise les principaux fonctionnements des 'type utilisateur' g�rant
 * en base de donn�es des types complexes sous la forme d'une seule colonne de type
 * cha�ne de caract�res<BR>
 * <BR>
 * @param <TYPE> D�finition du type complexe � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinStringUserType<TYPE extends Serializable>
       extends Bid4WinUserType<TYPE>
{
  /** Type SQL g�r�s */
  protected static final int[] SQL_TYPES = {Types.VARCHAR};

  /**
   * Constructeur
   * @param typeClass Classe du type complexe � g�rer
   */
  protected Bid4WinStringUserType(Class<TYPE> typeClass)
  {
    super(typeClass);
  }

  /**
   * Getter de la d�finition des colonnes utilis�es en base de donn�es pour stocker
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
   * Cette m�thode permet de r�cup�rer le type complexe dans le r�sultat de requ�te
   * en param�tre
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
   * Cette m�thode permet de positionner le type complexe donn� dans la requ�te
   * en param�tre
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
   * Cette fonction doit �tre d�finie de mani�re � r�cup�rer le type complexe
   * correspondant � la cha�ne de caract�res en argument
   * @param string Cha�ne de caract�res en argument � partir de laquelle il faut
   * r�cup�rer le type complexe
   * @return Le type complexe correspondant � la cha�ne de caract�res en argument
   * @throws Bid4WinException Si un probl�me intervient lors de la r�cup�ration
   * du type complexe
   */
  public abstract TYPE typeFromString(String string) throws Bid4WinException;
  /**
   * Cette fonction doit �tre d�finie de mani�re � construire la cha�ne de caract�res
   * correspondant au type complexe en argument
   * @param type Type complexe  � partir duquel il faut construire la cha�ne de
   * caract�res
   * @return La cha�ne de caract�res correspondant au type complexe en argument
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * de la cha�ne de caract�res
   */
  public abstract String stringFromType(TYPE type) throws Bid4WinException;
}
