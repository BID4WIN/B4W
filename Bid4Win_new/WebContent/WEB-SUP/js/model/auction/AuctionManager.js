/**
 * Class AuctionManager. Handles synchrone and asynchrone actions on auction.
 * 
 * @author Maxime Ollagnier
 */
AuctionManager.inherits(AjaxObjectManager);
function AuctionManager()
{
    this.objectName = 'Auction';
    this.objectType = Auction;
    this.map = new Map();
};

/**
 * Fetches an auction from the local auction map.
 */
AuctionManager.prototype.getAuction = function(auctionId)
{
    if (typeof auctionId == 'string')
    {
        return this.map.get(auctionId);
    }
    return undefined;
};

/**
 * Fetches an auction from the server with the given id.
 */
AuctionManager.prototype.getAuctionAJAX = function(id, callback)
{
    if (typeof callback != 'function')
    {
        callback = function()
        {
        };
    }
    this.getObjectMap(
    {
        id : id
    }, function(auctionMap, message)
    {
        var auction = undefined;
        if (typeof auctionMap != 'undefined' && auctionMap.size > 0)
        {
            auction = auctionMap.get(id);
        }
        callback(auction, message);
    });
};

/**
 * Fetches an auction map from the server with the given searchString.
 */
AuctionManager.prototype.getAuctionMapAJAX = function(searchString, callback)
{
    this.getObjectMap(
    {
        searchString : searchString
    }, callback);
};

/**
 * Saves the given auction into sever persistence system.
 */
AuctionManager.prototype.saveAuctionAJAX = function(auction, callback)
{
    this.saveObject(auction, callback);
};

/**
 * Deletes the auction pointed by the given ID from the server persistence
 * system.
 */
AuctionManager.prototype.removeAuctionAJAX = function(auctionId, callback)
{
    this.removeObject(auctionId, callback);
};

/**
 * Puts the given auction into the auction map of the manager.
 */
AuctionManager.prototype.cacheObject = function(auction)
{
    if (typeof auction == 'object')
    {
        ProductManager.getInstance().cacheObject(auction.product);
        this.map.put(auction.getId(), auction);
    }
};