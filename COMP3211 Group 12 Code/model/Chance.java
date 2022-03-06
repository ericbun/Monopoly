package model;

import java.util.Random;

public class Chance extends Square{

	// creating Random object
	Random rd;
	private boolean gain;
	private int result;

	private final int gainMax = 20;
	private final int gainMin = 1;


	private final int lossMax = 30;
	private final int lossMin = 1;

	public Chance(String type, int position) {

		rd = new Random();
		setType("Chance");
		setPosition(position);
		
	}


	public void Trigger_Chance(Player player){




		gain = rd.nextBoolean();

		//gain == true, gains a random amount (multiple of 10) up to HKD 200
		if(gain){


			result = rd.nextInt(gainMax - gainMin + 1) + gainMin;

			result = result * 10;


			player.setMoney(player.getMoney()+result);
			System.out.println("Player "+player.getPlayerNum()+" earns $"+result);


		}

		//gain == false, loses a random amount (multiple of 10) up to HKD 300.
		else{


			result = rd.nextInt(lossMax - lossMin + 1) + lossMin;

			result = result * -10;

			if(player.getMoney()-result <=0) {
				player.setMoney(0);
				player.setGameOverStatus(true);
				System.out.println("\"You don't have enough money to pay -----> Game Over!!\"");
			}else{
				player.setMoney(player.getMoney()+result);
				System.out.println("Player "+player.getPlayerNum()+" deducts $"+result);
			}



		}



	}



}
