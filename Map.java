import java.io.*;
import static java.lang.Integer.parseInt;

public class Map {

    /* Representation of the map */
    private char[][] map;

    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

    /* Map dimensions */
    protected int mapWidth;
    protected int mapHeight;

    /* Takes map file name and passes to readMap() method */
    public Map(String fileName){
        readMap(fileName);
    }

    /* Returns gold required */
    protected int getGoldRequired() {
        return goldRequired;
    }

    /* Returns map as 2D array */
    protected char[][] getMap() {
        return map;
    }

    /* Returns map name */
    protected String getMapName() {
        return mapName;
    }

    /* Reads map details */
    protected void readMap(String fileName){
        try {
            /* Defining file stream and buffered reader */
            FileInputStream getSizeFS = new FileInputStream(fileName);
            BufferedReader getSizeReader = new BufferedReader(new InputStreamReader(getSizeFS));

            getSizeReader.readLine();//skipping first two lines (not part of map)
            getSizeReader.readLine();

            int lineCnt = 0;
            String line = getSizeReader.readLine();
            int charInLine = line.length();

            while(line != null){
                /* loop through map and count lines, used later to define map array.
                Allows use of custom map */
                if(line.length()!=charInLine){
                    //if map width changes
                    System.out.println("Error: invalid map. Must be rectangular.");
                    break;
                }
                /* Updating to move to next line and incrementing count */
                charInLine = line.length();
                line = getSizeReader.readLine();
                lineCnt++;

            }
            /* Updating map height and width variables after measuring. +1 for null value */
            mapHeight = lineCnt+1;
            mapWidth = charInLine+1;

            /* Closing file stream and buffered reader */
            getSizeReader.close();
            getSizeFS.close();

            /* Defining separate file stream and buffered reader to read map*/
            FileInputStream mapFileStream = new FileInputStream(fileName);
            BufferedReader mapReader = new BufferedReader(new InputStreamReader(mapFileStream));

            /* Taking in the map name */
            mapName = mapReader.readLine();
            mapName = mapName.replaceAll("name ", "");

            /* Taking in the gold-to-win value */
            String goldLine = mapReader.readLine();
            goldLine = goldLine.replaceAll("\\D+ ","");
            goldRequired = parseInt(goldLine);
            if(goldRequired<1){
                //validates map file's gold-to-win value
                System.out.println("Map must have a minimum 1 gold to win. Please use another map.");
                System.exit(0);
            }

            /* Defining variables for use in looping through and reading map data */
            String mapLine = mapReader.readLine();
            map = new char[charInLine+1][lineCnt+1];
            int j = 0;

            /* loop through row by row, adding map items to 2d array one-by-one */
            while(mapLine != null){
                /* Convert line to char array */
                char[] lineArray = mapLine.toCharArray();
                int i=0;
                for(char ch : lineArray){
                    //adds each item in row to map
                    i++;
                    map[i][j]=ch;
                }
                /* Move to next row (line) */
                j++;
                mapLine = mapReader.readLine();
            }
            /* Closing file stream and buffered reader */
            mapFileStream.close();
            mapReader.close();

        }catch(IOException e){
            System.out.println("File not found.");
            System.exit(0);
        }
    }
}
