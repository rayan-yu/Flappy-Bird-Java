=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: rayanyu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays - this is implemented in the selections of bird and background features. This format of storing the
  selections makes storage and retrieval the most efficient; the 2D capability allows us to store everything in one
  static data structure that integrates well with File I/O to be saved and loaded easily.

  2. Collections - I implemented ArrayLists to store the pipes currently on the screen. This use of ArrayLists and by
  extension, collections, works well because it allows me to dynamically remove pipes once they go off screen, iterate
  every pipe forward one index in the List, and randomly generate a new one to fill the index of the last one. I also used
  this to store parameters since the dynamic state of a List allows for more flexibility in case I don't have any parameters,
  i.e. when there is no loaded game.

  3. File I/O - I implemented File I/O by giving the option for the player to pause and save the game, which stores the
  score, high score, bird/background selections, bird/pipe positions. The player then can use this save as a sort of 
  that can be loaded when relaunching the game, or can be overrided by a new save. This feature appropriately allows
  players to save a game state in a manner external to a specific run of a program--i.e. it stores it in a .txt that
  is not wiped when the program ends.

  4. Inheritance - I implemented inheritance by creating the circle interface, which the Bird and Projectile classes
  extends. It is an appropriate implementation since the Bird and Projectile classes use the same core concepts, but they
  operate in a different way such that the Bird is controlled by the user, only moves up/down, and does not need to
  be concerned about whether something is inside it. Accordingly, the Projectile is randomized in movement, moves in 
  all directions, and has the function to test if it is touching anything.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  class FlappyBird: This is the core functionality class of the game. It contains the creation of creation, logic,
  state selection, etc. of all the components of the game.
  class Key: creates the Key that allows the game to detect user input on the keyboard
  class Listener: creates the listening action that triggers the initation of the game and allows key input
  to initiate logic
  class Pipe: basic pipe class that creates template for how a pipe should look
  class MovingPipe: moving pipe class extends original pipe to give it moving capability
  class Bird: extends Circle interface to create the template for what a bird does and how it acts, how it moves, etc.
  class Projectile: extends Circle interface to create the template for what a projectile does--i.e. random movements,
  if a bird is in it, etc.
	

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  The major pain point was figuring out how to have smooth animations with the pipes' movement and the bird.
  I had to figure out how to implement a timer and repaint the screen every single essentially to have
  the animations play out in a way I wanted.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  Functionality is seperated well in my game, it accomplishes everything I wanted it
  to logic-wise. Private state is encapsulated in all fields to my knowledge. If I 
  had the chance to refactor, I might want to try to figure out how to implement more 
  complex physics given the every frame repaint which made it difficult to have a changing
  speed at a rate determined with vector calculations.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.
  
  Images:
  https://www.nicepng.com/png/detail/151-1515315_flappy-bird-flappy-bird-graph-paper-drawing.png
  https://toppng.com/uploads/preview/flappy-bird-pixel-art-flappy-bird-1156289438531sspmvwnk.png
  https://www.seekpng.com/png/detail/151-1515323_flappy-bird-png-flappy-bird-sprite-png.png
  https://wallpaperaccess.com/full/4622710.png
  https://pixeljoint.com/files/icons/full/save3.png
  https://wallpaperaccess.com/full/4622688.png
