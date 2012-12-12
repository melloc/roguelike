cs195n

Roguelike final project

Week 4

cody, jte, lelberty

=== Current bugs: ===

- Terminal close isn't supported from eclipse. You need to either press 'Q' to quit or
  use eclipse to stop the process

=== Package structure: === 

All below are prefixed with edu.brown.cs.roguelike

==== Engine ====

This is code for the engine, which should be code more general to roguelikes:

* engine.config - configuration file loading/object marshalling
* engine.entities - entity (player/monster/npc etc) logic
* engine.entities.events - events and actions that entities can observe and take, respectively
* engine.events - high-level action/event abstraction (keystrokes, etc)
* engine.game - core game logic/turn management
* engine.graphics - graphics (Lanterna) classes
* engine.level - all classes that collectively make up a Level
* engine.proc - procedural content generation
* engine.save - all save-game logic
* engine.fsm -  Finite state machine
* engine.fsm.monster - FSM's for monsters
* engine.pathfinding - A* pathfinding logic

Testing:

* engine.config.test - round-trip marshalling tests
* engine.graphics.test - a simple test implementation of Application and Layer
* engine.level.test - unit tests for level classes - primarily serialization
* engine.save.test - unit tests for save-game logic classes

==== Game ====

This is code related to the actual game:

* game - GUIApp is the in-progress implementation of Application, and Roguelike is our actual game

==== Support ====

Then we also have some support classes, all located in (with no prefix):

* cs195n - TA support code we use

Engine requirements
========================================
| Engine supports transitions between two levels  | GoToLevel provides means for transporting player to other level
| Engine has some concept of equipment            | Stackables

Game requirements
========================================
| Player can equip items from inventory           | Weapons can be picked up and wielded.
| Player can move between levels                  | Stairs provide means to access other levels
| No serious bugs exist in gameplay               | Not that we know of.

Approximate number of hours:

40 hours collectively

