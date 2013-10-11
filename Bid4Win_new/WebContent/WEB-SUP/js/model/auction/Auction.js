/**
 * Class Auction.
 * 
 * @author Maxime Ollagnier
 */

Auction.inherits(Bean);
function Auction(jsonAuction)
{
    if(typeof jsonAuction != 'object')
    {
        this.id = '';
        this.product = undefined;
        this.price = '0';
        this.closed = false;
        this.countDown = -1;
        this.startDate = Date.parseDDIMMIYYYY('01/01/2100');
        this.bidHistory = undefined;
    }
    else
    {
        this.id = jsonAuction.id;
        this.product = new Product(jsonAuction.product);
        this.price = jsonAuction.price;
        this.closed = jsonAuction.closed;
        this.countDown = jsonAuction.countDown;
        
        if(typeof jsonAuction.startDate == 'string')
        {
            this.startDate = Date.parseDDIMMIYYYY(jsonAuction.startDate);
        }
        else
        {
            this.startDate = jsonAuction.startDate;
        }
        
        this.bidHistory = jsonAuction.bidHistory;
    }
}

/**
 * Returns the JSON object
 */
Auction.prototype.getJSON = function()
{
    var json =
    {
        'id' : this.id,
        'productId' : this.product.getId(),
        'price' : this.price,
        'closed' : this.closed,
        'countDown' : this.countDown,
        'startDate' : this.startDate,
        'bidHistory' : this.bidHistory
    };
    return json;
};

/**
 * Returns true if the auction has not started yet
 */
Auction.prototype.isSleeping = function()
{
    return (new Date() - this.startDate) < 0;
};

/**
 * Returns true if the auction currently running
 */
Auction.prototype.isRunning = function()
{
    return !this.isSleeping() && !this.isClosed();
};

/**
 * Returns true if the auction over
 */
Auction.prototype.isClosed = function()
{
    return this.closed;
};

/**
 * Returns the state of the auction (sleeping, running or closed)
 */
Auction.prototype.getState = function()
{
    if(this.isSleeping())
    {
        return "sleeping";
    }
    if(this.isRunning())
    {
        return "running";
    }
    return "closed";
};

/**
 * Returns true if the auction has not started yet
 */
Auction.prototype.isSleeping = function()
{
    return (new Date() - this.startDate) < 0;
};

/**
 * Returns the jQuery object
 */
Auction.prototype.getJQLight = function(searchString)
{
    var jQ = $(
            '<div class="auction light ' + this.getState() + '" id="' + this.id + '">' +
            '<div class="name">' + this.product.name.showStr(searchString, 'span', 'found') + '</div>' +
            '<div class="image">' +
            '<img class="img" alt="Image not available." src="' + Image.path1 + this.product.imageFileName + '"/>' +
            '</div>' +
            '<div class="creation-date">' + this.startDate.toStringDDIMMIYYYY() + '</div>' +
            '<div class="price">' + this.price.format(2, ',') + ' &euro;</div>' +
            '</div>'
            );
    
    return jQ;
};
