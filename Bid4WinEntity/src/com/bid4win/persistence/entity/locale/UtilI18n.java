package com.bid4win.persistence.entity.locale;

import java.util.regex.Pattern;

import com.bid4win.commons.core.collection.Bid4WinSet;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class UtilI18n
{
  /**
   *
   * TODO A COMMENTER
   * @param element TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictions TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static boolean isFiltered(I18nElement element, String searchString, Language ... restrictions)
  {
    return UtilI18n.isFiltered(element, searchString, new Bid4WinSet<Language>(restrictions));
  }
  /**
   *
   * TODO A COMMENTER
   * @param element TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictionSet TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static boolean isFiltered(I18nElement element, String searchString, Bid4WinSet<Language> restrictionSet)
  {
    if((restrictionSet.size() == 0 || restrictionSet.contains(element.getType())) &&
       Pattern.compile(searchString, Pattern.CASE_INSENSITIVE).matcher(element.getValue()).find())
    {
      return false;
    }
    return true;
  }
  /**
   *
   * TODO A COMMENTER
   * @param elementSet TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictions TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinSet<I18nElement> getFilteredElement(Bid4WinSet<I18nElement> elementSet,
                                                           String searchString,
                                                           Language ... restrictions)
  {
    Bid4WinSet<I18nElement> result = new Bid4WinSet<I18nElement>(elementSet.size());
    Bid4WinSet<Language> restrictionSet = new Bid4WinSet<Language>(restrictions);
    for(I18nElement element : elementSet)
    {
      if(!UtilI18n.isFiltered(element, searchString, restrictionSet))
      {
        result.add(element);
      }
    }
    return result;
  }

  /**
   *
   * TODO A COMMENTER
   * @param group TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictions TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static boolean isFiltered(I18nGroup group, String searchString, Language ... restrictions)
  {
    return UtilI18n.isFiltered(group, searchString, new Bid4WinSet<Language>(restrictions));
  }
  /**
   *
   * TODO A COMMENTER
   * @param group TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictionSet TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static boolean isFiltered(I18nGroup group, String searchString, Bid4WinSet<Language> restrictionSet)
  {
    for(I18nElement element : group.getEmbeddedSet())
    {
      if(!UtilI18n.isFiltered(element, searchString, restrictionSet))
      {
        return false;
      }
    }
    return true;
  }
  /**
   *
   * TODO A COMMENTER
   * @param groupSet TODO A COMMENTER
   * @param searchString TODO A COMMENTER
   * @param restrictions TODO A COMMENTER
   * @return TODO A COMMENTER
   */
  public static Bid4WinSet<I18nGroup> getFilteredGroup(Bid4WinSet<I18nGroup> groupSet,
                                                       String searchString,
                                                       Language ... restrictions)
  {
    Bid4WinSet<I18nGroup> result = new Bid4WinSet<I18nGroup>(groupSet.size());
    Bid4WinSet<Language> restrictionSet = new Bid4WinSet<Language>(restrictions);
    for(I18nGroup group : groupSet)
    {
      if(!UtilI18n.isFiltered(group, searchString, restrictionSet))
      {
        result.add(group);
      }
    }
      return result;
  }
}
