# Minecraft Access Features

This page contains details of all the features that are currently in the mod.
If you have any suggestion on improvements existing features or about a new feature, you can [join the discord server](https://discord.gg/yQjjsDqWQX) or [post an issue](https://github.com/khanshoaib3/minecraft-access/issues).

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Read Crosshair](#read-crosshair)
    * [Partial Speaking](#partial-speaking)
4. [Inventory Controls](#inventory-controls)

## Camera Controls

This feature allows you to control the camera (your in-game facing direction and crosshair) through the keyboard.
The mod will automatically report the current direction as the camera moves, such as "North" "South East" "Up" "Down" "Straight".

See also: [Keybindings](/doc/KEYBINDINGS.md#camera-controls), [Configuration](/doc/CONFIG.md#camera-controls)

## Mouse Simulation

This feature allows you to simulate five mouse operations (left middle right click, scroll up and down) through the keyboard, while keep original mouse input still working.
This feature supports continuous key pressing, e.g. destroying a block requires continuous press of the left mouse button against that block.

Please note that:

1. This feature only works when no screen is opened.
2. You need to keep the vanilla `Attack/Destroy` key, `Use Item/Place Block` key and `Pick Block` key remain on default mouse keys.

See also: [Keybindings](/doc/KEYBINDINGS.md#mouse-simulation), [Configuration](/doc/CONFIG.md#mouse-simulation)

## Read Crosshair

This mod will automatically speak the information of the block and entity (i.e., living creatures and moving objects) you are targeting with your crosshair (i.e., what you're looking at).
The crosshair is in the center of the screen.
This mod has no corresponding keybinding.

Additionally, if you're looking at a block, the mod will speak which side of that block you are looking at, it's useful for recognizing directions and placing blocks accurately.
For simple targets, the mod will only speak the name (and side if it's a block), such as "Pig", "Cow", "Grass Block Up", "Stone North".
For functional blocks or entities with multiple forms, the mod will also speak current state of the block, such as "Ripe Wheat Crops Up", "White Sheep Shearable", "Opened Oak Door South", "Powered Dispenser West Facing East".

See also: [Configuration](/doc/CONFIG.md#read-crosshair)

### Partial Speaking

Let the mod only (or only not) speak entities/blocks that you've configured. This feature can be easily misused, so it is not enabled by default. A user said that she didn't want to hear the grass block anymore, this feature is the solution to her problem once and for all.

See also: [Configuration](/doc/CONFIG.md#partial-speaking)

## Inventory Controls

This feature allows you to operate various screens using the keyboard instead of the mouse.
In fact this is a feature makes all screens accessible, not just for the inventory screen.
The vast majority of screens contain operations for transferring and using items, so it's appropriate to call this feature `Inventory Controls`.
This feature has independent keybindings for simulating mouse buttons and wheel, not same as the `Mouse Simulation` feature.

We divide the various parts of screens into different slot groups, following the original design of screens.
One slot group contains one to more slots, arranged in a table shape, each of which may be empty or contain one type of items (one stack at max).
You can switch focusing group, move to different slots within a group, or pick up items from slots in one group and place them into slots in another group (to transfer or use them).

The mod will speak what is currently in the slot, such as "Empty Slot" or "64 Stone".
When you pick up full number of items in a slot, the mod will speak "Empty Slot" to represent current state of the slot.
When you put down grabbing items in a occupied slot, items in the slot will switch with what in your hand, now you're grabbing the items originally in that slot.

Please note that:

1. Item speaking in "Scrollable Recipes Group" under "Crafting Screen" will be delayed about one second, please wait after press the slot moving key to hear the item name.
2. In "Scrollable Recipes Group" under "Crafting Screen", except the first page, sometimes you will hear nothing while you moving between slots. It's best to keep items you want to craft within the first page of the recipe book by moving unused resource items out of your inventory.

See also: [Feature Description](/doc/FEATURES.md#inventory-controls), [Configuration](/doc/CONFIG.md#inventory-controls), [Keybindings](/doc/KEYBINDINGS.md#inventory-controls)

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)