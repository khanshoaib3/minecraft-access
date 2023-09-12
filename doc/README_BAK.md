# Minecraft Access


*Note that you may have to enable the narrator from within minecraft, in the first sartup, the mod speaks "Initializing minecraft access" but does not speak anything else, then you'll have to use `control + b` to enable the narrator. And for fabric users, you will have to manually un-bound or re-bound the advancement key from l to something else as that is also used by the camera controls feature.*


# Features

## Position Narrator

Adds key bindings to speak the player's position.

### Keybindings and Combinations

1. Speak Player Position Key (default: G) = Speaks the player's x y and z position.
2. Left Alt + X = Speaks only the x position.
3. Left Alt + C = Speaks only the y position.
4. Left Alt + Z = Speaks only the z position.

## Points of Interest

This feature scans the area for entities, ore blocks, doors, levers, etc. and plays a sound at their position (only for
entities and ore blocks).
We can lock onto the closest entity or block with a hotkey.
We can also "mark" a block/entity so that it only scans for that type of block/entity in the area.

### Keybindings and Combinations

1. Locking Key (default: Y) = Locks onto the nearest entity or block.
2. Alt key + Locking Key = Unlocks from the currently locked entity or block.
3. Control Key + Locking Key = Mark the crosshair block/entity.
4. Control Key + Alt key + Locking Key = Unmark the marked block/entity.

### Configuration Options

1. For Blocks
    - Enabled = Enables detecting blocks.
    - Detect Fluid Blocks = Enables detecting fluids.
    - Range = The detection range.
    - Play Sound = Enables playing a sound at ore blocks
    - Sound Volume = The volume of the sound.
    - Play Sound for Other Blocks as well = Enables playing sound for other interactive blocks
    - Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of
      milliseconds.
2. For Entities
    - Enabled = Enables detecting entities.
    - Range = The detection range.
    - Play Sound = Enables playing a sound at entities
    - Sound Volume = The volume of the sound.
    - Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of
      milliseconds.
3. For Entities/Blocks Locking
    - Enabled = Enables the locking feature.
    - Lock on Blocks = Enables locking on to the closest block,
    - Speak Relative Distance to Entity/Block = Speaks the distance to the entity/block on locking.
    - Play Unlocking Sound = Enables playing a sound on unlock.
    - Auto Lock on to Eye of Ender when Used = Enables locking onto the eye of ender when it's used/thrown.
    - Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of
      milliseconds.

## Narrator Menu

A menu that opens up when we press `F4` button.

### Options included in this menu

1. Block and fluid target information = Speaks the name and information of the targeted block. The range is much more
   than normal crosshair target.
2. Block and fluid target position = Speaks the name and position of the targeted block. The range is much more than
   normal crosshair target.
3. Light level = Speaks the light level at the player's current position.
4. Find water = Finds the closest water source block.
5. Find lava = Finds the closest lava source block.
6. Biome = Speaks the name of biome the player is currently in.
7. Time of Day = Speaks the current time of day.
8. XP = Speaks the experience level and progress of the player.
9. Refresh screen Reader = Refreshes the screen reader.
10. Open config menu = Opens the config menu which can be used to configure the mod.

### Keybindings and Combinations

1. Narrator Menu Function Hot Key (default: B) = Holding `F4` then press this key to loop switch between functions in Narrator Menu,
   then press this single key for executing selected function.
2. When Narrator Menu is opened, press the number key corresponding to the function to execute it,
   without pressing `Tab` several times.

### Configuration Options

1. For Find Water and Find Lava, or Fluid Detector:
    - Range = The detection range.
    - Sound Volume = The volume of the sound.

## Health n Hunger

Adds a key bind to narrate/speak the player's health and hunger.

- Speak Player Health and Hunger Key (default: R) = Speaks the health and hunger.

## Player Warnings

This feature warns the player when the health, hunger or air bubble is low.

### Configuration Options

1. Enabled = Enables this feature.
2. Play Sound = Enables playing a sound when the health/hunger/air reaches the threshold.
3. Health Threshold First = The first health threshold.
4. Health Threshold Second = The seconds health threshold.
5. Hunger Threshold = The threshold for hunger/food.
6. Air Threshold = The threshold for air when submerged in water.

## Biome Indicator

Narrates the name of the biome when entering a different biome.

## XP Indicator

Narrates when the player xp level is increased or decreased.

## Durability Indicator

Adds the item durability into the tooltip.

## Fall Detector

Check the surrounding height difference and warn the user with a drop sound effect at the corresponding position.

### Configuration Options

1. Enabled = Enables this feature.
2. Range = The player-centered detection range.
3. Depth Threshold = The threshold for playing the sound effect.
4. Sound Volume = The volume of the sound.
5. Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of
   milliseconds.

## Book Editing

Press Use key while holding a [Book and Quill](https://minecraft.fandom.com/wiki/Book_and_Quill) item to open the book editing screen.
Press Tab key to select buttons, while button is focused, press Space key or Left Mouse key to click it (space can be inputted as text when no button is focused).
Press Page Up and Page Down Key to switch between pages.
Press "Done" button to save your unfinished work and quit editing screen.
Press "Sign" button and enter a title for the book to make it permanently non-editable.


