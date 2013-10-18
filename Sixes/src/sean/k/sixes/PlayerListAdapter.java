package sean.k.sixes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class PlayerListAdapter extends ArrayAdapter<Player> {


	List<Player> list;
	//int turn;
	SixesOffline c;
	//boolean showdown;

	public PlayerListAdapter(SixesOffline context, int textViewResourceId, ArrayList<Player> playerList) {
		super(context, textViewResourceId, playerList);
		c = context;
		//turn = theTurn;
		//showdown = showdownRound;
		list = new ArrayList<Player>(playerList);


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// return super.getView(position, convertView, parent);
		LayoutInflater inflater = ((Activity) c).getLayoutInflater();
		View row = inflater.inflate(R.layout.player_panel_entry, parent, false);
		
		//int achNumber = list.get(position);
		
		if (position == c.currentTurn){
			row.setBackgroundColor(Color.BLUE);
		}
		
		ImageView dead = (ImageView) row.findViewById(R.id.player_panel_check);
		TextView name = (TextView) row.findViewById(R.id.player_panel_name);
		TextView showdownScore = (TextView) row.findViewById(R.id.player_panel_score);
	
		if (!list.get(position).alive){
			dead.setImageResource(R.drawable.xmark);
			dead.setVisibility(ImageView.VISIBLE);
		} else {
			dead.setVisibility(ImageView.INVISIBLE);
		}

		name.setText(list.get(position).getName());
		name.setTextColor(Color.WHITE);
		if (c.showdownRound){
		showdownScore.setText(""+list.get(position).showdownPoints);
		} else{
			showdownScore.setVisibility(TextView.INVISIBLE);
		}
		
		ListView parentL = (ListView) parent;
		//parentL.smoothScrollToPosition(c.currentTurn+1);
		//parentL.setSelection(c.currentTurn+1);
		//parentL.setSelection(9);
		

		return row;
	}

}