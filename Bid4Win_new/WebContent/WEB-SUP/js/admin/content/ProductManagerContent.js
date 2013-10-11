/**
 * Class ProductManagerContent.
 * 
 * @author Maxime Ollagnier
 */
ProductManagerContent.inherits(Content);
function ProductManagerContent()
{
};

/** Name of the content */
ProductManagerContent.prototype.name = 'ProductManager';

/** Product id of the content */
ProductManagerContent.prototype.product = undefined;

/** Sections flags */
ProductManagerContent.prototype.imagesExpanded = false;
ProductManagerContent.prototype.namesExpanded = false;
ProductManagerContent.prototype.summariesExpanded = false;
ProductManagerContent.prototype.amountsExpanded = false;

/** AreWeEditing flag */
ProductManagerContent.prototype.editing = false;

/**
 * Initialization
 */
ProductManagerContent.prototype.init = function()
{
    this.editing = false;
    if(!Util.checkObject(this.product) || this.product != this.parameters.product)
    {
        this.namesExpanded = false;
        this.summariesExpanded = false;
        this.amountsExpanded = false;
        if (typeof this.parameters.product == 'object')
        {
            this.product = this.parameters.product;
        }
        else
        {
            this.product = new Product();
            this.parameters.product = this.product;
        }
    }
    if(Util.checkObject(this.product) && Util.check(this.parameters.imageStorageId))
    {
        this.addImageToProduct(this.parameters.imageStorageId);
    }
    this.parameters.imageStorageId = undefined;
};

/**
 * Generates the jQuery object
 */
ProductManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div id="title">' + this.product.name + '</div>' +
            '<div id="infos">' +
            '<div id="reference">' + (Util.checkStringNotEmpty(this.product.reference)?this.product.reference:'Reference') + '</div>' +
            '</div>'
            );
    var that = this;
    jQ.find('#reference').bind('dblclick', function(){that.editField(this);});
    jQ.find('#infos').append(this.getSectionJQ('image', 'images'));
    jQ.find('#infos').append(this.getSectionJQ('name', 'names'));
    jQ.find('#infos').append(this.getSectionJQ('summary', 'summaries'));
    jQ.find('#infos').append(this.getSectionJQ('amount', 'amounts'));
    
    callback(jQ);
};

/**
 * Generates the section jQuery element
 */
ProductManagerContent.prototype.getSectionJQ = function(elementName, sectionName)
{
    var jQ = $(
        '<div class="' + sectionName + ' section ' + (this[sectionName + 'Expanded']?'expanded':'reduced') + '">' +
        '<div class="title">' +
        '<div class="switch">&nbsp;</div>' +
        '<div class="label">' + this.product[elementName + 'Map'].size + ' ' + sectionName + '</div>' +
        '</div>' +
        '<div class="content"><div class="' + sectionName + '"></div></div>' +
        '</div>' +
        '</div>'
        );
    var that = this;
    this.product[elementName + 'Map'].foreach(function(type, value)
    {
        jQ.find('.' + sectionName).append(that.getElementJQ(elementName, type, value));
    });
    jQ.children('.content').append($('<div class="addNew">+ New ' + elementName + '</div>'));
    jQ.find('.title .switch').bind('click', function(){that.switchSection(sectionName);});
    jQ.find('.content .addNew').bind('click', function(){that.addElement(elementName, sectionName);});
    if(!this[sectionName + 'Expanded']) jQ.children('.content').hide();
    return jQ;
};

/**
 * Returns a jQuery element
 */
ProductManagerContent.prototype.getElementJQ = function(elementName, type, value)
{
    if(elementName == 'image') return this.getImageElementJQ(type, value);
    var that = this;
    var jQElement = $('<div class="' + elementName + '"></div>');
    jQElement.append($('<div class="delete">&nbsp;</div>'));
    jQElement.append(this[elementName + 'TypeSelectJQ'](this.product[elementName + 'Map'], type));
    jQElement.append($('<div class="value" id="' + elementName + '">' + value + '</div>'));
    jQElement.find('.type').bind('change', function(){that.changeType(elementName, this);});
    jQElement.find('.value').bind('dblclick', function(){that.editField(this);});
    jQElement.find('.delete').bind('click', function(){that.deleteElement(elementName, this);});
    return jQElement;
};

/**
 * Returns a jQuery image element
 */
ProductManagerContent.prototype.getImageElementJQ = function(id, path)
{
    var that = this;
    var jQElement = $('<div class="image"></div>');
    jQElement.append($('<div id="' + id + '" class="delete">&nbsp;</div>'));
    jQElement.append($('<div class="img"><img src="' + path + '"></div>'));
    jQElement.find('.delete').bind('click', function(){that.deleteImage(this);});
    return jQElement;
};

/**
 * Add an empty element
 */
ProductManagerContent.prototype.addElement = function(elementName, sectionName)
{
    if(elementName == 'image') return this.addImage();
    var jQEmpty = this.getElementJQ(elementName, undefined, elementName);
    this.getContent().find('.section.' + sectionName + ' .' + sectionName).append(jQEmpty);
    this.changeType(elementName, jQEmpty.children('.type'));
};

/**
 * Add an image
 */
ProductManagerContent.prototype.addImage = function()
{
    ImagesManagerContent.getInstance().buildPopup(
    {
        product : this.product
    });
};

/**
 * Add an image
 */
ProductManagerContent.prototype.addImageToProduct = function(imageStorageId)
{
    var that = this;
    ProductManager.getInstance().addProductImageAJAX(this.product.id, imageStorageId, function(result)
    {
        if (result.success)
        {
            var productMap = result.getObject();
            that.product = productMap.get(that.product.id);
            that.parameters.product = that.product;
            that.rebuild();
        }
    });
};

