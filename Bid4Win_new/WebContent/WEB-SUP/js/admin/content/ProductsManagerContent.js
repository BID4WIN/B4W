/**
 * Class ProductsManagerContent.
 * Singleton handling the UI for the products collection management.
 * 
 * @author Maxime Ollagnier
 */
ProductsManagerContent.inherits(Content);
function ProductsManagerContent()
{
};

/** Properties names */
ProductsManagerContent.NB_LINE_PROPERTY = 'content.admin.products_manager.nb_line';

/** Name of the content */
ProductsManagerContent.prototype.name = 'ProductsManager';

/**
 * Initialization
 */
ProductsManagerContent.prototype.init = function()
{
};

/**
 * Generates the jQuery object
 */
ProductsManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.product.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            //'<div title="' + I18nManager.get('admin.product.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.product.add') + '" class="add click"></div>' +
            '<div title="' + I18nManager.get('admin.product.refresh') + '" class="refresh click"></div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<div class="subContent"></div>' +
            '</div>'
            );
            
    var that = this;
    jQ.find('.search').bind('keyup', function(){that.handleKeySearch(this);});
    jQ.find('.refresh').bind('click', function(){that.refresh(this);});
    jQ.find('.add').bind('click', function(){that.add(this);});
    jQ.find('.remove').bind('click', function(){that.remove(this);});

    this.getJQSubContent(function(jQSubContent)
    {
        jQ.find('.subContent').append(jQSubContent);
        callback(jQ);
    });
};

/**
 * Generates the jQuery collection of products (as a table or a list)
 */
ProductsManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    ProductManager.getInstance().getProductMapAJAX(
    {
        searchString : this.searchString
    }, function(result)
    {
        if (result.success && result.checkObject())
        {
            var productMap = result.getObject();
            productMap.sort('name', 'asc');
            var nbLineMax = PropertyManager.getInstance().getInt(ProductsManagerContent.NB_LINE_PROPERTY);
            var jQList = $('<ul>');
            var nbLine = 0;
            productMap.foreach(function(id, product)
            {
                if (nbLine < nbLineMax)
                {
                    var jQProduct = $(
                            '<div class="product unselectable" id="' + product.id + '">' +
                            '<div class="name">' + product.name.showStr(that.searchString, 'span', 'found') + '</div>' +
                            '<div class="reference">' + product.reference.showStr(that.searchString, 'span', 'found') + '</div>' +
                            '</div>'
                            );
                    jQProduct.addClass('dblclick');
                    jQProduct.addClass('click');
                    jQProduct.bind('dblclick', function(){that.show(this);});
                    //jQProduct.bind('click', function(){that.selectItem(this);});
                    if (that.selectedItem == product.id)
                    {
                        jQProduct.addClass('selected');
                    }
                    var jQLine = $('<li>').append(jQProduct);
                    jQList.append(jQLine);
                }
                nbLine++;
            });
            
            callback(jQList);
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * Refresh the collection
 */
ProductsManagerContent.prototype.refresh = function(callback)
{
    this.rebuildSubContent(callback);
};

/**
 * Displays a popup to create a new product
 */
ProductsManagerContent.prototype.add = function()
{
    ProductManagerContent.getInstance().buildPopup();
};

/**
 * Displays a popup to update the double clicked product
 */
ProductsManagerContent.prototype.show = function(elem)
{
    var productId = $(elem).attr('id');
    ProductManager.getInstance().getProductAJAX(productId, function(result)
    {
        if (result.success && result.checkObject())
        {
            var productMap = result.getObject();
            var product = productMap.get(productId);
            ProductManagerContent.getInstance().buildPopup(
            {
                product : product
            });
        }
    });
};

/**
 * Remove the selected product from the server an refreshes the product
 * collection
 */
ProductsManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        ProductManager.getInstance().removeProductAJAX(this.selectedItem, function(result)
        {
            if (result.success && result.checkObject())
            {
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};