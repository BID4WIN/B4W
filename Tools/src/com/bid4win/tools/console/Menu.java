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
 * @param <MENU> Définition du menu auquel appartient le menu<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public abstract class Menu<MENU extends Menu<?>> extends Item<MENU>
{
  /** Liste des différents item contenus dans le menu */
  private Bid4WinList<Item<?>> itemList = new Bid4WinList<Item<?>>();
  /** Flag indiquant si l'utilisation du menu est terminée */
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
   * @param wording Libellé correspondant au menu
   */
  protected Menu(String wording)
  {
    this(wording, null);
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel le menu est initialisé
   */
  protected Menu(MENU menu)
  {
    this("Main menu", menu);
  }
  /**
   * Constructeur d'un sous menu
   * @param wording Libellé correspondant au menu
   * @param menu Menu dans lequel le menu est initialisé
   */
  protected Menu(String wording, MENU menu)
  {
    super(wording, menu);
    // Montage du menu
    this.loadMenu();
  }

  /**
   * Cette fonction crée le menu
   */
  protected void loadMenu()
  {
    // On ajoute obligatoirement l'item d'arrêt
    this.addItemExit();
    // Ajoute les items spécifiques
    this.addSpecificItems();
    // On ajoute obligatoirement l'item principal
    this.addItemMain();
  }

  /**
   * Cette méthode crée et ajoute l'item de sortie au menu
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
   * Cette méthode crée et ajoute l'item principal au menu
   */
  protected void addItemMain()
  {
    this.addItem(new ItemMain(this));
  }

  /**
   * Cette méthode est à surcharger pour définir les items spécifiques et les ajouter
   * au menu
   */
  protected void addSpecificItems()
  {
    // A définir dans les sous classes
  }

  /**
   * Cette fonction traque les saisies claviers du menu et execute l'item correspondant.
   * Suivant les arguments renseignés, on peut aussi lancer directement un process
   * @param args Arguments passés en ligne de commande au menu
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
   * Cette méthode permet d'executer le menu, c'est à dire de l'afficher tant que
   * sa fin n'est pas demandée et de gérer les choix de ses différents items
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
        // Récupération du choix du client
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
   * Cette fonction execute l'item correspondant au choix en paramètre
   * @param selection Index du choix dans le menu
   * @param standalone Indique si le client est lance en ligne
   * @return True si le menu doit attendre avant d'être ré-affiché, false sinon
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
      // On récupère l'item à executer et on affiche l'action correspondante
      Item<?> item = this.getItem(selection);
      if(!(standalone || item instanceof ItemMain))
      {
        System.out.println("(" + selection + ") " + item.getWording());
      }
      // On execute l'action correspondant à l'item sélectionné
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
   * Permet de récupérer le nombre d'item du menu
   * @return Le nombre d'item du menu
   */
  public int getItemNb()
  {
    return this.itemList.size();
  }
  /**
   * Permet de récupérer un item grace à son index dans le menu
   * @param index Index de l'item à récupérer
   * @return l'item trouvé dans le menu à l'index indiqué
   * @throws ModelArgumentException Si l'item ne peut être trouvé
   */
  public Item<?> getItem(int index) throws ModelArgumentException
  {
    UtilNumber.checkMaxValue("index", index, this.getItemNb(), false);
    return this.itemList.get(index);
  }
  /**
   * Permet d'ajouter un item à la fin du menu
   * @param item Item à ajouter à la fin du menu
   */
  protected void addItem(Item<?> item)
  {
    this.itemList.add(item);
  }
  /**
   * Permet d'ajouter un item à un index spécifique
   * @param item Item à ajouter
   * @param index Position de l'item à ajouter
   */
  protected void addItem(Item<?> item, int index)
  {
    this.itemList.add(index, item);
  }

  /**
   * Getter du flag indiquant si l'utilisation du menu est terminée
   * @return Le flag indiquant si l'utilisation du menu est terminée
   */
  public boolean isStopped()
  {
    return this.stopped;
  }
  /**
   * Setter du flag indiquant si l'utilisation du menu est terminée
   * @param stopped Flag indiquant si l'utilisation du menu est terminée
   */
  protected void setStopped(boolean stopped)
  {
    this.stopped = stopped;
  }

  /**
   * Cette méthode permet de savoir si le menu est considéré comme menu principal
   * @return True si le menu est considéré comme menu principal, false sinon
   */
  public boolean isMainMenu()
  {
    return this.getMenu() == null;
  }
}
