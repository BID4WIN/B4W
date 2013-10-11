/**
 * Class ImagesManagerContent.
 * 
 * @author Maxime Ollagnier
 */
ImagesManagerContent.inherits(Content);
function ImagesManagerContent()
{
    this.tree = new Tree(DirectoryManager.IMAGE, this);
};

/** Properties names */
ImagesManagerContent.NB_COLUMN_PROPERTY = 'content.admin.images_manager.nb_column';

/** Name of the content */
ImagesManagerContent.prototype.name = 'ImagesManager';

/** Product id */
ImagesManagerContent.prototype.product = undefined;

/** AreWeEditing flag */
ImagesManagerContent.prototype.editing = false;


ImagesManagerContent.prototype.dragged = undefined;



/**
 * Initialization
 */
ImagesManagerContent.prototype.init = function()
{
    this.product = this.parameters.product;
};

/**
 * Generates the jQuery object
 */
ImagesManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.image.title') + '</div>' +
            '<input class="search" type="text" value="' + this.searchString + '" />' +
            '<div title="' + I18nManager.get('admin.image.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.image.add') + '" class="add click"></div>' +
            '<div class="image-properties">' +
            '<input class="image-name" type="text" value="ImageName" />' +
            '<div class="image-separator">/</div>' +
            '<input class="image-path" type="text" value="" />' +
            '</div>' +
            '<input id="image-upload-input" class="upload-input" name="upload" type="file"/>' +
            '<div title="' + I18nManager.get('admin.image.refresh') + '" class="refresh click"></div>' +
            '<div class="message">' + this.message + '</div>' +
            '</div>' +
            '<table><tr>' +
            '<td class="tree"></td>' +
            '<td style="border-left: solid 1px grey;" class="subContent"></td>' +
            '</tr></table>' +
            '<div id="square" style="position: absolute; width: 20px; height: 20px; border: solid 2px grey; z-index: 1000; display: none;"></div>' +
            '</div>'
            );
    
    var  that = this;
    document.onmouseup = function(e) {
        that.dragged = undefined;
    };
    document.onmousemove = function(e) {
        if(!$.browser.msie) {
            if(Util.checkString(that.dragged) && e.which == 1) {
                $('#square').css('display', 'block');
                $('#square').css('left', e.clientX + document.body.scrollLeft + 1);
                $('#square').css('top', e.clientY + document.body.scrollTop + 1);
            }
            else {
                that.dragged = undefined;
                $('#square').css('display', 'none');
            }
        }
    };
    jQ.find('.refresh').bind('click', function(){that.refresh(this);});
    jQ.find('.add').bind('click', function(){that.add(this);});
    jQ.find('.remove').bind('click', function(){that.remove(this);});
    jQ.find('.search').bind('keyup', function(){that.handleKeySearch(this);});
    
    this.tree.getJQ(function(jQTree)
    {
        jQ.find('.tree').append(jQTree);
        
        that.getJQSubContent(function(jQSubContent)
        {
            jQ.find('.subContent').append(jQSubContent);
            callback(jQ);
        });
    });
};

/**
 * Generates the jQuery image table
 */
ImagesManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    $('.header .image-path').val(this.tree.selectedItem);
    this.selectedItem = undefined;
    ImageManager.getInstance().getImageMapAJAX(this.tree.selectedItem, function(result)
    {
        var jQTable = $('<table>');
        if (result.success && result.checkObject())
        {
            var imageMap = result.getObject();
            imageMap.sort('name');
            var nbColumnMax = PropertyManager.getInstance().getInt(ImagesManagerContent.NB_COLUMN_PROPERTY);
            
            var i = 0;
            var nbRow = 0;
            var jQTR = $('<tr>');
            imageMap.foreach(function(id, image)
            {
                var selected = '';
                if (that.selectedItem == id)
                {
                    selected = ' selected';
                }
                var jQTD = $(
                        '<td>' +
                        '<div id="' + id + '" class="image-table-div' + selected + ' click">' +
                        '<div id="' + id + '" class="image-table-img dblclick">' +
                        '<div id="' + id + '" class="image-table-img-bck"></div>' +
                        '<img id="' + id + '" class="image-table-img-img" src="' + ImageManager.getInstance().getImageURL(id, ImageManager.SMALL) + '"/>' +
                        '</div>' +
                        '<div id="' + id + '" class="image-table-label dblclick">' + image.name.showStr(that.searchString, 'span', 'found') + '</div>' +
                        '</div>' +
                        '</td>'
                        );
                jQTD.find('.image-table-div').bind('mousedown', function(){
                    that.dragged = $(this).attr('id');
                    });
                jQTD.find('.image-table-div').mouseover(function(){
                    document.onselectstart = function(){ return false; };
                });
                jQTD.find('.image-table-div').bind('click', function(){that.selectItem(this);});
                jQTD.find('.image-table-img').bind('dblclick', function(){that.chooseImage(this);});
                jQTD.find('.image-table-label').bind('dblclick', function(){that.editName(this);});
                jQTR.append(jQTD);
                
                if (((i + 1) % nbColumnMax) == 0 || (i + 1) == imageMap.size)
                {
                    nbRow++;
                    jQTable.append(jQTR);
                    jQTR = $('<tr>');
                }
                i++;
            });
        }
        else
        {
            that.updateMessage(result.html());
        }
        callback(jQTable);
    });
};

/**
 * Refresh the page
 */
ImagesManagerContent.prototype.refresh = function(callback)
{
    this.editing = false;
    var that = this;
    this.rebuildTree(function()
    {
        that.rebuildSubContent(callback); 
    });
};

/**
 * Refresh the tree only
 */
ImagesManagerContent.prototype.refreshTree = function(callback)
{
    this.rebuildTree(callback);
};

/**
 * Sends the file within the input file field to the server
 */
ImagesManagerContent.prototype.add = function()
{
    if (this.getContent().find('.upload-input').val() != '')
    {
        var imageName = this.getContent().find('.image-name').val();
        if(imageName != '' && imageName != 'ImageName')
        {
            var path = $('.header .image-path').val();
            var that = this;
            this.updateMessage('');
            this.showLoadingGIF();
            ImageManager.getInstance().saveImageAJAX('image-upload-input', imageName, path, function()
            {
                that.hideLoadingGIF();
                that.refresh();
            });
        }
        else
        {
            this.updateMessage('Image name should be specified.');
        }
    }
    else
    {
        this.updateMessage('No file selected.');
    }
};

/**
 * Removes from the server the selected image
 */
ImagesManagerContent.prototype.remove = function()
{
    var id = this.selectedItem;
    if (Util.checkString(id))
    {
        var that = this;
        this.updateMessage('');
        ImageManager.getInstance().removeImageAJAX(id, function(result)
        {
            if (result.success)
            {
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Moves the image whose id is specified to the given path
 */
ImagesManagerContent.prototype.moveItem = function(id, path)
{
    if (Util.checkString(id) && Util.checkString(path))
    {
        var name = $('#' + id + ' .image-table-label').text();
        var that = this;
        this.updateMessage('');
        ImageManager.getInstance().moveImageAJAX(id, path, name, function(result)
        {
            if (result.success)
            {
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Picks the double clicked image as the related image for the current product
 */
ImagesManagerContent.prototype.chooseImage = function(elem)
{
    if (typeof this.product == 'object')
    {
        this.back({imageStorageId : $(elem).attr('id')});
    }
};

/**
 * Switch the image name field to edit mode
 */
ImagesManagerContent.prototype.editName = function(elem)
{
    if (!this.editing)
    {
        var imageName = $(elem).text();
        $(elem).unbind('dblclick');
        var jQEdit = $('<input id="' + $(elem).attr('id') + '" class="image-table-label-input" class="keypress" type="text" value="' + imageName + '" maxlength="20" />');
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
ImagesManagerContent.prototype.handleKeyUp = function(event, elem)
{
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        $(elem).unbind('keyup');
        if (event.keyCode == '13')
        {
            var id = $(elem).attr('id');
            var path = this.tree.selectedItem;
            var name= $(elem).val();
            var that = this;
            ImageManager.getInstance().moveImageAJAX(id, path, name, function(result)
            {
                if (result.success)
                {
                    that.refresh();
                }
                that.updateMessage(result.html());
            });
        }
        else
        {
            this.refresh();
        }
    }
};