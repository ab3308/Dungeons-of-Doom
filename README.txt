WELCOME TO DUNGEONS OF DOOM!
-----------------------------------------------------------------------------------------------
PLAYERS, read this:

	-------------
	INSTALLATION:
	-------------

	Before playing, you must ensure all java classes are in the same folder. 
	Compile the necessary code by typing "javac *.java" in your command line.
	Find your map file's absolute path. This will be required to load your map.
	
	-------------------------
	REQUIRED MAP FILE FORMAT:
	-------------------------

	The map must be rectangular. It is okay to have abstract map shapes, 
	but fill in all surrounding areas with walls, "#", 
	in order to make it an accepted format.

	The map file should follow the format below:

	name <map name>
	win <gold to win>
	<map>

	An example (simple) map:
	#####################################
	####.....G..........E.......P.......#
	#####################################
	
	When accessing your file, you must input the absolute path into the game.	

	------------------
	STARTING THE GAME:
	------------------

	To start the game, type "java GameLogic". 
	You will then be prompted to enter your map file's absolute path. 
	This is required in order to start, otherwise no map will be read.
	Enjoy!
	
	------------
 	HOW TO PLAY:
	------------

	You will be playing against a bot who is constantly trying to find you.
	Your available commands are:

	- "HELLO": this tells you the gold required to beat a map

	- "GOLD": this tells you how much gold you are carrying

	- "MOVE <direction>" where <direction> is replaced by your choice of N, E, S, W. 
	  You will either see success/fail.

	- "PICKUP": if stood on a gold square, you will pickup the gold. 
	  If successful, you will see your gold count.

	- "LOOK": this command gives you a 5x5 glimpse into the map around you. 
	  There is an example below:
		##..E
		#..G.
		#.P..
		#...B
		#####

	- "QUIT": if on an exit tile this command will check you have sufficient gold to win. 
	  If not, you lose and the game ends.

	Each command takes up your turn. Then, the bot has a chance to catch you... so be quick! 

	---------------------
	INTERPRETING THE MAP:
	---------------------

	#	Wall
	P	Player position
	B	Bot position
	.	Empty tile
	E	Exit tile
	G	Gold

	----------------
	AIM OF THE GAME:
	----------------

	The aim of the game is to traverse around the map, picking up gold until you can win. 
	Then, you need to safely make it across to the exit tile before the enemy catches you!

GOOD LUCK, ENJOY!
-----------------------------------------------------------------------------------------------
DEVELOPERS, read this:
	------------------------|---------------------------------------------------------
		CLASSES:	|			Methods:
	------------------------|---------------------------------------------------------
	- GameLogic.java:	|- GameLogic():	generates new map instance
				|
			 	|- Boolean gameRunning(): returns state of game activity
				|
			 	|- String hello(): returns gold required to win on map
				|
			 	|- String gold(): returns gold owned
				|
			 	|- String playerMove() / botMove(): Takes user/bot move 
			 	|	and updates position if successful(valid),  
			 	|	returning "success". Otherwise returns "fail". 
				|
				|- String look(): returns 5x5 grid for player to show
				|	glimpse of map around them.
				|
				|- String pickup(): If player on gold when they want to
				|	to pickup, gold is picked up and "success"
				|	returned alongside the gold owned. Otherwise
				|	returns "fail".
				|
				|- quitGame(): if player has sufficient gold and is on
				|	exit tile, returns "win" and game ends.  
				|	Otherwise, returns "lose" and game ends.
				|
				|- getRandPlayerPos() / getRandBotPos(): generates random
				|	positions for player and bot until they are valid.
				|
				|- main(): runs game logic, utilising other methods
	----------------------------------------------------------------------------------
	- BotPlayer.java:	|- String decision(): takes player/bot positions and
				|	alternates between either "looks" or moves.
				|	When looking, it checks if player is within range.
				|	If so, the next move attempts to move the bot 
				|	closer to the player. Otherwise, the move is
				|	random. This process repeats. The method returns
				|	either a move or "looked".
				|
				|- String getBotMove(): takes differences in positions and
				|	player positions. Uses these to find optimal move
				|	in order to catch the player. It returns the move.
				|
				|- String randomMove(): returns a random move.
	----------------------------------------------------------------------------------
	- HumanPlayer.java:	|- String getInputFromConsole(): reads user input and
				|	returns it.
				|
				|- String getNextAction(): interprets result of
				|	getInputFromConsole() and returns the command for
				|	use in GameLogic.
	----------------------------------------------------------------------------------
	- Map.java:		|- Map(): takes in map file to read from
				|
				|- int getGoldRequired(): returns gold required integer
				|
				|- char[][] getMap(): returns map as 2D array
				|
				|- String getMapName(): returns map name
				|
				|- readMap(): takes map file input. Reads the map file to
				|	find the map name, gold required, map dimensions
				|	and the map itself. These are all stored as
				|	different variables.
-----------------------------------------------------------------------------------------------
