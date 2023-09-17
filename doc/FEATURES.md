# Minecraft Access Features

This page contains details of all the features that are currently in the mod.
If you have any suggestion on improvements existing features or about a new feature, you can [join the discord server](https://discord.gg/yQjjsDqWQX) or [post an issue](https://github.com/khanshoaib3/minecraft-access/issues).

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Read Crosshair](#read-crosshair)
    * [Partial Speaking](#partial-speaking)
4. [Inventory Controls](#inventory-controls)
5. [Point of Interest](#point-of-interest)
   * [POI Locking](#poi-locking)
   * [POI Marking](#poi-marking)
6. [Position Narrator](#position-narrator)
7. [Fall Detector](#fall-detector)
8. [Narrator Menu](#narrator-menu)
9. [Player Warnings](#player-warnings)
10. [Book Editing](#book-editing)

## Camera Controls

This feature allows you to control the camera (your in-game facing direction and crosshair) through the keyboard.
The mod will automatically report the current direction as the camera moves, such as "North" "South East" "Up" "Down" "Straight".

See also: [Keybindings](/doc/KEYBINDINGS.md#camera-controls), [Configuration](/doc/CONFIG.md#camera-controls)

## Mouse Simulation

This feature allows you to simulate five mouse operations (left middle right click, scroll up and down) through the keyboard, while keep original mouse input still working.
This feature supports continuous key pressing, e.g. destroying a block requires continuous press of the left mouse button against that block.

You MUST keep the vanilla `Attack/Destroy` key, `Use Item/Place Block` key and `Pick Block` key remain on default mouse keys. This feature will only simulate mouse operations, not directly execute the attack or place operation, it needs mouse key bindings as a medium.

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
In fact this is a feature makes almost all screens accessible, not just for the inventory screen.
The vast majority of screens contain operations for transferring and using items, so it's appropriate to call this feature `Inventory Controls`.

We divide the various parts of screens into different slot groups, following the original design of screens.
One slot group contains one to more slots, arranged in a grid shape, each of which may be empty or contain one type of items (one stack at max).
You can switch focusing group, move to different slots within a group, or pick up items from slots in one group and place them into slots in another group (to transfer or use them).

Use mouse simulation keys in [`Mouse Simulation`](#mouse-simulation) feature to preform operations such as item transferring and button clicking.
Left mouse key will pick up and put down full number of items in the slot.
Right mouse key will pick up half number of items or put one item down. 
Middle mouse key can be used in creative mode to pick up a full stack of item from the item list.
The mod will speak what is currently in the slot, such as "Empty Slot" or "64 Stone".
When you pick up full number of items in a slot, the mod will speak "Empty Slot" to represent current state of the slot.
When you put down grabbing items in a occupied slot, items in the slot will switch with what in your hand, now you're grabbing the items originally in that slot.

Please note that:

1. Item speaking in "Scrollable Recipes Group" under "Crafting Screen" will be delayed about one second, please wait after press the slot moving key to hear the item name.

2. In "Scrollable Recipes Group" under "Crafting Screen", except the first page, sometimes you will hear nothing while you moving between slots. It's best to keep items you want to craft within the first page of the recipe book by moving unused resource items out of your inventory.

See also: [Configuration](/doc/CONFIG.md#inventory-controls), [Keybindings](/doc/KEYBINDINGS.md#inventory-controls)

## Point of Interest

This feature will scan and notify (with sound cues) you of (pre-configured) special blocks and all types of entity around you.

What blocks are considered special? Those you tend to miss.
In Minecraft, not every block takes up the entire space of a block.
Buttons are tiny and ladders is only a thin layer attached to the wall.
You'll find it difficult to point precisely at these blocks.
Precious blocks like raw ores can be seen at a glance by sighted players, but visually impaired players will need to point at them to know their existence.

By the same token, entities are movable, so scanning and locking onto them helps you get close to and interact with them.

| Sound Cue                                                                                             | Description                                                                                                                                                                                             |
|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Functional blocks without screens (like door, ladder)                                                 | ["Bit" (Square wave) of Note Block](https://minecraft.fandom.com/wiki/Note_Block#Notes), a beeping electronic sound                                                                                     |
| Functional blocks that have screens (like crafting table)                                             | [Banjo of Note Block](https://minecraft.fandom.com/wiki/Note_Block#Notes), a string instrument sound                                                                                                    |
| [Ore blocks](https://minecraft.fandom.com/wiki/Ore)                                                   | [Item plops](https://minecraft.fandom.com/wiki/Item_(entity)#Sounds), bubbling sound, like the sound of pulling out a toilet plunger                                                                    |
| [Item (entity)](https://minecraft.fandom.com/wiki/Item_(entity))                                      | [Pressure Plate clicks](https://minecraft.fandom.com/wiki/Pressure_Plate#Unique), crisp clacking sound, like sound of a lighter, or clicking on buttons                                                 |
| [Passive mobs, Neutral mobs or Players](https://minecraft.fandom.com/wiki/Mob?so=search#Passive_mobs) | [Bells (Glockenspiel) of Note Block](https://minecraft.fandom.com/wiki/Note_Block#Notes), with the lowest pitch, like the sound of a little bell                                                        |
| [Hostile mobs](https://minecraft.fandom.com/wiki/Mob?so=search#Hostile_mobs)                          | [Bells (Glockenspiel) of Note Block](https://minecraft.fandom.com/wiki/Note_Block#Notes) again, with the highest pitch, brief and sharp, like the sound of a time bomb, and you can hear their low roar |
| Unlocking                                                                                             | [Bass Drum (Kick) of Note Block](https://minecraft.fandom.com/wiki/Note_Block#Notes) with the highest pitch, but it's still pretty low                                                                  |

See also: [Configuration](/doc/CONFIG.md#point-of-interest), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

### POI Locking

You can lock onto the closest target with a key, then your camera will follow the target as it moves, so you can approach it more easily.
For example when you want to mine an ore, capture an animal or fight a monster.

See also: [Configuration](/doc/CONFIG.md#entitiesblocks-locking), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

### POI Marking

Sometimes you may need to search for a special type of block or entity that is not in the pre-configured list, here is the feature you want.
You can mark one type while you are pointing at one of them (with your crosshair).
You can set the mod to only scan and notify on marked type, suppress scanning and notifying on pre-configured ones.

See also: [Configuration](/doc/CONFIG.md#entitiesblocks-marking), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

## Position Narrator

Minecraft has a built-in [absolute coordinate system](https://minecraft.fandom.com/wiki/Coordinates), x-axis for the longitude, z for the latitude, and y for the elevation. This feature provides keystrokes to read out coordinates.

See also: [Keybindings](/doc/KEYBINDINGS.md#position-narrator)

## Fall Detector

This feature alerts you with a sound cue when you're near the edge of a big drop.
It will play a sound effect at every location meets the set threshold, the louder the sound, the closer you are to the edge.

| Sound Cue                   | Description                                                                                                                        |
|-----------------------------|------------------------------------------------------------------------------------------------------------------------------------|
| Position with high drop-off | [Breaking the Anvil](https://minecraft.fandom.com/wiki/Anvil#Generic), same sound as destroying blocks, like the sound of footstep |

See also: [Configuration](/doc/CONFIG.md#fall-detector)

## Narrator Menu

We also call it the `F4 Menu` because it requires pressing the `F4` key to open.
This menu integrates a number of helper functions, and you can execute them by opening the menu or by using the hotkey directly.

| Function                              | Description                                                                                                                                                                                                    |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. Block and fluid target information | Speak the name and information of the targeted block, the range (20 blocks) is much more than the `Read Crosshair` triggering distance (6 blocks).                                                             |
| 2. Block and fluid target position    | Speak the x y z position of the targeted block                                                                                                                                                                 |
| 3. Light level                        | Speak the [light](https://minecraft.fandom.com/wiki/Light) level of the player's position                                                                                                                      |
| 4. Find Closest Water Source          | Find the closest [water](https://minecraft.fandom.com/wiki/Water) source block in the set range, play a [Item plops](https://minecraft.fandom.com/wiki/Item_(entity)#Sounds) bubbling sound at target position |
| 5. Find Closest Lava Source           | Find the closest [lava](https://minecraft.fandom.com/wiki/Lava) source block in the set range,  play a [Item plops](https://minecraft.fandom.com/wiki/Item_(entity)#Sounds) bubbling sound at target position  |
| 6. Biome                              | Speak the name of [biome](https://minecraft.fandom.com/wiki/Biome) the player is currently in                                                                                                                  |
| 7. Time of Day                        | Speak current time of the in-game [Daylight Cycle](https://minecraft.fandom.com/wiki/Daylight_cycle)                                                                                                           |
| 8. XP                                 | Speak the player's current [experience](https://minecraft.fandom.com/wiki/Experience) level and progress                                                                                                       |
| 9. Refresh screen Reader              | Refresh the screen Reader                                                                                                                                                                                      |
| 0. Open Config Menu                   | Opens the Config Menu which can be used to change the configs of this mod that take effect immediately                                                                                                         |

See also: [Configuration](/doc/CONFIG.md#narrator-menu), [Keybindings](/doc/KEYBINDINGS.md#narrator-menu)

## Player Warnings

This feature warns you when your [health](https://minecraft.fandom.com/wiki/Health), [hunger](https://minecraft.fandom.com/wiki/Hunger) and [air](https://minecraft.fandom.com/wiki/Damage#Drowning) (when you're submerged in water) below the set thresholds.
You'll hear something like "Warning, Health is {current health}".
If you enable the `Play Sound` config, you'll also hear a sound cue along with the warning words.
You may want to learn about [the various damage types](https://minecraft.fandom.com/wiki/Damage) in the game.

| Sound Cue                  | Description                                                                           |
|----------------------------|---------------------------------------------------------------------------------------|
| Status reach the threshold | [Anvil landing](https://minecraft.fandom.com/wiki/Anvil#Unique), crisp metallic sound |

See also: [Configuration](/doc/CONFIG.md#player-warnings)

## Book Editing

The [Book Editing Screen](https://minecraft.fandom.com/wiki/Book_and_Quill#Writing) is special in that it has nothing to do with item management and has no slot groups.
Almost all keys in this screen are provided by the original game.
This section as a brief guide on how to use this screen, listing the keys corresponding to the functions.

See also: [Keybindings](/doc/KEYBINDINGS.md#book-editing)

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)