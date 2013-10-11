/**
 * Class Image.
 * 
 * @author Maxime Ollagnier
 */

Image.inherits(Bean);
function Image(image)
{
    /** Id of the image */
    this.id = image.id;
    
    /** Name of the image */
    this.name = image.name;
}

/**
 * Returns the id
 */
Image.prototype.getId = function()
{
    return this.id;
};

/**
 * Returns the name
 */
Image.prototype.getName = function()
{
    return this.name;
};