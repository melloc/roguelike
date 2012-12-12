package edu.brown.cs.roguelike.game;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.HelpRequestedException;

public class Roguelike {

	public static void main(String[] args) {
		try {
		Arguments appArgs = CliFactory.parseArguments(Arguments.class, args);
		GUIApp app = new GUIApp(appArgs);
		app.startup();
		} catch (HelpRequestedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
}
