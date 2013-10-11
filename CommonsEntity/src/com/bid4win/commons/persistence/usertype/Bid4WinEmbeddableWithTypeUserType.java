package com.bid4win.commons.persistence.usertype;

import java.util.Arrays;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 * Cette classe précise les principaux fonctionnements des 'type utilisateur' gérant
 * en base de données des objets associés à des types définis<BR>
 * <BR>
 * @param <EMBEDDED> Définition de l'objet à gérer en base de données<BR>
 * @param <TYPE> Définition du type associé à l'objet à gérer en base de données<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Bid4WinEmbeddableWithTypeUserType<EMBEDDED extends Bid4WinEmbeddableWithType<EMBEDDED, TYPE>,
                                                        TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinStringUserType<EMBEDDED>
{
  /** Séparateur utilisé en chaque champ des objets gérés */
  public final static String SEPARATOR = ":$:";

  /** Classe du type associé aux objets gérés */
  private Class<TYPE> typeClass;

  /**
   * Constructeur
   * @param embeddedClass Classe des objets à gérer
   * @param typeClass Classe du type associé aux objets gérés
   */
  protected Bid4WinEmbeddableWithTypeUserType(Class<EMBEDDED> embeddedClass,
                                              Class<TYPE> typeClass)
  {
    super(embeddedClass);
    this.setTypeClass(typeClass);
  }

  /**
   * Cette fonction défini la récupération de l'objet correspondant à la chaîne
   * de caractères en argument
   * @param string {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#typeFromString(java.lang.String)
   */
  @Override
  public final EMBEDDED typeFromString(String string) throws Bid4WinException
  {
    String[] strings = this.split(string, Bid4WinEmbeddableWithTypeUserType.SEPARATOR);
    UtilNumber.checkMinValue("stringNb", strings.length, 1, true);
    return this.typeFromStrings(Bid4WinObjectType.getType(this.getTypeClass(),
                                                          strings[0]),
                                Arrays.copyOfRange(strings, 1, strings.length));
  }
  /**
   * Cette fonction doit être implémenté afin de construire l'objet correspondant
   * aux chaînes de caractères en argument associé au type donné
   * @param type Type associé à l'objet à construire
   * @param strings Chaînes de caractères correspondant aux différents champs de
   * l'objet à construire autres que son type
   * @return L'objet correspondant aux chaînes de caractères en argument associé
   * au type donné
   * @throws Bid4WinException Si un problème intervient lors de la construction
   * de l'objet à retourner
   */
  public abstract EMBEDDED typeFromStrings(TYPE type, String ... strings) throws Bid4WinException;
  /**
   * Cette fonction défini la construction de la chaîne de caractères correspondant
   * à l'objet en argument. Elle doit être redéfinie afin d'y ajouter les différents
   * champs de l'objet
   * @param embedded {@inheritDoc}
   * @return {@inheritDoc}
   * @throws Bid4WinException {@inheritDoc}
   * @see com.bid4win.commons.persistence.usertype.Bid4WinStringUserType#stringFromType(java.io.Serializable)
   */
  @Override
  public final String stringFromType(EMBEDDED embedded) throws Bid4WinException
  {
    UtilObject.checkNotNull("embedded", embedded);
    StringBuffer result = new StringBuffer(embedded.getType().getCode());
    for(String string : this.stringsFromType(embedded))
    {
      result.append(Bid4WinEmbeddableWithTypeUserType.SEPARATOR).append(string);
    }
    return result.toString();
  }
  /**
   * Cette fonction doit être implémentée afin de récupérer la liste des champs
   * de l'objet en paramètre autres que son type sous la forme de chaînes de caractères
   * @param embedded Objet dont il faut récupérer la liste des champs autres que
   * son type sous la forme de chaînes de caractères
   * @return La liste des champs de l'objet en paramètre autres que son type sous
   * la forme de chaînes de caractères
   */
  public abstract Bid4WinList<String> stringsFromType(EMBEDDED embedded);

  /**
   * Getter de la classe du type associé aux objets gérés
   * @return La classe du type associé aux objets gérés
   */
  public Class<TYPE> getTypeClass()
  {
    return this.typeClass;
  }
  /**
   * Setter de la classe du type associé aux objets gérés
   * @param typeClass Classe du type associé aux objets gérés
   */
  private void setTypeClass(Class<TYPE> typeClass)
  {
    this.typeClass = typeClass;
  }
}
