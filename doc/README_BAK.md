# Minecraft Access


*Note that you may have to enable the narrator from within minecraft, in the first sartup, the mod speaks "Initializing minecraft access" but does not speak anything else, then you'll have to use `control + b` to enable the narrator. And for fabric users, you will have to manually un-bound or re-bound the advancement key from l to something else as that is also used by the camera controls feature.*


# Features

## Inventory Controls

This features lets us use keyboard in inventory screens. Works with all default minecraft screens.
Please note that item speaking in "Scrollable Recipes Group" under "Crafting Screen" will be delayed about one second,
wait after press the slot moving key to hear the item name.

*All key binds are re-mappable(except two keys) from the game's controls menu and these key binds do not interrupt with
any other key with same key.*

### Keybindings and Combinations

1. Up Key (default: I) = Focus to slot above.
2. Right Key (default: L) = Focus to slot right.
3. Down Key (default: K) = Focus to slot down.
4. Left Mouse Click Sim Key (default: `[` (left bracket)) = Simulates left mouse click, picking up and putting down all items in the slot.
5. Right Mouse Click Sim Key (default: `]` (right bracket)) = Simulates right mouse click, picking up half of the items or putting one item down.
6. Left Key (default: J) = Focus to slot left.
7. Group Key (default: C) = Select next group.
8. Left Shift + Group Key = Select previous group.
9. Switch Tab Key (default: V) = Select next tab (only for creative inventory screen and inventory/crafting screen).
10. Left Shift + Switch Tab Key = Select previous tab (only for creative inventory screen and inventory/crafting screen).
11. Toggle Craftable Key (default: R) = Toggle between show all and show only craftable recipes in inventory/crafting screen.
12. Left Shift + Up Key = Select previous page of the Recipe Book. (active when recipe book group is selected).
13. Left Shift + Down Key = Select next page of the Recipe Book. (active when recipe book group is selected).
14. T Key (not re-mappable) = Select the search box.
15. Enter Key (not re-mappable) = Deselect the search box.

### Configuration Options

1. Enabled = Enables this feature.
2. Auto Open Recipe Book (in creative/survival and crafting inventory) = Automatically opens the recipe
   book on opening the inventory.
3. Row and Column Format in Crafting Input Slots = The speaking format of row and column, the first %d
   is for row and second %d is for column.
4. Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of milliseconds.

## Read Crosshair

This feature reads the name of the targeted block, entity or fluid. It also gives feedback when a block is powered by a
redstone signal or when a door is open and other similar cases.

### Configuration Options

1. Enabled = Enables this feature.
2. Speak Block Sides = Enables speaking of the side of block as well.
3. Disable Speaking Consecutive Blocks With Same Name = Disables speaking the block if the previous block was also same.
4. Repeat Speaking Interval (in milliseconds) = Repeat speaking the block you are facing for the given amount of time, even you haven't moved the camera.
   Zero for tuning it off, default: 5000.
5. Enable Partial Speaking = Let the mod only speak entities/blocks that you've configured.
6. Partial Speaking White List Mode = If true, only speak what you've configured, if false for blacklist mode, only not spak what you've configured.
7. Partial Speaking Fuzzy Mode = Whether to do fuzzy matching, for example "bed" will match all colors of beds, "door" will match all textures of doors.
8. Partial Speaking Target Mode = Which type would you like to apply this feature to, either "all", "entity" or "block".
   This configuration can only be configured in config.json file.
9. Partial Speaking Targets = Indicated what to be spoken.
   This configuration can only be configured in config.json file.
   Values are written in Minecraft resource location format,
   the so-called "snake_case", and consists of lowercase letters with underscores.
   For example, the White Bed is written in "white_bed" and pronounced as "white underscore bed".
   There are lots of exceptions, the "Smooth Quartz Block" is written in "smooth_quartz", the "Block of Diamond" is written in "diamond_block",
   so please check the correct values in this wiki link:
   https://minecraft.fandom.com/wiki/Java_Edition_data_values#Blocks

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

## Facing Direction

Adds key binding to speak the player's facing direction.

- Speak Facing Direction Key (default: H) = Speaks the player facing direction.

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


