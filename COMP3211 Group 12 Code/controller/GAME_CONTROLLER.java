package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import view.Board;


public class GAME_CONTROLLER {
	
	//game round counter
	private int round = 1;

	//create scanner, get user input
	Scanner scanner = new Scanner(System.in);
	private List<Player> playerList = new ArrayList<>();
	private int diceroll = 0;
	private int winnerPlayer=0;
	private int[] maxRoundWinner={0,0,0,0,0,0};
	private int noOfLoser=0;
	private Property property;
	private Go go;
	private Chance chance;
	private FreeParking freeparking;
	private GoToJail goToJail;
	private IncomeTax incomeTax;
	private int jailMove;
	private InJailOrJustVisiting inJailOrJustVisiting;
	private Board board = new Board();
	private List<Square> squareList = board.getSquareList();
	private int postion;
	private String askResult;
	Dice dice = new Dice();

	
	//method to start game
	public void startGame(int numOfPlayer, JSONObject saveData){


		int round = 1;

		//create board

		board.createBoard();
		//create dice



		//create player for new game
		if(saveData == null) {


			for (int i = 0; i < numOfPlayer; i++) {

				playerList.add(i, new Player(i+1));

			}
		}

		//load player data from save
		else{


			for (int i = 1; i < numOfPlayer+1; i++) {

				// Creating example List and adding the data to it
				JSONArray tempArray = saveData.getJSONObject(String.valueOf(i)).getJSONArray("property");

				ArrayList<String> tempList = new ArrayList<String>();
				if (tempArray != null) {
					for (int x=0;x<tempArray.length();x++){

							tempList.add(tempArray.getString(x));
					}
				}



				playerList.add(i - 1, new Player(Integer.valueOf(saveData.getJSONObject(String.valueOf(i)).getString("money")),
						Integer.valueOf(saveData.getJSONObject(String.valueOf(i)).getString("pos")),
						i, tempList));


				String tempP = squareList.get(Integer.valueOf(saveData.getJSONObject(String.valueOf(i)).getString("pos"))-1).getType();
				if(tempP == "Property"){

					property =(Property) squareList.get(Integer.valueOf(saveData.getJSONObject(String.valueOf(i)).getString("pos"))-1);
					playerList.get(i-1).setCurrentPos(property.getName());


				}else{

					playerList.get(i-1).setCurrentPos(tempP);


				}

			}


			//load the current round
			setRound(Integer.valueOf(saveData.getString("round")));
			//System.out.println("Round: "+getRound());

		}
		//out side load game if------------------------------------------------------------------------------




		//Round start, max 100 rounds
		for(int i = getRound(); i < 100; i++) {

			setRound(i);
			//System.out.println("i: "+i);


//-----------------------------------------------------------save game code start----------------------------------------------------------------------
			boolean flagForWhile = true;
			while(flagForWhile) {




				try {

					//cache the game data first
					JSONObject gameData = new JSONObject();
					gameData.put("round", String.valueOf(i));
					gameData.put("totalPayer", String.valueOf(numOfPlayer));
					for( int k = 0; k < numOfPlayer; k++){


						JSONArray propertyArray = new JSONArray();

						String[] tempPropertyList = playerList.get(k).getPropertyArrayList().toArray(new String[0]);



						if(tempPropertyList != null) {

							for (int x = 0; x < tempPropertyList.length; x++) {
								propertyArray.put(x, tempPropertyList[x]);
							}


						}


						JSONObject tempData = new JSONObject();
						tempData.put("pos", String.valueOf(playerList.get(k).getCurrentPosNo()));
						tempData.put("money", String.valueOf(playerList.get(k).getMoney()));
						tempData.put("property", propertyArray);

						gameData.put(String.valueOf(k+1), tempData);


					}





					//at the beginning of every round, system wil ask player whether to save the game.
					System.out.println("Want to save the game? (0: No, 1: save to save1, 2: save to save2, 3: save to save3)\n");

					BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));


					//get user input
					String input =  buf.readLine();

					switch (input){

						//player don't want to save the game
						case "0":

							flagForWhile = false;

							break;


						//save the game to save1.json
						case "1":


							Files.write(Path.of("save/save1.json"), gameData.toString().getBytes());

							flagForWhile = false;
							break;

						//save the game to save2.json
						case "2":

							Files.write(Path.of("save/save2.json"), gameData.toString().getBytes());

							flagForWhile = false;
							break;


						//save the game to save3.json
						case "3":

							Files.write(Path.of("save/save3.json"), gameData.toString().getBytes());

							flagForWhile = false;
							break;

						default :
							//throw new Exception("Input should be number 0 to 3, try again...\n");

					}

				}

				//if input is not integer, keep looping
				catch (Exception e) {

					System.out.println("Error: "+ e + "\n");
					scanner.nextLine();
					continue;

				}

				//input is valid, break loop
				break;
			}
			//-----------------------------------------------------------save game code end----------------------------------------------------------------------


