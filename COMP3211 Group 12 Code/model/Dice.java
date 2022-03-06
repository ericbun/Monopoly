package model;

import java.util.Random;

public class Dice {

	
	int result;
	int roll1;
	int roll2;


	
	//roll the dice
	public int getResult() {

		Random  r = new Random();

		roll1 = r.nextInt(4)+1;
		roll2 = r.nextInt(4)+1;
		result = roll1 + roll2;

		System.out.println("Dice roll is: "+ result);
		return result;
			
	}

	public int throwFirstDice(){
		Random  r = new Random();
		roll1 = r.nextInt(4)+1;
		return roll1;
	}

	public int throwSecondDice(){
		Random  r = new Random();
		roll2 = r.nextInt(4)+1;
		return roll2;
	}



	
	
	
}
