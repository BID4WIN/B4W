package com.bid4win.commons.core.renderer;

import java.util.Collection;
import java.util.Iterator;

import com.bid4win.commons.core.UtilString;
import com.bid4win.commons.core.exception.Bid4WinRuntimeException;

/**
 * Cette classe d�fini la prise en charge de base de la transformation d'une collection
 * d'objets d'un type donn� en cha�ne de caract�res<BR>
 * <BR>
 * @param <OBJECT> D�finition du type des objets de la collection � transformer
 * en cha�ne de caract�res<BR>
 * @param <RENDERER> D�finition de la classe en charge du rendu des objets de la
 * collection<BR>
 * <BR>
 * @author Emeric Fill�tre
 */
public class Bid4WinCollectionRenderer<OBJECT, RENDERER extends Bid4WinRenderer<OBJECT>>
       extends Bid4WinRenderer<Collection<? extends OBJECT>>
{
  /** C'est l'instance utilis�e comme singleton */
  private final static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
      instance = new Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>(
          Bid4WinRenderer.getInstance());
  /**
   * D�fini la m�me m�thode que la classe parente en la d�pr�ciant afin de pr�ciser
   * qu'on doit explicitement appeler l'autre m�thode de classe
   * @return Cette m�thode ne retournera rien
   * @throws Bid4WinRuntimeException Exception lanc�e car la m�thode est d�pr�ci�e
   * @see com.bid4win.commons.core.renderer.Bid4WinRenderer#getInstance()
   * @deprecated M�thode d�pr�ci�e afin de cacher la m�thode de la classe parent
   */
  @Deprecated
  public static Bid4WinRenderer<Object> getInstance() throws Bid4WinRuntimeException
  {
    throw new Bid4WinRuntimeException("Deprecated");
  }
  /**
   * Le renderer est un singleton. On passe donc par cette m�thode pour r�cup�rer
   * l'unique objet en m�moire
   * @return L'instance du renderer
   */
  public static Bid4WinCollectionRenderer<Object, Bid4WinRenderer<Object>>
         getInstanceCollection()
  {
    return Bid4WinCollectionRenderer.instance;
  }

  /** Classe de rendu par d�faut des objets de la collection */
  private RENDERER objectRenderer = null;

  /**
   * Constructeur
   * @param objectRenderer Classe de rendu par d�faut des objets de la collection
   */
  public Bid4WinCollectionRenderer(RENDERER objectRenderer)
  {
    this.objectRenderer = objectRenderer;
  }

  /**
   * Red�fini la m�thode afin de transformer la collection d'objets en argument
   * en cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu par d�faut pour chaque objet de la collection en les affichant bout
   * � bout sur la m�me ligne en se basant sur le retour de la m�thode render(TYPE)
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
   * Red�fini la m�thode afin de transformer la collection d'objets en argument
   * en cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu par d�faut pour chaque objet de la collection en les affichant bout
   * � bout sur la m�me ligne en se basant sur le retour de la m�thode render(TYPE, RENDERER)
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
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu en param�tre pour chaque objet de la collection en les affichant
   * bout � bout sur la m�me ligne
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param renderer Classe de rendu � utiliser pour chaque objet de la collection
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public StringBuffer render(Collection<? extends OBJECT> toRender, RENDERER renderer)
  {
    return this.renderUniqueLine(toRender, renderer);
  }

  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu par d�faut pour chaque objet de la collection en les affichant bout
   * � bout sur la m�me ligne
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender)
  {
    return this.renderUniqueLine(toRender, this.getObjectRenderer());
  }
  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu en param�tre pour chaque objet de la collection en les affichant
   * bout � bout sur la m�me ligne
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param renderer Classe de rendu � utiliser pour chaque objet de la collection
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                             RENDERER renderer)
  {
    return this.renderUniqueLine(toRender, this.getObjectSeparator(), renderer);
  }

  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu par d�faut pour chaque objet de la collection en les affichant un
   * par ligne
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public final StringBuffer renderMultipleLine(Collection<? extends OBJECT> toRender)
  {
    return this.renderMultipleLine(toRender, this.getObjectRenderer());
  }
  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu en param�tre pour chaque objet de la collection en les affichant
   * un par ligne
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param renderer Classe de rendu � utiliser pour chaque objet de la collection
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public StringBuffer renderMultipleLine(Collection<? extends OBJECT> toRender,
                                         RENDERER renderer)
  {
    // G�re la possible nullit� de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entr�es pour les afficher une par une
    UtilString.addParagraph(buffer,
                            this.render(toRender,
                                        UtilString.SEPARATOR_NEW_LINE,
                                        renderer),
                            1);
    return UtilString.addParagraph(buffer, "]");
  }

  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu par d�faut pour chaque objet de la collection en les affichant bout
   * � bout sur la m�me ligne, s�par�s par la cha�ne de caract�res sp�cifi�e
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param objectSeparator Cha�ne de caract�res � utiliser pour s�parer chaque
   * objet
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public final StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                             String objectSeparator)
  {
    return this.renderUniqueLine(toRender, objectSeparator, this.getObjectRenderer());
  }
  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu en param�tre pour chaque objet de la collection en les affichant
   * bout � bout sur la m�me ligne, s�par�s par la cha�ne de caract�res sp�cifi�e
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param objectSeparator Cha�ne de caract�res � utiliser pour s�parer chaque
   * objet
   * @param renderer Classe de rendu � utiliser pour chaque objet de la collection
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  public StringBuffer renderUniqueLine(Collection<? extends OBJECT> toRender,
                                       String objectSeparator, RENDERER renderer)
  {
    // G�re la possible nullit� de la collection
    if(toRender == null)
    {
      return new StringBuffer("NULL");
    }
    // Effectue le rendu de la collection
    StringBuffer buffer = new StringBuffer("[");
    // Parse toutes les entr�es pour les afficher ensemble
    buffer.append(this.render(toRender, objectSeparator, renderer));
    return buffer.append("]");
  }

  /**
   * Cette m�thode permet de transformer la collection d'objets en argument en
   * cha�ne de caract�res la repr�sentant lisiblement. Elle utilisera la classe
   * de rendu en param�tre pour chaque objet de la collection en les affichant bout
   * � bout sur la m�me ligne, s�par�s par la cha�ne de caract�res sp�cifi�e
   * @param toRender Collection d'objets � transformer en cha�ne de caract�res
   * @param separator Cha�ne de caract�res � utiliser pour s�parer chaque objet
   * @param renderer Classe de rendu � utiliser pour chaque objet de la collection
   * @return La cha�ne de caract�res repr�sentant la collection d'objets en argument
   */
  private StringBuffer render(Collection<? extends OBJECT> toRender,
                              String separator, RENDERER renderer)
  {
    StringBuffer buffer = new StringBuffer();
    // Parse toutes les entr�es pour les afficher ensemble
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
   * Cette m�thode permet de d�finir le s�parateur � utiliser entre les �l�ments
   * de la collection dont il faut construire le rendu
   * @return Le s�parateur � utiliser entre les �l�ments de la collection dont il
   * faut construire le rendu
   */
  public String getObjectSeparator()
  {
    return "|";
  }
  /**
   * Getter de la classe de rendu par d�faut des objets de la collection
   * @return La classe de rendu par d�faut des objets de la collection
   */
  public RENDERER getObjectRenderer()
  {
    return this.objectRenderer;
  }
}
