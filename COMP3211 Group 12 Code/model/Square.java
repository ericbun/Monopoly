package model;

public abstract class Square {
	
	
	private String type;
	private int position;
	private int[] player = {0,0,0,0,0,0};


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public void setPlayerHere(int p){
		player[p-1]=1;
	}

	public int[] getPlayerHere(){
		return player;
	}
	

}

