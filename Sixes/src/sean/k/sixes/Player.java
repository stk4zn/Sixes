package sean.k.sixes;

public class Player{
	
	String name;
	boolean redemption;
	//int number;
	public int showdownPoints;
	public boolean alive;
	
	public Player(String theName){
		name = theName;
		
		//number = theNumber;
		redemption = false;
		alive  = true;
		showdownPoints = 0;
	}
	
	public String getName(){
		return name;
	}
	
	
	
	public void setName(String newName){
		name = newName;
	}
	
	public void resetPlayer(){
		showdownPoints = 0;
		alive = true;
	}
	/*
	public void setNumber(int newNumber){
		number = newNumber;
	}
	*/


}
