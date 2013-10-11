package com.bid4win.tools.console;

import com.bid4win.commons.core.UtilNumber;
import com.bid4win.commons.core.collection.Bid4WinList;
import com.bid4win.commons.core.exception.Bid4WinException;
import com.bid4win.commons.core.exception.ModelArgumentException;
import com.bid4win.commons.core.exception.TechnicalException;
import com.bid4win.commons.core.io.KeyboardSelection;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @param <MENU> D�finition du menu auquel appartient le menu<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public abstract class Menu<MENU extends Menu<?>> extends Item<MENU>
{
  /** Liste des diff�rents item contenus dans le menu */
  private Bid4WinList<Item<?>> itemList = new Bid4WinList<Item<?>>();
  /** Flag indiquant si l'utilisation du menu est termin�e */
  private boolean stopped = false;

  /**
   * Constructeur d'un menu principal
   */
  protected Menu()
  {
    this((MENU)null);
  }
  /**
   * Constructeur d'un menu principal
   * @param wording Libell� correspondant au menu
   */
  protected Menu(String wording)
  {
    this(wording, null);
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel le menu est initialis�
   */
  protected Menu(MENU menu)
  {
    this("Main menu", menu);
  }
  /**
   * Constructeur d'un sous menu
   * @param wording Libell� correspondant au menu
   * @param menu Menu dans lequel le menu est initialis�
   */
  protected Menu(String wording, MENU menu)
  {
    super(wording, menu);
    // Montage du menu
    this.loadMenu();
  }

  /**
   * Cette fonction cr�e le menu
   */
  protected void loadMenu()
  {
    // On ajoute obligatoirement l'item d'arr�t
    this.addItemExit();
    // Ajoute les items sp�cifiques
    this.addSpecificItems();
    // On ajoute obligatoirement l'item principal
    this.addItemMain();
  }

  /**
   * Cette m�thode cr�e et ajoute l'item de sortie au menu
   */
  protected void addItemExit()
  {
    if(this.getMenu() == null)
    {
      this.addItem(new ItemExit(this));
    }
    else
    {
      this.addItem(new ItemPrevious(this));
    }
  }
  /**
   * Cette m�thode cr�e et ajoute l'item principal au menu
   */
  protected void addItemMain()
  {
    this.addItem(new ItemMain(this));
  }

  /**
   * Cette m�thode est � surcharger pour d�finir les items sp�cifiques et les ajouter
   * au menu
   */
  protected void addSpecificItems()
  {
    // A d�finir dans les sous classes
  }

  /**
   * Cette fonction traque les saisies claviers du menu et execute l'item correspondant.
   * Suivant les arguments renseign�s, on peut aussi lancer directement un process
   * @param args Arguments pass�s en ligne de commande au menu
   */
  public void start(String[] args)
  {
    // TODO A COMMENTER
    this.applyArgs(args);
    this.execute();
  }
  /**
   *
   * TODO A COMMENTER
   * @param args TODO A COMMENTER
   */
  protected void applyArgs(String[] args)
  {
    if(args.length > 0)
    {
      System.out.println("Unknown arguments: " + new Bid4WinList<String>(args));
    }
  }

  /**
   * Cette m�thode permet d'executer le menu, c'est � dire de l'afficher tant que
   * sa fin n'est pas demand�e et de g�rer les choix de ses diff�rents items
   * @return {@inheritDoc}
   * @see com.bid4win.tools.console.Item#execute()
   */
  @Override
  public boolean execute()
  {
    this.setStopped(false);
    // Gestion du menu
    while(!this.isStopped())
    {
      // Execute l'affichage du menu
      this.displayMenu();
      try
      {
        // R�cup�ration du choix du client
        int selection = KeyboardSelection.getInstance().parseIntBounds(0, this.getItemNb() - 1);
        // Execution du choix du client
        if(this.execute(selection, true))
        {
          System.out.println("Hit ENTER to go back to menu");
          KeyboardSelection.getInstance().parseString();
        }
      }
      catch(ModelArgumentException ex)
      {
        System.out.println("Wrong choice : " + ex.getMessage());
      }
      catch(TechnicalException ex)
      {
        System.out.println("Wrong choice : " + ex.getMessage());
      }
    }
    return false;
  }

  /**
   *
   * TODO A COMMENTER
   */
  protected void displayMenu()
  {
    this.execute(-1, true);
  }

  /**
   * Cette fonction execute l'item correspondant au choix en param�tre
   * @param selection Index du choix dans le menu
   * @param standalone Indique si le client est lance en ligne
   * @return True si le menu doit attendre avant d'�tre r�-affich�, false sinon
   */
  private boolean execute(int selection, boolean standalone)
  {
    try
    {
      // Le choix -1 correspond a l'item d'affichage du menu principal
      if(selection == -1)
      {
        selection = this.getItemNb() - 1;
      }
      // On r�cup�re l'item � executer et on affiche l'action correspondante
      Item<?> item = this.getItem(selection);
      if(!(standalone || item instanceof ItemMain))
      {
        System.out.println("(" + selection + ") " + item.getWording());
      }
      // On execute l'action correspondant � l'item s�lectionn�
      return item.execute();
    }
    catch(Bid4WinException ex)
    {
      System.out.println("Le probleme suivant s'est produit : " + ex.getMessage());
      return true;
    }
  }

  /**
   * Cette fonction permet de quitter le menu
   */
  protected void exit()
  {
    this.execute(0, true);
  }

  /**
   * Permet de r�cup�rer le nombre d'item du menu
   * @return Le nombre d'item du menu
   */
  public int getItemNb()
  {
    return this.itemList.size();
  }
  /**
   * Permet de r�cup�rer un item grace � son index dans le menu
   * @param index Index de l'item � r�cup�rer
   * @return l'item trouv� dans le menu � l'index indiqu�
   * @throws ModelArgumentException Si l'item ne peut �tre trouv�
   */
  public Item<?> getItem(int index) throws ModelArgumentException
  {
    UtilNumber.checkMaxValue("index", index, this.getItemNb(), false);
    return this.itemList.get(index);
  }
  /**
   * Permet d'ajouter un item � la fin du menu
   * @param item Item � ajouter � la fin du menu
   */
  protected void addItem(Item<?> item)
  {
    this.itemList.add(item);
  }
  /**
   * Permet d'ajouter un item � un index sp�cifique
   * @param item Item � ajouter
   * @param index Position de l'item � ajouter
   */
  protected void addItem(Item<?> item, int index)
  {
    this.itemList.add(index, item);
  }

  /**
   * Getter du flag indiquant si l'utilisation du menu est termin�e
   * @return Le flag indiquant si l'utilisation du menu est termin�e
   */
  public boolean isStopped()
  {
    return this.stopped;
  }
  /**
   * Setter du flag indiquant si l'utilisation du menu est termin�e
   * @param stopped Flag indiquant si l'utilisation du menu est termin�e
   */
  protected void setStopped(boolean stopped)
  {
    this.stopped = stopped;
  }

  /**
   * Cette m�thode permet de savoir si le menu est consid�r� comme menu principal
   * @return True si le menu est consid�r� comme menu principal, false sinon
   */
  public boolean isMainMenu()
  {
    return this.getMenu() == null;
  }
}
