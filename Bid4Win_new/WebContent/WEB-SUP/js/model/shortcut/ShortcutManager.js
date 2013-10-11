/**
 * Pseudo-class ShortcutManager.
 * 
 * @author Maxime Ollagnier
 */

function ShortcutManager()
{
}

/** TODO */
ShortcutManager.shortcuts = new Array();

/** TODO */
ShortcutManager.shift = false;

/** TODO */
ShortcutManager.ctrl = false;

/** TODO */
ShortcutManager.alt = false;

/**
 * TODO
 */
ShortcutManager.init = function()
{
    $(document).bind('keydown', ShortcutManager.handleKeyDown);
    $(document).bind('keyup', ShortcutManager.handleKeyUp);
};

/**
 * TODO
 */
ShortcutManager.handleKeyDown = function(event)
{
    if (event.keyCode == ShortcutManager.keyCode['shift'])
    {
        ShortcutManager.shift = true;
    }
    else if (event.keyCode == ShortcutManager.keyCode['ctrl'])
    {
        ShortcutManager.ctrl = true;
    }
    else if (event.keyCode == ShortcutManager.keyCode['alt'])
    {
        ShortcutManager.alt = true;
    }
};

/**
 * TODO
 */
ShortcutManager.handleKeyUp = function(event)
{
    if (event.keyCode == ShortcutManager.keyCode['shift'])
    {
        ShortcutManager.shift = false;
    }
    else if (event.keyCode == ShortcutManager.keyCode['ctrl'])
    {
        ShortcutManager.ctrl = false;
    }
    else if (event.keyCode == ShortcutManager.keyCode['alt'])
    {
        ShortcutManager.alt = false;
    }
    else
    {
        for ( var i = 0; i < ShortcutManager.shortcuts.length; i++)
        {
            if (ShortcutManager.shortcuts[i].isCorrectEvent(event))
            {
                ShortcutManager.shortcuts[i].callback();
            }
        }
    }
};

ShortcutManager.keyCode =
{
    'backspace' : 8,
    'tab' : 9,
    'enter' : 13,
    'shift' : 16,
    'ctrl' : 17,
    'alt' : 18,
    'pause' : 19,
    'caps_lock' : 20,
    'escape' : 27,
    'page_up' : 33,
    'page_down' : 34,
    'end' : 35,
    'home' : 36,
    'left_arrow' : 37,
    'up_arrow' : 38,
    'right_arrow' : 39,
    'down_arrow' : 40,
    'insert' : 45,
    'delete' : 46,
    '0' : 48,
    '1' : 49,
    '2' : 50,
    '3' : 51,
    '4' : 52,
    '5' : 53,
    '6' : 54,
    '7' : 55,
    '8' : 56,
    '9' : 57,
    'a' : 65,
    'b' : 66,
    'c' : 67,
    'd' : 68,
    'e' : 69,
    'f' : 70,
    'g' : 71,
    'h' : 72,
    'i' : 73,
    'j' : 74,
    'k' : 75,
    'l' : 76,
    'm' : 77,
    'n' : 78,
    'o' : 79,
    'p' : 80,
    'q' : 81,
    'r' : 82,
    's' : 83,
    't' : 84,
    'u' : 85,
    'v' : 86,
    'w' : 87,
    'x' : 88,
    'y' : 89,
    'z' : 90,
    'f1' : 112,
    'f2' : 113,
    'f3' : 114,
    'f4' : 115,
    'f5' : 116,
    'f6' : 117,
    'f7' : 118,
    'f8' : 119,
    'f9' : 120,
    'f10' : 121,
    'f11' : 122,
    'f12' : 123
};