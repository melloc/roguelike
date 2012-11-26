package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.terminal.Terminal.Color;

public interface Drawable {

    public char getCharacter();
    public Color getColor();

}
