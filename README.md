Roguelike Dungeons: _Fnar Edition_
===================

This is a mod for minecraft that adds randomly generated dungeon complexes. This version is a fork of [Greymerk's masterpeice](http://github.com/greymerk/minecraft-roguelike).

### Clone to desktop

There's documentation for how to clone to desktop here:

https://help.github.com/articles/set-up-git

Clone the repository using

`git clone https://github.com/greymerk/minecraft-roguelike`

### Gradle setup

Open a command window in the minecraft-roguelike directory

type `gradlew setupDecompWorkspace eclipse`
type `gradlew setupDevWorkspace eclipse`

### Run MineCraft with the Mod

type `gradlew runClient`

or 

type `gradlew runServer`

### Import project

1. In IntelliJ, select File > Open.
1. Select the `build.gradle` file and select OK.

### Run Configurations

1. In IntelliJ, select Run > Edit Configurations.
1. Create a new configuration of type `Gradle` 
1. Choose `minecraft-roguelike` for the project and enter `runClient` for the task.
1. Do the same for the server, create a new configuration of type `Gradle` with the same project and with `runServer` as the task.

### Build

Open a command window in minecraft-roguelike and type:

`gradlew build`

The mod jar file should be in build/libs

### External Links

[TUTORIAL] Getting Started with ForgeGradle
* http://www.minecraftforge.net/forum/index.php?topic=14048.0

Lex's Video regarding gradle
* https://www.youtube.com/watch?v=8VEdtQLuLO0