			for (Player player: playerList){

				checkEndGame(numOfPlayer);
				if(winnerPlayer != 0){
					break;
				}

				//Printing the game board
				board.printBoard();
				System.out.println("ROUND: "+getRound());
				System.out.println("");

				//Printing player information every round
				for (Player players: playerList){
					System.out.println("Player "+players.getPlayerNum()+"    Money: "+players.getMoney()+"    Position: "+players.getCurrentPosNo()+"    PosName: "+players.getCurrentPos()+"    OwnProperty: "+players.getProperty().toString());
				}
				System.out.println("");

				//Check if this player is lost already
				if(player.getMoney()<=0 || player.getGameOverStatus()==true){
					System.out.println("**** Player "+player.getPlayerNum()+" has lost already ****");
					System.out.println("Round goes to Next player");
					continue;
				}else if(player.getJailStop()>0){ //Check if this player is in the Jail
					jailMove= goToJail.escapeJail(player,player.getJailStop());
					if(jailMove==0){
						continue;
					}else{
						System.out.println("Escape successfully!");
						postion = (player.getCurrentPosNo()+jailMove);
						player.setCurrentPosNo(postion);
						usualGameFlow(player);

					}
				}

				//If they are not lost and in the Jail, they can play the game as usual in this else{}
				else{
					System.out.println("Player "+player.getPlayerNum()+" :");
					System.out.println("Enter 1 to roll the dice.....");

					try {
						//get user input and close scanner
						diceroll = scanner.nextInt();
						//if input is not 1, tell user and keep asking input
						if(diceroll != 1) {
							throw new ArithmeticException("Input should be 1 ,try again...\n");
						}
					}
					//if input is not integer, keep looping
					catch (Exception e) {
						System.out.println("Input should be 1 ,try again...\n");
						scanner.nextLine();
					}

					postion = (player.getCurrentPosNo()+dice.getResult());

					//Check if they pass Goal
					if(postion-1 == 20){
						postion = postion%20;
						go = (Go)squareList.get(0);
						go.Trigger_Go(player);
					}
					else if(postion/20.0 > 1.0){
						postion = postion%20;
						go = (Go)squareList.get(0);
						go.Trigger_Go(player);
					}else {
						postion = postion%20;
					}
					player.setCurrentPosNo(postion);

					//If the dice is rolled
					if(diceroll == 1){

						usualGameFlow(player);

						if(postion==0){
							squareList.get(19).setPlayerHere(player.getPlayerNum());
						}
						else if(postion==1){
							squareList.get(0).setPlayerHere(player.getPlayerNum());
						}else{
							squareList.get(postion-1).setPlayerHere(player.getPlayerNum());
						}



					}

				}




				System.out.println(".......");
				System.out.println(".......");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			checkEndGame(numOfPlayer);
			if(winnerPlayer != 0){
				System.out.println("Winner is Player "+winnerPlayer+" !!!!");
				break;
			}





		}
		int max= playerList.get(0).getMoney();
		for(Player player:playerList){
			if(player.getMoney() > max){
				max = playerList.get(0).getMoney();
			}
		}
		for(Player player:playerList){
			if(player.getMoney() == max){
				maxRoundWinner[player.getPlayerNum()-1]=1;
			}
		}
		System.out.print("Winner is: ");
		for(int i=0; i< 6;i++){
			if(maxRoundWinner[i] == 1){
				System.out.print("Player "+(i+1)+" ");
			}
		}
		System.out.println("");
		
		
		
		
		
	}

	public void usualGameFlow(Player player){
		if(postion == 1){
			System.out.println("Back to Go");
		}
		else if(!squareList.get(postion-1).getType().equals("Property")){
			System.out.print("Enter: ");
			System.out.print(squareList.get(postion-1).getType());
			System.out.println(" ("+postion+")");
			player.setCurrentPos(squareList.get(postion-1).getType());
			switch (squareList.get(postion-1).getType()){
							/*case "Go":
								go = (Go)squareList.get(postion-1);
								go.Trigger_Go(player);
								break;*/
				case "Chance":
					chance = (Chance)squareList.get(postion-1);
					chance.Trigger_Chance(player);
					break;
				case "FreeParking":
					freeparking = (FreeParking)squareList.get(postion-1);
					freeparking.Trigger_freeparking();
					break;
				case "GoToJail":
					goToJail = (GoToJail) squareList.get(postion-1);
					goToJail.Trigger_GoJail(player);
					break;
				case "Income Tax":
					incomeTax = (IncomeTax) squareList.get(postion-1);
					incomeTax.Trigger_incomeTax(player);
					break;


			}
		}else{
			property =(Property) squareList.get(postion-1);
			player.setCurrentPos(property.getName());
			askResult= property.defaultAsking(player,player.getPlayerNum());
			if(askResult == null){
				System.out.println("You don't want to buy "+property.getName());
			}

			else if(askResult.equals("pay")){
				System.out.println("You need to pay $"+property.getRent()+" to Player "+property.getOwner());

				if(player.getMoney() < property.getRent()){  //Check if player have enought money to pay, if not then GG, if yes then continue
					player.setMoney(0);
					player.setGameOverStatus(true);
					System.out.println("You don't have enough money to pay -----> Game Over!!");
				}else{
					player.setMoney(player.getMoney()-property.getRent());
					playerList.get(property.getOwner()-1).setMoney(playerList.get(property.getOwner()-1).getMoney()+property.getRent());
					System.out.println("Paid successfully!");
				}


			}
			else if(askResult.equals("money")){
				System.out.println("You dont have enough money to buy...... Skip");
			}

			else{
					player.setMoney(player.getMoney()-property.getPrice());
					System.out.println("Bought "+property.getName()+" successfully!");
					player.addProperty(property.getName());
			}
		}

	}

	public void checkEndGame(int numOfPlayer){
		for (Player count_player: playerList){
			if(count_player.getGameOverStatus()==true){
				noOfLoser++;
			}
		}
		if(noOfLoser == (numOfPlayer-1)){
			for(Player count_player1: playerList){
				if(count_player1.getGameOverStatus()==false){
					winnerPlayer = count_player1.getPlayerNum();
				}
			}

		}
		noOfLoser=0;
	}

	
	public int getRound() {
		return round;
	}


	public void setRound(int round) {
		this.round = round;
	}


}
