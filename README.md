# Minesweeper4J
Minesweepr for Java (using Swing via [Clear2D](http://github.com/kefik/Clear2D)) tailored for casual playing but especially for creating custom Minesweeper agents. Fully playable but truly meant for programmers for the development of Sokoban artificial players.

**LICENSED UNDER** [CC-BY-3.0](https://creativecommons.org/licenses/by/3.0/legalcode) Please retain URL to the [Minesweeper4J](https://github.com/kefik/Minesweeper4J) in your work.

![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-1.png)
![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-2.png)

## FEATURES

1) HumanAgent and ArtificialAgent stubs; ArtificialAgent is using own thread for thinking (does not stuck GUI).

2) Possible to run headless simulations (same result as visualized, only faster).

3) Use "Minesweeper" static methods for quick startups of your code (both for humans and artificial agents).

4) Mavenized (repo and dependency at the end of the page); uses some of my other stuff, but that can be easily cut off if you're considering branching.

5) Tested with Java 1.8, compilable with 1.6 as well; jars in Maven are compiled using Java 1.8.

6) Using pure Swing via [Clear2D](http://github.com/kefik/Clear2D), no other 3rd-party libs = no complexities...

------------------------------------------------------------

## PROJECT STRUCTURE

**Minesweeper4J** -> main project containing the simulator and visualizer of the game

------------------------------------------------------------

![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-3.png)
![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-4.png)

------------------------------------------------------------

## COMPILATION

Compile Minesweeper4J project (from within Minesweeper4J directory), requires [Maven](https://maven.apache.org/):

Windows (from cmd; assuming you have mvn.bat on path):

    mvn package
    
Linux (from bash, assuming you have mvn on path):

    mvn package

## MAVEN [REPOSITORY](http://diana.ms.mff.cuni.cz:8081/artifactory)

    <repository>
        <id>amis-artifactory</id>
        <name>AMIS Artifactory</name>
        <url>http://diana.ms.mff.cuni.cz:8081/artifactory/repo</url>
    </repository>
    
## MAVEN DEPENDENCY

    <dependency>
        <groupId>cz.sokoban4j</groupId>
        <artifactId>sokoban4j</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>
    
    <dependency>
        <groupId>cz.sokoban4j</groupId>
	    <artifactId>sokoban4j-tournament</artifactId>
	    <version>0.1.0-SNAPSHOT</version>
    </dependency>

------------------------------------------------------------

## CREDITS

Using [tileset](https://opengameart.org/content/minesweeper-tile-set) done by Eugene Loza, thank you!