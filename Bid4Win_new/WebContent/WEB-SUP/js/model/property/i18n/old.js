/**
 * Namespace I18nManager.
 *
 * @author Maxime Ollagnier
 */
I18nManager = function()
{
};

/** Favorite language cookie name */
I18nManager.FAVORITE_LANGUAGE_COOKIE_NAME = 'I18N';

/** Supported languages property name */
I18nManager.SUPPORTED_LANGUAGES_PROPERTY_NAME = 'i18n.supported_languages';

/** Default language property name */
I18nManager.DEFAULT_LANGUAGE_PROPERTY_NAME = 'i18n.default_language';

/** Current language */
I18nManager.language = undefined;

/**
 * Initializes the internationalization properties
 */
I18nManager.init = function()
{
    I18nManager.language = $.cookie(I18nManager.FAVORITE_LANGUAGE_COOKIE_NAME);
    if (I18nManager.language == null) 
    {
        if (navigator.browserLanguage) 
            I18nManager.language = navigator.browserLanguage;
        else 
            I18nManager.language = navigator.language;
    }
    if (PropertyManager.getInstance().getString(I18nManager.SUPPORTED_LANGUAGES_PROPERTY_NAME).indexOf(I18nManager.language) < 0) 
    {
        I18nManager.language = PropertyManager.getInstance().getString(I18nManager.DEFAULT_LANGUAGE_PROPERTY_NAME);
    }
};

/**
 * Fetches the correct translation
 */
I18nManager.get = function(key)
{
    try 
    {
        return PropertyManager.getInstance().getString(I18nManager.getPropertyKey(key));
    }
    catch(errorMessage)
    {
        Logger.warn('I18N key "' + I18nManager.getPropertyKey(key) + '" does not exists.');
        return I18nManager.getPropertyKey(key);
    }
};

/**
 * Returns the property key i18n key
 */
I18nManager.getPropertyKey = function(key)
{
    return 'i18n.' + I18nManager.language + '.' + key;
};
