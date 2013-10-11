/**
 * Class HowtoContent.
 * Singleton handling the UI for the running auctions collection.
 * 
 * @author Maxime Ollagnier
 */
HowtoContent.inherits(Content);
function HowtoContent()
{
};

/** Name of the content */
HowtoContent.prototype.name = 'Howto';

/**
 * Initialization
 */
HowtoContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
HowtoContent.prototype.getJQ = function(callback)
{
    var jQ = $('<div id="' + this.name + '">HOW TO</div>');
    callback(jQ);
};