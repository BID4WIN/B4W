package com.bid4win.persistence.usertype;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.bid4win.persistence.entity.account.Gender;
import com.bid4win.persistence.entity.account.credit.Origin;
import com.bid4win.persistence.entity.image.ImageType;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.price.Currency;
import com.bid4win.persistence.usertype.account.GenderUserType;
import com.bid4win.persistence.usertype.account.credit.OriginUserType;
import com.bid4win.persistence.usertype.image.ImageTypeUserType;
import com.bid4win.persistence.usertype.locale.LanguageUserType;
import com.bid4win.persistence.usertype.locale.collection.I18nElementMapUserType;
import com.bid4win.persistence.usertype.locale.collection.LanguageSetUserType;
import com.bid4win.persistence.usertype.price.CurrencyUserType;
import com.bid4win.persistence.usertype.price.collection.AmountMapUserType;

/**
 * Cette classe sert uniquement pour la définition des UserType utilisés<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
@MappedSuperclass
@TypeDefs({@TypeDef(defaultForType = Gender.class,
                    name = "GENDER", typeClass = GenderUserType.class),
           @TypeDef(defaultForType = Origin.class,
                    name = "CREDIT_ORIGIN", typeClass = OriginUserType.class),
           @TypeDef(defaultForType = Currency.class,
                    name = "CURRENCY", typeClass = CurrencyUserType.class),
           @TypeDef(name = "AMOUNT_MAP", typeClass = AmountMapUserType.class),
           @TypeDef(defaultForType = Language.class,
                    name = "LANGUAGE", typeClass = LanguageUserType.class),
           @TypeDef(name = "LANGUAGE_SET", typeClass = LanguageSetUserType.class),
           @TypeDef(name = "I18N_ELEMENT_MAP", typeClass = I18nElementMapUserType.class),
           @TypeDef(defaultForType = ImageType.class,
                    name = "IMAGE_TYPE", typeClass = ImageTypeUserType.class),
           @TypeDef(defaultForType = com.bid4win.persistence.entity.image.UsageType.class,
                    name = "IMAGE_USAGE_TYPE", typeClass = com.bid4win.persistence.usertype.image.UsageTypeUserType.class),
           @TypeDef(defaultForType = com.bid4win.persistence.entity.locale.inner.InnerContentType.class,
                    name = "INNER_CONTENT_TYPE", typeClass = com.bid4win.persistence.usertype.locale.inner.InnerContentTypeUserType.class),
           @TypeDef(defaultForType = com.bid4win.persistence.entity.locale.inner.UsageType.class,
                    name = "INNER_CONTENT_USAGE_TYPE", typeClass = com.bid4win.persistence.usertype.locale.inner.UsageTypeUserType.class),
           @TypeDef(defaultForType = com.bid4win.persistence.entity.sale.Step.class,
                    name = "SALE_STEP", typeClass = com.bid4win.persistence.usertype.sale.StepUserType.class),
           @TypeDef(defaultForType = com.bid4win.persistence.entity.auction.Status.class,
                    name = "AUCTION_STATUS", typeClass = com.bid4win.persistence.usertype.auction.StatusUserType.class)})
public class Bid4WinUserTypeDefinition extends com.bid4win.commons.persistence.usertype.Bid4WinUserTypeDefinition
{
  // Cette classe sert uniquement pour la définition des UserType utilisés
}
