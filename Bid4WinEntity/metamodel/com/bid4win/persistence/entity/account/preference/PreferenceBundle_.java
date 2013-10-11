package com.bid4win.persistence.entity.account.preference;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.bid4win.commons.persistence.entity.Bid4WinEmbeddable_;
import com.bid4win.persistence.entity.locale.Language;

/**
 * Metamodel de la classe PreferenceBundle<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@StaticMetamodel(PreferenceBundle.class)
public class PreferenceBundle_ extends Bid4WinEmbeddable_
{
  /** Définition de la préférence de langage */
  public static volatile SingularAttribute<PreferenceBundle, Language> language;

}
