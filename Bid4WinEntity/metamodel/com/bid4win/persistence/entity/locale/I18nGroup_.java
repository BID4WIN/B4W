package com.bid4win.persistence.entity.locale;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.core.collection.Bid4WinMap;
import com.bid4win.commons.persistence.entity.Bid4WinEmbeddableWithTypeMap_;

/**
 * Metamodel de la classe I18nGroup<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(I18nGroup.class)
public class I18nGroup_ extends Bid4WinEmbeddableWithTypeMap_
{
  /** Définition de la map d'éléments d'internationalisation associés à des langues définies */
  public static volatile SingularAttribute<I18nGroup, Bid4WinMap<Language, I18nElement>> embeddedMap;
}
