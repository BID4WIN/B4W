package com.bid4win.commons.core.renderer;

import java.util.Collection;
import java.util.Iterator;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe défini la prise en charge de base de la transformation d'une collection
 * d'objets d'un type donné en chaîne de caractères<BR>
 * <BR>
 * @param <OBJECT> Définition du type des objets de la collection à transformer
 * en chaîne de caractères<BR>
 * @param <RENDERER> Définition de la classe en charge du rendu des objets de la
 * collection<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class Bid4WinCollectionRenderer<OBJECT, RENDERER extends Bid4WinRenderer<OBJECT>>
       extends Bid4WinRenderer<Collection<? extends OBJECT>>
{
  /** C'est l'instance utilisée comme singleton */
  private final static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
      instance = new Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>(
          Bid4WinRenderer.getInstance());
  /**
   * Défini la même méthode que la classe parente en la dépréciant afin de préciser
   * qu'on doit explicitement appeler l'autre méthode de classe
   * @return Cette méthode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lancée car la méthode est dépréciée
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#getInstance()
   * @deprecated Méthode dépréciée afin de cacher la méthode de la classe parent
   */
  @Deprecated
  public static Bid4WinRenderer<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette méthode pour récupérer
   * l'unique objet en mémoire
   * @return L'instance du renderer
   */
  public static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
         getInstanceCollection()
  {
    return Bid4WinCollectionRenderer.instance;
  }

  /** Classe de rendu par défaut des objets de la collection */
  private RENDERER objectRenderer = null;

  /**
   * Constructeur
   * @param objectRenderer Classe de rendu par défaut des objets de la collection
   */
  public Bid4WinCollectionRenderer(RENDERER objectRenderer)
  {
    this.objectRenderer = objectRenderer;
  }

  /**
   * Redéfini la méthode afin de transformer la collection d'objets en argument
   * en chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu par défaut pour chaque objet de la collection en les affichant bout
   * à bout sur la même ligne en se basant sur le retour de la méthode render(TYPE)
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#toString(java.lang.Object)
   */
  @Override
  public final String toString(Collection<? extends OBJECT> toRender)
  {
    return this.render(toRender).toString();
  }
  /**
   * Redéfini la méthode afin de transformer la collection d'objets en argument
   * en chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu par défaut pour chaque objet de la collection en les affichant bout
   * à bout sur la même ligne en se basant sur le retour de la méthode render(TYPE, RENDERER)
   * @param toRender {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#render(java.lang.Object)
   */
  @Override
  public final StringBuffer render(Collection<? extends OBJECT> toRender)
  {
    return this.render(toRender, this.getObjectRenderer());
  }

  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu en paramètre pour chaque objet de la collection en les affichant
   * bout à bout sur la même ligne
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param renderer Classe de rendu à utiliser pour chaque objet de la collection
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public StringBuffer render(Collection<? extends OBJECT> toRender, RENDERER renderer)
  {
    return this.renderUniqueLine(toRender, renderer);
  }

  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu par défaut pour chaque objet de la collection en les affichant bout
   * à bout sur la même ligne
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender)
  {
    return this.renderUniqueLine(toRender, this.getObjectRenderer());
  }
  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu en paramètre pour chaque objet de la collection en les affichant
   * bout à bout sur la même ligne
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param renderer Classe de rendu à utiliser pour chaque objet de la collection
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                             RENDERER renderer)
  {
    return this.renderUniqueLine(toRender, this.getObjectSeparator(), renderer);
  }

  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu par défaut pour chaque objet de la collection en les affichant un
   * par ligne
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public final StringBuffer renderMultipleLine(Collection<? extends OBJECT> toRender)
  {
    return this.renderMultipleLine(toRender, this.getObjectRenderer());
  }
  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu en paramètre pour chaque objet de la collection en les affichant
   * un par ligne
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param renderer Classe de rendu à utiliser pour chaque objet de la collection
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public StringBuffer renderMultipleLine(Collection<? extends OBJECT> toRender,
                                         RENDERER renderer)
  {
    // Gère la possible nullité de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entrées pour les afficher une par une
    UtilString.addParagraph(buffer,
                            this.render(toRender,
                                        UtilString.SEPARATOR_NEW_LINE,
                                        renderer),
                            1);
    return UtilString.addParagraph(buffer, "]");
  }

  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu par défaut pour chaque objet de la collection en les affichant bout
   * à bout sur la même ligne, séparés par la chaîne de caractères spécifiée
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param objectSeparator Chaîne de caractères à utiliser pour séparer chaque
   * objet
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                             String objectSeparator)
  {
    return this.renderUniqueLine(toRender, objectSeparator, this.getObjectRenderer());
  }
  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu en paramètre pour chaque objet de la collection en les affichant
   * bout à bout sur la même ligne, séparés par la chaîne de caractères spécifiée
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param objectSeparator Chaîne de caractères à utiliser pour séparer chaque
   * objet
   * @param renderer Classe de rendu à utiliser pour chaque objet de la collection
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  public StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                       String objectSeparator, RENDERER renderer)
  {
    // Gère la possible nullité de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entrées pour les afficher ensemble
    buffer.append(this.render(toRender, objectSeparator, renderer));
    return buffer.append("]");
  }

  /**
   * Cette méthode permet de transformer la collection d'objets en argument en
   * chaîne de caractères la représentant lisiblement. Elle utilisera la classe
   * de rendu en paramètre pour chaque objet de la collection en les affichant bout
   * à bout sur la même ligne, séparés par la chaîne de caractères spécifiée
   * @param toRender Collection d'objets à transformer en chaîne de caractères
   * @param separator Chaîne de caractères à utiliser pour séparer chaque objet
   * @param renderer Classe de rendu à utiliser pour chaque objet de la collection
   * @return La chaîne de caractères représentant la collection d'objets en argument
   */
  private StringBuffer render(Collection<? extends OBJECT> toRender,
                              String separator, RENDERER renderer)
  {
    StringBuffer buffer = new StringBuffer();
    // Parse toutes les entrées pour les afficher ensemble
    for(Iterator<? extends OBJECT> iterator = toRender.iterator() ; iterator.hasNext() ;)
    {
      buffer.append(renderer.render(iterator.next()));
      if(iterator.hasNext())
      {
        buffer.append(separator);
      }
    }
    return buffer;
  }

  /**
   * Cette méthode permet de définir le séparateur à utiliser entre les éléments
   * de la collection dont il faut construire le rendu
   * @return Le séparateur à utiliser entre les éléments de la collection dont il
   * faut construire le rendu
   */
  public String getObjectSeparator()
  {
    return "|";
  }
  /**
   * Getter de la classe de rendu par défaut des objets de la collection
   * @return La classe de rendu par défaut des objets de la collection
   */
  public RENDERER getObjectRenderer()
  {
    return this.objectRenderer;
  }
}
