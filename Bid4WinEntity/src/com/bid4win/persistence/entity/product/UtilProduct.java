package com.bid4win.persistence.entity.product;

import java.util.Collection;

import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.collection.Bid4WinSet;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.UtilI18n;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilProduct
{
  /**
   *
   * TODO A COMMENTER
   * @param products TODO A COMMENTER
   * @param searchedName TODO A COMMENTER
   * @param restrictions TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinList<Product> getFilteredProduct(Collection<Product> products,
                                                        String searchedName,
                                                        Language ... restrictions)
  {
    Bid4WinList<Product> result = new Bid4WinList<Product>(products.size());
    Bid4WinSet<Language> restrictionSet = new Bid4WinSet<Language>(restrictions);
    for(Product product : products)
    {
      if(!UtilI18n.isFiltered(product.getNames(), searchedName, restrictionSet))
      {
        result.add(product);
      }
    }
    return result;
  }
}
