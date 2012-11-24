package edu.brown.cs.roguelike.engine.graphics;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.screen.ScreenWriter;

import cs195n.Vec2i;

public class Section extends ScreenWriter {

	protected Screen screen;
	protected Vec2i upperLeft;
	protected Vec2i bottomRight = null;

	/**
	 * @param screen
	 * @param upperLeft
	 * @param bottomRight
	 */
	public Section(Screen screen, Vec2i upperLeft, Vec2i bottomRight) {
		super(screen);
		this.screen = screen;
		this.upperLeft = upperLeft;
		this.bottomRight = bottomRight;
	}

	/**
	 * @param screen
	 */
	public Section(Screen screen) {
		this(screen, new Vec2i(0, 0), new Vec2i(screen.getTerminalSize()
				.getColumns() - 1, screen.getTerminalSize().getRows() - 1));
	}

	public void drawString(int x, int y, String string,
			ScreenCharacterStyle... styles) {
		if (x > bottomRight.x || y > bottomRight.y)
			throw new IndexOutOfBoundsException(
					"Attempted to draw outside of Section's boundaries. Drawing to ("
							+ x + ", " + y + "), when bottom right corner is "
							+ bottomRight);

		super.drawString(x + upperLeft.x, y + upperLeft.y, string, styles);

	}

	/**
	 * @return the upperLeft
	 */
	public Vec2i getUpperLeft() {
		return upperLeft;
	}

	/**
	 * @return the bottomRight
	 */
	public Vec2i getBottomRight() {
		return bottomRight;
	}

	public Section moveUpperLeft(Vec2i ul) {
		assert ul.x < bottomRight.x;
		assert ul.y < bottomRight.y;
		return new Section(screen, ul, bottomRight);
	}

	public Section moveUpperRight(Vec2i rl) {
		assert rl.x > upperLeft.x;
		assert rl.y > upperLeft.y;
		return new Section(screen, rl, bottomRight);
	}
}
