# Minesweeper4J
Minesweepr for Java (using Swing) tailored for casual playing but especially for creating custom Minesweeper agents. Fully playable but truly meant for programmers for the development of Minesweeper artificial players.

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

------------------------------------------------------------

![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-3.png)
![alt tag](https://github.com/kefik/Minesweeper4J/raw/master/Minesweeper4J/Minesweeper-4.png)

------------------------------------------------------------

## CREDITS

Using [tileset](https://opengameart.org/content/minesweeper-tile-set) done by [Eugene Loza](https://opengameart.org/users/eugeneloza), thank you!
