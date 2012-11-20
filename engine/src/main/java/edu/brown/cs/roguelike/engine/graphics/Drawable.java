package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.terminal.Terminal.Color;

import cs195n.Vec2i;

public interface Drawable {

    public char getCharacter();
    public Color getColor();

}
