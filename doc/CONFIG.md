# Minecraft Access Configuration

This page contains all the configuration that controls the features on-off and behavior.

The mod configuration can be modified in two ways: via the config menu or directly editing the configuration file.
The `Config Menu` can be opened via opening the `Narrator Menu` (press F4) then click `Open Config Menu` button.
The configuration file (named as `config.json`) can be found in the `{minecraft directory}/config/minecraft_access/` directory.
You can use notepad or any text editor to edit the file.
You can also use online json editor like [Json Editor Online](https://jsoneditoronline.org/), [Json Formatter](https://jsonformatter.org/json-editor) or [Code Beautify Json Online Editor](https://jsonformatter.org/json-editor).

If you want to reset the config back to default values, simply delete the configuration file while the game is not running, or click `Reset Config` button in the `Config Menu`.

## Table Of Contents

* [Camera Controls](#camera-controls)
* [Mouse Simulation](#mouse-simulation)

## Camera Controls

| Configuration           | Default Value | Description                                                                                   |
|-------------------------|---------------|-----------------------------------------------------------------------------------------------|
| Enabled                 | true          | Whether to enable this feature                                                                |
| Normal Rotating Angle   | 22.5          | The rotation angle when we press the camera moving keys                                       |
| Modified Rotating Angle | 11.25         | The rotation angle when we press the camera moving keys while holding down the `Left Alt` key |
| Delay                   | 250           | Cooldown between two function executions, in milliseconds                                     |

See also: [Feature Description](/doc/FEATURES.md#camera-controls), [Keybindings](/doc/KEYBINDINGS.md#camera-controls)

## Mouse Simulation

| Configuration | Default Value | Description                                                          |
|---------------|---------------|----------------------------------------------------------------------|
| Enabled       | true          | Whether to enable this feature                                       |
| Scroll Delay  | 150           | Cooldown between two mouse wheel scroll simulations, in milliseconds |

See also: [Feature Description](/doc/FEATURES.md#mouse-simulation), [Keybindings](/doc/KEYBINDINGS.md#mouse-simulation)

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [FAQ](/doc/FAQ.md)