EcoWars
Authors: Timothy Li, Nir Reiter
Revision: 5/24/21

Introduction: 
EcoWars is a competitive 2-player game where each player tries to launch ecosystem-based attacks by introducing species to the other player’s environment. Each player wants to keep their side stable while causing the other side to collapse. The game is played from a top-down view 2d perspective.
Our game simulates an ecosystem so that each player tries to have their animals become the dominant species. For example, if a player places down a group of mice, the opposing player might place down cats to eat the mice (these animals are not actually the animals in the game). Each organism can reproduce and do other species-specific actions (eating, moving around, etc.).
The rules of the game are that each player can only influence the ecosystem indirectly, by adding species that affect the other species present. The player can move anywhere on the map, and click to spawn in new organisms of a certain species to influence the ecosystem. Each player has a limited amount of DNA which they can use to cause these changes, so they have to consider their actions carefully. Players start off with 300 DNA and can get more DNA by keeping more of their species alive. The goal of the game is to make your opponent lose most of its species while keeping most of your own species. The players play for 3 minutes, and after those 3 minutes end, the game ends, and a score is calculated based on how many and which animals (does not count plants) are still alive. Whoever has a higher score wins.
This multiplayer game would be on network.

Instructions:
Use arrow keys to move around.
Use the mouse to select an organism from the buttons at the bottom of the screen, and click to place it. This will only work if the player has enough balance to afford the animal.

Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:
[These are features that we agree you will definitely have by the project due date. A good final project would have all of these completed. At least 5 are required. Each feature should be fully described (at least a few full sentences for each)]
We will definitely have the general structure of the game down. EcoWars is an ecosystem simulation where species can interact with each other. For example, animals eat animals of other species and reproduce with animals of the same species. The goal of the game is to have as many animals alive as possible.
Another feature that we would like to have is a semi-real time simulation, where the player can move around in real-time but the ecosystem as a whole only really updates on a fixed time interval. The reason we would start with this is that it is easier to balance and develop an ecosystem simulation that is not constantly changing.
EcoWars has at least 3 species. These species will all interact with each other, by using each other as food or as a habitat (for a relationship between species such as trees and squirrels)
EcoWars is a multi-player competitive game, where each player tries to balance their ecosystem while destabilizing their opponent’s ecosystem. The game can be played in local multiplayer.
Players will be able to add new organisms of certain species, either to their side or to their opponent’s side, in order to influence the ecosystem. This is the only major way to influence the ecosystem. The players will have to indirectly influence the ecosystem in order to push it towards their objective.
We will have some sort of currency (we are thinking about calling it either DNA or molecules right now). This currency will be used to “buy” the animals to put on their side or their opponent’s side to influence the game (hopefully) in their favor. The player will receive DNA for each organism present on their side of the map.
There are very basic graphics and animations for each species. For example, each species has an animation for when they reproduce, each animal has an animation for eating, etc.

Want-to-have Features:
[These are features that you would like to have by the project due date, but you’re unsure whether you’ll hit all of them. A good final project would have perhaps half of these completed. At least 5 are required. Again, fully describe each.]
In the end, we would like to have a game with 5 total species. As mentioned above, these species will all interact with each other.
We would also like to have Network multiplayer so people can play together even from a distance in their homes. Each player would be able to use a different device.
Instead of just being able to buy species with one type of currency, we would like to have multiple types of currency like sunlight and water so combinations of resources are required to buy animals.
Temporary and permanent upgrades to abilities (one species can eat less food, make a species move faster, etc)
More of an actual real-time simulation, in which the different organisms of the ecosystem would be able to update their own personal timers and move around continuously.
We would like to have sound effects when the animal eats

Stretch Features:
[These are features that we agree a fully complete version of this program would have, but that you probably will not have time to implement. A good final project does not necessarily need to have any of these completed at all. At least 3 are required. Again, fully describe each.]
Specific animations for different possible interactions, for example a squirrel might climb a tree but burrow into a bush. Both are similar (entering a sleeping place), so it would be easier to have one generic animation, but a complete game would have animations that make more sense
In addition to the normal species on the map, species would be able to mutate into other animals with special abilities (possible positive or negative).
It would be nice to have 10 species. As above, they will all interact with each other, and will be bought by currency. More species would allow for more variety, but each species becomes harder to develop because each species has to interact with all or a large number of previous species.
Class List:
EcoWars
Main method
MenuScreen
Swing graphics which allow the user to select certain rooms to join or create new ones
DrawingSurface
Draw method
Holds players, obstacles, UI elements, organisms
Sound - represents the sound element of the game
RoomPost - represents a room in the database
PlayerPost - represents a player in the database
OrganismPost - represents an organism in the database
Sprite
Handles movement, imaging, etc.
Player
Players can move around, have a certain amount of each currency, etc.
Organism
Every species should share a parent class because they all have similar traits (need food/water, reproduce, etc) and so other classes can interact with every organism.
Animal - extends Organism
Every animal has an internal counter of how much food it has, which decreases based on its food needs. They have a maximum food storage, and if they aren’t full they will eat.
One class for each species (each extends organism or animal). All numbers are rough and need balancing.
Yellowberry Tree
Costs 100 DNA, worth 3 DNA per 10 seconds
Can’t move. Grows next to water. After 1 minute it “grows” and starts producing 1 berry every 10 seconds. Yellowberries are worth 2 units of food.
Reproduces every 2 minutes
Glowing moss
Costs 30 DNA, worth 1 DNA per 10 seconds
Can’t move. Glows at night. Worth 3 units of food.
Reproduces every 20 seconds
Flame bird
Costs 60 DNA, worth 2 DNA per 10 seconds
Move by flying. Need to rest on the ground every 10 seconds. It can eat the glowing moss and the yellowberries on the trees. Consumes 2 units of food every 10 seconds. Only eats berries until none left (or exactly 1 left), then eats moss.
Can sit on Fluffy Ram. Have a small chance to light the ram on fire. If the ram can’t get to the water in 10 seconds it dies.
Reproduces every 50 seconds
Mousehopper
Costs 50 DNA, worth 2 DNA per 10 seconds
Move by jumping. Eats yellowberries and needs 1 unit of food every 10 seconds. Worth 5 units.
Reproduces every 30 seconds
Fluffy Ram
Costs 90 DNA, worth 3 DNA per 10 seconds
Moves by walking, when it is hungry it looks for a mouse hopper and rams it, then eats it. Needs 5 units of food every 10 seconds.
Reproduces every minute

Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
Timothy Li
DrawingSurface - UI Elements
Organism class
Animal class
All the different species of organisms
Nir Reiter
Sprite class, Player class
DrawingSurface - Game field elements
Networking and database
MenuScreen
Libraries
Processing
G4P
Google Firebase, including Java libraries
Sound effects
www.zapsplat.com 
Mr. Shelby
Consulting and examples
