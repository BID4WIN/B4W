/**
 * Class LanguageManager
 * 
 * @author Maxime Ollagnier
 */
function LanguageManager()
{
};

/** Languages */
LanguageManager.map = new Map();
LanguageManager.map.put("en", "English");
LanguageManager.map.put("fr", "Francais");
LanguageManager.map.put("it", "Italiano");
LanguageManager.map.put("es", "Espingoing");

/** Default language */
LanguageManager.DEFAULT = "English";

/**
 * Returns the jQuery select element
 */
LanguageManager.getSelectJQ = function(selectedCode)
{
    var jQ = $('<select class="language"></select>');
    LanguageManager.map.foreach(function(code, value)
    {
        jQ.append($('<option value="' + code + '"' + ((code == selectedCode) ? ' selected' : '') + '>' + value + '</option>'));
    });
    return jQ;
};