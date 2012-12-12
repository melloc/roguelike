package edu.brown.cs.roguelike.game;

import com.lexicalscope.jewel.cli.Option;

public interface Arguments {

	@Option(shortName="c",defaultValue="../config")
	String getConfig();

	@Option(shortName="t")
	boolean getTty();

	@Option(helpRequest = true)
	boolean getHelp();

}
