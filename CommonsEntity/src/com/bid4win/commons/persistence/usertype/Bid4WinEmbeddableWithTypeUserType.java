package com.bid4win.commons.persistence.usertype;

import java.util.Arrays;

import com.bid4win.commons.core.Bid4WinObject.Bid4WinObjectType;
import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.UtilObject;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType;

/**
 * Cette classe pr�cise les principaux fonctionnements des 'type utilisateur' g�rant
 * en base de donn�es des objets associ�s � des types d�finis<BR>
 * <BR>
 * @param <EMBEDDED> D�finition de l'objet � g�rer en base de donn�es<BR>
 * @param <TYPE> D�finition du type associ� � l'objet � g�rer en base de donn�es<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Bid4WinEmbeddableWithTypeUserType<EMBEDDED extends Bid4WinEmbeddableWithType<EMBEDDED, TYPE>,
                                                        TYPE extends Bid4WinObjectType<TYPE>>
       extends Bid4WinStringUserType<EMBEDDED>
{
  /** S�parateur utilis� en chaque champ des objets g�r�s */
  public final static String SEPARATOR = ":$:";

  /** Classe du type associ� aux objets g�r�s */
  private Class<TYPE> typeClass;

  /**
   * Constructeur
   * @param embeddedClass Classe des objets � g�rer
   * @param typeClass Classe du type associ� aux objets g�r�s
   */
  protected Bid4WinEmbeddableWithTypeUserType(Class<EMBEDDED> embeddedClass,
                                              Class<TYPE> typeClass)
  {
    super(embeddedClass);
    this.setTypeClass(typeClass);
  }

  /**
   * Cette fonction d�fini la r�cup�ration de l'objet correspondant � la cha�ne
   * de caract�res en argument
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
   * Cette fonction doit �tre impl�ment� afin de construire l'objet correspondant
   * aux cha�nes de caract�res en argument associ� au type donn�
   * @param type Type associ� � l'objet � construire
   * @param strings Cha�nes de caract�res correspondant aux diff�rents champs de
   * l'objet � construire autres que son type
   * @return L'objet correspondant aux cha�nes de caract�res en argument associ�
   * au type donn�
   * @throws Bid4WinException Si un probl�me intervient lors de la construction
   * de l'objet � retourner
   */
  public abstract EMBEDDED typeFromStrings(TYPE type, String ... strings) throws Bid4WinException;
  /**
   * Cette fonction d�fini la construction de la cha�ne de caract�res correspondant
   * � l'objet en argument. Elle doit �tre red�finie afin d'y ajouter les diff�rents
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
   * Cette fonction doit �tre impl�ment�e afin de r�cup�rer la liste des champs
   * de l'objet en param�tre autres que son type sous la forme de cha�nes de caract�res
   * @param embedded Objet dont il faut r�cup�rer la liste des champs autres que
   * son type sous la forme de cha�nes de caract�res
   * @return La liste des champs de l'objet en param�tre autres que son type sous
   * la forme de cha�nes de caract�res
   */
  public abstract Bid4WinList<String> stringsFromType(EMBEDDED embedded);

  /**
   * Getter de la classe du type associ� aux objets g�r�s
   * @return La classe du type associ� aux objets g�r�s
   */
  public Class<TYPE> getTypeClass()
  {
    return this.typeClass;
  }
  /**
   * Setter de la classe du type associ� aux objets g�r�s
   * @param typeClass Classe du type associ� aux objets g�r�s
   */
  private void setTypeClass(Class<TYPE> typeClass)
  {
    this.typeClass = typeClass;
  }
}
