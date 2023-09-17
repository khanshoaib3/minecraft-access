Please read [this issue for guide for modifying this document](https://github.com/khanshoaib3/minecraft-access/pull/155)

Release v1.3.0 (2023-09)
---------------------------

### Feature Changes

* BREAKING CHANGE: Remove left and right mouse simulation keys in `Inventory Controls` and have the `Mouse Simulation` feature replace their functions. [#168](https://github.com/khanshoaib3/minecraft-access/issues/168)

### Bug Fixes

* Fix a bug that `PositionNarrator` speaks multiple times with single keystroke. [#164](https://github.com/khanshoaib3/minecraft-access/issues/164)
* Fix a bug that custom keybindings can not take effect immediately until restart the game. [#167](https://github.com/khanshoaib3/minecraft-access/issues/167)
* Fix a bug that all vanilla keys rebinding are invalid. [#171](https://github.com/khanshoaib3/minecraft-access/issues/171)
* Fix a bug that POI Entity config can not take effect immediately until restart the game.

### Refactoring, Documentation and Chores

* Rewrite big README into [manual structure](https://github.com/khanshoaib3/minecraft-access/blob/1.20/README.md) for better readability.

Release v1.2.2 (2023-09-12)
---------------------------

### Bug Fixes

* Fix camera straight up down without right alt [#159](https://github.com/khanshoaib3/minecraft-access/pull/159) [#160](https://github.com/khanshoaib3/minecraft-access/pull/160)

### Refactoring, Documentation and Chores

* Add `CHANGELOG.md` for persistent change log and `PULL_REQUEST_TEMPLATE.md` for reminding PR submitters.
* Move `PlayerPositionUtils`, `PositionUtils` into `utils.position` subpackage.
* Move `MouseUtils`, `OSUtils`, `KeyUtils` into `utils.system` subpackage.
* Add several reusable junit `*Extension` for testing.
* Add `Orientation` enum for representing directions, refactor related code to apply it.
* Add `IntervalKeystroke` for interval executed keystrokes.

Release v1.2.1 (2023-08-31)
---------------------------

### Changes and Fixes

* Remove "speak different slots with same item content" function in "held item (hotbar) narration", since it will cause the mod to speak in more places it shouldn't, and there is no alternative way to reimplement this function. [#151](https://github.com/khanshoaib3/minecraft-access/pull/151) [#152](https://github.com/khanshoaib3/minecraft-access/pull/152)

Release v1.2.0 (2023-08-30)
---------------------------

### New Features

* Camera Look straight Up and Down. [#131](https://github.com/khanshoaib3/minecraft-access/pull/131) [#132](https://github.com/khanshoaib3/minecraft-access/pull/132) [#133](https://github.com/khanshoaib3/minecraft-access/pull/133) [#135](https://github.com/khanshoaib3/minecraft-access/pull/135)
* New Brazilian Portuguese Translation. https://github.com/khanshoaib3/minecraft-access-i18n/pull/18

### Changes and Fixes

* Improve held item (hotbar) narration: speak empty slots, speak different slots with same item content. [#121](https://github.com/khanshoaib3/minecraft-access/pull/121) [#122](https://github.com/khanshoaib3/minecraft-access/pull/122)
* The direction and power of `Redstone Wire` are now be spoken in `Read Crosshair` feature. [#142](https://github.com/khanshoaib3/minecraft-access/pull/142) [#143](https://github.com/khanshoaib3/minecraft-access/pull/143)
* Fix a bug that `Find Closest Water/Lava Source` will recognize the water/lava fall as a valid still fluid block. [#144](https://github.com/khanshoaib3/minecraft-access/pull/144) [#145](https://github.com/khanshoaib3/minecraft-access/pull/145)

Release v1.1.1-beta.1 (for 1.20.1) v1.0.4-beta.1 (for 1.19.3) (2023-08-05)
---------------------------

## Changes and Fixes

* Optimize screen groups order, in the order of interactive frequency, for reducing the number of user keystrokes. [#107](https://github.com/khanshoaib3/minecraft-access/pull/107) [#108](https://github.com/khanshoaib3/minecraft-access/pull/108)

Release v1.1.0 (for 1.20.1) v1.0.3 (for 1.19.3) (2023-08-02)
---------------------------

### New Features

* Simulate mouse with keyboard. [#94](https://github.com/khanshoaib3/minecraft-access/pull/94) [#95](https://github.com/khanshoaib3/minecraft-access/pull/95)
* Speak monster spawner mob type. [#97](https://github.com/khanshoaib3/minecraft-access/pull/97) [#100](https://github.com/khanshoaib3/minecraft-access/pull/100)

## Changes and Fixes

* Fixed speaking multiple items in one recipe book slot when item icon changed. [#79](https://github.com/khanshoaib3/minecraft-access/pull/79) [#80](https://github.com/khanshoaib3/minecraft-access/pull/80)
* Close the `Narrator Menu` if the F4 key is pressed while the menu is open. [#83](https://github.com/khanshoaib3/minecraft-access/pull/83) [#84](https://github.com/khanshoaib3/minecraft-access/pull/84)

Release v1.1.0-beta.2 (for 1.20.1) v1.0.2 (for 1.19.3) (2023-07-08)
---------------------------

### New Features

* Customizable partial speaking in `Read CrossHair`. [#69](https://github.com/khanshoaib3/minecraft-access/pull/69) [#70](https://github.com/khanshoaib3/minecraft-access/pull/70)

## Changes and Fixes

* Fix a bug that recipe book has no next page button clicking in `1.20`. [#71](https://github.com/khanshoaib3/minecraft-access/pull/71)
* Fixed inventory control in `1.20`. [#73](https://github.com/khanshoaib3/minecraft-access/pull/73)
* Fix mod crashing on loading wrong config.json [#76](https://github.com/khanshoaib3/minecraft-access/pull/76) [#77](https://github.com/khanshoaib3/minecraft-access/pull/77)
* Fix the failure that mouse click event is performed before mouse cursor moving event in Windows. [#65](https://github.com/khanshoaib3/minecraft-access/pull/65) [#66](https://github.com/khanshoaib3/minecraft-access/pull/66)
* Fix a bug about KeyBindingsHandler's initialization. [#63](https://github.com/khanshoaib3/minecraft-access/pull/63) [#64](https://github.com/khanshoaib3/minecraft-access/pull/64)

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v1.1.0-beta.1...v1.1.0-beta.2

Release v1.1.0-beta.1 (for 1.20.1) (2023-07-02)
---------------------------

### New Features

* Added sign's front and back text narration.
* Added torch flower and pitcher crop's ripe level info in `Read Crosshair`.

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v1.0.1...v1.1.0-beta.1

Release v1.0.1 (for 1.19.3) (2023-07-01)
---------------------------

### New Features

- Fall detector is back but in this version instead of speaking "warning fall detected", a sound is played at block (Anvil Block Hit sound to be specific). There will be an alternate sound option in the next update.
- The mod now automatically setups the screen reader libraries. You would still need to download the other required mods (fabric api and architectury api).
- Narrator menu hotkeys - You can now trigger the narrator menu options without opening the menu. More about this [here](https://github.com/khanshoaib3/minecraft-access#narrator-menu).
- Added a mod configuration menu. You can open this menu from the `Open Config Menu` option in Narrator/F4 Menu.
- POI marking feature, with this feature you can select to only detect a specific type of block/entity, more on this [here](https://github.com/khanshoaib3/minecraft-access#points-of-interest)
- The mod now reports the items picked up when fishing.

## Changes and Fixes

- Chat message reader improvements, now you can use alt + number keys to speak last 9 chat messages.
- Added configs to disable speaking action bar updates, speak time in 12 hour format and to repeat speaking the block/entity name.
- Simplified Chinese translations.
- Added sculk sensor/catalyst/shrieker to poi blocks
- The group names in inventory controls are now internationalized.
- Added beehive info to the read crosshair feature.
- Added narration about crops ripe level and farmland wet state
- Fixed default narrator override in forge version.
- Fixed enchant name not being spoken when in non-creative world.
- Removed the power level info from comparators because it isn't working.

<details><summary>Auto Generated Release Notes</summary>
<p>

## What's Changed
* Added config option to speak time in 12 hour format by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/21
* Chat History Reader by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/23
* Misc by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/24
* Bug Fixes by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/28
* Auto Library Installer by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/29
* I18N SlotsGroup by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/30
* Mainly move facing-direction functions by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/31
* Reset previousQuery after every 5000ms by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/32
* Fix some SlotGroups non-config-key groupNames. by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/34
* Bee Hive Info by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/40
* Chore Narration improvements by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/38
* Add FluidDetector config. by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/37
* Config Menu by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/45
* Add narration about crops ripe level and farmland wet state. Refactor block narration methods. close [#44](https://github.com/khanshoaib3/minecraft-access/pull/44) by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/46
* Add Narrator Menu hotkey and Narrator Menu number key listening by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/47
* Add speak harvest of fishing feature, to be clear it will report what your picked up when you're holding a fishing rod.  by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/52
* Comparator info fix; Bug fix for redstone related blocks; Refactored i18n entries; by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/53
* Add speak harvest of fishing config by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/54
* Fix debugMode dynamic config. close [#50](https://github.com/khanshoaib3/minecraft-access/pull/50) by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/57
* Add marked poi scan feature by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/59
* Refactor key related code by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/56
* Fixes buggy Time Utils with camera and inventory controls key inputs. by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/63


**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v1.0.0...v1.0.1
</p>
</details> 

Beta 1.0.1-beta.2 (for 1.19.3) (2023-06-18)
---------------------------

## Changelog

Note that you may face issue in accessing the F4 menu, this can be resolved by deleting the mod's configuration file. You can delete the `minecraft-access` folder found in the `.minecraft\config\` folder to do this.*

* (By @boholder) Various i18n related improvements including internationalizing the group names in inventory controls and Chinese translations for various entries.
* Added a mod configuration menu. You can open this menu from the `Open Config Menu` option in Narrator/F4 Menu.
* (By @boholder) Added fluid detector configurations and a read crosshair config to repeat speaking the block/entity name.
* The mod now automatically setups the screen reader libraries. You would still need to download the other required mods (fabric api and architectury api).
* Added beehive info to the read crosshair feature.
* Fixed system messages not being read in chat menu.
* Fixed default narrator override in forge version.
* Fixed enchant name not being spoken when in non-creative world.
* Code refactoring, also by @boholder

<details><summary>Auto Generated Release Notes</summary>
<p>

## Auto Generated Release Notes

### What's Changed
* Bug Fixes by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/28
* Auto Library Installer by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/29
* I18N SlotsGroup by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/30
* Mainly move facing-direction functions by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/31
* Reset previousQuery after every 5000ms by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/32
* Fix some SlotGroups non-config-key groupNames. by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/34
* Bee Hive Info by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/40
* Chore Narration improvements by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/38
* Add FluidDetector config. by @boholder in https://github.com/khanshoaib3/minecraft-access/pull/37
* Config Menu by @khanshoaib3 in https://github.com/khanshoaib3/minecraft-access/pull/45

### New Contributors
* @boholder made their first contribution in https://github.com/khanshoaib3/minecraft-access/pull/30

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v1.0.1-beta.1...v1.0.1-beta.2

</p>
</details>

Beta 1.0.1-beta.1 (for 1.19.3) (2023-04-30)
---------------------------

### New Features

- Simplified Chinese translations (thanks to @onion108 for this).
- Fall detector is back but in this version instead of speaking "warning fall detected", a sound is played at block (Anvil Block Hit sound to be specific). Do let me know if you think that previous implementation was better :)

## Changes and Fixes

- Chat Message Reader, now you can use alt + number keys to speak last 9 chat messages (this atm does not include any system sent messages).
- Added config to disable speaking action bar updates.
- Added sculk sensor/catalyst/shrieker to poi blocks

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v1.0.0...v1.0.1-beta.1

Release 1.0.0 (for 1.19.3) (2023-02-05)
---------------------------

This marks the release of Minecraft Access. All the features from the previous mods have been implemented to this mod (except fall detector). Some have been changed or improved. Here are some noticeable changes:

### Noticeable Changes

- The mod now works with both fabric and forge now.
- The mod now uses [Tolk](https://github.com/ndarilek/tolk) library for accessing screen reader in Windows and [libSpeechdWrapper](https://github.com/khanshoaib3/libspeechdwrapper) for Linux.
- Inventory controls key mapping is changed and now supports all the menus (including creative inventory)
- Key mappings for camera controls have also been updated to better suit keyboard with no numpad/keypad.
- The feature that reads the crosshair target also gives additional information like whether a block is being powered by a redstone source, a door is open or closed, sheep's color, etc.
- The narrator menu now has a few more options including time of day which now works for servers/multiplayer also.
- There is no longer a dedicated menu for mod customization, instead we have to manually edit the config.json file. (more on this [here](https://github.com/khanshoaib3/minecraft-access#mod-configuration))

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v0.1.6-beta...v1.0.0

Beta Release 0.1.6 (for 1.19.3) (2023-01-07)
---------------------------

_Note that this will only work on 1.19.3 and for fabric it is recommended to use fabric api version 0.72.0 with fabric loader version 0.14.12. For forge, version 44.1.0 is recommended and both require architectury api to be installed as well (version 7.0.66 recommended)_

### Changelog

- Patched book screen.
    - Use Left alt + r to repeat the contents of current page
- Added health and hunger feature (keybind: R)
- Added fluid block detection in read crosshair feature
- Added player warnings feature
- Added point of interest feature with keybind to lock on to nearest entity or block. Use Y to lock and alt + Y to unlock.

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v0.1.0-beta...v0.1.6-beta

Beta Release 0.1.0 (for 1.19.3) (2022-11-20)
---------------------------

### Changelog

- Camera Controls
- Inventory Controls
- Read Block
- Position Narrator
- Facing Direction
- Biome Indicator

**Full Changelog**: https://github.com/khanshoaib3/minecraft-access/compare/v0.0.1...v0.1.0-beta
