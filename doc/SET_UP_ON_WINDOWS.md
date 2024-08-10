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
    3. [Install the NeoForge](#install-the-neoforge)
3. [Install Mods](#install-mods)
4. [Additional Installation for Windows](#additional-installation-for-windows)
5. [Start the Game](#start-the-game)
6. [Upgrade the Game and Mods](#upgrade-the-game-and-mods)

## Purchase the Game and Download the Launcher

### Purchase the Game

Before you purchase the game, make sure you have a computer with a 64-bit processor. [Minecraft no longer supports 32-bit computers around 2022](https://www.minecraftforum.net/forums/minecraft-java-edition/recent-updates-and-snapshots/3140967-the-end-of-minecraft-32-bit).
Below are system requirements from [the official website](https://www.minecraft.net/en-us/store/minecraft-java-bedrock-edition-pc):

1. Minimum:
    * OS: Windows 7
    * Architecture: ARM64, x64
    * Memory: 2 GB
    * Processor: Intel Core i3-3210 3.2 GHz | AMD A8-7600 APU 3.1 GHz | Apple M1
    * Graphics: Intel HD Graphics 4000 | AMD Radeon R5

2. Optimum:
    * OS: Windows 10
    * Architecture: ARM64, x64
    * Memory: 4 GB
    * Processor: Intel Core i5-4690 3.5 GHz | AMD A10-7800 APU 3.5 GHz | Apple M1
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

If you've got a redeem code of this game, [here is the redeem page](https://www.minecraft.net/redeem), no need to buy the game.
Or if you're Xbox Game Pass member, you can also get the Minecraft for free. Unfortunately, the help link is dead.

There are currently two purchasable versions of Minecraft, [the Deluxe Collection](https://www.minecraft.net/store/minecraft-deluxe-collection-pc) and [the bare game](https://www.minecraft.net/store/minecraft-java-bedrock-edition-pc).
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
If you've missed it, [here is the launcher installer download page](https://www.minecraft.net/download). Or If you know how to use the Microsoft Store or Xbox, you can download it from there.
Note that Microsoft has replaced old official launcher installer (`MinecraftInstaller.msi`, that "Download Windows Legacy Launcher" button provided) with their new launcher installer for Microsoft store (`MinecraftInstaller.exe`, that "Download Launcher for Windows 10/11" button provided).

Both two launcher installers installs the same(?) launcher that can run the Java Edition game that this mod required, and subsequent mod installation steps are same.

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

There are currently three mod frameworks: the young and flexible Fabric, more younger Quilt which splits from Fabric, and the youngest NeoForge which splits from Forge. Forge was effectively destroyed in 2023, when a conflict broke out and NeoForge was created.
The mod framework itself and mods for Fabric and NeoForge are not compatible with each other.
Also, the same framework for different game versions are not compatible with mod files which support other game versions.
This mod supports for both NeoForge and Fabric, it might also support the other one if there is enough demand.

> I really don't know how to briefly describe the two, as time goes by, the difference between the two becomes smaller and smaller.
> Forge is an old school framework, existed almost as long as Minecraft.
> Years of development have made it huge and stable, for example, its support for new game versions will be slower than Fabric, but making mod for Forge is easier.
> Fabric is a modern framework with a lightweight design, fast support for new game versions.
> It may require more frequent mod file upgrades (downloads and replaces) for users.
>
> Originally there are many famous heavy mods only on Forge, they can modify Minecraft into almost another game, developing such mods on Fabric will be more difficult.
> But as time passing by, many heavy mods have provided Fabric version.
> And, there are many new good mods only provide Fabric version (some good people will port them onto Forge).

Which one would you choose, NeoForge or Fabric? Basically you can only choose one, and expect all the mods you want to use support that framework.
I recommend Fabric because I have observed that most of the users of this mod use Fabric.
However, if you want to play on a specific multiplayer server, ask the administrator in advance what framework they need the client (i.e. your local game) to install, the vanilla servers do not require the client to install a specified framework.

In fact, you can keep two frameworks at the same time within the official launcher.
But for bad historical design reason, you need to replace the contents of the `mods` folder with the corresponding mod files everytime before switching (i.e. running a game based on a different framework), which is a pain.

### Install the Java

> Java is a programming language, the Minecraft Java Edition and mod frameworks are written in this language.
> To be more precise, installing Java here means installing the JRE, the Java Runtime Environment which allows you to run Java written things, the jar file we just downloaded for example.
> It's like installing a driver on your computer in order to use a hardware.
> But actually we'll install the JDK, the Java Development Kit, which contains JRE, because Oracle, the former owner of Java, no longer offers a separate JRE installer.

Both Fabric and NeoForge requires the Java to be installed on your PC previously.
Minecraft 1.20.5 requires Java 21 to run, but you can also run previous game version with Java 21.
Do not install Java 22 yet. In some Java applications, keyboard input may not work properly (for example, test is typed as tst).
[Here is the download page for Java 21](https://www.oracle.com/java/technologies/downloads/#jdk21-windows).
Also, if you think the download page is complicated, [here is the direct link to download the JDK21 installer for the x64 architecture PC](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.msi).
Just run the installer and keep clicking Next, there's nothing to say.
Restart your computer after the installation is completed. Otherwise, installing the mod framework will not work properly with screen readers such as nvda.

### Install the Fabric

[Here is the download page for the Fabric loader](https://fabricmc.net/use/installer/).
Click the Download universal jar button to start downloading. You can also click the Download for Windows button to download the installer for Windows, but it may not work properly with some screen readers.
The executable jar file is named as `fabric-installer-<installer-version-number>.jar`, for example, `fabric-installer-1.0.1.jar`.

> In addition to the official launcher, there are a number of ways to install Fabric on other launchers available at the bottom of this download page.
> macOS and Linux users need to first install Java separately then download and run the `universal jar`, which is more troublesome than Windows.

Before running the installer, download and run the patch file from [here](https://johann.loefflmann.net/en/software/jarfix/index.html) so that you can run the jar file directly in Windows File Explorer. If you downloaded the installer for Windows, skip this step.
Start the installer, a window pops up for you to choose the installation configurations:

The fabric installer supports multiple languages. Unlike NeoForge, the language is automatically set to the configured Windows language.
1. The `Client` tab is selected by default, yeah we want to install the client, don't change it.
2. The first combo box is for selecting the game version you want to install, please refer to the current game version that supported by this mod on [releases page](https://github.com/khanshoaib3/minecraft-access/releases) (under `Mod Version Compatibility` section), and change the combo box to select that game version.
3. Next is a checkbox for showing game snapshot versions, just ignore it, it's for mod developers.
4. The second combo box is for selecting the Fabric loader version, the latest version is selected by default and no need to change.
5. Then there is an input field for specifying the installation location, normally the installer will automatically recognize the official loader's folder.
 Do   for example `C:\Users\username\AppData\Roaming\.minecraft`, that's where we want it to install so no need to change again.
   Copy this path to somewhere like the Notepad for further usage when installing mod files.
6. And then a checkbox for `Create profile`, checked by default, we want it to create a profile in the official launcher, no need to change.
7. Finally, click `Install` button to start the installation.
   It will download some files from the Internet, if the network is fast, the installation takes less than a minute.
   A pop-up will show up to remind you that the installation is successful.

### Install the NeoForge

NeoForge requires you to select the right version of the game at the time of download the installer.
Reports have been received that higher versions of NeoForge that are compatible with the game version may not be compatible with this mod (this mod is always built on one specific Forge version), so it is recommended to download the tested version of Forge with link in the [releases page](https://github.com/khanshoaib3/minecraft-access/releases) (under `Mod Version Compatibility` section).
If you still want to download the latest version of NeoForge for some reason, [here is the download page for that version of NeoForge](https://neoforged.net/).
Select the `Installer` button under the `Download Recommended` sector.
An executable jar file will be downloaded, named as `neoforge-<neoforge-version>-installer.jar`, for example `neoforge-21.1.4-installer.jar`.

Before running the installer, download and run the patch file from [here](https://johann.loefflmann.net/en/software/jarfix/index.html) so that you can run the jar file directly in Windows File Explorer. If you downloaded the installer for Windows, skip this step.
Now you can run the NeoForge jar file like normal executable file, a window pops up for you to choose the installation configurations:
The NeoForge installer supports multiple languages, so if you want to change the language, you can do so by pressing Shift Tab on the radio button.
1. First is a radio box for selecting install client or server, `Install client` is selected by default, yeah we want to install the client, no need to change it.
2. Then there is an input field for specifying the installation location, normally the installer will automatically recognize the official loader's folder.
   For example `C:\Users\username\AppData\Roaming\.minecraft`, that's where we want it to install so no need to change again.
   Copy this path to somewhere like the Notepad for further usage when installing mod files.
3. Now click the `OK` button to start the installation.
   It will download some files from the Internet, if the network is fast, the installation takes about three or two minutes.
   A pop-up will show up to remind you that the installation is successful.

## Install Mods

There are several popular mod download platforms, such as [CurseForge](https://legacy.curseforge.com/minecraft/mc-mods), [Modrinth](https://modrinth.com/mods), generally mod developers will release their works on multiple platforms at the same time.
Mod developers will release mod files that are compatible with different game versions.
Please always keep in mind when downloading mods, as incompatible mods will crash the game at startup.
It is worth mentioning that some mods can support multiple versions with one same mod file, and generally this will be mentioned in the mod file name.
It worth to note that all mod files (and resource pack files) have the "jar" extension, some downloader or browsers may download files as a compressed file like zip format, you'd extract the real mod file from the compressed one.

Neither mod frameworks nor the original game provide the ability to manage mod files, you need to manage them manually.
This guide will describe how to download and install mods manually, you can also manage them automatically with an extra application such as [Modrinth](https://modrinth.com/app) or [CurseForge for Windows](https://www.curseforge.com/download/app).
In fact there is no installation step, actually we just download and place the mod files into a special folder, let the mod frameworks know what mods we'd like to load.

Now let's download mod files.
For downloading this mod and the dependencies of this mod, I recommend you download them at the [release page](https://github.com/khanshoaib3/minecraft-access/releases), where we'll prepare download links of suitable version of mods, under `Mod Version Compatibility` section of each release.
Older versions of this mod used to depend on `fabric-api` and `architectury-api`, it's no longer needed since game version `1.20.6`.
By the way, you may be interested in the mods [provided in good resources page](/doc/GOOD_RESOURCES.md#quality-of-life-mods), they are good mods that our visually impaired users have found and tested through practice.

After all downloads, let's put them into right location.
Here it is: `%appdata%\.minecraft\mods` (the `%appdata%` is shortcut for `C:\Users\username\AppData\Roaming`), you can directly paste it into File Explorer then press enter key to jump to it.
We'll put downloaded mod files inside it, both Fabric and NeoForge will load mods from this folder.
We've learned from user report that we need to start the launcher once after installing the mod framework for the `mods` folder to be created.
Haven't experimented but, I think it's ok to create it manually.

## Additional Installation for Windows

We've implemented the automatic set-up process inside the mod, it will set up the tolk for you at the first time you starting the modded game, there is no need to do it manually unless something goes wrong.

> Here is the original guide for manually installing tolk, I put it here in case you need to adjust the tolk's files manually:
>
> Given the specificity of this mod, we need to install a few extra things to make this mod work.
> For Windows, we need the [tolk](https://github.com/ndarilek/tolk) extension, which enable the mod to speak through multiple screen readers such as [NVDA](https://www.nvaccess.org/download/) and [JAWS](https://www.freedomscientific.com/products/software/jaws/).
> We need to download the tolk extension files and place it under the minecraft folder.
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

Select the profile created by the mod framework installer and start it, for Fabric, the profile name is `fabric-loader-<game-version>`, for NeoForge, the name is simply `neoforge` but the subtitle contains the game version. If everything is fine, you can start exploring the game.

Please note that you need to enable the narrator configuration from within minecraft after you enter the main screen of game (when you hear the music), there will be an accessible setting guide for this purpose, that's a game built-in function.
You can also press `Control + B` to enable the narrator, keep pressing to switch it to `Narrator Narrates All`.

If the game crashes and a bunch of error logs pop up, the biggest probability is that there is an incompatibility between mod files and mod framework, or between mod files and game version, please read the [Upgrade the Game and Mods](#upgrade-the-game-and-mods) section.
And [here is a complex self-helped FAQ](./FAQ.md#self-help-guide-for-abnormal-situation) for you to address the problem, from can't hear the narration to crash.

## Upgrade the Game and Mods

Upgrade the game is simple, for [Fabric](#install-the-fabric), change the preferring installing game version in Fabric installer screen, for [NeoForge](#install-the-neoforge), download another installer for the game version you want and install it.
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

Another situation that causes the game to crash or misbehave is conflict between mods, which is difficult to reflect in the error log because it is hard to predict.
For this type of error, there is a simple, common, cumbersome, no-better-choice method for all modded games: move all mod files into a temporal folder, then add them back one by one, try to start the game every time you move back a file.

## Other Pages

* [Home](/README.md)
* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)
* [Sound Effects](https://html-preview.github.io/?url=https://github.com/khanshoaib3/minecraft-access/blob/1.21/doc/SOUND.html)
* [FAQ](/doc/FAQ.md)
* [Good Resources](/doc/GOOD_RESOURCES.md)