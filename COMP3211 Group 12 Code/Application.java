import java.io.*;
import java.util.Scanner;

import controller.GAME_CONTROLLER;
import org.json.JSONObject;

public class Application {
	
   
    public static String command;



    public static void main(String[] args){


        JSONObject saveData = null;
    	
        // Initialize and utilize the system
    	System.out.println("\n" +
                "░██╗░░░░░░░██╗███████╗██╗░░░░░░█████╗░░█████╗░███╗░░░███╗███████╗\n" +
                "░██║░░██╗░░██║██╔════╝██║░░░░░██╔══██╗██╔══██╗████╗░████║██╔════╝\n" +
                "░╚██╗████╗██╔╝█████╗░░██║░░░░░██║░░╚═╝██║░░██║██╔████╔██║█████╗░░\n" +
                "░░████╔═████║░██╔══╝░░██║░░░░░██║░░██╗██║░░██║██║╚██╔╝██║██╔══╝░░\n" +
                "░░╚██╔╝░╚██╔╝░███████╗███████╗╚█████╔╝╚█████╔╝██║░╚═╝░██║███████╗\n" +
                "░░░╚═╝░░░╚═╝░░╚══════╝╚══════╝░╚════╝░░╚════╝░╚═╝░░░░░╚═╝╚══════╝");

        boolean started = true;

        
        //create game controller
    	GAME_CONTROLLER gc = new GAME_CONTROLLER();
   
        //keep looping if user input invalid input
        while(started) {
        
            System.out.println("");
            System.out.println("Select the commands below (Type the number): ");
            System.out.println("1.New game");
            System.out.println("2.Load game");
            System.out.println("3.How to play");
            System.out.println("0.Exit Monopoly");
            System.out.println("");

           
            //create scanner, get user input 
            Scanner scanner = new Scanner(System.in);
            
            command = scanner.nextLine();
            
      
            
            //functions
            switch (command){
                
            	//"0.Exit Monopoly"
                case "0":
 
                	System.exit(0);
                    
                    break;

                    
                //"1.New game"
                case "1":
                   
                	     
                	
                	int numOfPlayer = 0;
              
                	
                	boolean flagForCase1 = true;
                	//keep looping if user input invalid number
                    while(flagForCase1) {
                    	     	
                    	
                    	
                    	System.out.println("Enter the number of players (2 to 6): \n");
                    	
                    	
                    	
                    	try {
                         	
                    		//get user input and close scanner 
                    		numOfPlayer = scanner.nextInt();
                            
                    		
                    		
                    		
                             
           	
                    		//if input is not between 2 to 6, tell user and keep asking input
                    		if( (numOfPlayer > 6) || (numOfPlayer < 2)) {
                    		
                    		
                    			throw new ArithmeticException("Input should be number 2 to 6, try again...\n");
                    		
                    		
                    		}
                    	
                    	}
                    	
                    	//if input is not integer, keep looping
                        catch (Exception e) {
                            
                       	 System.out.println("Input should be number 2 to 6, try again...\n");
                       	 scanner.nextLine();
                       	 continue;
                            
                        }
                    	
                    	
                    	//input is valid, break loop
                    	break;
                    	
                    }  
                    
                    
                    
                    gc.startGame(numOfPlayer, saveData);
                    started = false;
                    break;

                    
                    
                //"2.Load game"
                case "2":


                    int saveNum = 1;
                    boolean flagForCase2 = true;
                    //keep looping if user input invalid number
                    //only have 3 save space, ask user which save they want to load
                    while(flagForCase2) {



                        System.out.println("Enter the save number(1, 2, 3): \n");

                        try {

                            //get user input and close scanner
                            saveNum = scanner.nextInt();


                            //if input is not between 2 to 6, tell user and keep asking input
                            if( (saveNum > 3) || (saveNum < 1)) {


                                throw new ArithmeticException("Input should be number 1 to 3, try again...\n");


                            }

                        }

                        //if input is not integer, keep looping
                        catch (Exception e) {

                            System.out.println("Input should be number 1 to 3, try again...\n");
                            scanner.nextLine();
                            continue;

                        }

                        //input is valid, break loop
                        break;

                    }

                    //read the json string from txt and turn into json object

                    try {

                        //load save file
                        saveData = new JSONObject( turnFileToString("save/save" + saveNum + ".json") );


                    } catch (Exception e) {
                        System.out.println("Error:" + e);
                        e.printStackTrace();
                    }

                    //pass the json to load game
                    gc.startGame(Integer.parseInt(saveData.getString("totalPayer")), saveData);
                  
                    break;


                //"3.How to play"
                case "3":

                    System.out.println("Start:\n" +
                            "New game: press 1 to start a new game of Monopoly in the main menu. Number of players is then required by the game, only 2 to 6 players are allowed. Then you can now choose whether to save the game progress in the 3 files or not, or later. \n" +
                            "All players start on “Go” and are arranged in ascending order to throw the dice by typing “1”, i.e. player 1 throws first, then player 2, then player 3... A pair of four-sided (tetrahedral) dice is used and therefore number 2 to 8 can be thrown by the players. This indicates the number of squares the players move for.\n" +
                            "According to the square the players land on, they may buy properties, or be forced to pay rent, pay income tax, pay fine, draw a Chance, “Go to Jail,” etc.\n" +
                            "\n“Go”:\n" +
                            "Each time a player lands on or passes through Go, he is paid a $1500 salary.\n" +
                            "\nBuying Properties:\n" +
                            "Whenever the player lands on an unowned property, he may buy that property for the written price on the board, or ignore it.\n" +
                            "\nPaying Rents:\n" +
                            "When a player lands on property owned by another player, the owner collects rent from him according to the “Rent Table” (See appendices: Rent Table).\n" +
                            "\n“Chance”:\n" +
                            "When a player lands on one of these three squares, he will either be paid a random amount (multiple of 10) up to $200, or pay a random amount (multiple of 10) up to $300 as well.\n" +
                            "\n“Income Tax”:\n" +
                            "If a player lands here, he will be forced to pay 10% of his money (rounded down to a multiple of 10) as tax.\n" +
                            "\n“Go To Jail”:\n" +
                            "If a player lands on “Go To Jail”, he will be immediately transferred to “In Jail/Just Visiting”. He cannot receive the $1500 salary from “Go” and his turn ends afterward. \n" +
                            "\n“In Jail/Just Visiting”:\n" +
                            "If a player lands on this square by throwing dice normally, nothing happens.\n" +
                            "\n" +
                            "“Free Parking”:\n" +
                            "There is no interaction between players and this square.\n" +
                            "\nBankruptcy:\n" +
                            "A player is considered bankrupt if he has a negative amount of money. He must be retired from the game and his properties will become unowned so that other players can buy.\n" +
                            "\nSave and Load Game:\n" +
                            "After each round, the players are asked if they want to pause the game and save it. Press “0” to ignore it, or press “1”, “2” or “3” to choose the desired save location.\n" +
                            "Next time when they start the game, they can load the saved progress in the main menu by pressing “2”. Choose the correct save location by pressing “1”, “2” or “3” and the game shall continue from where it stopped last time.\n" +
                            "\nExit the game:\n" +
                            "The players can exit the game by simply closing the window or pressing the “Stop” button on the toolbar. Remember to save the game beforehand or all progress will be lost!\n" +
                            "\nEnd of Game:\n" +
                            "The game stops either when there is only one player left on the board, or after 100 rounds have passed. For the 100-round situation, the player with the most amount of money wins, multiple winners is possible.\n");
                    break;
                    
                    
                //validation
                default:
                	
                	System.out.println("Wrong input, try again...\n");	
                	

            }
            
        }





    }

    //change txt to string object
    private static String turnFileToString(String fileName){
        String str = "";
        try {
            InputStream is = new FileInputStream(fileName);
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));

            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();

            return fileAsString;

        } catch(Exception e){
            System.out.println("Error: " + e);
        }

        return str;
    }

}

