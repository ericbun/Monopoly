package model;

public class Go extends Square{



	public Go(String type, int position) {
		
	
		setType(type);
		setPosition(position);
		
	}

	public void Trigger_Go(Player player){
		System.out.println("Passed Go: +$1500");
		player.setMoney(player.getMoney()+1500);
	}




}