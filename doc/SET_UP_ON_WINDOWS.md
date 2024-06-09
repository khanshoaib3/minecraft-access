# Tutorial for Windows: From Purchasing Game To Installing This Mod

This tutorial tries to guide you step by step on how to set the whole thing up. This tutorial is for Windows system.

If you want to chat with other this mod's users and developers, please join [our Discord server](https://discord.gg/yQjjsDqWQX).
If you know where this tutorial could be improved, please let us know as well.

> The paragraphs in the block quotes are additional nonsense.
> They do not provide help with the installation, but they can give you a little more insight into things that are relevant.
> If you don't want to read them, skip and move on, you won't miss anything important.

## Index

1. [Purchase the Game and Download the Launcher](#purchase-the-game-and-download-the-launcher)
    1. [Purchase the Game](#purchase-the-game)
    2. [Download the Launcher](#download-the-launcher)
2. [Choose and Install the Mod Framework](#choose-and-install-the-mod-framework)
    1. [Install the Java](#install-the-java)
    2. [Install the Fabric](#install-the-fabric)
    3. [Install the Forge](#install-the-forge)
3. [Install Mods](#install-mods)
4. [Additional Installation for Windows](#additional-installation-for-windows)
5. [Start the Game](#start-the-game)
6. [Upgrade the Game and Mods](#upgrade-the-game-and-mods)
7. [Some Helpful Resources](#some-helpful-resources)
    1. [Quality of Life Mods](#quality-of-life-mods)
    2. [A Very Simple Beginner Guide](#a-very-simple-beginner-guide)

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

There are two editions of Minecraft: Java Edition and Bedrock Edition.
This Mod is only available for the Java Edition.
Don't worry about buying the wrong one, the two editions are sold in a bundle.

> While we need not concern ourselves with the Bedrock Edition, I would like you to know the differences between the two editions:
>
> At one time the Minecraft only had one edition, the Java Edition. Since Microsoft acquired Mojang Studios, they have launched a new Bedrock Edition.
> The Java Edition is available for PC (Windows, Mac and Linux), while new Bedrock Edition is available for smartphones, game consoles, etc.
> Microsoft makes the Bedrock Edition playable on Windows as well, and named it as `Minecraft for Windows` which increase the confusion.
>
> Please also note that the Bedrock Edition in the next purchase link only contains the version that runs on Windows.
> The Bedrock Edition on other platforms, such as on smartphones, need to be purchased separately in the mobile application store.

If you've got a redeem code of this game, [here is the redeem page](https://www.minecraft.net/en-us/redeem), no need to buy the game.
Or if you're Xbox Game Pass member, you can also [get the Minecraft for free](https://help.minecraft.net/hc/en-us/articles/4412266114573-Minecraft-Java-Bedrock-Edition-for-PC-is-on-Xbox-Game-Pass-).

There are currently two purchasable versions of Minecraft, [the Deluxe Collection](https://www.minecraft.net/en-us/store/minecraft-deluxe-collection-pc) and [the bare game](https://www.minecraft.net/store/minecraft-java-bedrock-edition-pc).
I want to remind you that things that the Deluxe Collection has more than the bare game are just virtual currency and items only in the Bedrock Edition, that we can't use and don't care.

The links will jump to the pages that correspond to the language of your browser, and there is a `CHECKOUT` button.
Click it, the website will be redirected, then it require you to sign in before the purchase with a... Microsoft account, well, makes sense.
Your purchase will be tied to a Microsoft account, when you log into the launcher, you also need to log into this account.
Keep going after you have logged in, I don't know how to describe the purchase page, fill in the purchase information and click on confirm, that's all.

### Download the Launcher

> The launcher is something that inherited all the way from old version of Minecraft.
> Like the launchers for some old PC games, you can use the launcher to choose which version of Minecraft you want to run, or to modify the game launch configuration.
> There are many kinds of launchers, anyone can write their own launcher to provide customized Minecraft game launching experience.
> The launcher we will download is provided by Microsoft, let's call it the official launcher.

At the end of the purchase process there will be a `Download Game` button to download the launcher.
If you've missed it, [here is the launcher installer download page](https://www.minecraft.net/download).
Note that Microsoft has replaced old official launcher installer (`MinecraftInstaller.msi`, that "Download Windows Legacy Launcher" button provided) with their new launcher installer for Microsoft store (`MinecraftInstaller.exe`, that "Download Launcher for Windows 10/11" button provided).

Both two launcher installers installs the (same?) launcher that can run the Java Edition game that this mod required, and subsequent mod installation steps are same.
The new launcher installer will let you log into Microsoft Store first before installing the launcher.
So if you have problem with Microsoft Store service, please download the older one (the `MinecraftInstaller.msi`).

The launcher installer is a normal executable file, run it and wait for the finish.
If the installation is successful, you will now have a new application called `Minecraft Launcher`.

The next steps require starting the launcher once first to let it generate the game folder, so try [starting the launcher](#start-the-game), then start whatever version of the Java Edition Minecraft (the latest version by default).
Something may need to be prepared every time the launcher starts, especially at the first time the whole game need to be downloaded.
This may take a few minutes, and you can listen to the screen reader's progress bar sound effect to know the progress.
At the first time you start the launcher it requires you to sign in with your Microsoft account.

## Choose and Install the Mod Framework

> The original Java Edition doesn't support modding, but the community makes it possible.
> Like any other game, the mod loader is responsible for loading the mod files into the game at launch.

In order to use this mod, you need to modify the game, or to be precise, install a copy of the modified game that is ready for use, the mod framework installer will download and install it for you.

There are currently four mod frameworks: the old and mature Forge (MinecraftForge), the young and flexible Fabric, more younger Quilt which splits from Fabric, and the youngest NeoForge which splits from Forge.
The mod framework itself and mods for Fabric and Forge are not compatible with each other.
Also, the same framework for different game versions are not compatible with mod files which support other game versions.
This mod supports for both Forge and Fabric, it might also support the other two if there is enough demand.

> I really don't know how to briefly describe the two, as time goes by, the difference between the two becomes smaller and smaller.
> Forge is an old school framework, existed almost as long as Minecraft.
> Years of development have made it huge and stable, for example, its support for new game versions will be slower than Fabric, but making mod for Forge is easier.
> Fabric is a modern framework with a lightweight design, fast support for new game versions.
> It may require more frequent mod file upgrades (downloads and replaces) for users.
>
> Originally there are many famous heavy mods only on Forge, they can modify Minecraft into almost another game, developing such mods on Fabric will be more difficult.
> But as time passing by, many heavy mods have provided Fabric version.
> And, there are many new good mods only provide Fabric version (some good people will port them onto Forge).

Which one would you choose, Forge or Fabric? Basically you can only choose one, and expect all the mods you want to use support that framework.
I recommend Fabric because I have observed that most of the users of this mod use Fabric.
However, if you want to play on a specific multiplayer server, ask the administrator in advance what framework they need the client (i.e. your local game) to install, the vanilla servers do not require the client to install a specified framework.

In fact, you can keep two frameworks at the same time within the official launcher.
But for bad historical design reason, you need to replace the contents of the `mods` folder with the corresponding mod files everytime before switching (i.e. running a game based on a different framework), which is a pain.

### Install the Java

> Java is a programming language, the Minecraft Java Edition and mod frameworks are written in this language.
> To be more precise, installing Java here means installing the JRE, the Java Runtime Environment which allows you to run Java written things, the jar file we just downloaded for example.
> It's like installing a driver on your computer in order to use a hardware.
> But actually we'll install the JDK, the Java Development Kit, which contains JRE, because Oracle, the former owner of Java, no longer offers a separate JRE installer.

Both Fabric and Forge requires the Java to be installed on your PC previously.
Minecraft 1.20.5 requires Java 21 to run, but you can also run previous game version with Java 21.
[Here is the download page for Java 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows).
Also, if you think the download page is complicated, [here is the direct link to download the JDK21 installer for the x64 architecture PC](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.msi).
Just run the installer and keep clicking Next, there's nothing to say.
Restart your computer after the installation is completed.

### Install the Fabric

[Here is the download page for the Fabric loader](https://fabricmc.net/use/installer/).
Fabric provides an executable installer for Windows, click the `Download for Windows` button to download it.
The executable file is named as `fabric-installer-<installer-version-number>.exe`, for example, `fabric-installer-0.11.2.exe`.
We have received reports that some screen readers such as NVDA cannot read the installer's interface if your current input method is not English.
Try to switch to English input method first, if the problem still persists, please download the jar form of the installer by click the `Download universal jar` button, just below the `Download for Windows` button.

> In addition to the official launcher, there are a number of ways to install Fabric on other launchers available at the bottom of this download page.
> macOS and Linux users need to first install Java separately then download and run the `universal jar`, which is more troublesome than Windows.

Start the installer, a window pops up for you to choose the installation configurations:

1. The `Client` tab is selected by default, yeah we want to install the client, don't change it.
2. The first combo box is for selecting the game version you want to install, please refer to the current game version that supported by this mod on [releases page](https://github.com/khanshoaib3/minecraft-access/releases) (under `Mod Version Compatibility` section), and change the combo box to select that game version.
3. Next is a checkbox for showing game snapshot versions, just ignore it, it's for mod developers.
4. The second combo box is for selecting the Fabric loader version, the latest version is selected by default and no need to change.
5. Then there is an input field for specifying the installation location, normally the installer will automatically recognize the official loader's folder,
   for example `C:\Users\username\AppData\Roaming\.minecraft`, that's where we want it to install so no need to change again.
   Copy this path to somewhere like the Notepad for further usage when installing mod files.
6. And then a checkbox for `Create profile`, checked by default, we want it to create a profile in the official launcher, no need to change.
7. Finally, click `Install` button to start the installation.
   It will download some files from the Internet, if the network is fast, the installation takes less than a minute.
   A pop-up will show up to remind you that the installation is successful.

### Install the Forge

Forge requires you to select the right version of the game at the time of download the installer.
Reports have been received that higher versions of Forge that are compatible with the game version may not be compatible with this mod (this mod is always built on one specific Forge version), so it is recommended to download the tested version of Forge with link in the [releases page](https://github.com/khanshoaib3/minecraft-access/releases) (under `Mod Version Compatibility` section).
If you still want to download the latest version of Forge for some reason, [here is the download page for that version of Forge](https://files.minecraftforge.net/net/minecraftforge/forge/).
Select the `Installer` button under the `Download Recommended` sector.
Then the website will redirect you to a Minecraft server provider's advertising page, in which you'll find a `SKIP` button, click it to trigger the real downloading process.
An executable jar file will be downloaded, named as `forge-<game-version>-<forge-version>-installer.jar`, for example `forge-1.20.1-47.1.0-installer.jar`.

We have received reports that some screen readers such as NVDA cannot read the installer's interface if your current input method is not English.
Try to switch to English input method first then start the Forge installer.
Now you can run the forge jar file like normal executable file, a window pops up for you to choose the installation configurations:

1. First is a radio box for selecting install client or server, `Install client` is selected by default, yeah we want to install the client, no need to change it.
2. Then there is an input field for specifying the installation location, normally the installer will automatically recognize the official loader's folder,
   for example `C:\Users\username\AppData\Roaming\.minecraft`, that's where we want it to install so no need to change again.
   Copy this path to somewhere like the Notepad for further usage when installing mod files.
3. Now click the `OK` button to start the installation.
   It will download some files from the Internet, if the network is fast, the installation takes about three or two minutes.
   A pop-up will show up to remind you that the installation is successful.

## Install Mods

In fact there is no installation step, actually we just download and place the mod files into a special folder, let the mod frameworks know what mods we'd like to load.

The mod framework itself does not provide the ability to manage mod files, you need to manage them manually.
Some applications, like [CurseForge for Windows](https://www.curseforge.com/download/app), make it easier to manage mod files, but I haven't used them, so I don't know if they work well.
You can write Powershell or batch scripts to implement automatic file replacement, but this is a complex topic and will not be expanded here.

Now let's download mod files.
There are several popular mod download platforms, such as [CurseForge](https://legacy.curseforge.com/minecraft/mc-mods), [Modrinth](https://modrinth.com/mods), generally mod developers will release their works on multiple platforms at the same time.
Mod developers will release mod files that are compatible with different game versions.
Please always keep this in mind when downloading mods, as incompatible mods will crash the game at startup.
It is worth mentioning that some mods can support multiple versions with one same mod file, and generally this will be mentioned in the mod file name.

For downloading this mod and the dependencies of this mod, I recommend you download them at the [release page](https://github.com/khanshoaib3/minecraft-access/releases), where we'll prepare download links of suitable version of mods, under `Mod Version Compatibility` section of each release.
Older versions of this mod used to depend on `fabric-api` and `architectury-api`, they are no longer needed since game version `1.20.6`.
This mod's jar file is the only one which is necessary to play with this mod.

By the way, you may be interested in the mods [provided at the end of this tutorial](#quality-of-life-mods), they are good mods that our visually impaired users have found and tested through practice.

After all download works, let's put them into right location.
Here it is: `%appdata%\.minecraft\mods`, you can directly paste it into File Explorer then press enter key to jump into it.
We'll put downloaded mod files inside it, both Fabric and Forge will load mods from this folder.
We've learned from user report that we need to start the launcher once after installing the mod framework for the `mods` folder to be created.
Haven't experimented but, I think it's ok to create it manually.
It worth to note that all mod files (and resource pack files) have the "jar" extension, some downloader or browsers may download files as a compressed file like zip format, you'd extract the real mod file from the compressed one.

## Additional Installation for Windows

Given the specificity of this mod, we need to install a few extra things to make this mod work.
For Windows, we need the [tolk](https://github.com/ndarilek/tolk) extension, which enable the mod to speak through multiple screen readers such as [NVDA](https://www.nvaccess.org/download/) and [JAWS](https://www.freedomscientific.com/products/software/jaws/).
We need to download the tolk extension files and place it under the minecraft folder.
We've implemented the automatic set-up process inside the mod, it will set up the tolk for you at the first time you starting the modded game.

> Here is the original guide for manually installing tolk, I put it here in case you need to adjust the tolk's files manually:
>
> 1. Download the latest build zip - here.
> 2. Extract the downloaded zip file anywhere you like, the Downloads folder should be fine.
> 3. Open the extracted folder and in that folder you'll find a folder called x64 and another called x86.
> 4. Based on your operating system, x64 for 64-bit OS and x86 for 32-bit OS, copy all the files present in the folder and paste them into the minecraft root folder (the folder containing the mods folder). The default location is `%appdata%\.minecraft`

## Start the Game

Microsoft launcher, or the official launcher, at the first time you start the launcher, it requires you to sign in with your Microsoft account.

The launcher's sidebar contains not only the Java Edition, it also includes `Minecraft for Windows`, and even two derivative games of Minecraft - `Minecraft Dungeons` and `Minecraft Legends`.
The sidebar also contains an entry for the settings of the launcher itself.
If you want to change your name in the multiplayer game of the Java Edition, you need to go to the launcher settings screen, `Accounts` tab, select your account, `More Options` button, `Manage Minecraft: Java Edition profile`, then a web page will be opened in your browser which leads you to the profile management page. Good UI design, Microsoft.

The main screen of `Minecraft Java Edition` contains four tabs:

* `Play` - Where you can choose which profile to play and start the game.
* `Installations` - You can have different game profiles for different game version or game configuration, you can manage them here.
* `Skins` - You can upload skin files here to change your appearance in game.
* `Patch Notes` - Update logs about the game.

Select the profile created by the mod framework installer and start it, for Fabric, the profile name is `fabric-loader-<game-version>`, for Forge, the name is simply `forge` but the subtitle contains the game version. If everything is fine, you can start exploring the game.

Please note that you need to enable the narrator configuration from within minecraft after you enter the main screen of game (when you hear the music), there will be an accessible setting guide for this purpose, that's a game built-in function.
You can also press `Control + B` to enable the narrator, keep pressing to switch it to `Narrator Narrates All`.

If the game crashes and a bunch of error logs pop up, the biggest probability is that there is an incompatibility between mod files and mod framework, or between mod files and game version, please read the [Upgrade the Game and Mods](#upgrade-the-game-and-mods) section.
And [here is a complex self-helped FAQ](./FAQ.md#self-help-guide-for-abnormal-situation) for you to address the problem, from can't hear the narration to crash.

## Upgrade the Game and Mods

Upgrade the game is simple, for [Fabric](#install-the-fabric), change the preferring installing game version in Fabric installer screen, for [Forge](#install-the-forge), download another installer for the game version you want and install it.
You need to check all mod files in the `mods` folder for compatibility with the new game version, and replace or remove incompatible mods.

Now let's talk about how to upgrade mods, recall what is mentioned above:

1. The mod framework itself and mods of Fabric and Forge are not compatible with each other.
2. The same framework for different game versions are not compatible with mod files which support other game versions.
3. Both Fabric and Forge load mods from the folder `%appdata%\.minecraft\mods`.

Some examples to help you understand:

* Fabric loader with any Forge mod file in the `mods` folder, crush, vice versa.
* Fabric loader for game version `1.19.3`, with a Fabric mod file for game `1.20.1` in the `mods` folder, crush.
* All mod files in the `mods` folder need to be conflict free for the game to start.

According to these conditions, if you want to upgrade some mods version while keeping the game version unchanged, you just need to replace the corresponding mod files.
For mods that have dependencies like this mod, you also need to be aware of that whether the version of dependent mods have changed.
For example, when upgrading this mod, you should be also aware the version changes of `Fabric Api` and `Architectury Api` mods.

Another situation that causes the game to crash or misbehave is conflict between mods, which is difficult to reflect in the error log because it is hard to predict.
For this type of error, there is a simple, common, cumbersome, no-better-choice method for all modded games: move all mod files into a temporal folder, then add them back one by one, try to start the game every time you move back a file.

## Some Helpful Resources

* [The manual of this mod](https://github.com/khanshoaib3/minecraft-access) - Check what features this mod provides.
* [Minecraft Wiki](https://minecraft.wiki/w/Minecraft_Wiki) - Has details and guides on everything about Minecraft, it also has [many detailed text tutorials](https://minecraft.wiki/w/Tutorials) on it.
* [Minecraft Survival Guide (Season 3)](https://www.youtube.com/watch?v=VfpHTJsn9I4&list=PLgENJ0iY3XBjmydGuzYTtDwfxuR6lN8KC) - A great series by Pixlriffs, highly recommend for beginners.
* [Playability Discord server](https://discord.gg/yQjjsDqWQX) - Again, you can join our Discord server if you need help in setting up the mod or any issue related to Minecraft Java.
* [Twitter](https://twitter.com/shoaib_mk0) - You can follow the developer on Twitter to get notification when a new update drops.
* [Patreon](https://www.patreon.com/shoaibkhan)

### Quality of Life Mods

1. Client side, which you can use in both single-player and multiplayer game:
    * Presence Footsteps ([Fabric](https://modrinth.com/mod/presence-footsteps), [Forge port](https://www.curseforge.com/minecraft/mc-mods/presence-footsteps-forge)): Footstep sound enhancement mod. If the Fabric version of mod isn't work, select `Default sound pack` in the resource pack menu. If the mod has not supported your game version, you would consider to use this resource pack
      instead: [Presence Footsteps: Remastered Sounds Pack](https://modrinth.com/resourcepack/presense-footsteps-sounds), put it under `%appdata%\.minecraft\resourcepacks` folder.
    * Just Enough Items ([Fabric and Forge](https://modrinth.com/mod/jei)): Item and recipe viewing mod, far better than similar recipe book feature in original game (but this mod hasn't been 100% accessible by us, you need some vision to use it).
    * Sound Physics Remastered ([Fabric and Forge](https://modrinth.com/mod/sound-physics-remastered)): Provides realistic sound attenuation, reverberation, and absorption through blocks. I'm sure this mod consumes resources, so install it only when you're confident in your PC hardware.

2. Server side, which you can only use in single-player game (unless the multiplayer server admins add them into the server):
    * Tree Harvester ([Fabric and Forge](https://modrinth.com/mod/tree-harvester)): Harvest trees and huge mushrooms instantly with an axe. It's a common scenario in Minecraft that you can't reach the logs and leaves at higher place when standing on the ground, with this mod, you won't bother yourself with stepping yourself up.
    * Carry On ([Fabric and Forge](https://modrinth.com/mod/carry-on)): Allowing players to pick up, carry, and place some blocks (such as Chests) and animals without breaking blocks or guiding animals with a leash.
    * FTB Ultimine ([Fabric](https://www.curseforge.com/minecraft/mc-mods/ftb-ultimine-fabric), [Forge](https://www.curseforge.com/minecraft/mc-mods/ftb-ultimine-forge)): This mod can makes you operate on same type blocks around you by operating only once, like harvest crops, mining a vein and chopping trees.

### A Very Simple Beginner Guide

Try reading [text tutorials for beginners on Minecraft Wiki](https://minecraft.wiki/w/Tutorials), if you find out that listening YouTube videos to learn to play this game is slow or difficult.
Because Minecraft's world generation is randomized, you may run into the difficulty of trying to generate multiple worlds but all of their spawn location are bad. You can search good [world generation seeds](https://minecraft.wiki/w/Seed_(level_generation)) [on Google](https://www.google.com/search?client=firefox-b-d&q=minecraft+good+seed) and set them when you're creating single player worlds.

I highly recommend you lower the difficulty of the game to get better experience, such as change the [difficulty](https://minecraft.wiki/w/Difficulty) to `Peaceful` to avoid monster spawning, or [use](https://www.minecraft.net/en-us/article/minecraft-commands) [commands](https://minecraft.wiki/w/Commands) to gain advantages (remember to turn on `Allow Cheats` option when you create the world).
Some commands are very convenient when you're playing single player worlds, such as ` /gamerule keepInventory true` to keep your inventory (highly recommended), `/tp` to teleport to another location, `/locate village` to find the nearest village, `/attribute @p minecraft:generic.max_health base set 1000` to make you take more damage so you can explore the game features freely, etc.