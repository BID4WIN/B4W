/**
 * Class Property.
 * 
 * @author Maxime Ollagnier
 */

Property.inherits(Bean);
function Property(jsonProperty, parentProperty)
{
    /** Key of the property */
    this.key = jsonProperty.key;
    
    /** Value of the property */
    this.value = jsonProperty.value;
    
    /** Sub properties Map */
    this.properties = new PropertyMap(jsonProperty.properties, this);
    
    /** The parent property */
    this.parentProperty = parentProperty;
    
    /** Expanded flag for admin table */
    this.expanded = false;
}

/**
 * Returns the id of the property
 */
Property.prototype.getId = function()
{
    return this.getFullKey();
};

/**
 * Returns the JSON object
 */
Property.prototype.getJSON = function()
{
    var json =
    {
        key : this.getFullKey(),
        value : this.value
    };
    return json;
};

/**
 * Returns the full hierarchical key of the property
 */
Property.prototype.getFullKey = function()
{
    if (typeof this.parentProperty == 'undefined')
    {
        return this.key;
    }
    return this.parentProperty.getFullKey() + '.' + this.key;
};

/**
 * Returns the first key of the property
 */
Property.prototype.getFirstKey = function()
{
    if (typeof this.parentProperty == 'undefined')
    {
        return this.key;
    }
    return this.parentProperty.getFirstKey();
};