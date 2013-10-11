/**
 * class I18nManager.
 * 
 * @author Maxime Ollagnier
 */
I18nManager.inherits(PropertyManager);
function I18nManager()
{
    this.objectName = 'I18n';
    this.objectType = Property;
    this.map = new PropertyMap();
}

/**
 * Initializes the internationalization properties
 */
I18nManager.prototype.init = function(callback)
{
    this.getObjectMap(
    {
        cache : true
    }, callback);
};

/**
 * Fetches the correct translation
 */
I18nManager.prototype.get = function(key)
{
    var res = this.map.get(key);
    if (Util.checkObject(res))
    {
        return res.value;
    } else
    {
        Logger.warn('I18N key "' + key + '" does not exists.');
        return key;
    }
};

/**
 * Fetches the correct translation
 */
I18nManager.get = function(key)
{
    return I18nManager.getInstance().get(key);
};

/**
 * Fetches the correct translation
 */
I18nManager.prototype.addLanguage = function(lg, callback)
{
    AjaxManager.getJSON('AddLanguageAction',
    {
        language : lg
    }, function(result)
    {
        if (!result.success)
        {
            result.addWarn('Error adding language ' + lg + '.');
        }
        callback(result);
    });
};
