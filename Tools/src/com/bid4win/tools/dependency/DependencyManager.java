package com.bid4win.tools.dependency;

import com.bid4win.tools.console.Menu;
import com.bid4win.tools.dependency.generator.EclipseProjectWebGenerator;
import com.bid4win.tools.dependency.inspector.EclipseProjectWebInspector;

/**
 * Cette classe défini le menu de gestion des dépendances entre projets Eclipse<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class DependencyManager extends Menu<Menu<?>>
{
  /**
   * Permet de lancer directement le menu
   * @param args Arguments pour un lancement en ligne de commande
   */
  public static void main(String[] args)
  {
    DependencyManager manager = new DependencyManager();
    manager.start(args);
  }
  
  /**
   * Constructeur d'un menu principal
   */
  public DependencyManager()
  {
    super();
  }
  /**
   * Constructeur d'un sous menu
   * @param menu Menu dans lequel l'item est initialisé
   */
  public DependencyManager(Menu<?> menu)
  {
    super("Project classpath management", menu);
  }

  /**
   * Ajoute les items de gestion des dépendances entre projets Eclipse
   * @see com.bid4win.tools.console.Menu#addSpecificItems()
   */
  @Override
  protected void addSpecificItems()
  {
    this.addItem(new EclipseProjectWebGenerator(this));
    this.addItem(new EclipseProjectWebInspector(this));
  }
}
