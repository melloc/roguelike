package edu.brown.cs.roguelike.engine.graphics.test;

import org.junit.Test;

import cs195n.Vec2i;
import edu.brown.cs.roguelike.engine.graphics.Application;


public class TestApplication extends Application {

    public TestApplication() {
        super("test");
    }

	@Override
	protected boolean initialize(Vec2i screenSize) {
		this.layers.add(new TestLayer());
		return true;
	}
	
    //@BeforeClass
    public static void main() {
        main(new String[]{});
    }

	public static void main(String[] args) {
		Application app = new TestApplication();
		app.startup();
	}

    @Test
    public void fakeTest() {
        // This exists so that we can create an interactive test.
    }

	@Override
	public Vec2i getSize() {
		return new Vec2i(80,30);
	}

}
