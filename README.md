# Minesweeper4J
Minesweepr for Java (using Swing via [Clear2D](http://github.com/kefik/Clear2D)) tailored for casual playing but especially for creating custom Minesweeper agents. Fully playable but truly meant for programmers for the development of Minesweeper artificial players.

**LICENSED UNDER** [CC-BY-3.0](https://creativecommons.org/licenses/by/3.0/) Please retain URL to the [Minesweeper4J](https://github.com/kefik/Minesweeper4J) in your work.

![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-1.png)
![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-2.png)

## FEATURES

1) HumanAgent and [ArtificialAgent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J/src/main/java/cz/minesweeper4j/agents/ArtificialAgent.java) stubs; ArtificialAgent is using own thread for thinking (does not stuck GUI).

2) "Advice" action that provides an agent with "next safe position" advice; agent performance can thus be random-free, just compare how many advices they required.

    * Press "S" when playing as a human player to obtain "next advice".

3) You can press "SPACE" to show the whole board even when the artificial agent is playing.

4) It is possible to run headless simulations (same result as visualized, only faster).

5) Use [Minesweeper](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J/src/main/java/cz/minesweeper4j/Minesweeper.java) static methods for quick startups of your code (both for humans and artificial agents).

    * If you just want to play a quick game as a human, run [Main](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J/src/main/java/cz/minesweeper4j/Main.java) class.
    * If you want to see some agent in action, run either [AdviceAgent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/AdviceAgent.java) or [RandomAgent1](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/RandomAgent1.java).

6) Stubs for SAT-based agents (using [SAT4J](http://www.sat4j.org/)) and Prolog-based agents (using [tuProlog](https://bitbucket.org/tuprologteam/tuprolog)).

7) Mavenized (repo and dependency at the end of the page); uses some of my other stuff, but that can be easily cut off if you're considering branching.

8) Tested with Java 1.8, compilable with 1.6 as well; jars in Maven are compiled using Java 1.8.

9) Using pure Swing via [Clear2D](http://github.com/kefik/Clear2D) for visualization, no other 3rd-party libs = no complexities, just download & run.

------------------------------------------------------------

## PROJECT STRUCTURE

**Minesweeper4J** -> main project containing the simulator and visualizer of the game

**Minesweeper4J-Agents** -> example [AdviceAgent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/AdviceAgent.java), [RandomAgent1](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/RandomAgent1.java), [RandomAgent2](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/RandomAgent2.java) + [SAT4J agent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/SATAgentBase.java) stub and [Prolog agent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Agents/src/main/java/cz/minesweeper4j/agents/PrologAgentBase.java) stub

**Minesweeper4J-Playground** -> project with custom [SATAgent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Playground/src/main/java/cz/minesweeper4j/playground/SATAgent.java) and [PrologAgent](https://github.com/kefik/Minesweeper4J/blob/master/Minesweeper4J-Playground/src/main/java/cz/minesweeper4j/playground/PrologAgent.java) stubs; start experimenting with SAT-based and/or Prolog-based agent for Minesweeper from here

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
        <groupId>cz.minesweeper4j</groupId>
        <artifactId>minesweeper4j</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>

------------------------------------------------------------

## CREDITS

Using [tileset](https://opengameart.org/content/minesweeper-tile-set) done by [Eugene Loza](https://opengameart.org/users/eugeneloza), thank you!