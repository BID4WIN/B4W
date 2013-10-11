/**
 * Class CurrencyManager
 * 
 * @author Maxime Ollagnier
 */
function CurrencyManager()
{
};

/** Currencies */
CurrencyManager.map = new Map();
CurrencyManager.map.put("EURO", "EURO");
CurrencyManager.map.put("US DOLLAR", "US DOLLAR");
CurrencyManager.map.put("UK POUND", "UK POUND");

/** Default currency */
CurrencyManager.DEFAULT = "EURO";

/**
 * Returns the jQuery select element
 */
CurrencyManager.getSelectJQ = function(selectedCode)
{
    var jQ = $('<select class="currency"></select>');
    CurrencyManager.map.foreach(function(code, value)
    {
        jQ.append($('<option value="' + code + '"' + ((code == selectedCode) ? ' selected' : '') + '>' + value + '</option>'));
    });
    return jQ;
};