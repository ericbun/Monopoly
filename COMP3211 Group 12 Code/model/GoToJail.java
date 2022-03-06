package model;

import java.util.Scanner;

public class GoToJail extends Square{

	private Dice dice = new Dice();
	int dice1,dice2;
	private Scanner scanner = new Scanner(System.in);

	public GoToJail(String type, int position) {
		
		
		setType(type);
		setPosition(position);
		
	}

	public void Trigger_GoJail(Player player){

		player.setJailStop(3);
		player.setCurrentPos("InJailOrJustVisiting");
		player.setCurrentPosNo(6);
		System.out.println("You are in the jail now!");
	}

	public int escapeJail(Player player, int jailStop){
		System.out.println("JailStop Times: "+jailStop);
		if(jailStop==3){
			dice1=dice.throwFirstDice();
			dice2=dice.throwSecondDice();
			System.out.println("Dice 1 is : "+dice1);
			System.out.println("Dice 2 is : "+dice2);
			if( dice1 == dice2){
				player.setJailStop(0);
				return dice1+dice2;
			}else{
				player.setJailStop(jailStop-1);
				System.out.println(".......");
				System.out.println(".......");
				System.out.println("Fail");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if(jailStop==2){
			int input = 0;
			System.out.println("Do you want to pay $150 before throwing the dice? (Then you can move without throwing Doubles)");
			System.out.println("1: Pay before throwing");
			System.out.println("2: Not pay (I believe in my luck!!!)");

			try {
				//get user input and close scanner
				input = scanner.nextInt();
				//if input is not 1, tell user and keep asking input

				if((input > 2) || (input < 1)) {
					throw new ArithmeticException("Input should be 1 or 2 ,try again...\n");
				}
			}
			//if input is not integer, keep looping
			catch (Exception e) {
				System.out.println("Input should be 1 or 2 ,try again...\n");
				scanner.nextLine();
			}
			dice1=dice.throwFirstDice();
			dice2=dice.throwSecondDice();
			System.out.println("Dice 1 is : "+dice1);
			System.out.println("Dice 2 is : "+dice2);
			if(input == 1){
				if(player.getMoney()<=150){
					System.out.println("\"You don't have enough money to pay\"");
					System.out.println("Try your luck");
					if( dice1 == dice2){
						player.setJailStop(0);
						return dice1+dice2;
					}else{
						player.setJailStop(jailStop-1);
						System.out.println(".......");
						System.out.println(".......");
						System.out.println("Fail");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}else{
					System.out.println("Deduct $150 from account....");
					player.setMoney(player.getMoney()-150);
					System.out.println("Balance: "+player.getMoney());
					player.setJailStop(0);
					return dice1+dice2;
				}

			}
			else{
				if( dice1 == dice2){
					player.setJailStop(0);
					return dice1+dice2;
				}else{
					player.setJailStop(jailStop-1);
					System.out.println(".......");
					System.out.println(".......");
					System.out.println("Fail");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

		else if(jailStop == 1){
			int input = 0;
			System.out.println("Do you want to pay $150 before throwing the dice? (Then you can move without throwing Doubles)");
			System.out.println("But if you fail this time, you still need to pay $150 without moving forward");
			System.out.println("1: Pay before throwing");
			System.out.println("2: Not pay (I believe in my luck!!!)");

			try {
				//get user input and close scanner
				input = scanner.nextInt();
				//if input is not 1, tell user and keep asking input

				if((input > 2) || (input < 1)) {
					throw new ArithmeticException("Input should be 1 or 2 ,try again...\n");
				}
			}
			//if input is not integer, keep looping
			catch (Exception e) {
				System.out.println("Input should be 1 or 2 ,try again...\n");
				scanner.nextLine();
			}
			dice1=dice.throwFirstDice();
			dice2=dice.throwSecondDice();
			System.out.println("Dice 1 is : "+dice1);
			System.out.println("Dice 2 is : "+dice2);
			if(input == 1){
				if(player.getMoney()<=150) {
					System.out.println("\"You don't have enough money to pay\"");
					System.out.println("Try your luck");
					if (dice1 == dice2) {
						player.setJailStop(0);
						return dice1 + dice2;
					} else {
						System.out.println("XXXXXX You still fail this time XXXXXX");
						System.out.println("Deduct $150 from account....");
						if(player.getMoney()<=150) {
							System.out.println("\"You don't have enough money to pay -----GameOver\"");
							player.setGameOverStatus(true);
							player.setMoney(0);
						}
					}
				}else{
					System.out.println("Deduct $150 from account....");
					player.setMoney(player.getMoney()-150);
					System.out.println("Balance: "+player.getMoney());
					player.setJailStop(0);
					return dice1+dice2;
				}

			}
			else{
				if( dice1 == dice2){
					player.setJailStop(0);
					return dice1+dice2;
				}else{
					System.out.println("XXXXXX You still fail this time XXXXXX");
					System.out.println("Deduct $150 from account....");
					if(player.getMoney()<=150) {
						System.out.println("\"You don't have enough money to pay\"");
						player.setGameOverStatus(true);
						player.setMoney(0);
					}else{
						player.setMoney(player.getMoney()-150);
						System.out.println("Balance: "+player.getMoney());
						player.setJailStop(0);
					}


				}
			}

		}
		return 0;

	}



}
