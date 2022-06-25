import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/* Main game logic */
public class GameLogic {

    /* Initializing variables */
    private Map map;
    protected int playerPosX;
    protected int playerPosY;
    protected int botPosX;
    protected int botPosY;
    private int goldEarned;

    /* Default constructor */
    public GameLogic(String mapName) {
        map = new Map(mapName);
    }

    /* Returns if game running */
    protected boolean gameRunning() {
        return true;
    }

    /* Returns gold required to win */
    protected String hello() {
        return "Gold to win: " + map.getGoldRequired();
    }

    /* Returns gold owned */
    protected String gold() {
        return "Gold owned: " + goldEarned;
    }

    /* Checks if movement is legal and updates player's location on the map. */
    protected String playerMove(char direction, char[][] loadedMap) {
        if(direction=='n'){
            if(loadedMap[playerPosX][playerPosY-1] != '#') {
                playerPosY--;
                return "Success";
            }else {
                return "Fail";
            }
        }else if(direction=='e'){
            if(loadedMap[playerPosX+1][playerPosY] != '#') {
                playerPosX++;
                return "Success";
            }else {
                return "Fail";
            }
        }else if(direction=='s'){
            if(loadedMap[playerPosX][playerPosY+1] != '#') {
                playerPosY++;
                return "Success";
            }else {
                return "Fail";
            }
        }else if(direction=='w'){
            if(loadedMap[playerPosX-1][playerPosY] != '#') {
                playerPosX--;
                return "Success";
            }else {
                return "Fail";
            }
        }else{
            return "Invalid";
        }
    }

    /* Checks if movement is legal and updates bots location on the map. */
    protected String botMove(char direction, char[][] loadedMap) {
        try {
            if (direction == 'n') {
                if (loadedMap[botPosX][botPosY - 1] != '#') {
                    botPosY--;
                    return "Success";
                } else {
                    return "Fail";
                }
            } else if (direction == 'e') {
                if (loadedMap[botPosX + 1][botPosY] != '#') {
                    botPosX++;
                    return "Success";
                } else {
                    return "Fail";
                }
            } else if (direction == 's') {
                if (loadedMap[botPosX][botPosY + 1] != '#') {
                    botPosY++;
                    return "Success";
                } else {
                    return "Fail";
                }
            } else if (direction == 'w') {
                if (loadedMap[botPosX - 1][botPosY] != '#') {
                    botPosX--;
                    return "Success";
                } else {
                    return "Fail";
                }
            }
        }catch(Exception OutOfBounds){
            return "Fail";
        }
        return null;
    }

    /* Converts map from 2D char array to single string to represent 5x5 grid around player */
    protected String look(char[][] loadedMap) {

        /* Saving previous map items to variables so that player/bot
        placeholders don't permanently overwrite them */
        char prevPlayerChar = loadedMap[playerPosX][playerPosY];
        loadedMap[playerPosX][playerPosY]='P';

        char prevBotChar = loadedMap[botPosX][botPosY];
        loadedMap[botPosX][botPosY]='B';

        StringBuilder output = new StringBuilder();
        for(int j=-2; j<3; j++){
            output.append("\n");//starts next line of 5x5 map
            //nested for-loops to loop through rows and columns of 5x5 grid
            for(int i=-2; i<3; i++) {
                try {
                    char currentChar = loadedMap[playerPosX + i][playerPosY + j];
                    if (currentChar == '.' || currentChar == 'G' || currentChar == '#' || currentChar == 'E' || currentChar == 'B' || currentChar == 'P') {
                        //if valid item, added to output
                        output.append(loadedMap[playerPosX + i][playerPosY + j]);
                    } else {
                        //wall item
                        output.append("#");
                    }
                }catch(Exception outOfMap) {
                    //character is outside map bounds, so filled with
                    output.append("#");
                }
            }
        }

        /* Replacing bot/player items with original map items */
        loadedMap[playerPosX][playerPosY]= prevPlayerChar;
        loadedMap[botPosX][botPosY]=prevBotChar;

        return output.toString();//return grid
    }

    /* Processes the player's pickup command, updating the map and the player's gold amount. */
    protected String pickup(char[][] loadedMap) {
        if(loadedMap[playerPosX][playerPosY]=='G'){
            //if player on gold when pickup method called, it is picked up
            loadedMap[playerPosX][playerPosY]='.';
            goldEarned += 1;
            return "Success. " + gold();
        }else{
            return "Fail.";
        }
    }

