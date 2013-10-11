package com.bid4win.persistence.entity.locale;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap_;

/**
 * Metamodel de la classe I18nGroup<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
@StaticMetamodel(I18nGroup.class)
public class I18nGroup_ extends Bid4WinEmbeddableWithTypeMap_
{
  /** D�finition de la map d'�l�ments d'internationalisation associ�s � des langues d�finies */
  public static volatile SingularAttribute<I18nGroup, Bid4WinMap<Language, I18nElement>> embeddedMap;
}
