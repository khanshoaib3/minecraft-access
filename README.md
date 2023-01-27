# Minecraft Access

Minecraft Access is a replacement of the other accessibility [mods.](https://github.com/accessible-minecraft) Just like
the previous mods, this mod also supports screen readers for windows, but now it also supports additional screen readers
thanks to [tolk](https://github.com/ndarilek/tolk) library. Additionally, the mod also supports linux using
the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper).
Note that not all features have been ported over to this mod.

# Setup

## Requirements

1. The mod works with both forge and fabric, so you need to install either one of those. For forge, you can
   follow [this](https://thebreakdown.xyz/how-to-download-install-forge-to-play-minecraft-mods/) guide and for fabric
   you can follow [this](https://thebreakdown.xyz/how-to-download-install-the-fabric-mod-loader/) guide.
2. We also need to install the [architectury api](https://www.curseforge.com/minecraft/mc-mods/architectury-api) mod.
3. Windows specific:-
   In windows we need to install the [tolk](https://github.com/ndarilek/tolk) library. Here are the steps to install the
   library:-
    - Head over to the [description](https://github.com/ndarilek/tolk#tolk-screen-reader-abstraction-library) of the
      library and download
      the [latest build](https://github.com/ndarilek/tolk/releases/download/refs%2Fheads%2Fmaster/tolk.zip).
    - Extract the downloaded zip file anywhere you like, the Downloads folder should be fine.
    - Open the extracted folder and in that folder you'll find a folder called `x64` and another called `x86`.
    - Based on your operating system, `x64` for 64-bit OS and `x86` for 32-bit OS, copy all the files present in the
      folder and paste them into the minecraft root folder.
      For windows the default location is -
      %appdata%\.minecraft
4. Linux specific:-
   In linux we need to install the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper) as well
   as [xdotool](https://github.com/jordansissel/xdotool) which is used for simulating the mouse actions.
   Although the mod overrides the library used for tts, minecraft still needs the `flite` library to be installed, so
   you can install it using your distro's package manager itself.
   For libspeechdwrapper, [download](https://github.com/khanshoaib3/libspeechdwrapper/raw/main/lib/libspeechdwrapper.so)
   the library and move it over to the minecraft folder.
   For linux the default location is -
   ~/.minecraft
   Follow the [installation instruction](https://github.com/jordansissel/xdotool#installation) in the description to
   install xdotool.

## Mod Installation

- Download the mod file according to the loader installed from
  the [latest release page](https://github.com/khanshoaib3/minecraft-access/releases).
- Move the downloaded jar file over to the mods folder.
  For windows the default location is - `%appdata%\.minecraft\mods`
  And for linux it is - `~/.minecraft/mods`

# Features

## Camera Controls

This feature adds the following key bindings to control the camera through the keyboard.

**Key bindings and combinations:-**

1. Look Up Key (default: I) or Alternate Look Up Key(default: Keypad 8) = Moves the camera vertically up by the normal rotating angle (default=22.5).
2. Look Right Key (default: L) or Alternate Look Right Key(default: Keypad 6) = Moves the camera vertically right by the normal rotating angle (default=22.5).
3. Look Down Key (default: K) or Alternate Look Down Key(default: Keypad 2) = Moves the camera vertically down by the normal rotating angle (default=22.5).
4. Look Left Key (default: J) or Alternate Look Up Key(default: Keypad 4) = Moves the camera vertically left by the normal rotating angle (default=22.5).
5. Left Alt + Look Up Key = Moves the camera vertically up by the modified rotating angle (default=11.25).
6. Left Alt + Look Right Key = Moves the camera vertically right by the modified rotating angle (default=11.25).
7. Left Alt + Look Down Key = Moves the camera vertically down by the modified rotating angle (default=11.25).
8. Left Alt + Look Left Key = Moves the camera vertically left by the modified rotating angle (default=11.25).
9. Right Alt + Look Up Key or Look North Key (default: Keypad 7) = Snaps the camera to the north block.
10. Right Alt + Look Right Key or Look East Key (default: Keypad 9) = Snaps the camera to the east block.
11. Right Alt + Look Down Key or Look South Key (default: Keypad 3) = Snaps the camera to the south block.
12. Right Alt + Look Left Key or Look West Key (default: Keypad 1) = Snaps the camera to the west block.
13. Center Camera (default: Keypad 5) = Snaps the camera to the closest cardinal direction and centers it.
14. Left Alt + Center Camera = Snaps the camera to the closest opposite cardinal direction and centers it.

**Configuration Options:-**

1. Enabled = Enables this feature.
2. Normal Rotating Angle = The rotation angle when we press the keys without holding down the left alt
   key.
3. Modified Rotating Angle = The rotation angle when we press the keys while holding down the left alt
   key.
4. Delay = Pauses the execution of this feature after each cycle for the given amount of milliseconds.

## Inventory Controls

This features lets us use keyboard in inventory screens. Works with all default minecraft screens.

*All key binds are re-mappable(except two keys) from the game's controls menu and these key binds do not interrupt with
any other key with same key.*

**Keybindings and combinations:-**

1. Up Key (default: I) = Focus to slot above.
2. Right Key (default: L) = Focus to slot right.
3. Down Key (default: K) = Focus to slot down.
4. Left Mouse Click Sim Key (default: [) = Simulates left mouse click.
5. Left Key (default: J) = Focus to slot left.
6. Group Key (default: C) = Select next group.
7. Left Shift + Group Key = Select previous group.
8. Switch Tab Key (default: V) = Select next tab (only for creative inventory screen and inventory/crafting screen).
9. Left Shift + Switch Tab Key = Select previous tab (only for creative inventory screen and inventory/crafting screen).
10. Toggle Craftable Key (default: R) = Toggle between show all and show only craftable recipes in inventory/crafting
    screen.
11. Left Mouse Click Sim Key (default: [) = Simulates left mouse click.
12. Right Mouse Click Sim Key (default: ]) = Simulates right mouse click.
13. T Key (not re-mappable) = Select the search box.
14. Enter Key (not re-mappable) = Deselect the search box.

**Configuration Options:-**

1. Enabled = Enables this feature.
2. Auto Open Recipe Book (in creative/survival and crafting inventory) = Automatically opens the recipe
   book on opening the inventory.
3. Row and Column Format in Crafting Input Slots = The speaking format of row and column, the first %d
   is for row and second %d is for column.
4. Delay (in milliseconds) = Pauses the execution of this feature after each cycle for the given amount of milliseconds.

## Read Crosshair

This feature reads the name of the targeted block, entity or fluid. It also gives feedback when a block is powered by a
redstone signal or when a door is open and other similar cases.

**Configuration Options:-**

1. Enabled = Enables this feature.
2. Speak Block Sides = Enables speaking of the side of block as well.
3. Disable Speaking Consecutive Blocks With Same Name = Disables speaking the block if the previous block was also same.

## Position Narrator

Adds key bindings to speak the player's position.

**Keybindings and combinations:-**

1. Speak Player Position Key (default: G) = Speaks the player's x y and z position.
2. Left Alt + X = Speaks only the x position.
3. Left Alt + C = Speaks only the y position.
4. Left Alt + Z = Speaks only the z position.

## Points of Interest

This feature scans the area for entities, ore blocks, doors, levers, etc. and plays a sound at their position (only for
entities and ore blocks).
We can also lock onto the closest entity or block with a hotkey.

**Keybindings and combinations:-**

1. Locking Key (default: Y) = Locks onto the nearest entity or block
2. Alt key + Locking Key = Unlocks from the currently locked entity or block

**Configuration Options:-**

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

**Options included in this menu:-**

1. Block and fluid target information = Speaks the name and information of the targeted block. The range is much more than normal crosshair target.
2. Block and fluid target position = Speaks the name and position of the targeted block. The range is much more than normal crosshair target.
3. Light level = Speaks the light level at the player's current position.
4. Find water = Finds the closest water source block.
5. Find lava = Finds the closest lava source block.
6. Biome = Speaks the name of biome the player is currently in.
7. XP = Speaks the experience level and progress of the player.

## Health n Hunger

Adds a key bind to narrate/speak the player's health and hunger.

- Speak Player Health and Hunger Key (default: R) = Speaks the health and hunger.

## Player Warnings

This feature warns the player when the health, hunger or air bubble is low.

**Configuration Options:-**

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

# Mod Configuration

The mod can be configured by the config.json file in the `config/minecraft_access/` directory.
You can use notepad or any text editor to edit the file. You can also use online json editor like
[Json Editor Online](https://jsoneditoronline.org/), [Json Formatter](https://jsonformatter.org/json-editor)
or [Code Beautify Json Online Editor](https://jsonformatter.org/json-editor).
