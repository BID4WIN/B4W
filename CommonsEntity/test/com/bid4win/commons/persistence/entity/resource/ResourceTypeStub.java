package com.bid4win.commons.persistence.entity.resource;

/**
 *
 * TODO A COMMENTER<BR>
 * <BR>
 * @author Emeric Fillâtre
 */
public class ResourceTypeStub extends ResourceType<ResourceTypeStub>
{
  /** Determine si un objet dé-sérialisé est compatible avec cette classe. La
   * valeur doit être modifiée si une évolution de la classe la rend incompatible
   * avec les versions précédentes */
  private static final long serialVersionUID = -3754325110646208019L;

  /** TODO A COMMENTER */
  public final static ResourceTypeStub TYPE1 = new ResourceTypeStub("TYPE1", "typ1", "path", "type_1");
  /** TODO A COMMENTER */
  public final static ResourceTypeStub TYPE2 = new ResourceTypeStub("TYPE2", "typ2", "path", "type_2");

  /**
   * Constructeur d'un type de resource
   * @param code Code du type de resource
   * @param extension Extension liée au type de ressource
   * @param pathPrefixes Potentiels classement spécifiques du type de ressource
   */
  protected ResourceTypeStub(String code, String extension, String ... pathPrefixes)
  {
    super(code, extension, pathPrefixes);
  }

  /**
   * Constructeur d'un type de resource
   * @param code Code du type de resource
   * @param extension Extension liée au type de ressource
   */
  protected ResourceTypeStub(String code, String extension)
  {
    super(code, extension);
  }
}
