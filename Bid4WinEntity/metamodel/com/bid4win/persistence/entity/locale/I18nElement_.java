package com.bid4win.persistence.entity.locale;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithType_;

/**
 * Metamodel de la classe I18nElement<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(I18nElement.class)
public class I18nElement_ extends Bid4WinEmbeddableWithType_
{
  /** D�finition de la valeur de l'�l�ment d'internationalisation */
  public static volatile SingularAttribute<I18nElement, String> value;
}
