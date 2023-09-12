# Minecraft Access

Minecraft Access is a [Minecraft](https://www.minecraft.net) mod that specifically helps visually impaired players play Minecraft.
It is an integration and replacement for [a series of previous mods](https://github.com/accessible-minecraft).
This mod primarily borrows the help of a screen reader to describe (narrate) the game interface, and incorporates sound cues to provide orientation perception in this 3D world.
Currently this mod [has enough features](doc/FAQ.md#is-the-mod-enough-to-play-the-game-normally) to help the visually impaired people play the game normally.

This mod supports:

* Game version `1.19.3`, and subsequent versions start from `1.20.1`
* On [Fabric](https://fabricmc.net/use/installer/) and [Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) modding platform
* On Windows and Linux operating system ([Help us on macOS porting](https://github.com/khanshoaib3/minecraft-access/issues/22))

## Other Pages

* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)

## Useful Links

* [Minecraft Wiki](https://minecraft.fandom.com/wiki/Minecraft_Wiki) - Has details and guides on everything about Minecraft.
* [Minecraft Survival Guide (Season 3)](https://www.youtube.com/watch?v=VfpHTJsn9I4&list=PLgENJ0iY3XBjmydGuzYTtDwfxuR6lN8KC) - A great series by Pixlriffs, highly recommend for beginners.
* [Playability Discord server](https://discord.gg/yQjjsDqWQX) - Join our Discord server if you want to chat with other this mod's users and developers.
* [Twitter](https://twitter.com/shoaib_mk0) - You can follow the developer on Twitter to get notification when a new update drops.
* [Patreon](https://www.patreon.com/shoaibkhan)

## Known Issues

1. The default narrator speaks even if the narrator is turned off.
2. (Linux only) xdotool is not recognised even if it is installed.
3. (Linux only) Minecraft says not narrator available even if flite is installed.