/**
 * Class ClosedAuctionsContent.
 * Singleton handling the UI for the ended auctions collection.
 * 
 * @author Maxime Ollagnier
 */
ClosedAuctionsContent.inherits(Content);
function ClosedAuctionsContent()
{
};

/** Properties names */
ClosedAuctionsContent.NB_ROW_PROPERTY = 'closed_auctions.nb_row';
ClosedAuctionsContent.NB_COLUMN_PROPERTY = 'closed_auctions.nb_column';

/** Default properties values */
ClosedAuctionsContent.DEFAULT_NB_ROW = 10;
ClosedAuctionsContent.DEFAULT_NB_COLUMN = 4;

/** Name of the content */
ClosedAuctionsContent.prototype.name = 'ClosedAuctions';

/**
 * Initialization
 */
ClosedAuctionsContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
ClosedAuctionsContent.prototype.getJQ = function(callback)
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
        type : 'closed',
        mode : 'light'
    }, nbColumnMax, nbRowMax);
    var jQAuctions = jQAuctionTable.find('.auction');
    for ( var i = 0; i < jQAuctions.length; i++)
    {
        $(jQAuctions[i]).children('.title').bind('click', function(){alert('Call board');});
    }
    callback(jQAuctionTable);
};