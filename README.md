# Minecraft Access

Minecraft Access is a replacement of the other accessibility [mods.](https://github.com/accessible-minecraft) Just like the previous mods, this mod also supports screen readers for windows, but now it also supports additional screen readers thanks to [tolk](https://github.com/ndarilek/tolk) library. Additionally, the mod also supports linux using the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper).
Note that not all features have been ported over to this mod.

# Setup

## Requirements

1. The mod works with both forge and fabric, so you need to install either one of those. For forge, you can follow [this](https://thebreakdown.xyz/how-to-download-install-forge-to-play-minecraft-mods/) guide and for fabric you can follow [this](https://thebreakdown.xyz/how-to-download-install-the-fabric-mod-loader/) guide.
2. We also need to install the [architectury api](https://www.curseforge.com/minecraft/mc-mods/architectury-api) mod.
3. Windows specific:-
    In windows we need to install the [tolk](https://github.com/ndarilek/tolk) library. Here are the steps to install the library:-
    - Head over to the [description](https://github.com/ndarilek/tolk#tolk-screen-reader-abstraction-library) of the library and download the [latest build](https://github.com/ndarilek/tolk/releases/download/refs%2Fheads%2Fmaster/tolk.zip).
    - Extract the downloaded zip file anywhere you like, the Downloads folder should be fine.
    - Open the extracted folder and in that folder you'll find a folder called `x64` and another called `x86`.
    - Based on your operating system, `x64` for 64-bit OS and `x86` for 32-bit OS, copy all the files present in the folder and paste them into the minecraft root folder.
        For windows the default location is -
            %appdata%\.minecraft
4. Linux specific:-
    In linux we need to install the [libspeechdwrapper](https://github.com/khanshoaib3/libspeechdwrapper) as well as [xdotool](https://github.com/jordansissel/xdotool) which is used for simulating the mouse actions.
    Although the mod overrides the library used for tts, minecraft still needs the `flite` library to be installed, so you can install it using your distro's package manager itself.
    For libspeechdwrapper, [download](https://github.com/khanshoaib3/libspeechdwrapper/raw/main/lib/libspeechdwrapper.so) the library and move it over to the minecraft folder.
    For linux the default location is -
        ~/.minecraft
    Follow the [installation instruction](https://github.com/jordansissel/xdotool#installation) in the description to install xdotool.

## Mod Installation

- Download the mod file according to the loader installed from the [latest release page](https://github.com/khanshoaib3/minecraft-access/releases).
- Move the downloaded jar file over to the mods folder.
  For windows the default location is - `%appdata%\.minecraft\mods`
  And for linux it is - `~/.minecraft/mods`

# Features

## Camera Controls

This feature adds the following key bindings to control the camera through the keyboard.

**Key bindings and combinations:-**

1. Look Up Key (default: I) = Moves the camera vertically up by the normal rotating angle (default=22.5).
2. Look Right Key (default: L) = Moves the camera vertically right by the normal rotating angle (default=22.5).
3. Look Down Key (default: K) = Moves the camera vertically down by the normal rotating angle (default=22.5).
4. Look Left Key (default: J) = Moves the camera vertically left by the normal rotating angle (default=22.5).
5. Left Alt + Look Up Key = Moves the camera vertically up by the modified rotating angle (default=11.25).
6. Left Alt + Look Right Key = Moves the camera vertically right by the modified rotating angle (default=11.25).
7. Left Alt + Look Down Key = Moves the camera vertically down by the modified rotating angle (default=11.25).
8. Left Alt + Look Left Key = Moves the camera vertically left by the modified rotating angle (default=11.25).
9. Right Alt + Look Up Key = Snaps the camera to the north block.
10. Right Alt + Look Right Key = Snaps the camera to the east block.
11. Right Alt + Look Down Key = Snaps the camera to the south block.
12. Right Alt + Look Left Key = Snaps the camera to the west block.

## Inventory Controls


This features lets us use keyboard in inventory screens. Works with all default minecraft screens.

*All key binds are re-mappable(except two keys) from the game's controls menu and these key binds do not interrupt with any other key with same key.*

**Keybindings and combinations:**

1. Up Key (default: I) = Focus to slot above.
2. Right Key (default: L) = Focus to slot right.
3. Down Key (default: K) = Focus to slot down.
4. Left Mouse Click Sim Key (default: [) = Simulates left mouse click.
5. Left Key (default: J) = Focus to slot left.
6. Group Key (default: C) = Select next group.
7. Left Shift + Group Key = Select previous group.
8. Switch Tab Key (default: V) = Select next tab (only for creative inventory screen and inventory/crafting screen).
9. Left Shift + Switch Tab Key = Select previous tab (only for creative inventory screen and inventory/crafting screen).
10. Toggle Craftable Key (default: R) = Toggle between show all and show only craftable recipes in inventory/crafting screen.
11. Left Mouse Click Sim Key (default: [) = Simulates left mouse click.
12. Right Mouse Click Sim Key (default: ]) = Simulates right mouse click.
13. T Key (not re-mappable) = Select the search box.
14. Enter Key (not re-mappable) = Deselect the search box.

## Read Block

This feature reads the name of the targeted block or entity. It also gives feedback when a block is powered by a redstone signal or when a door is open similar cases.

## Position Narrator

Adds key bindings to speak the player's position.

**Keybindings and combinations:**

1. Speak Player Position Key (default: G) = Speaks the player's x y and z position.
2. Left Alt + X = Speaks only the x position.
3. Left Alt + C = Speaks only the y position.
4. Left Alt + Z = Speaks only the z position.

## Facing Direction

Adds key binding to speak the player's facing direction.

- Speak Facing Direction Key (default: H) = Speaks the player facing direction.

## Biome Indicator

Narrates the name of the biome when entering a different biome.