/**
 * Delete an element
 */
ProductManagerContent.prototype.deleteElement = function(elementName, elem)
{
    var type = $(elem).next().val();
    this.product[elementName + 'Map'].remove(type);
    this.saveProductAndRebuild();
};

/**
 * Delete an image
 */
ProductManagerContent.prototype.deleteImage = function(elem)
{
    var imageId = $(elem).attr('id');
    var that = this;
    ProductManager.getInstance().removeProductImageAJAX(this.product.id, imageId, function(result)
    {
        if (result.success)
        {
            var productMap = result.getObject();
            that.product = productMap.get(that.product.id);
            that.parameters.product = that.product;
            that.rebuild();
        }
    });
};

/**
 * Toggles the specified section (reduced / expanded)
 */
ProductManagerContent.prototype.switchSection = function(sectionName)
{
    var jQSection = this.getContent().find('.section.' + sectionName);
    jQSection.toggleClass('reduced');
    jQSection.toggleClass('expanded');
    if(jQSection.hasClass('reduced'))
    {
        jQSection.children('.content').slideUp('fast');
        this[sectionName + 'Expanded'] = false;
    }
    if(jQSection.hasClass('expanded'))
    {
        jQSection.children('.content').slideDown('fast');
        this[sectionName + 'Expanded'] = true;
    }
};

/**
 * Switch the field to edit mode
 */
ProductManagerContent.prototype.editField = function(elem)
{
    if (!this.editing)
    {
        var field = $(elem).attr('id');
        $(elem).unbind('dblclick');
        var jQEdit = '';
        switch (field)
        {
            case 'reference':
                jQEdit = $('<input class="' + field + '-input" id="' + field + '-input" class="keyup" type="text" value="' + $(elem).text() + '" maxlength="40" />');
                break;
            case 'name':
                jQEdit = $('<input class="' + field + '-input" id="' + field + '-input" class="keyup" type="text" value="' + $(elem).text() + '" maxlength="40" />');
                break;
            case 'summary':
                jQEdit = $('<textarea class="' + field + '-input" id="' + field + '-input" class="keyup" rows="10" cols="98">' + $(elem).html() + '</textarea>');
                break;
            case 'amount':
                jQEdit = $('<input class="' + field + '-input" id="' + field + '-input" class="keyup" type="text" value="' + $(elem).text() + '" />');
                break;
        }
        var that = this;
        jQEdit.bind('keyup', function(event){that.handleKeyUp(event, this);});
        $(elem).empty();
        $(elem).append(jQEdit);
        jQEdit.focus();
        jQEdit.select();
        this.editing = true;
    }
};

/**
 * Handles key event
 */
ProductManagerContent.prototype.handleKeyUp = function(event, elem)
{
    var newValue= $(elem).val();
    var jQParent = $(elem).parent();
    var fieldId = $(elem).attr('id');
    var field = fieldId.substring(0, fieldId.indexOf('-input'));
    if (event.keyCode == '13' && (field != 'summary' || event.ctrlKey))
    {
        if(field == 'reference')
            this.product.reference = newValue;
        else
        {
            var type = jQParent.prev().children('option:selected').val();
            this.product[field + 'Map'].put(type, newValue);
        }
        this.saveProductAndRebuild();
    }
};

/**
 * Handles the change of type (language or currency)
 */
ProductManagerContent.prototype.changeType = function(elemName, elem)
{
    var type = $(elem).val();
    var value = $(elem).next().text();
    this.product[elemName + 'Map'].put(type, value);
    this.saveProductAndRebuild();
};

/**
 * Saves the product
 */
ProductManagerContent.prototype.saveProductAndRebuild = function()
{
    var that = this;
    ProductManager.getInstance().saveProductAJAX(this.product, function(result)
    {
        if (result.success)
        {
            var productMap = result.getObject();
            that.product = productMap.get(productMap.hash[0]);
            that.parameters.product = that.product;
            that.rebuild();
        }
    });
};

/**
 * Returns the jQuery element of the select
 */
ProductManagerContent.prototype.getLanguageSelectJQ = function(usedLanguageMap, selectedCode)
{
    var jQ = $('<select class="type"></select>');
    LanguageManager.map.foreach(function(code, value)
    {
        if(code == selectedCode || !usedLanguageMap.contains(code))
            jQ.append($('<option value="' + code + '"' + ((code == selectedCode) ? ' selected' : '') + '>' + value + '</option>'));
    });
    return jQ;
};

/**
 * Returns the jQuery element of the select
 */
ProductManagerContent.prototype.getCurrencySelectJQ = function(usedCurrencyMap, selectedCode)
{
    var jQ = $('<select class="type"></select>');
    CurrencyManager.map.foreach(function(code, value)
    {
        if(code == selectedCode || !usedCurrencyMap.contains(code))
            jQ.append($('<option value="' + code + '"' + ((code == selectedCode) ? ' selected' : '') + '>' + value + '</option>'));
    });
    return jQ;
};

/**
 * Section type jQuery select generation methods
 */
ProductManagerContent.prototype.nameTypeSelectJQ = ProductManagerContent.prototype.getLanguageSelectJQ;
ProductManagerContent.prototype.summaryTypeSelectJQ = ProductManagerContent.prototype.getLanguageSelectJQ;
ProductManagerContent.prototype.amountTypeSelectJQ = ProductManagerContent.prototype.getCurrencySelectJQ;