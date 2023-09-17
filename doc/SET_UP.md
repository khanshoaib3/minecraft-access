# Mod Setup

Currently, this mod supports Windows and Linux operating system ([help us on macOS porting](https://github.com/khanshoaib3/minecraft-access/issues/22)).
The latest version of this mod (as well as other mod dependencies) can be downloaded at [release page](https://github.com/khanshoaib3/minecraft-access/releases).

## For Windows

There is [a detailed tutorial](/doc/SET_UP_ON_WINDOWS.md) for Windows, from purchasing game to installing mod.

## For Linux

Currently, there is no corresponding independent tutorial for Linux.
You can refer to [the Windows installation tutorial](/doc/SET_UP_ON_WINDOWS.md) to purchase, download, and install the game.
I'm not sure how to install Fabric on Linux (but I'm sure it's easier than choosing to install Forge), so you need to [google it](https://www.google.com/search?q=install+minecraft+fabric+on+linux) by yourself, sorry.
Next you can download the required mod files by referring to the tutorial and put them into mod directory.
The default game directory is `~/.minecraft` and the mod directory is `~/.minecraft/mods`.

### Additional Installation for Linux

We need to install a few extra things that this mod depends on after installing the mod to make this mod work properly.

1. We need to install the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper) for invoking screen reader's API.
Download the library from [its GitHub repository](https://github.com/khanshoaib3/libspeechdwrapper/raw/main/lib/libspeechdwrapper.so) and move it into the minecraft directory (default `~/.minecraft`).

2. Although the mod overrides the library used for TTS, minecraft still needs the `flite` library to be installed, so you can install it using your distro's package manager itself.

3. We also need to install [xdotool](https://github.com/jordansissel/xdotool) which is used for simulating the mouse actions.
Follow the [instruction](https://github.com/jordansissel/xdotool#installation) to install it.

## Other Pages

* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [FAQ](/doc/FAQ.md)