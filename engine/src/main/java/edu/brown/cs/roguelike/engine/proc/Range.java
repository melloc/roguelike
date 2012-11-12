package edu.brown.cs.roguelike.engine.proc;

public class Range {
	final int max;
	final int min;
	
	public Range(int a, int b) {
		min = Math.min(a,b);
		max = Math.max(a,b);
	}
}
