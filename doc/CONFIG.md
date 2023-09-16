# Minecraft Access Configuration

This page contains all the configuration that controls the features on-off and behavior.

The configuration can be modified in two ways: via the config menu or directly editing the configuration file.
The `Config Menu` can be opened via opening the `Narrator Menu` (press F4) then click `Open Config Menu` button.
The configuration file (named as `config.json`) can be found in the `{minecraft directory}/config/minecraft_access/` directory.
You can use notepad or any text editor to edit the file.
You can also use online json editor like [Json Editor Online](https://jsoneditoronline.org/), [Json Formatter](https://jsonformatter.org/json-editor) or [Code Beautify Json Online Editor](https://jsonformatter.org/json-editor).

If you want to reset the config back to default values, simply delete the configuration file while the game is not running, or click `Reset Config` button in the `Config Menu`.

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Read Crosshair](#read-crosshair)
    * [Partial Speaking](#partial-speaking)
4. [Inventory Controls](#inventory-controls)
5. [Point of Interest](#point-of-interest)

## Camera Controls

| Configuration           | Default Value | Description                                                                                   |
|-------------------------|---------------|-----------------------------------------------------------------------------------------------|
| Enabled                 | true          | Whether to enable this feature                                                                |
| Normal Rotating Angle   | 22.5          | The rotation angle when we press the camera moving keys                                       |
| Modified Rotating Angle | 11.25         | The rotation angle when we press the camera moving keys while holding down the `Left Alt` key |
| Delay (in milliseconds) | 250           | Cooldown between two feature executions                                                       |

See also: [Feature Description](/doc/FEATURES.md#camera-controls), [Keybindings](/doc/KEYBINDINGS.md#camera-controls)

## Mouse Simulation

| Configuration                  | Default Value | Description                                         |
|--------------------------------|---------------|-----------------------------------------------------|
| Enabled                        | true          | Whether to enable this feature                      |
| Scroll Delay (in milliseconds) | 150           | Cooldown between two mouse wheel scroll simulations |

See also: [Feature Description](/doc/FEATURES.md#mouse-simulation), [Keybindings](/doc/KEYBINDINGS.md#mouse-simulation)

## Read Crosshair

| Configuration                                      | Default Value         | Description                                                                     |
|----------------------------------------------------|-----------------------|---------------------------------------------------------------------------------|
| Enabled                                            | true                  | Whether to enable this feature                                                  |
| Speak Block Sides                                  | true                  | Enable speaking of the side of block as well                                    |
| Disable Speaking Consecutive Blocks With Same Name | true                  | Disable speaking the block if the previous block was also same                  |
| Repeat Speaking Interval (in milliseconds)         | 0 (for tuning it off) | Repeat speaking for the given amount of time, even you haven't moved the camera |

Config `Disable Speaking Consecutive Blocks With Same Name` is useful when you don't want to hear a repetitive speaking of a large area of the same block.

See also: [Feature Description](/doc/FEATURES.md#read-crosshair)

### Partial Speaking

| Configuration   | Default Value                            | Description                                                                                                            |
|-----------------|------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| Enabled         | true                                     | Whether to enable this feature                                                                                         |
| White List Mode | true                                     | If true, only speak what have been configured. Set to false for blacklist mode, only not speak what you've configured  |
| Fuzzy Mode      | true                                     | Whether to do fuzzy matching, for example "bed" will match all colors of beds, "door" will match all textures of doors |
| Target Mode     | "block"                                  | Which type would you like to apply this feature to, either "all", "entity" or "block"                                  |
| Targets         | ["slab","planks","block","stone","sign"] | Indicated what to be spoken                                                                                            |

The `Targets` config can only be configured in `config.json` file.
Values are written in Minecraft resource location format, the so-called "snake_case" (consists of lowercase letters with underscores).
For example, the White Bed is written in "white_bed".
There are lots of exceptions, the "Smooth Quartz Block" is written in "smooth_quartz", the "Block of Diamond" is written in "diamond_block", so please check the correct values in [this wiki link](https://minecraft.fandom.com/wiki/Java_Edition_data_values#Blocks) (expand to show the list by clicking "Blocks[show]" then "Item form's ID[show]").

See also: [Feature Description](/doc/FEATURES.md#partial-speaking)

## Inventory Controls

| Configuration                                                                             | Default Value | Description                                                                                            |
|-------------------------------------------------------------------------------------------|---------------|--------------------------------------------------------------------------------------------------------|
| Enabled                                                                                   | true          | Whether to enable this feature                                                                         |
| Auto Open Recipe Book                                                                     | true          | Automatically opens the recipe                                                                         |
| book on opening the inventory (in creative/survival inventory screen and crafting screen) |               |                                                                                                        |
| Row and Column Format in Crafting Input Slots                                             | "%dx%d"       | The speaking format of row and column prefix, two "%d" are representing the position of row and column |
| Delay (in milliseconds)                                                                   | 250           | Cooldown between two feature executions                                                                |

Most recipes [require](https://minecraft.fandom.com/wiki/Crafting) their ingredients to be arranged in a specific way on the crafting grid (a.k.a. our crafting input group).
That's how `Row and Column Format in Crafting Input Slots` config can help you, you'll hear something like "1x2 Empty Slot" which represent you're locating at row one and column two slot inside the crafting input group, and it contains nothing.

See also: [Feature Description](/doc/FEATURES.md#inventory-controls), [Keybindings](/doc/KEYBINDINGS.md#inventory-controls)

## Point of Interest

See also: [Feature Description](/doc/FEATURES.md#point-of-interest), [Keybindings](/doc/KEYBINDINGS.md#point-of-interest)

### Blocks

| Configuration                       | Default Value | Description                                                 |
|-------------------------------------|---------------|-------------------------------------------------------------|
| Enabled                             | true          | Whether to enable scanning blocks                           |
| Detect Fluid Blocks                 | true          | Enable detecting fluid blocks (water and lava)              |
| Range                               | 6             | Range of blocks to scan                                     |
| Play Sound                          | true          | Play a sound cue at positions of detected blocks            |
| Sound Volume                        | 0.25          | Volume of the sound cue                                     |
| Play Sound for Other Blocks as well | false         | Play sound cue for other blocks other than the ores as well |
| Delay (in milliseconds)             | 3000          | Execute at set intervals                                    |

### Entities

| Configuration                       | Default Value | Description                                                 |
|-------------------------------------|---------------|-------------------------------------------------------------|
| Enabled                             | true          | Whether to enable scanning entities                         |
| Range                               | 6             | Range of entities to scan                                   |
| Play Sound                          | true          | Play a sound cue at positions of detected entities          |
| Sound Volume                        | 0.25          | Volume of the sound cue                                     |
| Delay (in milliseconds)             | 3000          | Execute at set intervals                                    |

### Entities/Blocks Locking

| Configuration                           | Default Value | Description                                                                                           |
|-----------------------------------------|---------------|-------------------------------------------------------------------------------------------------------|
| Enabled                                 | true          | Whether to enable this feature                                                                        |
| Lock on Blocks                          | true          | Enable lock on POI blocks as well, if false, only entities can be locked                              |
| Play Sound                              | true          | Play a sound cue at positions of detected entities                                                    |
| Speak Relative Distance to Entity/Block | false         | Speak relative distance to the target when locking on                                                 |
| Play Unlocking Sound                    | true          | Play a sound cue on unlock                                                                            |
| Auto Lock on to Eye of Ender when Used  | true          | Automatically lock on to the [Eye of Ender](https://minecraft.fandom.com/wiki/Eye_of_Ender) when used |
| Delay (in milliseconds)                 | 100           | Cooldown between two feature executions                                                               |

### Entities/Blocks Marking

| Configuration                      | Default Value | Description                                                             |
|------------------------------------|---------------|-------------------------------------------------------------------------|
| Enabled                            | true          | Whether to enable this feature                                          |
| Suppress Other POI When Marking On | true          | When marking on, suppress scanning and notifying on pre-configured ones |

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [FAQ](/doc/FAQ.md)