package com.bid4win.tools.console;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.ModelArgumentException;

/**
 * Cette classe est l'item de menu qui permet d'en afficher l'integralite<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ItemMain extends Item<Menu<?>>
{
  /**
   * Constructeur
   * @param menu Menu dans lequel l'item est initialisé
   */
  public ItemMain(Menu<?> menu)
  {
    super("Back to previous menu", menu);
  }

  /**
   * L'execution de cet item affiche l'intégralité du menu
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute() throws ModelArgumentException
  {
    System.out.println("");
    System.out.println("********************************************************************");
    System.out.println("*** " + this.getMenu().getWording());
    System.out.println("********************************************************************");
    int itemNb = this.getMenu().getItemNb();
    int nbChar = String.valueOf(itemNb - 1).length();
    // On affiche tous les items sauf le premier qui correspond à l'action 'quitter'
    // et le dernier qui est l'affichage du menu
    for (int i = 1 ; i < itemNb - 1 ; i++)
    {
      Item<?> item = this.getMenu().getItem(i);
      String id = String.valueOf(i);
      System.out.println(UtilString.SEPARATOR_INDENTATION + "(" + id + ")" +
                         UtilString.createRepeatedString(" ", (nbChar - id.length())) +
                         UtilString.SEPARATOR_INDENTATION + item.getWording());
    }
    // Le premier item du menu est toujours sensé être 'exit' avec la valeur 0
    // et on l'affiche à la fin du menu
    System.out.println("");
    System.out.println(UtilString.SEPARATOR_INDENTATION + "(0)" +
                       UtilString.createRepeatedString(" ", (nbChar - 1)) +
                       UtilString.SEPARATOR_INDENTATION + this.getMenu().getItem(0).getWording());
    System.out.println("----------------------------------------------------");
    System.out.println("| Please choose between 0 and " + (itemNb - 2) + " then hit ENTER");
    System.out.println("----------------------------------------------------");
    return false;
  }
}
