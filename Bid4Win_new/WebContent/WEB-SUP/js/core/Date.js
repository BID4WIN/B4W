/**
 * Date.js
 *
 * Extends the Date object.
 *
 * @author Maxime Ollagnier
 */
/**
 * Returns a new Date instance based on the given string date.
 */
Date.parseDDIMMIYYYY = function(stringDate)
{
    if (typeof stringDate == 'string' &&
    stringDate.length == 10 &&
    stringDate.charAt(2) == '/' &&
    stringDate.charAt(5) == '/')
    {
        var day = parseInt(stringDate.substr(0, 2), 10);
        var month = parseInt(stringDate.substr(3, 2), 10) - 1;
        var year = parseInt(stringDate.substr(6, 4), 10);
        
        if (!isNaN(day) && 0 < day && day <= 31 &&
        !isNaN(month) && 0 <= month && month < 12 &&
        !isNaN(year) && 1900 <= year && year <= 9999) 
        {
        
            return new Date(year, month, day);
        }
    }
    throw new Error('Date.parseDDIMMIYYYY - Parse error.');
};

/**
 * Returns a string from this Date.
 */
Date.prototype.toStringDDIMMIYYYY = function()
{
    return this.getDate() + '/' + (this.getMonth() + 1) + '/' + this.getFullYear();
};
