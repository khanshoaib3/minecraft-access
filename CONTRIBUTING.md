# Contributing to the minecraft-access

Thank you for considering making this mod better.

## Required knowledge

First, general Java project developing knowledge is required: Java programming, concepts of writing maintainable elegant code, git operations, basic knowledge of Linux shell commands, collaboration with GitHub...
If you are not familiar with how to clone a project and submit a Pull Request using GitHub, please read [the GitHub official documentation](https://docs.github.com/en/get-started/quickstart/contributing-to-projects).

Then there are some knowledge for Minecraft modding you'd like to learn before coding.
You'd better learning by doing, first git clone the project and set up the local environment, then read project files, start as debugging mode and set breakpoints to understand the order of execution.

This project uses [`Gradle`](https://docs.gradle.org/current/userguide/getting_started_eng.html) as dependency manager and `Groovy` DSL for writing Gradle scripts, thus you need at least know the basics of Gradle, like how to start a gradle task, how to add new dependencies, how to build the project... [here is a tutorial from Gradle official](https://docs.gradle.org/current/userguide/getting_started_eng.html#getting_started).
Honestly, I'm not familiar with it and always facing trouble when gradle part needs to be changed, but things will eventually work after searching and fiddling.

This project supports `Fabric` and `NeoForge` (stop supporting `Forge` after 1.21) with the help of [Architectury Loom](https://docs.architectury.dev/loom/introduction), a framework for multi-platform modding, derivatives from [Fabric Loom](https://fabricmc.net/wiki/documentation:fabric_loom).
The Architectury Loom participates as gradle plugin and mod configurations, it won't affect the code.
For questions about architectury, you'd better asking in [their Discord server](https://discord.architectury.dev/) instead of searching for documentation, but it doesn't harm that do searching before asking.
Since Architectury Loom is derivatives from Fabric Loom, the Fabric Loom's documentation also applies to Architectury Loom in most cases, which is more detail than the Architectury Loom's.

Then the core framework we heavily depend on - [SpongePowered Mixin](https://github.com/SpongePowered/Mixin/wiki), "Mixin" in short, a framework for modifying Java bytecode.
For convenience of reusing same set of code for all platforms, this mod isn't depending on Forge API or Fabric API, instead we directly modify original game code, injecting our logic into it, same principle as computer virus and cheating tools.
Well, to be precise, 90% of the code is platform-independent, and in fact the mod uses part of the Fabric API in a jar-in-jar way.
Mixin is a complex but handy framework, for learning it I recommend reading [tutorial by Fabric](https://fabricmc.net/wiki/tutorial:mixin_introduction) and [the official wiki](https://github.com/SpongePowered/Mixin/wiki).
Please note that the official wiki is very technical and detailed and is not suitable for using as a user manual, use it as an API reference.
For questions about Mixin, you can ask in `mod-dev` channels of [Fabric Discord server](https://discord.com/invite/v6v4pMv) or the `mixin` channel of [NeoForge Discord server](https://discord.com/invite/neoforged), they are a friendly bunch and willing to answer any questions you may have.

Finally, for searching for a suitable injection point, we need to [read the original code](https://fabricmc.net/wiki/tutorial:reading_mc_code).
Thanks to the [Yarn](https://fabricmc.net/wiki/documentation:yarn) tool we can read the source code that de-obfuscated from the game's bytecode.
You don't need to manually operate Yarn, Loom will do it for you while running related gradle tasks.
And you'd like to know some terms of Minecraft project structure like ["client side", "server side"](https://fabricmc.net/wiki/tutorial:side), by the way this mod is pure client side mod for now.

## Setting up Local Environment

Java 21 and Gradle is required to be pre-installed for this project.
IntelliJ IDEA is recommended since it has some convenient Minecraft modding plugins: [Architectury](https://plugins.jetbrains.com/plugin/16210-architectury), [Minecraft Development](https://plugins.jetbrains.com/plugin/8327-minecraft-development).
Can't find corresponding vscode extensions, but since Gradle is platform-independent, you can use any IDE as you like.

Now clone the project and run `gradlew build`, to let Gradle automatically detects gradle version and downloads dependencies, it takes minutes to set up at first time.
After finishing first build, you'd like to run `gradlew genSources` to generate the de-obfuscated source code, set generated source jar as compiled jar's source according to the "Generating Minecraft Sources" section of the [tutorial](https://fabricmc.net/wiki/tutorial:setup).

Now you can explore the project.

For IntelliJ, the Loom will generate some [Run Configurations](https://www.jetbrains.com/help/idea/run-debug-configuration.html) like `Minecraft Client (:fabric)`, `Minecraft Server (:fabric)`,  `Minecraft Client (:neoforge)`, `Minecraft Server (:neoforge)`, you can tell the purpose of each configuration by their name, for example, `Minecraft Client (:fabric)` is for starting the client side game on Fabric.

PS: If the Run Configurations are broken accidentally, delete `.idea/runConfigurations` then refresh the gradle, the Loom will re-generate them.

Minecraft is essentially a Java program in form of executable jar files (one jar for client and one jar for server), so you can modify the run configuration of jar file as you need. For example, `-Dmixin.debug.export=true` is for exporting "mixined" bytecode (`.class` files) when [debugging mixin](https://fabricmc.net/wiki/tutorial:mixin_export), `--username=<minecraft username> --password=<minecraft password>` is for providing user information to client side game when developing.

## Project Structure

### File Structure

* `.github`: GitHub related stuff like workflow (CI) files.
* `gradle`: Mainly contains Gradle wrapper configuration, Gradle wrapper's version is passively upgraded as Loom's version is upgraded.
* `doc`: Project documentations.
* `.gitmodules`: The I18N part ([`assets/<mod name>/lang`](https://fabricmc.net/wiki/tutorial:lang)) of this mod is separated as an independent repository - [minecraft-access-i18n](https://github.com/khanshoaib3/minecraft-access-i18n), and being used by this main repository in form of git submodule.
* `build.gradle`: Root Gradle build script of this Gradle managing project. ([Gradle doc about `build.gradle` file](https://docs.gradle.org/current/userguide/build_file_basics.html))
* `gradle.properties`: Configuration about mod and dependencies versioning.
* `settings.gradle`: Defines that this project has three subprojects: `common`, `fabric`, `neoforge`. ([Gradle doc about `settings.gradle` file](https://docs.gradle.org/current/userguide/settings_file_basics.html))
* `common`: Gradle subproject, Platform-independent code and configurations, normal Java project structure.
    * `build.gradle`: Gradle build script of `common` subproject, it has developing dependencies like `lombok`, `log4j`, `junit`.
    * `src/main/java`: Source code.
    * `src/main/resources`
        * `log4j.xml`: Log configuration.
        * `architectury.common.json`: Configuration.
        * `minecraft_access.accesswidener`: Yet another configuration.
        * `minecraft_access-common.mixins.json`: Mixin configuration that tells the Mixin framework where to find Mixin classes under `common` directory.
        * `assets/minecraft_access`: Contains custom resource files of this mod, structured in the [Minecraft Resource Pack](https://minecraft.wiki/w/Resource_pack) format.
            * `lang`: The I18N text used by this mod for narration purpose, managed by another git repository.
    * `src/test`: Unit test suite.
* `fabric`: Gradle subproject, Fabric-dependent mod code.
    * `build.gradle`: Gradle build script of Fabric version mod.
    * `src/main/java`: Source code.
    * `src/main/resources`:
        * `minecraft_access.mixins.json`: Indicates Mixin classes under `fabric` directory.
        * `fabric.mod.json`: [Fabric mod configuration](https://fabricmc.net/wiki/documentation:fabric_mod_json). You can see we refer both Mixin config files under `common` and `fabric` subprojects in this file.
* `neoforge`: Gradle subproject, NeoForge-dependent mod code.
    * `build.gradle`: Gradle build script of NeoForge version mod.
    * `src/main/java`: Source code.
    * `src/main/resources`:
        * `minecraft_access.mixins.json`: Indicates Mixin classes under `neoforge` directory.
        * `META-INF/neoforge.mods.toml`: [NeoForge mod configuration](https://docs.neoforged.net/docs/gettingstarted/modfiles/#neoforgemodstoml).

### Program Structure

The root package class path of this project is `com.github.khanshoaib3.minecraft_access`, below I'll use relative path to describe code.

There are two types of logic in this project, classified by the execution entry point.

One is triggered by original game logic invoking.
With the help of Mixin framework, we inject logics into original code, so when original code is called, the injected logic will be executed too.
This execution type is suitable for making existing game content accessible, like speak held item when switching items in hotbar.

The other type of logic is constantly triggered by the [tick](https://minecraft.fandom.com/wiki/Tick) mechanism.
This is achieved by adding custom function invoking logic into original game's tick processing method in platform-dependent code, which is called every tick.
Different platforms implement different ways to achieve this.
The `MainClass.clientTickEventsMethod()` is invoked when platform tick event is fired, then it calls every execution function inside this method, each feature will check if its condition is met, if so, it will execute their particular logic, or it will directly return as skipping this tick.
This execution type is suitable for implementing new features that not exist in the original game, like `Camera Controls` that rotating the player view with keyboard.

Logics about mod-specific configuration are under `config` subpackage.
The mod configurations are stored in `{main directory}/config/minecraft_access/config.json` file.
We also implemented a custom configuration screen, so users can change config on fly without restart the game.
But this feature also requires every config-requiring feature to check their condition before every execution.

The `mixin` subpackage contains Mixin related things like `FooMixin` and `BarAccessor`.
`...Mixin` classes are responsible for injecting logic into corresponding original game classes.
`...Accessor` interfaces are used for accessing fields and methods of original game classes, they are referred in `...Mixin` classes and custom features.

The `features` subpackage contains the implementation of custom features, if one mixin-based feature has complex logic, we'll also extract the non-mixin part as an independent class in `features` subpackage.

The `screen_reader` subpackage contains the implementation of screen reader proxy.
Following the Interface segregation principle, we invoke this proxy in everywhere where needs to speak some text through screen reader, then this proxy is responsible for passing text to real screen reader (another layer of interface from SAPI, in fact).

The `utils` subpackage, like regular Java project, is home for common utility codes (or you can say they are codes that nowhere to go).
We'd keep functions in `utils` as static methods (["pure function"](https://en.wikipedia.org/wiki/Pure_function) in functional programming) so we can easily reuse them.

This project has very few unit tests, hopefully we will be able to add more tests in the future.
Applying less mock and embracing programming strategies like ["Functional Core, Imperative Shell"](https://news.ycombinator.com/item?id=34860164), ["Red - Green - Refactor"](https://martinfowler.com/bliki/TestDrivenDevelopment.html) and more described in [this article](https://www.amazingcto.com/mocking-is-an-antipattern-how-to-test-without-mocking/).

## CI

Since this project is hosted on GitHub, it's natural for us to choose GitHub Action as the CI system.
This project has automatically test-build-release pipeline.

* For every PR and building, [`test` workflow](.github/workflows/test.yml) will be triggered for running the test suite against current code.
* The [`fast-forward` workflow](.github/workflows/fast_forward.yml) is used for performing fast-forward merging (to avoid generating merge commits) and collect changelog in PR description, it will be triggered by `/fast-forward` comment in PR.
* The [`build` workflow](.github/workflows/build.yml) is responsible for building all platforms mod jars, you can manually dispatch it in GitHub Action Page.
* When a new version is ready and merged, we'll manually run the [`release` workflow](.github/workflows/release.yml) to create a new pre-release in GitHub Release, after one week exposure, we'll run this workflow against this version again to change the GitHub release to formal state, and publish mod to CurseForge and Modrinth.

## Developing Tips

* You'd like to enable this mod's `Other Configurations - Debug Mode` configuration to let the mod print debug level logs.
* When running the game with IDE, the main directory is `{platform directory}/run` (`.minecraft` in production environment), you can see familiar directories like `config`, `mods`, `saves` under it.
* You can run `gradlew {platform}:build` to build mod for specific platform (when root Gradle `build` task fails for some reason), built mod jars are under `{platform directory}/build/libs`, the one without suffix.

## Recommended Approaches

### Tell Us What You Intend To Do Before Doing

When you decide to make contributions, we expect you to comment in the corresponding issue or new issue, or talking in `mc-dev` channel of our [Discord server](https://discord.gg/yQjjsDqWQX).
Industry experience tells us that shift-left checks and discussions is good for software development, for example we can provide more relevant knowledge, project details and advice to help you do things better, it only can be done if we know what you want to do.

Of course there is no mandatory requirement (praise [the freewheeling open source community](http://www.catb.org/~esr/writings/cathedral-bazaar/)!), the code for this project is open source under opensource license, thus you do not need to get anyone's permission to make changes to (your fork of) this project, you just need to make it work **for your personal needs**.
If you feel that your changes will also benefit the upstream, you can submit a Pull Request to the upstream, and only then does the "contributing" begin.