/**
 * Class AuctionsManagerContent.
 * Singleton handling the UI for the auctions collection management.
 * 
 * @author Maxime Ollagnier
 */
AuctionsManagerContent.inherits(Content);
function AuctionsManagerContent()
{
};

/** Properties names */
AuctionsManagerContent.NB_ROW_PROPERTY = 'content.admin.auctions_manager.nb_row';
AuctionsManagerContent.NB_COLUMN_PROPERTY = 'content.admin.auctions_manager.nb_column';
AuctionsManagerContent.NB_LINE_PROPERTY = 'content.admin.auctions_manager.nb_line';

/** Display modes */
AuctionsManagerContent.DISPLAY_TABLE = 'table';
AuctionsManagerContent.DISPLAY_LIST = 'list';

/** Name of the content */
AuctionsManagerContent.prototype.name = 'AuctionsManager';

/** Content attributes */
AuctionsManagerContent.prototype.displayMode = AuctionsManagerContent.DISPLAY_TABLE;
AuctionsManagerContent.prototype.displaySleeping = true;
AuctionsManagerContent.prototype.displayRunning = true;
AuctionsManagerContent.prototype.displayClosed = true;

/**
 * Initialization
 */
AuctionsManagerContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
AuctionsManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.auction.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            '<div title="' + I18nManager.get('admin.auction.display_closed') + '" class="display-closed click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.display_running') + '" class="display-running click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.display_sleeping') + '" class="display-sleeping click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.display_table') + '" class="display-table click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.display_list') + '" class="display-list click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.update') + '" class="update click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.add') + '" class="add click"></div>' +
            '<div title="' + I18nManager.get('admin.auction.refresh') + '" class="refresh click"></div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<div class="subContent"></div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.search').bind('keyup', function(){that.handleKeySearch(this);});
    jQ.find('.refresh').bind('click', function(){that.refresh(this);});
    jQ.find('.add').bind('click', function(){that.add(this);});
    jQ.find('.update').bind('click', function(){that.update(this);});
    jQ.find('.remove').bind('click', function(){that.remove(this);});
    jQ.find('.display-list').bind('click', function(){that.displayList(this);});
    jQ.find('.display-table').bind('click', function(){that.displayTable(this);});
    jQ.find('.display-sleeping').bind('click', function(){that.switchDisplaySleeping(this);});
    jQ.find('.display-running').bind('click', function(){that.switchDisplayRunning(this);});
    jQ.find('.display-closed').bind('click', function(){that.switchDisplayClosed(this);});

    this.getJQSubContent(function(jQSubContent)
    {
        jQ.find('.subContent').append(jQSubContent);
        callback(jQ);
    });
};

/**
 * Generates the jQuery collection of auctions (as a table or a list)
 */
AuctionsManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    AuctionManager.getInstance().getAuctionMapAJAX(this.searchString, function(result)
    {
        if (result.success && result.checkObject())
        {
            var auctionMap = result.getObject();
            var finalAuctionMap = new Map();
            auctionMap.foreach(function(auctionId, auction)
            {
                if ((auction.isSleeping() && that.displaySleeping) ||
                    (auction.isRunning() && that.displayRunning) ||
                    (auction.isClosed() && that.displayClosed))
                {
                    finalAuctionMap.put(auctionId, auction);
                }
            });
            finalAuctionMap.sort('startDate', 'asc');
            switch (that.displayMode)
            {
                case AuctionsManagerContent.DISPLAY_TABLE:
                    that.getJQTable(finalAuctionMap, function(jQTable)
                    {
                        callback(jQTable);
                    });
                    break;
                
                case AuctionsManagerContent.DISPLAY_LIST:
                    that.getJQList(finalAuctionMap, function(jQList)
                    {
                        callback(jQList);
                    });
                    break;
            }
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * Generates the jQuery auction table
 */
AuctionsManagerContent.prototype.getJQTable = function(auctionMap, callback)
{
    var nbRowMax = AuctionsManagerContent.DEFAULT_NB_ROW;
    var nbColumnMax = AuctionsManagerContent.DEFAULT_NB_COLUMN;
    if (PropertyManager.getInstance().exists(AuctionsManagerContent.NB_ROW_PROPERTY))
    {
        nbRowMax = PropertyManager.getInstance().getInt(AuctionsManagerContent.NB_ROW_PROPERTY);
    }
    if (PropertyManager.getInstance().exists(AuctionsManagerContent.NB_COLUMN_PROPERTY))
    {
        nbColumnMax = PropertyManager.getInstance().getInt(AuctionsManagerContent.NB_COLUMN_PROPERTY);
    }
    
    var jQTable = $('<table>');
    
    var i = 0;
    var nbRow = 0;
    
    var that = this;
    var jQTR = $('<tr>');
    auctionMap.foreach(function(id, auction)
    {
        var jQAuction = auction.getJQLight(that.searchString);
        jQAuction.addClass('dblclick');
        jQAuction.addClass('click');
        jQAuction.bind('dblclick', function(){that.show(this);});
        jQAuction.bind('click', function(){that.selectItem(this);});
        if (that.selectedItem == auction.id)
        {
            jQAuction.addClass('selected');
        }
        
        var jQTD = $('<td>').append(jQAuction);
        jQTR.append(jQTD);
        if (((i + 1) % nbColumnMax) == 0 || (i + 1) == auctionMap.size)
        {
            nbRow++;
            jQTable.append(jQTR);
            jQTR = $('<tr>');
        }
        i++;
    });
    
    callback(jQTable);
};

/**
 * Generates the jQuery auction list
 */
AuctionsManagerContent.prototype.getJQList = function(auctionMap, callback)
{
    var nbLineMax = AuctionsManagerContent.DEFAULT_NB_LINE;
    if (PropertyManager.getInstance().exists(AuctionsManagerContent.NB_LINE_PROPERTY))
    {
        nbLineMax = PropertyManager.getInstance().getInt(AuctionsManagerContent.NB_LINE_PROPERTY);
    }
    
    var jQList = $('<ul>');

    var that = this;
    var nbLine = 0;
    auctionMap.foreach(function(id, auction)
    {
        if (nbLine < nbLineMax)
        {
            var jQAuction = auction.getJQLight(that.searchString);
            jQAuction.addClass('dblclick');
            jQAuction.addClass('click');
            jQAuction.bind('dblclick', function(){that.show(this);});
            jQAuction.bind('click', function(){that.selectItem(this);});
            if (that.selectedItem == auction.id)
            {
                jQAuction.addClass('selected');
            }
            
            var jQLine = $('<li>').append(jQAuction);
            jQList.append(jQLine);
        }
        nbLine++;
    });
    
    callback(jQList);
};

/**
 * Refresh the collection
 */
AuctionsManagerContent.prototype.refresh = function(callback)
{
    this.rebuildSubContent(callback);
};

/**
 * Displays a popup to create a new auction
 */
AuctionsManagerContent.prototype.add = function()
{
    AuctionManagerContent.getInstance().buildPopup();
};

/**
 * Displays a popup to update the selected auction
 */
AuctionsManagerContent.prototype.update = function()
{
    if (typeof this.selectedItem == 'string' && this.selectedItem != '')
    {
        AuctionManagerContent.getInstance().buildPopup(
        {
            auction : AuctionManager.getAuction(this.selectedItem)
        });
    }
    else
    {
        this.updateMessage('Please select a auction first.');
    }
};

/**
 * Displays a popup to update the double clicked auction
 */
AuctionsManagerContent.prototype.show = function(elem)
{
    AuctionManagerContent.getInstance().buildPopup(
    {
        auction : AuctionManager.getInstance().getAuction($(elem).attr('id'))
    });
};

/**
 * Remove the selected auction from the server an refreshes the auction
 * collection
 */
AuctionsManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        AuctionManager.removeAuction(this.selectedItem, function(success, message)
        {
            that.updateMessage(message);
            if (success)
            {
                that.refresh();
            }
        });
    }
};

/**
 * Switches the display mode to list and refreshes the auction collection
 */
AuctionsManagerContent.prototype.displayList = function()
{
    if (this.displayMode != AuctionsManagerContent.DISPLAY_LIST)
    {
        this.displayMode = AuctionsManagerContent.DISPLAY_LIST;
        this.refresh();
    }
};

/**
 * Switches the display mode to table and refreshes the auction collection
 */
AuctionsManagerContent.prototype.displayTable = function()
{
    if (this.displayMode != AuctionsManagerContent.DISPLAY_TABLE)
    {
        this.displayMode = AuctionsManagerContent.DISPLAY_TABLE;
        this.refresh();
    }
};

/**
 * Switches the display sleeping mode from true to false or vice e versa
 */
AuctionsManagerContent.prototype.switchDisplaySleeping = function()
{
    this.displaySleeping = !this.displaySleeping;
    this.getContent().find('.display-sleeping').toggleClass('off');
    this.refresh();
};

/**
 * Switches the display running mode from true to false or vice e versa
 */
AuctionsManagerContent.prototype.switchDisplayRunning = function()
{
    this.displayRunning = !this.displayRunning;
    this.getContent().find('.display-running').toggleClass('off');
    this.refresh();
};

/**
 * Switches the display closed mode from true to false or vice e versa
 */
AuctionsManagerContent.prototype.switchDisplayClosed = function()
{
    this.displayClosed = !this.displayClosed;
    this.getContent().find('.display-closed').toggleClass('off');
    this.refresh();
};