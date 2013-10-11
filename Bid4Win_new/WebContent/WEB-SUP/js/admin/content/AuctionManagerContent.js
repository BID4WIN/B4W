/**
 * Class AuctionManagerContent.
 * 
 * @author Maxime Ollagnier
 */
AuctionManagerContent.inherits(Content);
function AuctionManagerContent()
{
};

/** Name of the content */
AuctionManagerContent.prototype.name = 'AuctionManager';

/** Auction of the content */
AuctionManagerContent.prototype.auction = undefined;

/** AreWeEditing flag */
AuctionManagerContent.prototype.editing = false;

/**
 * Initialization
 */
AuctionManagerContent.prototype.init = function()
{
    this.editing = false;
    if (typeof this.parameters.auction == 'object')
    {
        this.auction = this.parameters.auction;
    }
    else
    {
        this.auction = new Auction();
        this.parameters.auction = this.auction;
    }
    var that = this;
    this.addShortcut(new Shortcut('ctrl+shift+s', function(){that.save();}));
};

/**
 * Generates the jQuery object
 */
AuctionManagerContent.prototype.getJQ = function(callback)
{
    var that = this;
    LabelManager.getInstance().getLabelMapAJAX('', function(result)
    {
        var labelMap = result.getObject();
        var jQ = $(
                '<div id="' + that.name + '">' +
                '<div class="name">' + that.auction.product.name + 
                '<div class="products-manager click">(Products Manager)</div>' +
                '</div>' +
                '<div class="message">' + that.message + '</div>' +
                '<div class="image">' +
                '<img class="img" alt="Image not available." src="' + Image.path2 + that.auction.product.imageFileName + '"/>' +
                '</div>' +
                '<div class="infos">' +
                '<div class="start-date-label">Start date :</div>' +
                '<div class="start-date">' + that.auction.startDate.toStringDDIMMIYYYY() + '</div>' +
                '<div class="public-price-label">Public price :</div>' +
                '<div class="public-price">' + that.auction.product.price.format(2, ',') + ' &euro;</div>' +
                '</div>' +
                '<div class="price">' + that.auction.price.format(2, ',') + '</div>' +
                '<div class="countDown">' + that.auction.countDown + '</div>' +
                '<div class="save" class="click">Save (ctrl+shift+s)</div>' +
                '</div>'
                );
                
        jQ.find('.products-manager').bind('click', function(){that.showProductsManager(this);});
        jQ.find('.save').bind('click', function(){that.save(this);});
        
        callback(jQ);
    });
};

/**
 * Displays the products manager
 */
AuctionManagerContent.prototype.showProductsManager = function()
{
    ProductsManagerContent.getInstance().buildPopup();
};

/**
 * Saves the auction into DB
 */
AuctionManagerContent.prototype.save = function()
{
    var that = this;
    AuctionManager.getInstance().saveAuctionAJAX(this.auction, function(result)
    {
        if (result.success && result.checkObject())
        {
            that.hide();
            AuctionsManagerContent.getInstance().updateMessage(result.html());
            AuctionsManagerContent.getInstance().buildPage();
        }
        else
        {
            AuctionsManagerContent.getInstance().updateMessage(result.html());
        }
    });
};