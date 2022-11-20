# Minecraft Access

Minecraft Access is a replacement of the other accessibility [mods.](https://github.com/accessible-minecraft) Just like the previous mods, this mod also supports screen readers for windows but now it also supports additional screen readers thanks to [tolk](https://github.com/ndarilek/tolk) library. Additionally the mod also supports linux using the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper).
Note that not all features have been ported over to this mod.

# Setup

## Requirements

1. The mod works with both forge and fabric so you need to install either one of those. For forge, you can follow [this](https://thebreakdown.xyz/how-to-download-install-forge-to-play-minecraft-mods/) guide and for fabric you can follow [this](https://thebreakdown.xyz/how-to-download-install-the-fabric-mod-loader/) guide.

2. Windows specific:-
    In windows we need to install the [tolk](https://github.com/ndarilek/tolk) library. Here are the steps to install the library:-
    - Head over to the [description](https://github.com/ndarilek/tolk#tolk-screen-reader-abstraction-library) of the library and download the [latest build](https://github.com/ndarilek/tolk/releases/download/refs%2Fheads%2Fmaster/tolk.zip).
    - Extract the downloaded zip file anywhere you like, the Downloads folder should be fine.
    - Open the extracted folder and in that folder you'll find a folder called `x64` and another called `x86`.
    - Based on your operating system, `x64` for 64-bit OS and `x86` for 32-bit OS, copy all the files present in the folder and paste them into the minecraft root folder.
        For winodws the default location is -
            %appdata%\.minecraft

3. Linux specific:-

    In linux we need to install the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper) as well as [xdotool](https://github.com/jordansissel/xdotool) which is used for simulating the mouse actions.
    For libspeechdwrapper, [download](https://github.com/khanshoaib3/libspeechdwrapper/raw/main/lib/libspeechdwrapper.so) the library and move it over to the minecraft folder.
    For linux the default location is -
        ~/.minecraft
    Follow the [installation instruction](https://github.com/jordansissel/xdotool#installation) in the description to install xdotool.

## Mod Installation

- Download the mod file according to the loader installed from the [latest release page]().
- Move the downloaded jar file over to the mods folder.
  For windows the default location is - `%appdata%\.minecraft\mods`
  And for linux it is - `~/.minecraft/mods`