    /* Quits game after checking win status */
    protected void quitGame(char[][] loadedMap) {
        if(goldEarned==map.getGoldRequired() && loadedMap[playerPosX][playerPosY]=='E') {
            //if on exit tile with sufficient gold, player wins.
            System.out.println("WIN");
        }else{
            //otherwise they lose
            System.out.println("LOSE");
        }
        System.exit(0);//exits game
    }

    /* Generates valid random start position for player */
    protected void getRandPlayerPos(char[][] loadedMap){
        playerPosX = new Random().nextInt(loadedMap.length);
        playerPosY = new Random().nextInt(loadedMap[0].length);
        if (loadedMap[playerPosX][playerPosY] == '.' || loadedMap[playerPosX][playerPosY] == 'E') {
            //valid position - do nothing
        } else {
            //generates new random position
            getRandPlayerPos(loadedMap);
        }
    }

    /* Generates valid random start position for bot */
    protected void getRandBotPos(char[][] loadedMap){
        botPosX = new Random().nextInt(loadedMap.length);
        botPosY = new Random().nextInt(loadedMap[0].length);
        if (loadedMap[botPosX][botPosY] == '.' || loadedMap[botPosX][botPosY] == 'E'
                && botPosX!=playerPosX && botPosY!=playerPosY) {
            //position is valid when if either '.' or 'E' AND not in same position as player
        } else {
            //generates new random position
            getRandBotPos(loadedMap);
        }
    }

    /* MAIN */
    public static void main(String[] args) {

        System.out.println("Welcome to Dungeons of Doom.\nTo play, please enter the absolute path of your map file here:");
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        String fileLocation="";
        try {
            fileLocation = inputReader.readLine();
        }catch(IOException e){
            System.out.println(e);
        }

        try {
            //creating new instances of GameLogic, HumanPlayer and BotPlayer
            GameLogic gameLogic = new GameLogic(fileLocation);
            HumanPlayer humanPlayer = new HumanPlayer();
            BotPlayer botPlayer = new BotPlayer();

            //defining map locally, used to generate random positions
            char[][] map = gameLogic.map.getMap();
            gameLogic.getRandPlayerPos(map);
            gameLogic.getRandBotPos(map);

            //identify map name
            System.out.println("Map selected: "+gameLogic.map.getMapName());

            while (gameLogic.gameRunning()) {
                /* While game active, updates map with each iteration */
                char[][] currentMap = gameLogic.map.getMap();

                /* Check to see if bot has caught human */
                if (gameLogic.botPosX == gameLogic.playerPosX && gameLogic.botPosY == gameLogic.playerPosY) {
                    System.out.println("The bot has caught you!");
                    gameLogic.quitGame(currentMap);
                }

                /* Taking user commands through HumanPlayer methods */
                String userIn = humanPlayer.getInputFromConsole();
                String playerAction = humanPlayer.getNextAction(userIn);

                /* Player's turn: checks which command was input */
                String playerResult="";
                if (playerAction.equals("quit")) {
                    gameLogic.quitGame(currentMap);
                    //quits game
                } else if (playerAction.equals("hello")) {
                    playerResult=gameLogic.hello();
                    //hello
                } else if (playerAction.contains("move")) {
                    if (playerAction.length() == 6) {
                        //correct length for 'move m' where m is the move
                        char moveChar = playerAction.charAt(5);
                        playerResult=gameLogic.playerMove(moveChar, currentMap);
                    } else {
                        System.out.println("Invalid");
                    }
                } else if (playerAction.equals("pickup")) {
                    //pickup
                    playerResult=gameLogic.pickup(currentMap);
                } else if (playerAction.equals("gold")) {
                    //gold owned
                    playerResult=gameLogic.gold();
                } else if (playerAction.equals("look")) {
                    //look
                    playerResult=gameLogic.look(currentMap);
                }else{
                    playerResult=playerAction;
                }
                System.out.println(playerResult);//prints output of player action

                /* Bots turn: either looks or attempts to move */
                String botAction = botPlayer.decision(gameLogic.playerPosX, gameLogic.playerPosY, gameLogic.botPosX, gameLogic.botPosY);
                if (botAction.contains("move")) {
                    //attempt to move
                    char moveChar = botAction.charAt(5);
                    System.out.println("\n"+gameLogic.botMove(moveChar, currentMap) + " (Bot)\n");
                }else{
                    //looked
                    System.out.println("\nBot looked\n");
                }
            }
        }catch(Exception fileNotFoundException) {
            System.out.println("Error processing file. Please check your file.");
            System.exit(0);
        }
    }
}