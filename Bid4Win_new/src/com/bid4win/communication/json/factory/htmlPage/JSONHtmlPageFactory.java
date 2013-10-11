package com.bid4win.communication.json.factory.htmlPage;

import org.json.JSONObject;

import com.bid4win.communication.json.factory.JSONFactory;
import com.bid4win.persistence.entity.locale.Language;
import com.bid4win.persistence.entity.locale.html.HtmlPageStorage;

/**
 * Fabrique de structures JSON<BR>
 * <BR>
 *
 * @author Maxime Ollagnier
 */
public class JSONHtmlPageFactory extends JSONFactory<HtmlPageStorage>
{
  /** Nom des attributs dans la structure JSON */
  public final static String NAME = "name";
  public final static String LANGUAGES = "languages";

  /** C'est l'instance utilis�e comme singleton */
  private final static JSONHtmlPageFactory instance = new JSONHtmlPageFactory();

  /**
   * @return L'unique instance de la fabrique
   */
  public static JSONHtmlPageFactory getInstance()
  {
    return JSONHtmlPageFactory.instance;
  }

  /**
   * Constructeur prot�g� car la fabrique doit �tre acc�d�e comme un singleton
   */
  protected JSONHtmlPageFactory()
  {
    super();
  }

  /**
   * Remplit la structure JSON avec les donn�es de l'objet en param�tre
   *
   * @param json Structure JSON � remplir � partir des donn�es de l'objet
   * @param object Objet avec lequel remplir la structure JSON en param�tre
   */
  @Override
  public void fill(JSONObject json, HtmlPageStorage object)
  {
    json.put(JSONFactory.ID, this.getId(object));
    json.put(JSONHtmlPageFactory.NAME, object.getName());
    JSONObject partTypesJSON = new JSONObject();
    for(Language language : object.getLanguageSet())
    {
      partTypesJSON.put(language.getName(), language.getName());
    }
    json.put(JSONHtmlPageFactory.LANGUAGES, partTypesJSON);
  }

  /**
   * @param object {@inheritDoc}
   * @return {@inheritDoc}
   * @see com.bid4win.communication.json.factory.JSONFactory#getId(Object)
   */
  @Override
  public String getId(HtmlPageStorage object)
  {
    return String.valueOf(object.getId());
  }
}
