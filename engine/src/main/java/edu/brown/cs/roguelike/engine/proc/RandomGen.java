package edu.brown.cs.roguelike.engine.proc;

import java.util.Random;

public class RandomGen {
	private Random random;

	public RandomGen(long nanoTime) {
		random = new Random(System.nanoTime());
	}

	//Returns an int between [0,n)
	public int getRandom(int n){
		if(n == 0)
			return 0;
		else 
			return random.nextInt(n);
	}

	//Returns a number between min and max, inclusive
	public int getRandom(int min, int max){
		return min+getRandom(max-min+1);
	}
}
