/**
 * Class CurrentAuctionsContent.
 * Singleton handling the UI for the running auctions collection.
 * 
 * @author Maxime Ollagnier
 */
CurrentAuctionsContent.inherits(Content);
function CurrentAuctionsContent()
{
};

/** Properties names */
CurrentAuctionsContent.NB_ROW_PROPERTY = 'current_auctions.nb_row';
CurrentAuctionsContent.NB_COLUMN_PROPERTY = 'current_auctions.nb_column';

/** Default properties values */
CurrentAuctionsContent.DEFAULT_NB_ROW = 10;
CurrentAuctionsContent.DEFAULT_NB_COLUMN = 4;

/** Name of the content */
CurrentAuctionsContent.prototype.name = 'CurrentAuctions';

/**
 * Initialization
 */
CurrentAuctionsContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
CurrentAuctionsContent.prototype.getJQ = function(callback)
{
    var nbRowMax = ProductsManagerContent.DEFAULT_NB_ROW;
    var nbColumnMax = ProductsManagerContent.DEFAULT_NB_COLUMN;
    if (PropertyManager.getInstance().exists(ProductsManagerContent.NB_ROW_PROPERTY))
    {
        nbRowMax = PropertyManager.getInstance().getInt(ProductsManagerContent.NB_ROW_PROPERTY);
    }
    if (PropertyManager.getInstance().exists(ProductsManagerContent.NB_COLUMN_PROPERTY))
    {
        nbColumnMax = PropertyManager.getInstance().getInt(ProductsManagerContent.NB_COLUMN_PROPERTY);
    }
    var jQAuctionTable = AuctionManager.getJQAuctionTable(
    {
        type : 'current',
        mode : 'light'
    }, nbColumnMax, nbRowMax);
    var jQAuctions = jQAuctionTable.find('.auction');
    for ( var i = 0; i < jQAuctions.length; i++)
    {
        $(jQAuctions[i]).children('.title').bind('click', function(){alert('Call board');});
    }
    callback(jQAuctionTable);
};