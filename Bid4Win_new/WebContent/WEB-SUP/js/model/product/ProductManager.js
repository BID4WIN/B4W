/**
 * Class ProductManager. Handles synchrone and asynchrone actions on product.
 * 
 * @author Maxime Ollagnier
 */
ProductManager.inherits(AjaxObjectManager);
function ProductManager()
{
    this.objectName = 'Product';
    this.objectType = Product;
    this.map = new Map();
};

/**
 * Fetches a product from the local product map.
 */
ProductManager.prototype.getProduct = function(productId)
{
    if (typeof productId == 'string')
    {
        return this.map.get(productId);
    }
    return undefined;
};

/**
 * Fetches a product from the server with the given id
 */
ProductManager.prototype.getProductAJAX = function(id, callback)
{
    this.getObjectMap({id : id}, callback);
};

/**
 * Fetches a product map from the server with the given parameters. (Parameters :
 * searchString | id)
 */
ProductManager.prototype.getProductMapAJAX = function(parameters, callback)
{
    this.getObjectMap(parameters, callback);
};

/**
 * Saves the given product into sever persistence system.
 */
ProductManager.prototype.saveProductAJAX = function(product, callback)
{
    this.saveObject(product, callback);
};

/**
 * TODO
 */
ProductManager.prototype.addProductImageAJAX = function(id, imageId, callback)
{
    var that = this;
    AjaxManager.getJSON('AddProductImageAction', {id : id, imageStorageId : imageId}, function(result)
    {
        if(result.success)
            result.json2Model(that, that.getObjectMapFromJsonMap);
        callback(result);
    });
};

/**
 * TODO
 */
ProductManager.prototype.removeProductImageAJAX = function(id, imageId, callback)
{
    var that = this;
    AjaxManager.getJSON('RemoveProductImageAction', {id : id, imageUsageId : imageId}, function(result)
    {
        if(result.success)
            result.json2Model(that, that.getObjectMapFromJsonMap);
        callback(result);
    });
};

/**
 * Deletes the product pointed by the given ID from the server persistence system.
 */
ProductManager.prototype.removeProductAJAX = function(productId, callback)
{
    this.removeObject(productId, callback);
};

/**
 * Puts the given product into the product map of the manager.
 */
ProductManager.prototype.cacheObject = function(product)
{
    if (typeof product == 'object')
    {
        LabelManager.getInstance().cacheObjectMap(product.labelMap);
        this.map.put(product.getId(), product);
    }
};