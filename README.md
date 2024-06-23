# Minecraft Access

Minecraft Access is a [Minecraft](https://www.minecraft.net) mod that specifically helps visually impaired players play Minecraft.
It is an integration and replacement for [a series of previous mods](https://github.com/accessible-minecraft).
This mod primarily borrows the help of a screen reader to describe (narrate) the game interface, and incorporates sound cues to provide orientation perception in this 3D world.
Currently, this mod [has enough features](doc/FAQ.md#is-the-mod-enough-to-play-the-game-normally) to help the visually impaired people play the game normally.

This mod supports:

* Game version `1.19.3`, `1.20.1`, `1.20.4`, `1.20.6`
* On [Fabric](https://fabricmc.net/use/installer/) and [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) modding platform
* On Windows and Linux operating system ([Help us on macOS porting](https://github.com/khanshoaib3/minecraft-access/issues/22))
* Works despite the language setting of the game (but the mod specific narration will [fall back to English](/doc/FEATURES.md#i18n-fallback-mechanism) if the mod does not support the language yet)

Each version of mod will be pre-released on [GitHub](https://github.com/khanshoaib3/minecraft-access/releases) and announced in [Discord server](https://discord.gg/yQjjsDqWQX) first as beta test stage, after one week of feedback collection, one version will be released in [Modrinth](https://modrinth.com/mod/minecraft-access/versions) and [CurseForge](https://legacy.curseforge.com/minecraft/mc-mods/blind-accessibility/files). 

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [Sound Effects](https://html-preview.github.io/?url=https://github.com/khanshoaib3/minecraft-access/blob/1.20/doc/SOUND.html)
* [FAQ](/doc/FAQ.md)
* [Good Resources](/doc/GOOD_RESOURCES.md)

## Useful Links

* [Playability Discord server](https://discord.gg/yQjjsDqWQX) - Join our Discord server if you want to chat with other this mod's users and developers.
* [Twitter](https://twitter.com/shoaib_mk0) - You can follow the developer on Twitter to get notification when a new update drops.
* [Patreon](https://www.patreon.com/shoaibkhan)

## Known Issues

1. The default narrator speaks even if the narrator is turned off.
2. (Linux only) xdotool is not recognised even if it is installed.
3. (Linux only) Minecraft says not narrator available even if flite is installed.

## Contribution

Any type of contribution is welcome:

* Take the first bite of the new versions and help us detect bugs and inconvenience.
* Improve this mod's manual for better readability and accessibility.
* Help us [translate](/doc/FAQ.md#how-can-i-contribute-to-i18n) this mod into other languages.
* Create more text or video tutorials about how to play the game with this mod ([examples](/doc/GOOD_RESOURCES.md#gameplay-with-this-mod)).
* Make sound effects for this mod.
* For development contribution, please go to the [issues page](https://github.com/khanshoaib3/minecraft-access/issues) for more details.
