package com.bid4win.commons.core.security;

import java.util.Random;

import com.bid4win.commons.core.Bid4WinDate;
import com.bid4win.commons.core.exception.UserException;
import com.bid4win.commons.core.security.IdPattern.Type;

/**
 * Cette classe défini un générateur d'identifiants uniques dont on pourra choisir
 * le format complet<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class IdGenerator
{
  /** Base du générateur */
  private final static Random generator = new Random(new Bid4WinDate().getTime());

  /**
   * Génère une nouvelle chaîne de nombres de taille spécifique aléatoirement
   * @param size Taille de la chaîne de nombre à générer
   * @return La chaîne de nombre de taille spécifique générée aléatoirement
   */
  public static String generateId(int size)
  {
    String id = "";
    while(id.length() < size)
    {
      id += IdGenerator.generateId();
    }
    if(id.length() > size && size > 0)
    {
      id = id.substring(0, size);
    }
    return id;
  }
  /**
   * Génère une nouvelle chaîne de nombres aléatoirement
   * @return La chaîne de nombre générée aléatoirement
   */
  public static String generateId()
  {
    return String.valueOf(Math.abs(IdGenerator.generator.nextLong()));
  }

  /**
   * Permet de générer un identifiant unique suivant un pattern défini
   * @param pattern Pattern de l'identifiant unique à générer
   * @return L'identifiant unique généré suivant le pattern en argument
   * @throws UserException Si le pattern en argument est invalide
   */
  public static String generateId(String pattern) throws UserException
  {
    return IdGenerator.generateId(new IdPattern(pattern));
  }

  /**
   * Permet de générer un identifiant unique suivant un pattern défini
   * @param pattern Pattern de l'identifiant unique à générer
   * @return L'identifiant unique généré suivant le pattern en argument
   */
  public static String generateId(IdPattern pattern)
  {
    StringBuffer id = new StringBuffer("");
    StringBuffer generated = new StringBuffer("");
    for(int i = 0 ; i < pattern.getTypeNb() ; i++)
    {
      Type type = pattern.getType(i);
      if(type.belongsTo(Type.SEPARATOR))
      {
        id.append(type.getRegexp());
      }
      else
      {
        if(Math.pow(10, generated.length()) < type.getValueNb())
        {
          generated.append(IdGenerator.generateId());
        }
        if(type.equals(Type.NUMERICAL))
        {
          id.append(generated.substring(0, 1));
          generated = generated.deleteCharAt(0);
        }
        else
        {
          int value = Integer.parseInt(generated.substring(0, 2));
          generated = generated.delete(0, 2);
          if(type.equals(Type.ALPHABETICAL))
          {
            id.append(IdGenerator.letterFor(value));
          }
          else if(type.equals(Type.BOTH))
          {
            id.append(IdGenerator.letterOrNumberFor(value));
          }
          else if(type.equals(Type.HEXADECIMAL))
          {
            id.append(IdGenerator.hexaFor(value));
          }
        }
      }
    }
    return id.toString();
  }

  /**
   * Cette méthode est utilisé pour convertir l'entier en argument en une lettre
   * @param value Valeur comprise en 0 et 99 à convertir en lettre
   * @return La lettre correspondant à l'entier en argument
   */
  public static String letterFor(int value)
  {
    if(value > 77)
    {
      value = 'A' + value - 78;
    }
    else
    {
      value = 'A' + value / 3;
    }
    char letter = (char)value;
    return String.valueOf(letter);
  }

  /**
   * Cette méthode est utilisé pour convertir l'entier en argument en une lettre
   * ou un nombre
   * @param value Valeur comprise en 0 et 99 à convertir en lettre ou nombre
   * @return La lettre ou le nombre correspondant à l'entier en argument
   */
  public static String letterOrNumberFor(int value)
  {
    if(value < 30)
    {
      return String.valueOf(value / 3);
    }
    value -= 30;
    if(value > 51)
    {
      value = 'A' + value - 52;
    }
    else
    {
      value = 'A' + value / 2;
    }
    char letter = (char)value;
    return String.valueOf(letter);
  }
  /**
   * Cette méthode est utilisé pour convertir l'entier en argument en un hexadécimal
   * @param value Valeur comprise en 0 et 99 à convertir en hexadécimal
   * @return L'hexadécimal correspondant à l'entier en argument
   */
  public static String hexaFor(int value)
  {
    if(value < 60)
    {
      return String.valueOf(value / 6);
    }
    value -= 60;
    if(value > 35)
    {
      value = 'A' + value - 36;
    }
    else
    {
      value = 'A' + value / 6;
    }
    char letter = (char)value;
    return String.valueOf(letter);
  }
}
