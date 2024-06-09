# Minecraft Access Features

This page contains details of all the features that are currently in the mod.

If you have any question about original game functions, please search on the [wiki](https://minecraft.wiki/?search) first before asking for help.
If you have any suggestion on improvements existing features or about a new feature, you can [join the discord server](https://discord.gg/yQjjsDqWQX) or [post an issue](https://github.com/khanshoaib3/minecraft-access/issues).

There is a page for [viewing all sound effects](https://html-preview.github.io/?url=https://github.com/khanshoaib3/minecraft-access/blob/1.20/doc/SOUND.html) used in this mod.

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Read Crosshair](#read-crosshair)
    * [Relative Position Sound Cue](#relative-position-sound-cue)
    * [Partial Speaking](#partial-speaking)
4. [Inventory Controls](#inventory-controls)
5. [Point of Interest](#point-of-interest)
    * [POI Locking](#poi-locking)
    * [POI Marking](#poi-marking)
6. [Fall Detector](#fall-detector)
7. [Narrator Menu](#narrator-menu)
8. [Play Status](#player-status)
    * [Position Narrator](#position-narrator)
    * [Health n Hunger](#health-n-hunger)
    * [Player Warnings](#player-warnings)
9. [Book Editing](#book-editing)
10. [Speak Text Editing](#speak-text-editing)
11. [Other Small Features](#other-small-features)
    * [Speak held item](#speak-held-item)
    * [Biome Indicator](#biome-indicator)
    * [XP Indicator](#xp-indicator)
    * [Speak Harvest Of Fishing](#speak-harvest-of-fishing)
    * [Speak Chat Messages](#speak-chat-messages)
    * [Speak Action Bar Updates](#speak-action-bar-messages)
    * [I18N Fallback Mechanism](#i18n-fallback-mechanism)

## Camera Controls

Like most 3D games, you can rotate the camera 360 degrees freely in the Minecraft with mouse, and the direction you look is the direction to go forward while your press the W key.
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

If you're looking at a block, the mod will speak which side of that block you are looking at, it's useful for recognizing directions and placing blocks accurately.
For example if the mod says "Stone North", it means you're looking at the north side of a stone block, this indicates that you are within 180 degrees of where this side is facing, not necessarily directly in front of it.
You can determine your current location with the help of your relative position to the static block.

For simple targets, the mod will only speak the name (and side if it's a block), such as "Pig", "Cow", "Grass Block Up", "Stone North".
For functional blocks or entities with multiple forms, the mod will also speak current state of the block, such as "Ripe Wheat Crops Up", "White Sheep Shearable", "Opened Oak Door South", "Powered Dispenser West Facing East".

According to [wiki](https://minecraft.wiki/w/Breaking#Basics_of_breaking), the breaking distance in survival mode is 4.5 blocks (in Java Edition), but this `ReadCrosshair` feature will speak targets at most 6 blocks away. So if the mod says something but you can't interact with it, move forward a little closer to it.

See also: [Configuration](/doc/CONFIG.md#read-crosshair)

### Relative Position Sound Cue

Whenever you're looking at a block and entity, the mod will play a piano sound cue to indicate relative location between you and target.
Volume to represent distance, the louder the sound the closer the distance.
Pitch to represent elevation, the higher the sound the higher the target relative to you.
You can turn off this feature or change sound volume in config.

See also: [Configuration](/doc/CONFIG.md#relative-position-sound-cue)

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
When you put down grabbing items in an occupied slot, items in the slot will switch with what in your hand, now you're grabbing the items originally in that slot.

Please note that:

1. Item speaking in "Scrollable Recipes Group" under "Crafting Screen" will be delayed about one second, please wait after press the slot moving key to hear the item name.
2. In "Scrollable Recipes Group" under "Crafting Screen", except the first page, sometimes you will hear nothing while you moving between slots. It's best to keep items you want to craft within the first page of the recipe book by moving unused resource items out of your inventory.
3. After put things into an [Anvil](https://minecraft.fandom.com/wiki/Anvil), the cursor will be automatically focused onto the rename input text field, all you need to do is pressing "Enter" key to go back to slot selection.
4. Servers will build custom inventory screens to serve as special option menus, some mods like hypixel will continuously changing the option (item) names, which will cause this mod continuously speaking changed item name. Close "Speak Focused Slot Changes" config to solve this problem.

See also: [Configuration](/doc/CONFIG.md#inventory-controls), [Keybindings](/doc/KEYBINDINGS.md#inventory-controls)

## Point of Interest

This feature will scan and notify (with sound cues) you of (pre-configured) special blocks and all types of entity around you. You can listen to the sound cues for different types of blocks and entities on the Sound Demo page.

What blocks are considered special? Those you tend to miss.
In Minecraft, not every block is cube shaped, for example buttons are tiny, single glass block is a thin bar, ladders is a thin layer attached to the wall, you'll find it difficult to point precisely at these blocks.
Precious blocks like raw ores can be seen at a glance by sighted players, but visually impaired players will need to point at them to know their existence.

By the same token, entities (players, animals, monsters) are movable, so scanning and locking onto them helps you get close to and interact with them.
Be aware that if you keep any screen opening, the mod will stop scanning and notifying for entities, for a cleaner screen narration, which may get you in danger if there are monsters around you.

See also: [Configuration](/doc/CONFIG.md#point-of-interest), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

### POI Locking

You can lock onto the closest target with a key, then your camera will follow the target as it moves, so you can approach it more easily.
For example when you want to mine an ore, capture an animal or fight a monster.

Note that:

* The mod will continue locking on the position where the eye of ender disappears (if you enabled "Auto Lock on to Eye of Ender when Used" config).
* The mod will automatically stop locking on ladders when you start climbing on them, so you can directly climb the ladder without manually pressing unlock key.
* The mod will automatically stop locking on blocks if they are destroyed or changed (for example, a [door](https://minecraft.wiki/w/Door) is opened or clocked, or a [piston](https://minecraft.wiki/w/Piston) is activated).

See also: [Configuration](/doc/CONFIG.md#entitiesblocks-locking), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

### POI Marking

Sometimes you may need to search for a special type of block or entity that is not in the pre-configured list, here is the feature you want.
You can mark one type while you are pointing at one of them (with your crosshair).
You can set the mod to only scan and notify on marked type, suppress scanning and notifying on pre-configured ones.

See also: [Configuration](/doc/CONFIG.md#entitiesblocks-marking), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

## Fall Detector

This feature alerts you with a foot stomping sound cue when you're near the edge of a big drop.
It will play a sound effect at every location meets the set threshold, the louder the sound, the closer you are to the edge.

See also: [Configuration](/doc/CONFIG.md#fall-detector)

## Narrator Menu

We also call it the `F4 Menu` because it requires pressing the `F4` key to open.
This menu integrates a number of helper functions, and you can execute them by opening the menu or by using the hotkey directly.

| Function                              | Description                                                                                                                                                                                  |
|---------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1. Block and fluid target information | Speak the name and information of the targeted block, the range (20 blocks) is much more than the `Read Crosshair` triggering distance (6 blocks).                                           |
| 2. Block and fluid target position    | Speak the x y z position of the targeted block                                                                                                                                               |
| 3. Light level                        | Speak the [light](https://minecraft.wiki/w/Light) level of the player's position                                                                                                             |
| 4. Find Closest Water Source          | Find the closest [water](https://minecraft.wiki/w/Water) source block in the set range, play a [Item plops](https://minecraft.wiki/w/Item_(entity)#Sounds) bubbling sound at target position |
| 5. Find Closest Lava Source           | Find the closest [lava](https://minecraft.wiki/w/Lava) source block in the set range,  play a [Item plops](https://minecraft.wiki/w/Item_(entity)#Sounds) bubbling sound at target position  |
| 6. Biome                              | Speak the name of [biome](https://minecraft.wiki/w/Biome) the player is currently in                                                                                                         |
| 7. Time of Day                        | Speak current time of the in-game [Daylight Cycle](https://minecraft.wiki/w/Daylight_cycle)                                                                                                  |
| 8. XP                                 | Speak the player's current [experience](https://minecraft.wiki/w/Experience) level and progress                                                                                              |
| 9. Refresh screen Reader              | Refresh the screen Reader                                                                                                                                                                    |
| 0. Open Config Menu                   | Opens the Config Menu which can be used to change the configs of this mod that take effect immediately                                                                                       |

See also: [Configuration](/doc/CONFIG.md#narrator-menu), [Keybindings](/doc/KEYBINDINGS.md#narrator-menu)

## Player Status

Some features that help you understand the status of the character you control.

### Position Narrator

Minecraft has a built-in [absolute coordinate system](https://minecraft.wiki/w/Coordinates), x-axis for the longitude, z for the latitude, and y for the elevation. This feature provides keystrokes to read out coordinates.

See also: [Configuration](/doc/CONFIG.md#position-narrator), [Keybindings](/doc/KEYBINDINGS.md#position-narrator)

### Health n Hunger

This feature adds a key to speak your health and hunger.

See also: [Configuration](/doc/CONFIG.md#health-n-hunger), [Keybindings](/doc/KEYBINDINGS.md#health-n-hunger)

### Player Warnings

This feature warns you with the sound of metal when your [health](https://minecraft.wiki/w/Health), [hunger](https://minecraft.wiki/w/Hunger) and [air](https://minecraft.wiki/w/Damage#Drowning) (when you're submerged in water) below the set thresholds.
You'll hear something like "Warning, Health is {current health}".
If you enable the `Play Sound` config, you'll also hear a sound cue along with the warning words.
You may want to learn about [the various damage types](https://minecraft.wiki/w/Damage) in the game.

See also: [Configuration](/doc/CONFIG.md#player-warnings)

## Book Editing

The [Book Editing Screen](https://minecraft.wiki/w/Book_and_Quill#Writing) is a special screen that has nothing to do with item management thus has no slot group.
We've added some not re-mappable keys and narration cues to make it 100% accessible.
This section as a brief guide on how to use this screen, listing the keys corresponding to the functions.

See also: [Keybindings](/doc/KEYBINDINGS.md#book-editing)

## Speak Text Editing

The mod will simulate the feedback you get when typing text in other software's input boxes.
It will speak the text that you delete, select, pass with cursor on [Chat Screen](https://minecraft.wiki/w/Chat), [Book Edit Screen](#book-editing), [Command Block Screen](https://minecraft.wiki/w/Command_Block#Modification) and so on.
Note that we have to speak whole unfinished book title while signing a book since you can't move the cursor in that state, which means that the cursor will stay at the end of title and ignoring your cursor moving operations.

The mod also simplifies the original [command](https://minecraft.wiki/w/Commands) suggestion narration for a better typing experience with lesser annoying too-detailed narrations.
The content of one command suggestion narration will be like "{the order of focused suggestion}x{total number of suggestions} {suggestion} selected", the format can be customized in the config.

See also: [Configuration](/doc/CONFIG.md#other-configurations)

## Other Small Features

Some small features that are automatically triggered based on your actions.

See also: [Configuration](/doc/CONFIG.md#other-configurations)

### Speak Held Item

When you switch held items, speak the name and number of items in your hand (main hand only).
The item [durability](https://minecraft.wiki/w/Durability) information is included.
If you feel that continuous item count reporting is annoying, you can close it in the [configuration](/doc/CONFIG.md#other-configurations).

### Biome Indicator

Speak the name of the [biome](https://minecraft.wiki/w/Biome) when you're entering one.

### XP Indicator

Speak when your [experience](https://minecraft.wiki/w/Experience) level is increased or decreased.

### Speak Harvest Of Fishing

In fact this feature will speak the items you pick up while you're holding the rod, whether you're actually fishing or not.

### Speak Chat Messages

Speak [chat](https://minecraft.wiki/w/Chat) messages, so you won't miss your friends' conversation.
If you feel that too many chat messages are making you feel noisy, you can turn off showing chat messages in the original `Chat Settings...` options, or press "P" to open [Social Interactions Screen](https://minecraft.wiki/w/Social_interactions) to mute particular players.

See also: [Keybindings](/doc/KEYBINDINGS.md#speak-chat-messages)

### Speak Action Bar Messages

Messages shown in the form of [action bar](https://minecraft.wiki/w/Commands/title) are common in modded multiplayer servers, usually they are used as server announcements or showing mod-specific information, for example in hypixel it shows extra "health, defense, mana" values of players.
When you feel like the mod is repeating action bar messages, that's because the messages are partially updated but the mod will speak the whole sentence, try to open "Only Speak Action Bar Updates" config in the [configuration](/doc/CONFIG.md#other-configurations). It happens when playing some mods like hypixel.

### I18N Fallback Mechanism

Minecraft has supported many languages, when referring languages that this mod supports, we mean text that introduced by this mod and don't exist in original game.
This mod has a fallback mechanism on I18n in case it fails on unsupported languages, or text that not translated in time in supported languages.
If any text I18N failed on your language, the mod will use the English version instead.
Set the game to your familiar language is recommended, even if it's not supported by this mod, since you can still be benefited from translatable game original text, such as name of blocks or creatures.

## Other Pages

* [Home](/README.md)
* [Set Up](/doc/SET_UP.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)
* [Good Resources](/doc/GOOD_RESOURCES.md)