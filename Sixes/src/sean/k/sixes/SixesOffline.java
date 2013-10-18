package sean.k.sixes;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SixesOffline extends Activity{
	
	ImageView dice1Image;
	ImageView dice2Image;
	ImageView handImage;
	PlayerList allPlayersPanel;
	PlayerListAdapter listAdapt;
	
	int diceImages[];
	
	int screenWidth;
	int screenHeight;
	int numPlayers;
	
	int currentTurn;
	int currentSixes;
	int currentBank;
	
	//turn info
	boolean win;
	boolean noRedemption;
	boolean anotherTurn;
	boolean doubleSix;
	
	int dice1;
	int dice2;
	
	boolean gameOver;
	
	boolean redemptionRound;
	int winner;
	
	boolean showdownRound;
	
	boolean rollAnim;
	boolean rolled;
	
	CharSequence[] playerNames;
	ArrayList<Player>playerArray;
	
	TextView mainText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//screenWidth = getIntent().getIntExtra("sean.k.sixes.screenWidth",100);
		//screenHeight = getIntent().getIntExtra("sean.k.sixes.screenHeight",100);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;
		screenHeight = displaymetrics.heightPixels;

		setContentView(R.layout.sixes_offline);
		
		initialize();
		
		
	}
	
	private void initialize(){
		
		

		
		currentTurn = 0;
		currentSixes = 0;
		currentBank = 0;
		dice1 = 0;
		dice2 = 0;
		
		diceImages = new int[6];
		
		diceImages[0] = R.drawable.dice1;
		diceImages[1] = R.drawable.dice2;
		diceImages[2] = R.drawable.dice3;
		diceImages[3] = R.drawable.dice4;
		diceImages[4] = R.drawable.dice5;
		diceImages[5] = R.drawable.dice6;
		
		mainText = (TextView) findViewById(R.id.sixesOfflineMainText);
		gameOver = false;
		rollAnim = false;
		rolled = false;
		

		
		playerArray = new ArrayList<Player>();
		
		playerNames = getIntent().getCharSequenceArrayExtra("sean.k.Sixes.SixesOfflineCreate.playerNames");
		numPlayers = playerNames.length;
		
		createPlayers(playerNames);
		
		//create hand
		handImage = (ImageView) findViewById(R.id.sixesOfflineHand);
		int handImageHeight = screenHeight/3;
		int handImageWidth =  handImageHeight;
		handImage.setImageResource(R.drawable.hand_closed);
		FrameLayout.LayoutParams handImageParams = (LayoutParams) handImage.getLayoutParams();
		handImageParams.setMargins(0, 0, 0, 0);
		handImageParams.height = handImageHeight;
		handImageParams.width = handImageWidth;
		handImage.setVisibility(ImageView.VISIBLE);
		handImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (rollAnim || gameOver){
					//if animation is currently happening or game is over
				} else{
					//no animation
					if (rolled){
						//if the dice have been rolled, put them back into hand
						nextTurn();
						grabDice();
					} else{
						//hand is closed, roll
						rollAnim = true;
						if (showdownRound){
							showdownRoll();
						} else{
							roll();
						}
					}

				}
				
			}
			
		});
		
		//create dice1
		dice1Image = (ImageView) findViewById(R.id.sixesOfflineDice1);
		int dice1ImageHeight = screenHeight/12;
		int dice1ImageWidth =  dice1ImageHeight;
		dice1Image.setImageResource(R.drawable.dice1);
		FrameLayout.LayoutParams dice1ImageParams = (LayoutParams) dice1Image.getLayoutParams();
		dice1ImageParams.setMargins(screenWidth/2-dice1ImageWidth-dice1ImageWidth/2, 0, 0, screenHeight/2-dice1ImageHeight/2);
		dice1ImageParams.height = dice1ImageHeight;
		dice1ImageParams.width = dice1ImageWidth;
		dice1Image.setVisibility(ImageView.INVISIBLE);
		
		//create dice2
		dice2Image = (ImageView) findViewById(R.id.sixesOfflineDice2);
		int dice2ImageHeight = screenHeight/12;
		int dice2ImageWidth =  dice2ImageHeight;
		dice2Image.setImageResource(R.drawable.dice2);
		FrameLayout.LayoutParams dice2ImageParams = (LayoutParams) dice2Image.getLayoutParams();
		dice2ImageParams.setMargins(screenWidth/2 +dice2ImageWidth/2, 0, 0, screenHeight/2-dice2ImageHeight/2);
		dice2ImageParams.height = dice2ImageHeight;
		dice2ImageParams.width = dice2ImageWidth;
		dice2Image.setVisibility(ImageView.INVISIBLE);
		
		//testRandom();
		
		//create currplayerpanel
		LinearLayout currentPlayerPanel = (LinearLayout) findViewById(R.id.sixesOfflineCurrentPlayerPanel);
		FrameLayout.LayoutParams currentPPParams = (LayoutParams) currentPlayerPanel.getLayoutParams();
		currentPPParams.width = screenWidth/4;
		
		//create allplayerpanel
		LinearLayout allPlayersPanelContainer = (LinearLayout) findViewById(R.id.sixesOfflineAllPlayersList);
		allPlayersPanel = (PlayerList) findViewById(R.id.all_players_panel_listview);
		FrameLayout.LayoutParams allPlayersParams = (LayoutParams) allPlayersPanelContainer.getLayoutParams();
		allPlayersParams.width = screenWidth/4;
		allPlayersParams.height = screenHeight*2/3;
		//listAdapt = new PlayerListAdapter(this,R.layout.player_panel_entry,playerArray,false, currentTurn);
		listAdapt = new PlayerListAdapter(this,R.layout.player_panel_entry,playerArray);
		allPlayersPanel.setAdapter(listAdapt);
		//allPlayersPanel.setEnabled(false);
		//allPlayersPanel.setMo
		//allPlayersPanel.setClickable(false);


		
		newGame(currentTurn);
		
		updateCurrentPlayerPanel();
		
		
	}
	
	private void createPlayers(CharSequence[] names){

		for (int i = 0; i<numPlayers;i++){
			playerArray.add(new Player(names[i].toString()));
		}
		
	}
	
	private void newGame(int firstTurn){
		
		winner = -1;
		
		showdownRound = false;
		redemptionRound = false;
		
		currentTurn = firstTurn;
		currentSixes = 0;
		gameOver = false;
		rollAnim =false;
		
		for (Player player: playerArray){
			player.resetPlayer();
		}
		
		//turn info
		win = false;
		noRedemption = false;
		anotherTurn = false;
		doubleSix = false;
		
		grabDice();
		
		mainText.setText("");
	}
	
	private void grabDice(){
		rolled = false;
		handImage.setImageResource(R.drawable.hand_closed);
		dice1Image.setVisibility(ImageView.INVISIBLE);
		dice2Image.setVisibility(ImageView.INVISIBLE);
		if (!gameOver){
		mainText.setText("");
		}
		if (redemptionRound){
			mainText.setText("REDEMPTION");
		}
		if (showdownRound){
			mainText.setText("SHOWDOWN");
		}
		updateCurrentPlayerPanel();
	}
	private void randomDice(){
		
		dice1 = (int)(6*Math.random() + 1);
		dice2 = (int)(6*Math.random() + 1);
		
		dice1Image.setImageResource(diceImages[dice1-1]);
		dice2Image.setImageResource(diceImages[dice2-1]);
	}
	
	/*
	private void testRandom(){
		int values[] = new int[6];
		
		for (int i = 0; i<1000; i++){
		randomDice();
		values[dice1-1] = values[dice1-1]+1;
		values[dice2-1] = values[dice2-1]+1;
		}
		
		TextView playerSixes = (TextView) findViewById(R.id.currentPlayerScore);
		playerSixes.setText("["+values[0]+", "+values[1]+", "+values[2]+", "+values[3]+", "+values[4]+", "+values[5]+", "+"]");

		
		
	}
	*/
	
	private void showdownRoll(){
		//take a turn
		
				handImage.setImageResource(R.drawable.hand_open);
				
				anotherTurn = false;
				win = false;
				doubleSix = false;
				
				rolled = true;
				
				
				randomDice();
				


				
				dice1Image.setVisibility(ImageView.VISIBLE);
				dice2Image.setVisibility(ImageView.VISIBLE);
				
				if ((dice1 + dice2 == 6) || (dice1==6) || (dice2==6)){
					playerArray.get(currentTurn).showdownPoints++;
					anotherTurn = true;
					mainText.setText("SIX");
				}
				
				if (dice1 == 3 && dice2 == 3){
					//if double three.  will already have another turn from above
					currentBank++;
					mainText.setText(currentBank+ " in the BANK");
					
				} else if (dice1==6 && dice2 ==6){
					//if double sixes
					win = true;
					doubleSix = true;
					mainText.setText("DOUBLE SIX");
				} else if (dice1 == dice2){
					
					mainText.setText("DUBS");
					anotherTurn = true;	
				}
				

				
				if (playerArray.get(currentTurn).showdownPoints>=10){
					win = true;
				}
				
				
				
				if (!win && !anotherTurn){
					// if current player did not win and does not have another turn
					if (currentBank>0){
						//if they have an extra turn from the bank
						mainText.setText("EXTRA TURN");
					} else{
						mainText.setText("");

					}
				} else if(win){
					endGame();
				}
				

				updateCurrentPlayerPanel();
				rollAnim = false;
	}
	
	private void roll(){
		//take a turn
		
		handImage.setImageResource(R.drawable.hand_open);
		
		anotherTurn = false;
		win = false;
		doubleSix = false;
		noRedemption = false;
		
		rolled = true;
		
		
		randomDice();
		


		
		dice1Image.setVisibility(ImageView.VISIBLE);
		dice2Image.setVisibility(ImageView.VISIBLE);
		
		if ((dice1 + dice2 == 6) || (dice1==6) || (dice2==6)){
			currentSixes++;
			anotherTurn = true;
			mainText.setText("SIX");
		}
		
		if (dice1 == 3 && dice2 == 3){
			//if double three.  will already have another turn from above
			currentBank++;
			mainText.setText(currentBank+ " in the BANK");
			
		} else if (dice1==6 && dice2 ==6){
			//if double sixes
			win = true;
			doubleSix = true;
			mainText.setText("DOUBLE SIX");
		} else if (dice1 == dice2){
			
			mainText.setText("DUBS");
			anotherTurn = true;	
		}
		

		
		if (currentSixes>=6){
			win = true;
			if (doubleSix){
				//if double six on sixth six
				mainText.setText("NO REDEMPTION");
				noRedemption = true;
			}
		}
		
		
		
		if (!win && !anotherTurn){
			// if current player did not win and does not have another turn
			if (currentBank>0){
				//if they have an extra turn from the bank
				mainText.setText("EXTRA TURN");
			} else{
				mainText.setText("");
				
				if (redemptionRound){
					playerArray.get(currentTurn).alive = false;
				}
			}
		} else if(win){
			
			if (!redemptionRound){
			winner = currentTurn;
			if (noRedemption){
			endGame();
			} else{
				redemption();
			}
			}
		}
		

		updateCurrentPlayerPanel();
		rollAnim = false;
		
	}
	
	
	
	private void redemption(){

		redemptionRound = true;
		playerArray.get(currentTurn).alive = true;
	}
	
	private void nextTurn(){
		//after rolling, this happens when you grab the dice
		
		if (currentBank>0 && !win && !anotherTurn){
			currentBank--;
			return;
		}
		
		if (anotherTurn && !win){
			return;
		}
		
		
		currentSixes = 0;
		currentBank = 0;
		
		if (currentTurn == numPlayers-1){
			currentTurn = 0;
		} else{
			currentTurn++;
		}
		
		if (playerArray.get(currentTurn).alive==false){
			nextTurn();
		}
		

		if (redemptionRound){
			
			if (currentTurn==winner){
				endRedemption();
			}
			
		} else if (showdownRound){
			
		} else{
			
		}

		

		
	}
	
	private void endRedemption(){
		redemptionRound = false;
		boolean redeemed = false;
		for (int i = 0; i<numPlayers; i++){
			if (playerArray.get(i).alive && (winner!=i)){
				redeemed = true;
			}
		}
		
		if (!redeemed){
			endGame();
		} else{
			showdown();
		}

	}
	
	private void showdown(){
		showdownRound = true;
	}
	
	private void endGame(){
		winner = currentTurn;
		gameOver = true;
		mainText.setText("GAME OVER. " +playerArray.get(winner).getName()+" WINS");
		
		AlertDialog.Builder theDialog = new AlertDialog.Builder(this);
		theDialog.setIcon(R.drawable.dice6);
		theDialog.setTitle("Game Over");
		theDialog.setMessage("Another Round?");
		theDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				newGame(winner);
				
			}

			
			
		});
		theDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				toSetup();
				dialog.dismiss();
			}
			
		});
		AlertDialog alert = theDialog.create();
		alert.show();
		

	}
	
	private void updateCurrentPlayerPanel(){
		
		TextView playerName = (TextView) findViewById(R.id.currentPlayerText);
		TextView playerSixes = (TextView) findViewById(R.id.currentPlayerScore);
		TextView playerBank = (TextView) findViewById(R.id.currentPlayerBank);
		
		playerName.setText(playerArray.get(currentTurn).getName());

		
		playerSixes.setText(""+currentSixes+"/6");
		
		if (showdownRound){
			playerSixes.setText(""+playerArray.get(currentTurn).showdownPoints+"/10");
			//allPlayersPanel.setAdapter(new PlayerListAdapter(this,R.layout.player_panel_entry,playerArray,true,currentTurn));
			listAdapt.notifyDataSetChanged();
			//listAdapt.notifyDataSetInvalidated();
		} else{
			//allPlayersPanel.setAdapter(new PlayerListAdapter(this,R.layout.player_panel_entry,playerArray,false,currentTurn));
			listAdapt.notifyDataSetChanged();
		}
		
		//allPlayersPanel.setSelection(currentTurn);
		//allPlayersPanel.setItemChecked(currentTurn, true);
		
		if (currentBank==0){
			playerBank.setVisibility(TextView.INVISIBLE);
		} else{
			playerBank.setVisibility(TextView.VISIBLE);
			playerBank.setText(""+currentBank+" in the Bank");
		}
		
	}
	
	private void toSetup(){
		Intent startIntent = new Intent(getApplicationContext(),SixesOfflineCreate.class);
		startIntent.putExtra("sean.k.Sixes.SixesOffline.playerNames",playerNames);
		//startIntent.putExtra("sean.k.sixes.screenHeight",screenHeight);
		startActivity(startIntent);
		finish();
	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder theDialog = new AlertDialog.Builder(this);
		theDialog.setMessage("Leave?");
		theDialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();

						toSetup();
					}

				});
		theDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
					}

				});
		AlertDialog alert = theDialog.create();
		alert.show();

	}

}
