# Set up on MacOS

This mod is now able to run on MacOS, although with a few caveats:

* The speech will often speak over itself, and there is no way to stop speech
* The setup process involves compiling and installing a command-line utility that the mod will use to move and click the mouse
* MacOS support has not been well-tested yet, so you might experience issues

## Compiling Cliclick

[Cliclick](https://github.com/BlueM/cliclick) is a utility that can generate mouse events, similar to xdotool on Linux. You must compile and install it to use this mod on MacOS.

Currently, the main cliclick repository is missing a few features needed for this mod, so you must use [emassey0135's minecraft-additions branch](https://github.com/emassey0135/cliclick/tree/minecraft-additions).  To compile and install it, do the following:

1. Open Terminal
2. If you have not installed the Xcode Command Line Tools, run `sudo xcode-select --install` and follow the instructions
3. Run `git clone https://github.com/emassey0135/cliclick -b minecraft-additions` to clone the repository
4. Change directory into it with `cd cliclick`
5. Run `make` to compile the project. You can also run `make -Jn`, where n is how many CPU cores you have, to run compilation steps concurrently.
6. Run `sudo make install` to install the executable into /usr/local/bin

## Enabling Accessibility Permission

MacOS does not allow applications to send input events unless they have the Accessibility permission, so you must grant it before mouse simulation will work.

1. Install the mod and start Minecraft
2. Start a game
3. Press a key that triggers a mouse event. Pressing left bracket should work if you have not changed the default keymap.
4. Open System Settings, and navigate to Privacy and Security > Accessibility
5. Turn on Minecraft in the applications list
6. Enter your password when prompted
7. Close and re-open Minecraft, and mouse simulation should work

## Changing Speech Rate

Since Minecraft uses the Apple text-to-speech API and doesn't speak through VoiceOver, the default speech rate can be very slow. To increase the speech rate the mod uses, open System Settings, and go to Accessibility > Spoken Content. From there, you can change the speech rate and any other settings you would like.
