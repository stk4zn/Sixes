package sean.k.sixes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.Spinner;

public class SixesOfflineCreate extends Activity {

	ArrayList<Player> playerArray;
	CharSequence playerNames[];
	CreateGamePlayerListAdapter listAdapt;
	int numPlayers;
	int screenWidth;
	int screenHeight;
	

	final int DEFAULT_STARTING_NUMBER_PLAYERS = 3;
	final int MAX_NAME_LENGTH = 12;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sixes_offline_create);
		playerNames=getIntent().getCharSequenceArrayExtra("sean.k.Sixes.SixesOffline.playerNames"); //null if coming from splash
		initialize();
	}

	private void initialize() {
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;
		screenHeight = displaymetrics.heightPixels;

		Spinner numPlayersSpinner;
		ListView playerList;

		Button startButton;
		
		playerArray = new ArrayList<Player>();
		
		if (playerNames==null){
			numPlayers = DEFAULT_STARTING_NUMBER_PLAYERS;
			for (int i = 0; i<numPlayers;i++){
				playerArray.add(new Player("Player " + (i+1)));
			}
		} else{
			numPlayers = playerNames.length;
			for (int i = 0; i<numPlayers;i++){
				playerArray.add(new Player(playerNames[i].toString()));
			}
		}
		
		
		final Integer numberArray[] = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
				11, 12 };

		/*

		*/

		// spinner stuff
		numPlayersSpinner = (Spinner) findViewById(R.id.sixesOfflineCreateNumPlayersSpinner);
		ArrayAdapter<Integer> spinnerAdapt = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, numberArray);
		spinnerAdapt.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		numPlayersSpinner.setAdapter(spinnerAdapt);

		numPlayersSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						numPlayers = numberArray[position];

						if (numPlayers > playerArray.size()) {
							for (int i = playerArray.size(); i < numPlayers; i++) {
								playerArray
										.add(new Player("Player " + (i + 1)));
							}
						} else if (numPlayers < playerArray.size()) {
							for (int i = playerArray.size(); i > numPlayers; i--) {
								playerArray.remove(i - 1);
							}
						}

						listAdapt.notifyDataSetChanged();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		numPlayersSpinner.setSelection(numPlayers-1);
		
		// listview stuff
		playerList = (ListView) findViewById(R.id.sixesOfflineCreatePlayerList);
		LinearLayout.LayoutParams playerListParams = (LayoutParams) playerList.getLayoutParams();
		playerListParams.width= screenWidth/2;
		listAdapt = new CreateGamePlayerListAdapter(this,
				R.layout.player_panel_entry, playerArray);
		playerList.setAdapter(listAdapt);
		playerList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(SixesOfflineCreate.this);

				alert.setTitle("Choose Name");
				
				final int pos = position;

				// Set an EditText view to get user input 
				final EditText input = new EditText(SixesOfflineCreate.this);
				alert.setView(input);
				input.setText(playerArray.get(position).getName());

				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					
				  String name = input.getText().toString();
				  if (name.length()>MAX_NAME_LENGTH){
					  name = name.substring(0,MAX_NAME_LENGTH+1);
				  }
				  playerArray.get(pos).setName(name);
				  
				  listAdapt.notifyDataSetChanged();
				  
				  }
				});

				alert.show();
				
			}
			
		});

		// start Button
		startButton = (Button) findViewById(R.id.sixesOfflineCreateStartButton);
		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String[] playerListNames = new String[playerArray.size()];
				for (int i = 0; i < playerArray.size(); i++) {
					playerListNames[i] = playerArray.get(i).getName();
				}

				Intent startIntent = new Intent(getApplicationContext(),
						SixesOffline.class);
				startIntent.putExtra(
						"sean.k.Sixes.SixesOfflineCreate.playerNames",
						playerListNames);
				startActivity(startIntent);
				finish();

			}

		});

	}

}
