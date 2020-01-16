package cz.minesweeper4j.playground;

class RunMyAgent {
	public static void main(String[] args) {
		IAgent agent = new MyAgent(); 
		
		int masterRandomSeed = 10;
		
		MinesweeperResult result = Minesweeper.playAgent("MyAgent", 15, 15, 20, 60 * 1000, masterRandomSeed, true, agent);
		System.out.println("---// FINISHED //---");
		System.out.println(result);
	}
}
