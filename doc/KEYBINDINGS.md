# Minecraft Access Keybindings

This page contains all of keybindings added by the mod, and key combinations for all features.

You can change these keybindings in the settings (open `Options...` then `Controls..` then `Key Binds..`), all keybinding setting groups that provided by this mod have a `Minecraft Access:` prefix to differentiate them from the original keybinding settings.

You may find that some features have duplicate keys, such as I, J, K, L as the arrow keys in various features.
It's ok since the same key takes effect in different interfaces for different functions.

You may want to save this page as a bookmark, it'll be something you'll consult frequently.

## Table Of Contents

1. [Camera Controls](#camera-controls)
2. [Mouse Simulation](#mouse-simulation)
3. [Inventory Controls](#inventory-controls)
4. [Point of Interest](#point-of-interest)
5. [Position Narrator](#position-narrator)
6. [Narrator Menu](#narrator-menu)

## Camera Controls

| Single Key                   | Default Keybinding | Description                                                                                                                          |
|------------------------------|--------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| `Look Up Key`                | I                  | Move the camera vertically up by the `Normal Rotating Angle` config value                                                            |
| `Alternate Look Up Key`      | Number Pad 8       | Same as `Look Up Key`, move the camera vertically up                                                                                 |
| `Look Right Key`             | L                  | Move the camera horizontally right by the `Normal Rotating Angle` config value                                                       |
| `Alternate Look Right Key`   | Number Pad 6       | Same as `Look Right Key`, move the camera horizontally right                                                                         |
| `Look Down Key`              | K                  | Move the camera vertically down by the `Normal Rotating Angle` config value                                                          |
| `Alternate Look Down Key`    | Number Pad 2       | Same as `Look Down Key`, Move the camera vertically down                                                                             |
| `Look Left Key`              | J                  | Move the camera horizontally left by the `Normal Rotating Angle` config value                                                        |
| `Alternate Look Left Key`    | Number Pad 4       | Same as `Look Left Key`, move the camera horizontally left                                                                           |
| `Look North Key`             | Number Pad 7       | Turn the camera to the north                                                                                                         |
| `Look East Key`              | Number Pad 9       | Turn the camera to the east                                                                                                          |
| `Look South Key`             | Number Pad 3       | Turn the camera to the south                                                                                                         |
| `Look West Key`              | Number Pad 1       | Turn the camera to the west                                                                                                          |
| `Center Camera Key`          | Number Pad 5       | Look straight ahead: Turn the camera to the closest of the eight cardinal directions and reset vertical angle to horizontal position |
| `Look Straight Up Key`       | Number Pad 0       | Turn the camera to the look above head direction                                                                                     |
| `Look Straight Down Key`     | Number Pad .       | Turn the camera to the look down at feet direction                                                                                   |
| `Speak Facing Direction Key` | H                  | Speak the currently facing direction                                                                                                 |

| Key Combination                                                           | Description                                                                                                                                         |
|---------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| hold `Left Alt` then press `Look Up Key` or `Alternate Look Up Key`       | Move the camera vertically up by the `Modified Rotating Angle` config value                                                                         |
| hold `Left Alt` then press `Look Right Key` or `Alternate Look Right Key` | Move the camera vertically right by the `Modified Rotating Angle` config value                                                                      |
| hold `Left Alt` then press `Look Down Key` or `Alternate Look Down Key`   | Move the camera vertically down by the `Modified Rotating Angle` config value                                                                       |
| hold `Left Alt` then press `Look Left Key` or `Alternate Look Left Key`   | Move the camera vertically left by the `Modified Rotating Angle` config value                                                                       |
| `Left Alt` + `Center Camera Key`                                          | Look straight back: Turn the camera to the opposite of the closest of the eight cardinal directions and reset vertical angle to horizontal position |
| hold `Right Alt` then press `Look Up Key`                                 | Same as single `Look North Key` and single `Look Straight Up Key`, alternately move the camera to the north or up                                   |
| `Right Alt` + `Look Right Key`                                            | Same as single `Look East Key`, turn the camera to the east                                                                                         |
| hold `Right Alt` then press `Look Down Key`                               | Same as single `Look South Key` and single `Look Straight Down Key`, alternately move the camera to the south or down                               |
| `Right Alt` + `Look Left Key`                                             | Same as single `Look West Key`, turn the camera to the west                                                                                         |

See also: [Feature Description](/doc/FEATURES.md#camera-controls), [Configuration](/doc/CONFIG.md#camera-controls)

## Mouse Simulation

| Single Key                    | Default Keybinding | Description                                                          |
|-------------------------------|--------------------|----------------------------------------------------------------------|
| `Left Mouse Sim Key`          | [                  | Simulate left mouse key, the original `Attack/Destroy` key           |
| `Middle Mouse Sim Key`        | \                  | Simulate middle mouse key, the original `Pick Block` key             |
| `Right Mouse Sim Key`         | ]                  | Simulate right mouse key, the original `Use Item/Place Block` key    |
| `Mouse Wheel Scroll Up Key`   | ;                  | Simulate mouse wheel scroll up, switching items in Hotbar forward    |
| `Mouse Wheel Scroll Down Key` | '                  | Simulate mouse wheel scroll down, switching items in Hotbar backward |

See also: [Feature Description](/doc/FEATURES.md#mouse-simulation), [Configuration](/doc/CONFIG.md#mouse-simulation)

## Inventory Controls

| Single Key                | Default Keybinding | Description                                                                      |
|---------------------------|--------------------|----------------------------------------------------------------------------------|
| Up Key                    | I                  | Focus to slot above                                                              |
| Right Key                 | L                  | Focus to slot right                                                              |
| Down Key                  | K                  | Focus to slot below                                                              |
| Left Key                  | J                  | Focus to slot left                                                               |
| Group Key                 | C                  | Select next slot group                                                           |
| Switch Tab Key            | V                  | Select next tab                                                                  |
| Toggle Craftable Key      | R                  | Switch between "show all" and "show only" craftable recipes in recipe book group |
| T                         | not re-mappable    | Select the search box or text box                                                |
| Enter                     | not re-mappable    | Deselect the search box or text box                                              |

| Key Combination                 | Description                             |
|---------------------------------|-----------------------------------------|
| `Left Shift` + `Group Key`      | Select previous slot group              |
| `Left Shift` + `Switch Tab Key` | Select previous tab                     |
| `Left Shift` + `Up Key`         | Select previous page of the Recipe Book |
| `Left Shift` + `Down Key`       | Select next page of the Recipe Book     |

`Switch Tab Key`, `Toggle Craftable Key`, `T` key and `Enter` key only works when there is a corresponding component in the opened screen. Recipe Book page turning only works when "Recipe Book Group" is selected.

See also: [Feature Description](/doc/FEATURES.md#inventory-controls), [Configuration](/doc/CONFIG.md#inventory-controls)

## Point of Interest

| Single Key  | Default Keybinding | Description                               |
|-------------|--------------------|-------------------------------------------|
| Locking Key | Y                  | Lock onto the closest POI block or entity |

| Key Combination                   | Description                                                |
|-----------------------------------|------------------------------------------------------------|
| `Alt` + `Locking Key`             | Unlock from the currently locked entity or block           |
| `Control` + `Locking Key`         | Mark the block or entity currently targeted with crosshair |
| `Control` + `Alt` + `Locking Key` | Unmark from the target                                     |

See also: [Feature Description](/doc/FEATURES.md#point-of-interest), [Configuration](/doc/CONFIG.md#point-of-interest)

## Position Narrator

| Single Key                | Default Keybinding | Description                       |
|---------------------------|--------------------|-----------------------------------|
| Speak Player Position Key | G                  | Speak the player's x y z position |

| Key Combination  | Description               |
|------------------|---------------------------|
| `Left Alt` + `Z` | Speak the player's z-axis |
| `Left Alt` + `X` | Speak the player's x-axis |
| `Left Alt` + `C` | Speak the player's y-axis |

See also: [Feature Description](/doc/FEATURES.md#position-narrator)

## Narrator Menu

| Single Key                     | Default Keybinding | Description                                                                                                                                |
|--------------------------------|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| F4                             | not re-mappable    | Open or close the Narrator Menu                                                                                                            |
| Narrator Menu Function Hot Key | B                  | Execute the selected function                                                                                                              |
| Number Keys                    | not re-mappable    | When Narrator Menu is opened, press number keys to execute corresponding desired functions, without pressing `Tab` several times to select |

| Key Combination                                       | Description                               |
|-------------------------------------------------------|-------------------------------------------|
| hold `F4` then press `Narrator Menu Function Hot Key` | Loop selecting functions in Narrator Menu |

See also: [Feature Description](/doc/FEATURES.md#narrator-menu), [Configuration](/doc/CONFIG.md#narrator-menu)

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)