/**
 * String.js
 *
 * Extends the String object.
 *
 * @author Maxime Ollagnier
 */
/** Id separator */
String.ID_SEPARATOR = "b4w";
String.ID_PREFIX = "id_";

/**
 * Returns the given string with its first letter in upper case.
 */
String.prototype.toFirstUpperCase = function()
{
    var str = this.toString();
    if (str != '') 
    {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    return str;
};

/**
 * Returns the string replacing the Id separator by dots
 */
String.prototype.id2Str = function(separator)
{
    if(!Util.checkString(separator))
    {
        separator = '.';
    }
    return this.toString().replace(new RegExp(String.ID_SEPARATOR, 'g'), separator).substring(String.ID_PREFIX.length);
};

/**
 * Returns the string replacing dots by the Id separator
 */
String.prototype.str2Id = function(separator)
{
    if(!Util.checkString(separator))
    {
        separator = '\\.';
    }
    return String.ID_PREFIX + this.toString().replace(new RegExp(separator, 'g'), String.ID_SEPARATOR);
};

/**
 * Returns this string embedding the given searchString with the given tag and with the given class
 */
String.prototype.showStr = function(searchString, tag, classe)
{
    var string = this.toString();
    if (typeof searchString == 'string' && searchString != '' && string != '') 
    {
        if (typeof tag != 'string' || tag == '') 
        {
            tag = 'b';
        }
        if (typeof classe != 'string') 
        {
            classe = '';
        }
        searchString = searchString.toLowerCase();
        var startOf = string.toLowerCase().indexOf(searchString);
        if (startOf >= 0) 
        {
            var endOf = startOf + searchString.length;
            var start = string.substring(0, startOf);
            var found = '<' + tag + ' class="' + classe + '">' + string.substring(startOf, endOf) + '</' + tag + '>';
            var end = string.substring(endOf).showStr(searchString, tag, classe);
            string = start + found + end;
        }
    }
    return string;
};

/**
 * Returns the string hashed
 */
String.prototype.hash = function()
{
    var string = this.toString();
    var B = 256;
    var N = 251;
    var r = 0;
    var hash = '';
    for (var i = 0; i < string.length; i++) 
    {
        r = (r * B + string.charCodeAt(i)) % N;
        hash += r.toString(16);
    }
    return hash;
};
