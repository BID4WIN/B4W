/**
 * Class Label.
 * 
 * @author Maxime Ollagnier
 */

Label.inherits(Bean);
function Label(label)
{
    /** Id of the label */
    this.id = label.id;
    /** Name of the label */
    this.name = label.name;
    /** Description of the label */
    this.description = label.description;
}