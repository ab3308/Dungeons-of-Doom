import java.util.Random;
import static java.lang.Math.abs;

/* Responsible for 'smart' bot which attempts to chase the human player down */
public class BotPlayer {

    protected boolean botMoveTurn = false;//tracks if turn to move
    protected boolean inRange = false;//tracks if player in bots range

    /* Makes decision about bots command/move */
    protected String decision(int playerPosX, int playerPosY, int botPosX, int botPosY) {
        int diffX = abs(botPosX-playerPosX);
        int diffY = abs(botPosY-playerPosY);
        if(diffX<3 && diffY<3 && !botMoveTurn){
            //checks if the player is in the bots range
            botMoveTurn = true;
            inRange = true;
            return "looked";
        }else if(botMoveTurn && inRange){
            //move towards player if bots turn to move and in range
            botMoveTurn=false;
            inRange=false;
            return getBotMove(diffX, diffY, playerPosX, playerPosY, botPosX, botPosY);
        }else if(botMoveTurn){
            //move randomly if turn to move
            botMoveTurn = false;
            return randomMove();
        }else{
            //if looked and nothing in range,
            //updates boolean so next turn the bot moves randomly
            botMoveTurn=true;
            return "looked";
        }
    }

    /* Finds optimal move for bot if in range */
    protected String getBotMove(int diffX, int diffY, int playerPosX, int playerPosY, int botPosX, int botPosY) {
        if(diffX<diffY){
            //difference in y is bigger, attempt to decrease to keep within 5x5 grid
            if(playerPosY>botPosY){
                return "move s";
            }else{
                return "move n";
            }
        }else if(diffX>diffY){
            //difference in x is bigger, attempt to decrease to keep within 5x5 grid
            if(playerPosX>botPosX){
                return "move e";
            }else{
                return "move w";
            }
        }else{
            //if differences are equal, randomly chooses to either decrease x or y difference
            int randomNum = new Random().nextInt(3);
            if(randomNum==2){
                //will move optimally in y plane
                return getBotMove(0,1, playerPosX, playerPosY, botPosX, botPosY);
            }else{
                //will move optimally in x plane
                return getBotMove(1,0, playerPosX, playerPosY, botPosX, botPosY);
            }
        }
    }

    /* Generates random move */
    protected String randomMove(){
        //generates random move by randomly picking from array
        String[] allMoves= new String[]{"move e", "move w", "move s", "move n"};
        int randomNum = new Random().nextInt(allMoves.length);
        return allMoves[randomNum];
    }

}
