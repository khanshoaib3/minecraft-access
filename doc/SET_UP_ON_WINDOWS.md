# Tutorial for Windows: From Purchasing Game To Installing This Mod

This tutorial tries to guide you step by step on how to set the whole thing up. This tutorial is for Windows system.

If you want to chat with this mod's users and developers, please join [our Discord server](https://discord.gg/yQjjsDqWQX).
If you know where this tutorial could be improved, please let us know as well.

> The paragraphs in the block quotes are additional nonsense.
> They do not provide help with the installation, but they can give you a little more insight into things that are relevant.
> If you don't want to read them, skip them and move on, you won't miss anything important.

## Index

1. [Purchase the Game and Download the Launcher](#purchase-the-game-and-download-the-launcher)
    1. [Purchase the Game](#purchase-the-game)
    2. [Download the Launcher](#download-the-launcher)
2. [Choose and Install Your Chosen Mod Loader](#choose-and-install-your-chosen-mod-loader)
    2. [Install Fabric](#install-fabric)
    3. [Install NeoForge](#install-neoforge)
3. [Install Your Mods](#install-your-mods)
5. [Start the Game](#start-the-game)
6. [Update the Game and Mods](#update-the-game-and-mods)

## Purchase the Game and Download the Launcher

### Purchase the Game

Before you purchase the game, make sure you have a computer with a 64-bit processor, [Minecraft no longer supports 32-bit computers around 2022](https://www.minecraftforum.net/forums/minecraft-java-edition/recent-updates-and-snapshots/3140967-the-end-of-minecraft-32-bit).
Below are system requirements from [the official website](https://www.minecraft.net/en-us/store/minecraft-java-bedrock-edition-pc):

1. Minimum:
    * OS: Windows 7 and up
    * Architecture: ARM, x64, x86
    * Memory: 2 GB
    * Processor: Intel Core i3-3210 3.2 GHz | AMD A8-7600 APU 3.1 GHz | Apple M1 or equivalent
    * Graphics: Intel HD Graphics 4000 | AMD Radeon R5

2. Optimum:
    * OS: Windows 10 and up
    * Architecture: ARM, x64, x86
    * Memory: 4 GB
    * Processor: Intel Core i5-4690 3.5 GHz | AMD A10-7800 APU 3.5 GHz | Apple M1 or equivalent
    * Graphics: NVIDIA GeForce 700 series or AMD Radeon Rx 200 series (excluding integrated chipsets) with OpenGL 4.45

There are two editions of Minecraft: Minecraft: Java Edition and Minecraft for Windows/Bedrock Edition (usually just referred to as Minecraft).
This Mod is only available for the Java Edition.
Don't worry about buying the wrong one, the two editions are sold in a bundle.

> While we need not concern ourselves with the Bedrock Edition, I would like you to know the differences between the two editions:
>
> At one time Minecraft only had one edition, the Java Edition. Since Microsoft acquired Mojang Studios, they have launched a new Bedrock Edition.
> The Java Edition is available for PC (Windows, Mac and Linux), while the newer Bedrock Edition is available for windows, smartphones, game consoles, etc.
>
> Please also note that the Bedrock Edition in the next purchase link only contains the version that runs on Windows.
> Bedrock Edition on other platforms, such as on smartphones, needs to be purchased separately in the platform's respective application store.

If you have a redeem code for this game, [here is the redeem page](https://www.minecraft.net/en-us/redeem), no need to buy the game.
Or if you're a Xbox Game Pass member, you can also get Minecraft using your active subscription.

There are currently two purchasable versions of Minecraft, [the Deluxe Collection](https://www.minecraft.net/en-us/store/minecraft-deluxe-collection-pc) and [the bare game](https://www.minecraft.net/store/minecraft-java-bedrock-edition-pc).
I want to remind you that things that the Deluxe Collection has included that the bare game doesn't are just virtual currency (minecoins) and items only usable in the Bedrock Edition that we can't use in the Java Edition.

The links will jump to the pages that correspond to the language of your browser, and there is a `CHECKOUT` button.
Click it, and then the webpage will redirect you to the payment flow, then it will require you to sign in with Microsoft.
Your purchase will be tied to a Microsoft account, when you log into the launcher, you also need to log into this account.
Keep going after you have logged in, I don't know how to describe the purchase page, fill in the purchase information and click on confirm, that's all.

### Download the Launcher

> Like the launchers for some old PC games, you can use the launcher to choose which version of Minecraft you want to run, or to modify the game launch configuration.
> There are many kinds of launchers, anyone can write their own launcher to provide a customized Minecraft game launching experience.
> The launcher we will download is provided by Microsoft, let's call it the official launcher.

At the end of the purchase process there will be a `Download Game` button to download the launcher.
If you've missed it, [here is the launcher installer download page](https://www.minecraft.net/download).


The launcher installer is a normal executable file, run it and wait for it to finish.
If the installation is successful, you will now have a new application called `Minecraft Launcher`.

The next steps require starting the launcher once first before installing the mod loader to let it generate the game folder, so try [starting the launcher](#start-the-game), then start Minecraft: Java Edition.
Something may need to be prepared every time the launcher starts, especially the first time you launch it as it has to install the entire game.
This may take a few minutes, and you can listen to the screen reader's progress bar sound effect to find out the progress.
The first time you start the launcher it will require you to sign in with your Microsoft account.

## Choose and Install Your Mod Loader

> Like any other game, the mod loader is responsible for loading the mod files into the game at launch.
The two mod loaders that Minecraft Access currently supports are Fabric and NeoForge.


Mods for Fabric and NeoForge are not compatible with each other.
Also, the same loader for a certain game version is almost always not compatible with mod files which support other game versions.
E.G.: if I install a mod for 1.17, it usually will not work on later or earlier versions of the game like 1.16 or 1.18.

Which one should you choose? Fabric or NeoForge?
Fabric is the preferred loader for most of this mod's users, as it has a simple installer and fast updates to the latest game version. If you are new to Minecraft, Fabric will be easier to set up and provide you with all of the mods you will need.

### Install Fabric

[Here is the download page for the Fabric loader](https://fabricmc.net/use/installer/).
Fabric provides an executable installer for Windows, click the `Download for Windows` button to download it.
The executable file is named as `fabric-installer-<installer-version-number>.exe`, for example, `fabric-installer-0.11.2.exe`.
We have received reports that some screen readers such as NVDA cannot read the installer's interface if your current input method is not English.
Try to switch to English input method first, if the problem still persists, please download the jar form of the installer by clicking the `Download universal jar` button, just below the `Download for Windows` button.

Start the installer, a window pops up for you to choose the installation configurations:

1. The `Client` tab is selected by default, don't change it.
2. The first combo box is for selecting the game version you want to install, please refer to the current game version that is supported by this mod on the [releases page](https://github.com/khanshoaib3/minecraft-access/releases) (under the `Mod Version Compatibility` section), and change the combo box to select that game version.
3. Next is a checkbox for showing game snapshot versions, just ignore it.
4. The second combo box is for selecting the Fabric loader version, the latest version is selected by default and you should also not touch this either.
5. Then there is an input field for specifying the installation location, the installer will recognize the correct folder automatically if you have been following this guide correctly,
   for example `C:\Users\username\AppData\Roaming\.minecraft`, that's where we want it to install so no need to change this either.
   Copy this path to somewhere like the Notepad for further usage when installing mod files.
6. And then a checkbox for `Create profile`, checked by default, we want it to create a profile in the official launcher, so there is no need to change this option either.
7. Finally, click the `Install` button to start the installation.
   It will download some files from the Internet, if the network is fast, the installation takes less than a minute.
   A pop-up will show up to notify you that the installation is successful.

### Install NeoForge

This guide does not provide exact instructions for installing NeoForge, as it is a more advanced mod loader with many existing tutorials and walk throughs online. Fabric is the recommended experience for new players who are using this guide.
NeoForge is only needed for advanced users or those wishing to heavily modify their game.

## Install Mods

There are several popular mod download platforms, such as [Modrinth](https://modrinth.com/mods) and [CurseForge](https://legacy.curseforge.com/minecraft/mc-mods), generally mod developers will release their work on multiple mod listing platforms at the same time.
Mod developers will release mod files that are compatible with different game versions.
Please always keep this in mind when downloading mods, as incompatible mods will crash the game at startup.
It is worth mentioning that some mods can support multiple versions with one same mod file, and generally this will be mentioned in the mod file name.
It is also good to note that all mod files have the ".jar" extension, but some downloaders or browsers may download these files in a compressed format like ".zip", in this case you will have to extract the real mod file from the compressed one.

Neither mod loaders nor the original game provide the ability to manage mod files, you need to manage them manually.
This guide will describe how to download and install mods manually, you can also manage them automatically with an extra application such as [Modrinth](https://modrinth.com/app) or [CurseForge for Windows](https://www.curseforge.com/download/app).

Now let's download mod files.
To download this mod and the dependencies of this mod, it is recommended you download them from the Minecraft Access's official [releases page](https://github.com/khanshoaib3/minecraft-access/releases/latest), where you can find download links of suitable versions of the required mods, under the `Mod Version Compatibility` section of each release.
By the way, you may be interested in the mods [provided in good resources page](/doc/GOOD_RESOURCES.md#quality-of-life-mods), they are good mods that our visually impaired users have found and tested through practice.

After all the mods you want are downloaded, you can move on to putting them into the right location for the mod loader to recognize them.
The default path is: `%appdata%\.minecraft\mods` (the `%appdata%` is a shortcut for `C:\Users\username\AppData\Roaming`), you can directly paste it into File Explorer then press the enter key to jump to it or paste it into the run box accessed with the Windows+R keys and click enter.
We'll put downloaded mod files inside it, both Fabric and NeoForge will load mods from this folder by default if installed. You can look up how to use alternate installation locations if you wish to maintain both Fabric and NeoForge installations at the same time.
You will need to start the game once after installing the mod loader for the `mods` folder to be created.

## Start the Game

The first time you start the launcher, it requires you to sign in with your Microsoft account.

The launcher's sidebar contains not only `Minecraft: Java Edition`, it also includes `Minecraft for Windows`, and even two derivative games of Minecraft.
The sidebar also contains an entry for the settings of the launcher itself.
If you want to change your username or player appearance, for Java Edition, you need to go to the launcher settings screen, `Accounts` tab, select your account, `More Options` button, `Manage Minecraft: Java Edition profile`, then a web page will be opened in your browser which leads you to the profile management page.

The main screen of `Minecraft: Java Edition` in the launcher contains four tabs:

* `Play` - Where you can choose which profile to play and start the game.
* `Installations` - Where you can manage different game profiles for different game versions or game configurations.
* `Skins` - You can upload skin files here to change your appearance in game.
* `Patch Notes` - Update logs about the game.

Select the profile created by the mod loader installer and start it, for Fabric, the profile name is `fabric-loader-<game-version>`, for NeoForge, the name is simply `NeoForge` but the subtitle contains the game version. If everything worked correctly, you can start exploring the game.

Please note, the first time you launch a new profile that you will need to enable the narrator option from within minecraft after you enter the main screen of the game (when you hear the music), there will be an accessible popup for this purpose, that's a built in function of the game.
You can also press `Control+B` to enable the narrator, keep pressing until it switches to `Narrator Narrates All`.

If the game crashes and a bunch of error logs pop up, the biggest probability is that there is an incompatibility between the mod files and mod loader, or between the mod files and game version, please read the [Update the Game and Mods](#update-the-game-and-mods) section.
And here is a [complex self-helped FAQ](./FAQ.md#self-help-guide-for-abnormal-situation) for you to address the problem, from not being able to hear the narration to a full game crash.

## Update the Game and Mods

Updating the game is simple, for [Fabric](#install-fabric), run the fabric installer again and change the installed game version in the Fabric installer screen, for [NeoForge](#install-forge), download another installer for the game version you want and install it.
You need to check all mod files in the `mods` folder for compatibility with the new game version, and replace or remove incompatible mods.

Now let's talk about how to update mods, recall what is mentioned above:

1. Mods of Fabric and NeoForge are not compatible with each other.
2. The same loader for a certain game version is almost always not compatible with mod files which support other game versions.
E.G.: if I install a mod for 1.17, it usually will not work on later or earlier versions of the game like 1.16 or 1.18.
3. Both Fabric and NeoForge load mods from the folder `%appdata%\.minecraft\mods` by default.

Here are some examples to help you understand:

* Fabric loader with any NeoForge mod file in the `mods` folder, will crash, and vice versa.
* Fabric loader for game version `1.19.3`, with a Fabric mod file for game version `1.20.1` will crash.
* All mod files in the `mods` folder need to be conflict free for the game to start.

Based on these conditions, if you want to update some mods versions while keeping the game version unchanged, you just need to replace the corresponding mod files.
For mods that have dependencies like Minecraft Access, you also need to be aware of whether the version of dependent mods have also changed.

Another situation that causes the game to crash or misbehave is conflict between mods, which is difficult to show in the error log because it is hard to predict.
For this type of error, there is a simple yet cumbersome method for all modded games: move all mod files out of the Mods folder and into a temporary folder, then add them back to the Mods folder one by one, try to start the game every time you move back a file.

## Other Pages

* [Home](/README.md)
* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [Sound Effects](https://html-preview.github.io/?url=https://github.com/khanshoaib3/minecraft-access/blob/1.20/doc/SOUND.html)
* [FAQ](/doc/FAQ.md)
* [Good Resources](/doc/GOOD_RESOURCES.md)