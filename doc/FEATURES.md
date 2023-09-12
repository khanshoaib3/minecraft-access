# Minecraft Access Features

This page contains details of all the features that are currently in the mod.
If you have any suggestion on improvements existing features or about a new feature, you can [join the discord server](https://discord.gg/yQjjsDqWQX) or [post an issue](https://github.com/khanshoaib3/minecraft-access/issues).

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Read Crosshair](#read-crosshair)
    * [Partial Speaking](#partial-speaking)

## Camera Controls

This feature allows you to control the camera (your in-game facing direction and crosshair) through the keyboard.
The mod will automatically report the current direction as the camera moves, such as "North" "South East" "Up" "Down" "Straight".

See also: [Keybindings](/doc/KEYBINDINGS.md#camera-controls), [Configuration](/doc/CONFIG.md#camera-controls)

## Mouse Simulation

This feature allows you to simulate five mouse operations (left middle right click, scroll up and down) through the keyboard, while keep original mouse input still working.
Please note that keys in this feature only work when no screen opened.
You need to keep the vanilla `Attack/Destroy` key, `Use Item/Place Block` key and `Pick Block` key remain on default mouse keys.

See also: [Keybindings](/doc/KEYBINDINGS.md#mouse-simulation), [Configuration](/doc/CONFIG.md#mouse-simulation)

## Read Crosshair

This mod will automatically speak the information of the block and entity (i.e., living creatures and moving objects) you are targeting with your crosshair (i.e., looking at). The crosshair is in the center of the screen.

Additionally, if you're looking at a block, the mod will speak which side of that block you are looking at, it's useful for recognizing directions and placing blocks accurately.
For simple targets, the mod will only speak the name (and side if it's a block), such as "Pig", "Cow", "Grass Block Up", "Stone North".
For functional blocks or entities with multiple forms, the mod will also speak current state of the block, such as "Ripe Wheat Crops Up", "White Sheep Shearable", "Opened Oak Door South", "Powered Dispenser West Facing East".

See also: [Configuration](/doc/CONFIG.md#read-crosshair)

### Partial Speaking

Let the mod only (or only not) speak entities/blocks that you've configured. This feature can be easily misused, so it is not enabled by default. A user said that she didn't want to hear the grass block anymore, this feature is the solution to her problem once and for all.

See also: [Configuration](/doc/CONFIG.md#partial-speaking)

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)