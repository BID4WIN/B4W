package com.bid4win.communication.json.factory;

import org.json.JSONObject;

/**
 * Singleton Factory class.
 * Handles the generation of errors through JSON
 * 
 * @author Maxime Ollagnier
 */
public class JSONErrorFactory
{
  /** Unique instance used as singleton */
  private static JSONErrorFactory instance;
  static
  {
    JSONErrorFactory.instance = new JSONErrorFactory();
  }

  /**
   * Getter of the unique instance in memory
   * @return The unique instance in memory
   */
  public static JSONErrorFactory getInstance()
  {
    return JSONErrorFactory.instance;
  }

  /**
   * Protected constructor
   */
  protected JSONErrorFactory()
  {
    super();
  }

  /**
   * Returns an error JSON object generated from the given exception
   * @param ex The exception to generate the JSON object from
   * @return The generated JSON object
   */
  public JSONObject getJSONError(Exception ex)
  {
    JSONObject jsonError = new JSONObject();
    jsonError.put("error", ex.getMessage());

    return jsonError;
  }

  /**
   * Returns an error JSON object generated from the given message
   * @param errorMsg The error message to generate the JSON object from
   * @return The generated JSON object
   */
  public JSONObject getJSONError(String errorMsg)
  {
    JSONObject jsonError = new JSONObject();
    jsonError.put("error", errorMsg);

    return jsonError;
  }
}
