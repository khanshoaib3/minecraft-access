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

## Camera Controls

| Configuration           | Default Value | Description                                                                                   |
|-------------------------|---------------|-----------------------------------------------------------------------------------------------|
| Enabled                 | true          | Whether to enable this feature                                                                |
| Normal Rotating Angle   | 22.5          | The rotation angle when we press the camera moving keys                                       |
| Modified Rotating Angle | 11.25         | The rotation angle when we press the camera moving keys while holding down the `Left Alt` key |
| Delay (in milliseconds) | 250           | Cooldown between two function executions                                                      |

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

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [FAQ](/doc/FAQ.md)