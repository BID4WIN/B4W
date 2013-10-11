/**
 * Class LabelsManagerContent.
 * 
 * @author Maxime Ollagnier
 */
LabelsManagerContent.inherits(Content);
function LabelsManagerContent()
{
};

/** Default constants */
LabelsManagerContent.DEFAULT_NAME = '_name_';
LabelsManagerContent.DEFAULT_DESCRIPTION = '_description_';

/** Name of the content */
LabelsManagerContent.prototype.name = 'LabelsManager';

/** Content label map */
LabelsManagerContent.prototype.labelMap = undefined;

/** Id of the label being edited */
LabelsManagerContent.prototype.editedLabelId = undefined;

/** Flag for the name being edited */
LabelsManagerContent.prototype.editingLabelName = undefined;

/** Flag for the description being edited */
LabelsManagerContent.prototype.editingLabelDescription = undefined;

/**
 * Initialization
 */
LabelsManagerContent.prototype.init = function()
{
    var that = this;
    this.addShortcut(new Shortcut('shift+delete', function(){that.remove();}));
};

/**
 * Generates the jQuery object
 */
LabelsManagerContent.prototype.getJQ = function(callback)
{
    var jQ = $(
            '<div id="' + this.name + '">' +
            '<div class="header">' +
            '<div class="title">' + I18nManager.get('admin.label.title') + '</div>' +
            '<input type="text" value="' + this.searchString + '" class="search" />' +
            '<div title="' + I18nManager.get('admin.label.remove') + '" class="remove click"></div>' +
            '<div title="' + I18nManager.get('admin.label.add') + '" class="add click"></div>' +
            '<div title="' + I18nManager.get('admin.label.refresh') + '" class="refresh click"></div>' +
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
 * TODO
 */
LabelsManagerContent.prototype.getJQSubContent = function(callback)
{
    var that = this;
    LabelManager.getInstance().getLabelMapAJAX(this.searchString, function(result)
    {
        if(result.success && result.checkObject())
        {
            that.labelMap = result.getObject();;
            that.labelMap.sort('name', 'asc');
            var jQTable = $('<table>');
            that.labelMap.foreach(function(id, label)
            {
                var id = label.id.str2Id();
                var jQTr = $(
                    '<tr id="' + id + '" class="click">' +
                    '<td id="' + id + '" class="dblclick name">' + label.name.showStr(that.searchString, 'span', 'found') + '</td>' +
                    '<td id="' + id + '" class="dblclick description">' + label.description + '</td>' + 
                    '</tr>');
                jQTr.find('.name').bind('dblclick', function(){that.edit(this);});
                jQTr.find('.description').bind('dblclick', function(){that.edit(this);});
                jQTr.bind('click', function()
                {
                    var it = this;
                    that.selectItem(this, function()
                    {
                        if (typeof that.editedLabelId != 'undefined' &&
                            that.editedLabelId != $(it).attr('id'))
                        {
                            that.refresh();
                        }
                    });
                });
                jQTable.append(jQTr);
            });
            callback(jQTable);
        }
        else
        {
            that.updateMessage(result.html());
        }
    });
};

/**
 * Refreshes the collection with items found on the server
 */
LabelsManagerContent.prototype.refresh = function(callback)
{
    var that = this;
    this.rebuildSubContent(function()
    {
        that.editedLabelId = undefined;
        that.editingLabelName = undefined;
        that.editingLabelDescription = undefined;
        if(typeof callback == 'function')
        {
            callback();
        }
    });
};

/**
 * Adds a new label with default name and description
 */
LabelsManagerContent.prototype.add = function()
{
    var that = this;
    var newLabel = new Label(
    {
        name : LabelsManagerContent.DEFAULT_NAME,
        description : LabelsManagerContent.DEFAULT_DESCRIPTION
    });
    LabelManager.getInstance().saveLabelAJAX(newLabel, function(result)
    {
        if (result.success && result.checkObject())
        {
            that.refresh();
        }
        that.updateMessage(result.html());
    });
};

/**
 * Removes the selected label
 */
LabelsManagerContent.prototype.remove = function()
{
    if (typeof this.selectedItem == 'string')
    {
        var that = this;
        LabelManager.getInstance().removeLabelAJAX(this.selectedItem.id2Str(), function(result)
        {
            if (result.success && result.checkObject())
            {
                that.selectedItem = undefined;
                that.refresh();
            }
            that.updateMessage(result.html());
        });
    }
};

/**
 * Switches a label TD from text to input field to make it editable
 */
LabelsManagerContent.prototype.edit = function(elem)
{
    if (typeof this.editedLabelId == 'undefined')
    {
        this.editedLabelId = $(elem).attr('id');

        var that = this;
        var jQInput = $('<input class="keyup" type="text" value="' + $(elem).text() + '">');
        jQInput.bind('keyup', function(event){that.handleKeyUp(event, this);});

        $(elem).unbind('dblclick');
        $(elem).empty();
        $(elem).append(jQInput);
        jQInput.focus();
        jQInput.select();

        if(this.selectedItem != this.editedLabelId)
        {
            if(typeof this.selectedItem != 'undefined' && this.selectedItem != '')
            {
                $('#' + this.selectedItem).toggleClass('selected');
            }
            this.selectedItem = this.editedLabelId;
            $('#' + this.selectedItem).toggleClass('selected');
        }
        $('tr#' + this.editedLabelId).unbind('click');
    }
};

/**
 * Handles the key event on a label TD input field
 */
LabelsManagerContent.prototype.handleKeyUp = function(event, elem)
{
    var that = this;
    var labelId = this.editedLabelId;
    var newValue = $(elem).val();
    if (event.keyCode == '27' || event.keyCode == '13')
    {
        if (event.keyCode == '13')
        {
            var updatedLabel = LabelManager.getInstance().getLabel(labelId.id2Str());
            if($(elem).parent().hasClass('name') && updatedLabel.name != newValue)
            {
                updatedLabel.name = newValue;
            }
            else if($(elem).parent().hasClass('description') && updatedLabel.description != newValue)
            {
                updatedLabel.description = newValue;
            }
            else
            {
                this.refresh();
                return;
            }
            LabelManager.getInstance().saveLabelAJAX(updatedLabel, function(result)
            {
                if (result.success && result.checkObject())
                {
                    that.refresh();
                    $('tr#' + labelId).css('font-weight', 'bold');
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