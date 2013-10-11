/**
 * Class Shortcut.
 * 
 * @author Maxime Ollagnier
 */

function Shortcut(fullKey, callback)
{
    /** TODO */
    this.shift = false;
    
    /** TODO */
    this.ctrl = false;
    
    /** TODO */
    this.alt = false;
    
    /** TODO */
    this.key = '';
    
    /** TODO */
    this.callback = callback;
    
    this.init(fullKey);
}

/**
 * TODO
 */
Shortcut.prototype.init = function(fullKey)
{
    var keyTab = fullKey.replace(/\s/g, '').split('+');
    for ( var i = 0; i < keyTab.length; i++)
    {
        if (keyTab[i] == 'shift')
        {
            this.shift = true;
        }
        else if (keyTab[i] == 'ctrl')
        {
            this.ctrl = true;
        }
        else if (keyTab[i] == 'alt')
        {
            this.alt = true;
        }
        else if (this.key == '')
        {
            this.key = keyTab[i];
        }
    }
};

/**
 * TODO
 */
Shortcut.prototype.isCorrectEvent = function(event)
{
    if (this.shift == ShortcutManager.shift && this.ctrl == ShortcutManager.ctrl && this.alt == ShortcutManager.alt)
    {
        return ShortcutManager.keyCode[this.key] == event.keyCode;
    }
    return false;
};

/**
 * TODO
 */
Shortcut.prototype.equals = function(shortcut)
{
    if (this.shift == shortcut.shift && this.ctrl == shortcut.ctrl && this.alt == shortcut.alt)
    {
        return this.key == shortcut.key;
    }
    return false;
};