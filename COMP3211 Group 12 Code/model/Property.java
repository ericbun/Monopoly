package model;

import java.util.Scanner;

public class Property extends Square{
	
	
	private String name;
	private int price;
	private int rent;
	private int owner=0;
	private String color;


	//constructor
	public Property(String name, int position, int price, int rent, String color) {
		
	
		setType("Property");
		this.setName(name);
		this.setPosition(position);
		this.setPrice(price);
		this.setRent(rent);
		this.setColor(color);
		
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public void setColor(String color) { this.color = color; }

	public String getColor(){
		return color;
	}

	public void setOwner(int player){
		owner = player;
	}

	public int getOwner(){
		return owner;
	}

	public String defaultAsking(Player player1,int player){

		if(getOwner()==0){
			System.out.println("Do you want to buy "+getName()+" ?");
			System.out.println("1. Buy");
			System.out.println("2. Not buy");
			int input = 0;
			Scanner scanner = new Scanner(System.in);

			try {
				//get user input and close scanner
				input = scanner.nextInt();
				//if input is not between 2 to 6, tell user and keep asking input
				if( (input > 2) || (input < 1)) {
					throw new ArithmeticException("Input should be 1 or 2, try again...\n");
				}
			}
			//if input is not integer, keep looping
			catch (Exception e) {
				System.out.println("Input should be 1 or 2, try again...\n");
				scanner.nextLine();
			}

			if(input == 1){
				if(player1.getMoney()-getPrice() <=0) {
					return "money";
				}else{
					setOwner(player);
					return getName();
				}

			}
		}else if(getOwner()== player){
			System.out.println("This is your own Property: Do nothing");
		}

		else{
			System.out.println("This square("+getName()+") has owner: Player "+getOwner());
			return "pay";
		}


			return null;

	}


	
	
}
