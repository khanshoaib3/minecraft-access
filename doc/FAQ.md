# Frequently Asked Questions

## Table Of Contents

1. [Is the mod enough to play the game normally?](#is-the-mod-enough-to-play-the-game-normally)
2. [Can this mod be used to play multiplayer servers?](#can-this-mod-be-used-to-play-multiplayer-servers)
3. [Why nobody answers my questions in Discord?](#why-nobody-answers-my-questions-in-discord)
4. [Why this mod is updated slowly / the feature I asked was not released in the new version?](#why-this-mod-is-updated-slowly--the-feature-i-asked-was-not-released-in-the-new-version)
5. [Has this mod been uploaded to mod platforms?](#has-this-mod-been-uploaded-to-mod-platforms)
6. [Is this mod supports XX language?](#is-this-mod-supports-xx-language)
7. [How can I contribute to I18N?](#how-can-i-contribute-to-i18n)
8. [Self help guide for abnormal situation](#self-help-guide-for-abnormal-situation)

## Is the mod enough to play the game normally?

Overall, the mod is enough for basic playing, but that doesn't mean you can play it as sighted player, for now at least.

We can claim that the current functionality of the mod is sufficient for normal play, given that (even totally blind) users have already played with the help of this mod. Some of them are streaming, so you can listen to the uploaded videos: [Logic Pro X Gaming](https://www.youtube.com/@LogicProXGaming/search?query=minecraft), [TrueBlindGaming](https://www.youtube.com/@TrueBlindGaming/search?query=minecraft).

The mod primarily borrows the help of a screen reader to describe the game interface, and incorporates sound cues to provide orientation perception in this 3D world.
Currently, the Mod has implemented such functions as `Read Crosshair` (read out blocks and creatures you're pointing at), 100% accessible of all screens, `Camera Controls` and `Mouse Simulation` with keyboard, `Points of Interest` (scan nearby special blocks and creatures and lock on them), and a new F4 `Narrator Manu` which contains assistant functions like "Read Light Level" "Find Closest Water Source".

You can play the game within keyboard's main typing part (which means a number pad isn't necessary), without a mouse. You can walk around the world, gather materials, farm the land, raise animals, mine, fight monsters, and even build buildings and redstone structures.

The "No" part of the answer contains two perspectives.

First, this mod does not achieve 100% accessibility.
For example, you'll find rowing the boat is difficult (since the relevant feature haven't been implemented yet), you can't directly recognize blocks that are placed together as a structure (since you can't see the entire appearance of a group of blocks, and there's very little this mod can do about that).
We're gradually adding new features, but I'm pessimistic that the Minecraft is too complex to ever achieve 100% accessibility.

Second, the Minecraft, despite the fact that it has a large number of players, is not easy to get started with. You'll need to learn many new concepts and knowledge to play it. You can learn how to play this game by listening the playing video (I recommend [Pixlriffs's Minecraft Survival Guide](https://www.youtube.com/watch?v=VfpHTJsn9I4&list=PLgENJ0iY3XBjmydGuzYTtDwfxuR6lN8KC)), reading [the wiki](https://minecraft.wiki), or read [text tutorials on wiki](https://minecraft.wiki/w/Tutorials), or find someone skilled enough to teach you.

So if you haven't bought the game yet and don't know much about Minecraft, think again before paying.

## Can this mod be used to play multiplayer servers?

Yeah! This mod is a pure client-side mod, which means you can play either single player game or multiplayer game with this mod without additional operations.
However, this also limits what the mod can do, and most of the possible changes that are powerful and convenient for visually impaired players often involve modifying server-side logic.
We stick to the pure client-side strategy for now, since you need to install a server-side mod on the server to make it work, and you can't achieve this if the server isn't belong to you.

## Why nobody answers my questions in Discord?

Sorry for the inconvenience. Here are some self-help materials.
If you have any question about installing, please read the [set-up guide](/doc/SET_UP.md). 
If you have any question about original game functions, please search on the [wiki](https://minecraft.wiki/w/Special:Search?scope=internal) or just google it.
If you want to learn to play the game, there are [some detailed text tutorials on wiki](https://minecraft.wiki/w/Tutorials), you can also listen [Pixlriffs's Minecraft Survival Guide](https://www.youtube.com/watch?v=VfpHTJsn9I4&list=PLgENJ0iY3XBjmydGuzYTtDwfxuR6lN8KC) on YouTube, or just google the question and search answers on forums.
If you have any question about this mod, please read the [manual](/README.md).

## Why this mod is updated slowly / the feature I asked was not released in the new version?

Uh, maintaining or updating this mod is a volunteer effort.
There are currently only two active developers.
Any type of contribution is welcome, please go to the [issues page](https://github.com/khanshoaib3/minecraft-access/issues) for more details.
If you know developers who might be willing to help contribute to this mod, please send them information about this mod and encourage them.

## Has this mod been uploaded to mod platforms?

Yes, it has been uploaded to Modrinth as [Minecraft Access](https://modrinth.com/mod/minecraft-access) and CurseForge as [Blind Accessibility](https://www.curseforge.com/minecraft/mc-mods/blind-accessibility).

## Is this mod supports XX language?

See [I18N Fallback Mechanism Feature](/doc/FEATURES.md#i18n-fallback-mechanism).

## How can I contribute to I18N?

We manage the translation of this mod in [another GitHub repository](https://github.com/khanshoaib3/minecraft-access-i18n#minecraft-access-i18n), take a look.

## Self help guide for abnormal situation

Chose the section that fits your progress:

### Haven't run the game yet

I assume you have taken a look at the [setup guide](/doc/SET_UP.md).

If you are walking on a path that the setup guide isn't mentioned (for example, you're not purchasing the game from the official site), developers can't help you since they just can't walk on the same path.
But you can still ask users in [Discord server](https://discord.gg/yQjjsDqWQX) (mc-help channel) for help with enough description, maybe there are users that solved that problem before.
Then we can update this path into the setup guide so everyone in the Future can benefit from it.

If you experience is not same as the setup guide describes you should expect when you're completely following the guide, it means there is something wrong or misleading in the setup guide.
Describe which step you currently are, what you find out is not same as the guide says.
I believe through talking we can find out what's wrong together and fix it.

### The game crushed when I start it from launcher

Great! Believe it or not, it's easier to be solved than "running but abnormal", game crushed because it KNEW something is wrong, better than  knowing nothing.
If the crush pop up dialog says something about "mod incompatibility", please refer [this section](/doc/SET_UP_ON_WINDOWS.md#upgrade-the-game-and-mods) in the setup guide.

If you are using Fabric, there would be crush report files under the `crash-reports` folder in your `.minecraft` folder, developers need the latest `crash-...` file.
If you are using Forge, there would be log files under the `logs` folder in your `.minecraft` folder, developers need the `debug.log` file.
Please upload it to wherever that developers can view, for example you can upload it to Discord channels while asking for help.

### The game running but acts abnormal

#### Can't hear the game narration

Sorry but we don't have enough knowledge to solve this problem on Linux, we haven't got this type of report.
If you're in Windows, please check:

1. There is no missing ["tolk" files](/doc/SET_UP_ON_WINDOWS.md#additional-installation-for-windows) under the game folder.
2. You're using one of tolk [supported screen readers](https://github.com/ndarilek/tolk?tab=readme-ov-file#supported-screen-readers), please note that Windows built-in Narrator is not supported by tolk.
3. You have set the ["Narrator"](https://minecraft.wiki/w/Options#Chat_Settings) game setting (under "Options", "Chat Settings") to "All" choice.

 Now open the launcher and start the game, when you're at the main screen (in which has "singleplayer game", "multiplayer game"...), close your screen reader and open built-in [Windows Narrator](https://support.microsoft.com/en-us/windows/complete-guide-to-narrator-e4397a0d-ef4f-b386-d8ae-c172f109bdb1) feature. Can you hear the menu selecting narration through Windows Narrator? If so the problem is in your screen reader, maybe the Narrator setting is wrong (for example, you need to close NVDA's keyboard narration setting since it will interrupt the narration of this mod), or there is a compatibility problem between it and "tolk".

#### This mod doesn't work like expected

I assume you have searched on [wiki](https://minecraft.wiki) or Internet to make sure you've understood the original game part of your question.
Please provide these information to developers in "mc-issues" channel:

* Your operation
* What you expected or what the mod manual premises
* What feedback the game and mod gave you
* In case the developers asking for log files: there would be log files under the `logs` folder in your `.minecraft` folder, developers need the `debug.log` file, please upload it to wherever that developers can view.

It might be a bug of this mod, or something in mod manual is misleading, or you misunderstand how the game works like.

## Other Pages

* [Home](/README.md)
* [Set Up](/doc/SET_UP.md)
* [Features](/doc/FEATURES.md)
* [Keybindings](/doc/KEYBINDINGS.md)
* [Configuration](/doc/CONFIG.md)