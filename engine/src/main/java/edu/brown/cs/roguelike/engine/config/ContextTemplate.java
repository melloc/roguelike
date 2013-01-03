package edu.brown.cs.roguelike.engine.config;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Template for a specific context for keyboard input.
 *
 * This class is meant to serve as a map from a key to an alias.
 *
 * This class is part of the path towards creating the appropriate 
 * {@link GameAction}s to be used throughout the application.
 *
 * @author cody
 */
public class ContextTemplate {

    public final String context;
    public final Map<String,String> bindings;

    @JsonCreator
    public ContextTemplate(@JsonProperty("context") String context,
            @JsonProperty("bindings") Map<String,String> bindings) {
        this.context = context;
        this.bindings = bindings;
    }

}
